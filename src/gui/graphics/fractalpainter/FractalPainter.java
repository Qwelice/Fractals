package gui.graphics.fractalpainter;

import convertation.ConvertPlane;
import convertation.Converter;
import fractal.Fractal;
import gui.graphics.components.Painter;
import gui.graphics.fractalcolors.Colorizer;
import math.Complex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Класс, отрисовывающий фрактал, является наследником класса {@link Painter} */
public class FractalPainter extends Painter{
    //поле, которое хранит в себе данные о декартовой плоскости
    private final ConvertPlane plane;
    private ArrayList<Thread> threads = new ArrayList<>();
    private ArrayList<FractalStripPainter> fsPainters = new ArrayList<>();
    public Colorizer col;

    //поле, представляющий фрактал, который мы будем рисовать
    private final Fractal fractal;

    /** Конструктор класса
     * @param plane информация о декартовой плоскости
     * @param fractal фрактал, который мы будем рисовать*/
    public FractalPainter(ConvertPlane plane, Fractal fractal){
        this.plane = plane;
        this.fractal = fractal;
    }

    /** Переопределённый метод класса {@link Painter}, вызывающий метод {@link #drawFractal(Graphics)},
     * который будет рисовать рисовать фрактал: {@link #fractal}
     * @param graphics контекст для рисования*/
    @Override
    public void draw(Graphics graphics) {
        drawFractal(graphics);
    }

    /** Метод, рисующий фрактал
     * @param graphics контекст для рисования
     * */
    public void drawFractal(Graphics graphics){
        var stripCount = Runtime.getRuntime().availableProcessors();
        for(var fp : fsPainters){
            fp.stop();
        }
        fsPainters.clear();
        for(var t : threads){
            try{
                t.join();
            }catch (InterruptedException ex){ }
        }
        threads.clear();
        for(int i = 0; i < stripCount; i ++){
            fsPainters.add(new FractalStripPainter(graphics, i, stripCount));
            threads.add(new Thread(fsPainters.get(i)));
            threads.get(i).start();
        }
        for(var t : threads){
            try{
                t.join();
            }catch (InterruptedException ex){ }
        }
    }

    /** Переопределенный метод класса {@link Painter}, обновляющий данные декартовой плоскости
     * @param width новая ширина плоскости
     * @param height новая высота плоскости*/
    @Override
    public void update(int width, int height) {
        plane.setRealWidth(width);
        plane.setRealHeight(height);
    }

    class FractalStripPainter implements Runnable {

        private Graphics graphics = null;
        private int width;
        private int stripIndex;
        private int stripCount;
        private int begPx, endPx;
        private BufferedImage bImg;
        private boolean stop = false;

        public FractalStripPainter(Graphics graphics, int stripIndex, int stripCount){
            this.graphics = graphics;
            this.stripIndex = stripIndex;
            width = plane.getWidth() / stripCount;
            begPx = stripIndex * width;
            if(stripIndex == stripCount - 1) {
                width += plane.getWidth() % stripCount;
            }
            endPx = begPx + width;
            bImg = new BufferedImage(width, plane.getHeight(), BufferedImage.TYPE_INT_RGB);
        }

        public void stop(){
            stop = true;
        }

        @Override
        public void run() {
            var g  = bImg.getGraphics();
            //пробегаем все пиксели по ширине
            for (int i = begPx; i < endPx; i++){
                //пробегаем все пиксели по высоте
                for (int j = 0; j < plane.getHeight(); j++){
                    if(stop) return;
                    //пиксель, с координатами (i, j), переводим в декартовую систему координат
                    var x = Converter.xScr2Crt(i, plane);
                    var y = Converter.yScr2Crt(j, plane);
                    //проверяем точку с координатами (x, y) на принадлежность множеству фрактала
                    var is = fractal.isInSet(new Complex(x, y));
                    //если точка принадлежит, то закрашиваем пиксель чёрным цветом, иначе белым
                    Color c = (col!=null) ? col.getColor(is) : ((is==1.0F)?Color.BLACK:Color.WHITE);
                    //устанавливаем цвет, которым будем закрашивать пиксель
                    g.setColor(c);
                    //закрашиваем пиксель
                    g.fillRect(i - begPx, j, 1, 1);
                }
            }
            synchronized (graphics){
                graphics.drawImage(bImg, begPx, 0, null);
            }
        }
    }
}
