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
    // Флаг приостановленности движения
    private boolean paused;
    private boolean pausedAngle;
    // Динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);

    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
    // При создании его экземпляра используется анонимный класс,
    // реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            // Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });

    // Конструктор класса BouncingBall
    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }

    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
        // Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        // Последовательно запросить прорисовку от всех мячей из списка
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
    }

    // Метод добавления нового мяча в список
    public void addBall() {
        //Заключается в добавлении в список нового экземпляра BouncingBall
        // Всю инициализацию положения, скорости, размера, цвета
        // BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBall(this));
    }

    // Метод синхронизированный, т.е. только один поток может одновременно быть внутри
    public synchronized void pause() {
        paused = true; //вкл пауза
    }

    public synchronized void pauseAngle(){
        pausedAngle = true; //вкл паузу  мячей, летящих с углом < 90
    }

    // Метод синхронизированный, т.е. только один поток может одновременно быть внутри
    public synchronized void resume() {
        paused = false; //выкл пауза
        pausedAngle = false; //выкл паузы мячей, летящих с углом < 90
        // Будим все ожидающие продолжения потоки
        notifyAll();
    }

    // Синхронизированный метод проверки, может ли мяч двигаться
    public synchronized void canMove(BouncingBall ball) throws
            InterruptedException {
        if (paused) {
            // Если режим паузы включен, то поток, зашедший
            // внутрь данного метода, засыпает
            wait();
        }
        if(pausedAngle && (ball.getAngle() <= (Math.PI/2) && ball.getAngle() >= 0
        )){
            wait();
        }
    }
}