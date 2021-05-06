package bsu.rfe.java.group7.lab6.kaydalina.var7A;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem pauseFlagItem;
    private JMenuItem resumeFlagItem;
    private Field field = new Field();
    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()
                        && !pauseFlagItem.isEnabled() && !resumeFlagItem.isEnabled()) {
                    pauseMenuItem.setEnabled(true);
                    pauseFlagItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение"){
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
                pauseFlagItem.setEnabled(false);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
                pauseFlagItem.setEnabled(true);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);

        Action pauseFlag = new AbstractAction("Приостановить 1 четверть"){
            public void actionPerformed(ActionEvent event) {
                field.pauseFlag();
                pauseFlagItem.setEnabled(false);
                resumeFlagItem.setEnabled(true);
                pauseMenuItem.setEnabled(false);
            }
        };
        pauseFlagItem = controlMenu.add(pauseFlag);
        pauseFlagItem.setEnabled(false);

        Action resumeFlag = new AbstractAction("Запустить 1 четверть") {
            public void actionPerformed(ActionEvent event) {
                field.resumeFlag();
                pauseFlagItem.setEnabled(true);
                resumeFlagItem.setEnabled(false);
                pauseMenuItem.setEnabled(true);
            }
        };
        resumeFlagItem = controlMenu.add(resumeFlag);
        resumeFlagItem.setEnabled(false);
        getContentPane().add(field, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}