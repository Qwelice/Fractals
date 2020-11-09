package gui;

import convertation.ConvertPlane;
import fractal.Mandelbrot;
import gui.graphics.FractalPainter;
import gui.graphics.components.GraphicsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyWindow extends JFrame {
    GraphicsPanel mainPanel;

    static final Dimension MIN_SIZE = new Dimension(450, 350);
    static final Dimension MIN_FRAME_SIZE = new Dimension(600, 500);

    public MyWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(MIN_FRAME_SIZE);
        setTitle("Полиномы");

        mainPanel = new GraphicsPanel();

        mainPanel.setBackground(Color.WHITE);

        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, (int)(MIN_SIZE.height*0.8), MIN_SIZE.height, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
        );
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addGroup(gl.createParallelGroup()
                        .addComponent(mainPanel, MIN_SIZE.width, MIN_SIZE.width, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4)
        );
        pack();
        var plane = new ConvertPlane(
                mainPanel.getWidth(),
                mainPanel.getHeight(),
                -2, 1, -1, 1
        );

        var m = new Mandelbrot();
        var fp = new FractalPainter(plane, m);

        mainPanel.addPainter(fp);

        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                plane.setRealWidth(mainPanel.getWidth());
                plane.setRealHeight(mainPanel.getHeight());
            }
        });
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainPanel.repaint();
            }
        });
    }
}
