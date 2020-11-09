package gui.graphics;

import convertation.ConvertPlane;
import convertation.Converter;
import fractal.Fractal;
import gui.graphics.components.Painter;
import math.Complex;

import java.awt.*;

public class FractalPainter extends Painter {
    private ConvertPlane plane;
    private final Fractal fractal;

    public FractalPainter(ConvertPlane plane, Fractal fractal){
        this.plane = plane;
        this.fractal = fractal;
    }

    @Override
    public void draw(Graphics graphics) {
        drawFractal(graphics);
    }

    public void drawFractal(Graphics graphics){
        for (int i = 0; i < plane.getWidth(); i++){
            for (int j = 0; j < plane.getHeight(); j++){
                var x = Converter.xScr2Crt(i, plane);
                var y = Converter.yScr2Crt(j, plane);
                var is = fractal.isInSet(new Complex(x, y));
                Color c = (is)?Color.BLACK:Color.WHITE;
                graphics.setColor(c);
                graphics.fillRect(i, j, 1, 1);
            }
        }
    }

    @Override
    public void update(int width, int height) {
        plane.setRealWidth(width);
        plane.setRealHeight(height);
    }
}
