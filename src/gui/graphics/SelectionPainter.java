package gui.graphics;

import java.awt.*;

public class SelectionPainter {
    private boolean isVisible = false;
    private Point startPoint = null;
    private Point currentPoint = null;
    private Graphics g;
    private static int cnt = 0;

    public SelectionPainter(Graphics g){
        this.g = g;
        g.setXORMode(Color.WHITE);
        g.drawRect(-2, -2, 1, 1);
        g.setPaintMode();
    }

    public void setGraphics(Graphics g){
        this.g = g;
    }

    public void setVisible(boolean value){
        if (!value){
            paint();
            currentPoint = null;
            startPoint = null;
        } else {

        }
        isVisible = value;
    }

    public void setStartPoint(Point p){
        startPoint = p;
    }

    public void setCurrentPoint(Point p){
        if (currentPoint!=null) {
            paint();
        }
        cnt++;
        currentPoint = p;
        paint();
    }
    public Point getStartPoint(){
        return startPoint;
    }
    public Point getCurrentPoint(){
        return currentPoint;
    }

    private void paint(){
        if (isVisible && startPoint!=null && currentPoint!=null) {
            g.setXORMode(Color.WHITE);
            g.setColor(Color.BLACK);
            var x = Math.min(startPoint.x, currentPoint.x);
            var y = Math.min(startPoint.y, currentPoint.y);
            var dx = Math.max(startPoint.x, currentPoint.x);
            var dy = Math.max(startPoint.y, currentPoint.y);
            g.drawRect(x, y, dx - x, dy - y);
            g.setPaintMode();
        }
    }
}
