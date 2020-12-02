package gui.graphics.transformation;

import convertation.ConvertPlane;
import convertation.Converter;

import java.awt.*;
import java.util.Stack;

public class Transformation {
    private Stack<AreaState> track = new Stack<>();

    private ConvertPlane plane = null;

    public Transformation(ConvertPlane plane){
        this.plane = plane;
    }

    public void addNewState(Point p1, Point p2){
        if(p1 != null && p2 != null){
            var x1 = Converter.xScr2Crt(p1.x, plane);
            var y1 = Converter.yScr2Crt(p1.y, plane);
            var x2 = Converter.xScr2Crt(p2.x, plane);
            var y2 = Converter.yScr2Crt(p2.y, plane);
            track.push(new AreaState(x1, y1, x2, y2));
        }
    }

    public void executeLastState(){
        if(!track.empty()){
            var t = track.pop();
            plane.setXMax(Math.max(t.getX1(), t.getX2()));
            plane.setXMin(Math.min(t.getX1(),t.getX2()));
            plane.setYMax(Math.max(t.getY1(), t.getY2()));
            plane.setYMin(Math.min(t.getY1(), t.getY2()));
        }
    }
}
