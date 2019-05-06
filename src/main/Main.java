package main;

import applications.AtomStructure;
import applications.SocialNetworkCircle;
import applications.TrackGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * a main software for run the applications
 */
public class Main extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * initiate the GUI
     */
    public void init() {
        ImageIcon ii = new ImageIcon("img/favicon.ico");
        Image icon = ii.getImage();
        this.setIconImage(icon);
        Container container = getContentPane();
        container.setLayout(null);
        this.setTitle("Application");
        this.setSize(600, 600);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        container.setBackground(Color.CYAN);
        JPanel jp = new MyPanel();
        jp.setLayout(null);
        jp.setBounds(0, 0, 600, 600);
        JButton jb1 = new RBigButton("<html><span color=white><font size='6' face='Comic Sans MS'><strong>TrackGame</strong></font></span><html>");
        JButton jb2 = new RBigButton("<html><span color=white><font size='6' face='Comic Sans MS'><strong>AtomStructure</strong></font></span><html>");
        JButton jb3 = new RBigButton("<html><span color=white><font size='6' face='Comic Sans MS'><strong>SocialNetworkCircle</strong></font></span><html>");
        jb1.setBounds(100, 100, 400, 100);
        jb2.setBounds(100, 250, 400, 100);
        jb3.setBounds(100, 400, 400, 100);
        jb1.addActionListener(e -> {
            new TrackGame().visualize();
            setVisible(false);
        });
        jb2.addActionListener(e -> {
            new AtomStructure().visualize();
            setVisible(false);
        });
        jb3.addActionListener(e -> {
            new SocialNetworkCircle().visualize();
            setVisible(false);
        });
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        container.add(jp);
        this.setVisible(true);
    }

    /**
     * a round and big button
     */
    public class RBigButton extends JButton {
        private static final long serialVersionUID = 39082560987930759L;
        public final Color BUTTON_COLOR2 = new Color(18, 183, 245);
        public final Color BUTTON_COLOR1 = new Color(71, 200, 248);
        public final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
        private boolean hover;

        /**
         * construct a RBigButton
         *
         * @param name
         */
        public RBigButton(String name) {
            this.setText(name);
            setFont(new Font("system", Font.PLAIN, 12));
            setBorderPainted(false);
            setForeground(BUTTON_COLOR2);
            setFocusPainted(false);
            setContentAreaFilled(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setForeground(BUTTON_FOREGROUND_COLOR);
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setForeground(BUTTON_COLOR2);
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            int h = getHeight();
            int w = getWidth();
            float tran = 0.77777F;
            if (!hover) {
                tran = 0.3F;
            }

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint p1;
            GradientPaint p2;
            if (getModel().isPressed()) {
                p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
                        new Color(100, 100, 100));
                p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3,
                        new Color(255, 255, 255, 100));
            } else {
                p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1,
                        new Color(0, 0, 0));
                p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,
                        h - 3, new Color(0, 0, 0, 50));
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    tran));
            RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
                    h - 1, 33, 33);
            Shape clip = g2d.getClip();
            g2d.clip(r2d);
            GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
                    h, BUTTON_COLOR2, true);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.setClip(clip);
            g2d.setPaint(p1);
            g2d.setPaint(p2);
            g2d.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * a panel for the main
     */
    class MyPanel extends JPanel {

        Graphics g;

        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) {
            this.g = g;
            super.paintComponent(g);
            ImageIcon ii = new ImageIcon("img/background.jpg");
            Image img = ii.getImage();
            g.drawImage(img, 0, 0, 600, 600, this);
        }

    }

    public static void main(String[] args) {
        new Main().init();
    }

}

