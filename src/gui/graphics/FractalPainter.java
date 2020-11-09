package gui.graphics;

import convertation.ConvertPlane;
import convertation.Converter;
import fractal.Fractal;
import gui.graphics.components.Painter;
import math.Complex;

import java.awt.*;
/** Класс, отрисовывающий фрактал, является наследником класса {@link Painter} */
public class FractalPainter extends Painter {
    //поле, которое хранит в себе данные о декартовой плоскости
    private final ConvertPlane plane;

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
     * @param graphics графика, через которую будем рисовать на панели, которая предоставляет эту графику*/
    @Override
    public void draw(Graphics graphics) {
        drawFractal(graphics);
    }

    /** Метод, рисующий фрактал
     * @param graphics графика панели, на которой будем рисовать
     * */
    public void drawFractal(Graphics graphics){
        //пробегаем все пиксели по ширине
        for (int i = 0; i < plane.getWidth(); i++){
            //пробегаем все пиксели по высоте
            for (int j = 0; j < plane.getHeight(); j++){
                //пиксель, с координатами (i, j), переводим в декартовую систему координат
                var x = Converter.xScr2Crt(i, plane);
                var y = Converter.yScr2Crt(j, plane);
                //проверяем точку с координатами (x, y) на принадлежность множеству фрактала
                var is = fractal.isInSet(new Complex(x, y));
                //если точка принадлежит, то закрашиваем пиксель чёрным цветом, иначе белым
                Color c = (is)?Color.BLACK:Color.WHITE;
                //устанавливаем цвет, которым будем закрашивать пиксель
                graphics.setColor(c);
                //закрашиваем пиксель
                graphics.fillRect(i, j, 1, 1);
            }
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
}
