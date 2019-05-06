package APIs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * the GUI API of the system
 *
 * @author 17895
 */
public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    final private int size = 800;
    protected MyPanel mp = new MyPanel();

    // Abstraction function:
    //   AF(mp, size) = the JFrame's panel and the size of the panel
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;


    /**
     * construct a GUI object
     *
     * @param name     the name of the application
     * @param filename the filename of the background's picture
     */
    public GUI(String name, String filename) {
        this.init(name);
        mp.setBackimg(filename);
    }

    /**
     * init the GUI
     */
    public void init(String name) {
        ImageIcon ii = new ImageIcon("img/favicon.ico");
        Image icon = ii.getImage();
        this.setIconImage(icon);
        this.setTitle(name);
        this.setSize(size, size + 100);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        mp.init(this);
        mp.setBounds(0, 0, size, size);
        this.add(mp);
        this.setVisible(false);
    }

    public void visualize() {
        this.setVisible(true);
    }

    /**
     * add a track by radius
     *
     * @param r the radius of the system
     */
    public void addTrack(Integer r) {
        mp.addTrack(r);
    }

    /**
     * add a point by radius and angle
     *
     * @param r     the radius
     * @param angle the angle
     * @param id    the id of the point and it will appear in the center of the
     *              point
     */
    public void addPoint(double r, double angle, int id) {
        int x = (int) (r * Math.cos(angle));
        int y = (int) (r * Math.sin(angle));
        mp.addPoint(x, y, id);
    }

    /**
     * add a relation between id1 and id2
     *
     * @param id1
     * @param id2
     */
    public void addRelation(int id1, int id2) {
        mp.addRelation(id1, id2);
    }

    /**
     * remove all relations
     */
    public void removeAllRelation() {
        mp.removeAllRelation();
    }

    /**
     * remove all tracks
     */
    public void removeAllTracks() {
        mp.removeAllTrack();
    }

    /**
     * remove all points
     */
    public void removeAllPoint() {
        mp.removeAllPoint();
    }

    /**
     * add text to the gui
     *
     * @param text the text you want to add
     */
    public void addText(String text) {
        mp.addText(text);
    }

    /**
     * remove all text on the gui
     */
    public void removeText() {
        mp.removeText();
    }

    /**
     * the class for repaint the panel
     *
     * @author 17895
     */
    class Rethread extends Thread {
        MyPanel mp;

        public Rethread(MyPanel mp) {
            this.mp = mp;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mp.repaint();
            }
        }
    }

    /**
     * the class for create a panel in the gui
     *
     * @author 17895
     */
    class MyPanel extends JPanel {

        GUI gui;
        Graphics g;
        Image img;
        private List<String> content = new ArrayList<>();
        private List<String> relation = new ArrayList<>();
        private List<String> text = new ArrayList<>();

        // Abstraction function:
        //   AF(content, relation, text) = all elements in the gui
        // Representation invariant:
        //   every relations's id will in the content
        // Safety from rep exposure:
        //   All fields are private;
        //   the List will not be open to the client


        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) { //paint the gui

            this.g = g;
            super.paintComponent(g);
            int height = 23;

            g.drawImage(img, 0, 0, size, size, this);
            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).indexOf(",") == -1) {
                    g.setColor(Color.BLACK);
                    int r = Integer.valueOf(content.get(i));

                    g.drawOval(size / 2 - r, size / 2 - r, r * 2, r * 2);
                } else if (content.get(i).split(",").length > 2) {
                    String[] pos = content.get(i).split(",");
                    int r = 18;
                    g.setColor(Color.CYAN);
                    g.fillOval(Integer.valueOf(pos[0]) - r + size / 2,
                            Integer.valueOf(pos[1]) - r + size / 2, r * 2,
                            r * 2);
                    g.setColor(Color.BLACK);
                    g.drawOval(Integer.valueOf(pos[0]) - r + size / 2,
                            Integer.valueOf(pos[1]) - r + size / 2, r * 2,
                            r * 2);
                    g.setColor(Color.BLACK);
                    g.drawString(pos[2], Integer.valueOf(pos[0]) + size / 2 - 4,
                            Integer.valueOf(pos[1]) + size / 2 + 4);
                }
            }

            for (int i = 0; i < relation.size(); i++) {
                String[] line = relation.get(i).split(",");
                g.setColor(Color.BLACK);
                int x1 = Integer.valueOf(line[0]) + size / 2;
                int y1 = Integer.valueOf(line[1]) + size / 2;
                int x2 = Integer.valueOf(line[2]) + size / 2;
                int y2 = Integer.valueOf(line[3]) + size / 2;
                g.drawLine(x1, y1, x2, y2);
            }

            g.setFont(new Font("Courier New", Font.BOLD, 23));
            for (String s : text) {
                g.drawString(s, 0, height);
                height += 23;
            }
        }

        /**
         * init the panel
         *
         * @param gui the panel's gui
         */
        private void init(GUI gui) {
            this.gui = gui;
            Rethread rt = new Rethread(this);
            rt.start();
        }

        /**
         * set back picture
         *
         * @param filename the back picture's filename
         */
        private void setBackimg(String filename) {
            ImageIcon ii = new ImageIcon("img/" + filename);
            img = ii.getImage();
        }

        /**
         * add track to the panel
         *
         * @param r the track's radius
         */
        private void addTrack(Integer r) {
            content.add(r.toString());
        }

        /**
         * add point to the panel
         *
         * @param x  the x
         * @param y  the y
         * @param id the id
         */
        private void addPoint(Integer x, Integer y, int id) {
            content.add(x.toString() + "," + y.toString() + "," + id);
        }

        /**
         * remove all track in the panel
         */
        private void removeAllTrack() {
            for (int i = content.size() - 1; i >= 0; i--) {
                if (content.get(i).indexOf(",") == -1) {
                    content.remove(i);
                }
            }
        }

        /**
         * remove all the point in the panel
         */
        private void removeAllPoint() {
            for (int i = content.size() - 1; i >= 0; i--)
                if (content.get(i).indexOf(",") != -1) {
                    content.remove(i);
                }
        }

        /**
         * add a relation in the panel
         *
         * @param id1 the source's id
         * @param id2 the target's id
         */
        private void addRelation(Integer id1, Integer id2) {    //add a relation to the gui
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            boolean flag1 = false, flag2 = false;

            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).split(",").length > 2) {
                    String[] pos = content.get(i).split(",");
                    if (pos[2].equals(id1.toString())) {
                        x1 = Integer.valueOf(pos[0]);
                        y1 = Integer.valueOf(pos[1]);
                        flag1 = true;
                    }
                    if (pos[2].equals(id2.toString())) {
                        x2 = Integer.valueOf(pos[0]);
                        y2 = Integer.valueOf(pos[1]);
                        flag2 = true;
                    }
                }
            }
            if (flag1 && flag2) {
                relation.add(x1 + "," + y1 + "," + x2 + "," + y2);
            }
        }

        /**
         * remove all the relation in the panel
         */
        private void removeAllRelation() {
            relation.clear();
        }

        /**
         * add text to the panel
         *
         * @param te
         */
        private void addText(String te) {
            text.add(te);
            Collections.sort(text, Comparator.comparing(o -> o.substring(0, 1)));
        }

        /**
         * remove all text to the panel
         */
        private void removeText() {
            text.clear();
        }
    }
}

