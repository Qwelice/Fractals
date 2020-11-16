package gui.graphics.fractalcolors;

import java.awt.*;

public class ColorScheme implements Colorizer {
    @Override
    public Color getColor(float x) {
        float r, g, b;
        r = (float)Math.abs(Math.sin(-9*(1-x))*Math.cos(3+17*(x)));
        g = (float)Math.abs(Math.sin(12*x)*Math.sin(75+12*(1-x)));
        b = (float)Math.abs(Math.cos(45+35*(1-x)));
        return new Color(r, g, b);
    }
}
