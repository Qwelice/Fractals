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

    private void paint(){
        if (isVisible && startPoint!=null && currentPoint!=null) {
            g.setXORMode(Color.WHITE);
            g.setColor(Color.BLACK);
            if(startPoint.x < currentPoint.x && startPoint.y < currentPoint.y)
                g.drawRect(startPoint.x, startPoint.y,
                        currentPoint.x - startPoint.x,
                        currentPoint.y - startPoint.y);
            else if(startPoint.x < currentPoint.x && startPoint.y > currentPoint.y)
                g.drawRect(startPoint.x, currentPoint.y,
                        currentPoint.x - startPoint.x,
                        startPoint.y - currentPoint.y);
            else if(startPoint.x > currentPoint.x && startPoint.y > currentPoint.y)
                g.drawRect(currentPoint.x, currentPoint.y,
                        startPoint.x - currentPoint.x,
                        startPoint.y - currentPoint.y);
            else
                g.drawRect(currentPoint.x, startPoint.y,
                        startPoint.x - currentPoint.x,
                        currentPoint.y - startPoint.y);
            g.setPaintMode();
        }
    }
}
