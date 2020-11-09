package fractal;

import math.Complex;

public class Mandelbrot implements Fractal {

    private int maxIters = 200;
    private double r2 = 4;

    public void setMaxIters(int value){
        maxIters = Math.max(5, value);
    }

    public void setR(int value){
        var r = Math.max(0.1, value);
        r2 = r*r;
    }

    @Override
    public boolean isInSet(Complex c) {
        var z = new Complex();
        for (int i = 0; i<maxIters; i++){
            z.timesAssign(z).plusAssign(c);
            if (z.abs2() > r2) return false;
        }
        return true;

    }
}
