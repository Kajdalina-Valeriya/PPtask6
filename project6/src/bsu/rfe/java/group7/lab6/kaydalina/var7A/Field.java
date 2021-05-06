package bsu.rfe.java.group7.lab6.kaydalina.var7A;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel {
    private boolean paused;
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            repaint();
        }
    });
    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball: balls) {
            ball.paint(canvas);
        }
    }
    public void addBall() {
        balls.add(new BouncingBall(this));
       /* Thread thisThread = new BouncingBall(this);
        thisThread.start();
        balls.add((BouncingBall) thisThread);*/
    }
    public synchronized void pause() {
        paused = true;
    }
    public synchronized void resume() {
        paused = false;
        notifyAll();
    }
    public synchronized void pauseFlag() {
        for (BouncingBall ball:balls) {
            if(ball.getSpeedX() > 0 && ball.getSpeedY() < 0){
                ball.setFlag(true);
            }
        }
    }
    public synchronized void resumeFlag() {
        for (BouncingBall ball:balls) {
            if(ball.getFlag()){
                ball.setFlag(false);
            }
        }
        notifyAll();
    }
    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        if (paused) {
            wait();
        }
        if(ball.getFlag()){
            wait();
        }
    }

}