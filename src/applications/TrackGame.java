package applications;

import APIs.CircularOrbitHelper;
import APIs.GUI;
import APIs.RButton;
import APIs.RoundBorder;
import circularOrbit.ConcreteCircularOrbit;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * a track game
 *
 * @author 17895
 */
public class TrackGame extends ConcreteCircularOrbit<Integer, Athlete> implements Iterable<Athlete> {
    private TrackGameGUI gui = new TrackGameGUI("TrackGame", "green.jpg");
    private List<String[]> list;
    private List<Integer> seq = new ArrayList<>();
    private List<Integer> mem = new ArrayList<>();
    //private Random ra = new Random();
    private int group = 1;
    private Map<Integer, Double> mark = new HashMap<>();    //存入排名数据以便于后面的排序
    private String filename = "TrackGame.txt";
    int gap = 42;

    // Abstraction function:
    //   AF(gui, list, seq, group, mark, filename, mem) = a track game which has br made by the file
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //   Some date is mutable but it is not be achieved by client

    /**
     * To construct a object
     */
    public TrackGame() {
        this.init();
    }

    /**
     * construct a TrackGame
     *
     * @param filename the file's name
     */
    public TrackGame(String filename) {
        this.filename = filename;
        this.init();
    }

    @Override
    public int getGap() {
        return gap;
    }

    /**
     * inin the track game
     */
    private void init() {
        int r = 80;
        int id = 1;
        list = CircularOrbitHelper.readContent(filename, "Athlete");
        for (int k = 0; k < list.size(); k++) {
            mark.put(Integer.valueOf(list.get(k)[1]), Double.valueOf(list.get(k)[4]));
        }
        for (int i = 0; i < 8; i++) {
            Track track = makeTrack(id, r);
            this.addTrack(track);
            r += gap;
            id++;
        }
        this.Order();
    }

    @Override
    public void visualize() {
        gui.visualize();
    }

    @Override
    public void addTrack(Track t) {
        super.addTrack(t);
        gui.addTrack((int) t.getR());
    }

    @Override
    public void removeAllPhysics() {
        super.removeAllPhysics();
        gui.removeAllPoint();
    }

    /**
     * sort the athlete in random
     */
    public void Random() {
        //System.out.println("random!");
        group = 1;

        seq.clear();
        mem.clear();
        for (int i = 1; i <= list.size(); i++) {
            seq.add(i);
        }
        Collections.shuffle(seq);

        removeAllPhysics();
        Configure();

    }

    /**
     * sort the athlete in order
     */
    public void Order() {
        group = 1;
        removeAllPhysics();
        seq.clear();
        mem.clear();
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(mark.entrySet());

        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        for (Map.Entry<Integer, Double> temp : list) {
            seq.add(temp.getKey());
            mem.add(temp.getKey());
        }
        Configure();
    }

    /**
     * configure the track game
     */
    private void Configure() {
        gui.removeText();
        if (seq.size() > getTracksSize()) {
            for (int i = 0; i < getTracksSize(); i++) {
                double angle = /*ra.nextDouble() * 2 * */Math.PI;
                gui.addPoint(getTracks(i).getR(), angle, seq.get(getTracksSize() - i - 1));
                String name = Information(seq.get(i))[0];
                String nation = Information(seq.get(i))[2];
                gui.addText(i + "L:\t" + "id" + seq.get(i) + " \t" + name);
                int age = Integer.valueOf(Information(seq.get(i))[3]);
                double score = Double.valueOf(Information(seq.get(i))[4]);
                Athlete ath = new Athlete(seq.get(i), getTracksSize() - i - 1, angle, name, nation, age, score);
                this.addPhysical(ath);
            }
        } else {
            for (int i = 0; i < seq.size(); i++) {
                double angle = /*ra.nextDouble() * 2 * */Math.PI;
                gui.addPoint(getTracks(seq.size() - i - 1).getR(), angle, seq.get(i));
                String name = Information(seq.get(i))[0];
                String nation = Information(seq.get(i))[2];
                gui.addText(i + "L:\t" + "id" + seq.get(i) + " \t" + name);
                int age = Integer.valueOf(Information(seq.get(i))[3]);
                double score = Double.valueOf(Information(seq.get(i))[4]);
                Athlete ath = new Athlete(seq.get(i), seq.size() - i - 1, angle, name, nation, age, score);
                this.addPhysical(ath);
            }
        }
    }

    /**
     * delete all the physics in the game
     */
    private void Delete() {
        if (seq.size() > getTracksSize()) {
            for (int i = getTracksSize() - 1; i >= 0; i--) {
                seq.remove(i);
            }
        } else {
            seq.clear();
        }
    }

    /**
     * the next game group
     */
    private void Next() {
        if (seq.size() == 0) {
            gui.removeAllPoint();
            return;
        }
        gui.removeAllPoint();
        group++;
        Delete();
        Configure();
    }

    /**
     * to change the track for the game
     *
     * @param id1 should in the physics
     * @param id2 shoule in the physics
     */
    public void Change(int id1, int id2) {
        removeAllPhysics();
        mem.clear();
        if (seq.contains(id1) && seq.contains(id2)) {
            int index1 = seq.indexOf(id1);
            int index2 = seq.indexOf(id2);
            if (index1 > index2) {
                int temp = index1;
                index1 = index2;
                index2 = temp;
            }
            seq.add(index1, seq.get(index2));
            seq.add(index2 + 1, seq.get(index1 + 1));
            seq.remove(index1 + 1);
            seq.remove(index2 + 1);
        }
        for(int i = 0; i <seq.size(); i++){
            mem.add(seq.get(i));
        }
        Configure();
    }

    /**
     * get the information with the id
     *
     * @param id the person's id
     * @return the information for the person
     */
    private String[] Information(int id) {
        for (int i = 0; i < list.size(); i++) {
            if (Integer.valueOf(list.get(i)[1]) == id) {
                return list.get(i);
            }
        }
        return new String[5];
    }

    /**
     * get Athlete by id
     *
     * @param id0 the person's id
     * @return the Athlete
     */
    public Athlete getAthleteByID(int id0) {
        for (Athlete ath : physics) {
            if (ath.getID() == id0) {
                return ath;
            }
        }
        return null;
    }

    @Override
    public Iterator<Athlete> iterator() {
        List<Athlete> lis = new ArrayList<>();
        for (Athlete ath : physics) {
            lis.add(ath);
        }
        lis.sort(Comparator.comparingInt(PhysicalObject::getTrackid));
        return lis.iterator();
    }

    @Override
    public String toString() {
        return mem.toString();
    }

    /**
     * Create a GUI
     *
     * @author 17895
     */
    class TrackGameGUI extends GUI {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        TrackGameGUI(String name, String filename) {
            super(name, filename);
            JPanel jp = new JPanel();
            jp.setBackground(Color.WHITE);
            JButton jb1 = new RButton("Random");
            JButton jb2 = new RButton("Order");
            JButton jb3 = new RButton("Next");
            JButton jb4 = new RButton("Change");
            JTextField jt1 = new JTextField();
            JTextField jt2 = new JTextField();
            JLabel jl1 = new JLabel(CircularOrbitHelper.Render("id1", "black", 4));
            JLabel jl2 = new JLabel(CircularOrbitHelper.Render("id2", "black", 4));
            JLabel jgroup = new JLabel(CircularOrbitHelper.Render("Group: 0", "black", 4));
            jgroup.setBounds(620, 20, 80, 30);
            jl1.setBounds(275, 20, 20, 30);
            jl2.setBounds(375, 20, 20, 30);
            jt1.setBounds(300, 20, 50, 30);
            jt2.setBounds(400, 20, 50, 30);
            jt1.setBorder(new RoundBorder());
            jt2.setBorder(new RoundBorder());
            jb1.addActionListener(e -> {
                        Random();
                        jgroup.setText(CircularOrbitHelper.Render("Group: 1", "black", 4));
                    }
            );
            jb2.addActionListener(e -> {
                        Order();
                        jgroup.setText(CircularOrbitHelper.Render("Group: 1", "black", 4));
                    }
            );
            jb3.addActionListener(e -> {
                        Next();
                        jgroup.setText(CircularOrbitHelper.Render("Group: " + group, "black", 4));
                    }
            );
            jb4.addActionListener(e -> {
                        String s1 = jt1.getText();
                        String s2 = jt2.getText();
                        if (CircularOrbitHelper.isDigital(s1) && CircularOrbitHelper.isDigital(s2)) {
                            Change(Integer.valueOf(s1), Integer.valueOf(s2));
                        }
                    }
            );
            jb1.setBounds(-10, 20, 100, 30);
            jb2.setBounds(120, 20, 100, 30);
            jb3.setBounds(710, 20, 100, 30);
            jb4.setBounds(500, 20, 100, 30);
            //add panel to the panel
            jp.add(jb1);
            jp.add(jb2);
            jp.add(jb3);
            jp.add(jb4);
            jp.add(jt1);
            jp.add(jt2);
            jp.add(jl1);
            jp.add(jl2);
            jp.add(jgroup);
            jp.setLayout(null);
            jp.setBounds(0, 800, 800, 100);
            this.add(jp);
        }
    }
}

/**
 * The athlete in the track
 *
 * @author 17895
 */
class Athlete extends PhysicalObject {

    private String name;
    private String nation;
    private int age;
    private double score;

    // Abstraction function:
    //   AF(name, nation, age, score) = a Athlete with it's name, nation, age, score;
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    /**
     * Construct a athlete
     *
     * @param id      the id of the athlete
     * @param trackid the id of the track which the athlete run with
     * @param angle   the athlete begin’s angle
     * @param name    the athlete’s name
     * @param nation  the athlete’s name
     * @param age     the athlete’s name
     * @param score   the athlete’s best score
     */
    public Athlete(int id, int trackid, double angle, String name, String nation, int age, double score) {
        super(id, trackid, angle);
        this.name = name;
        this.nation = nation;
        this.age = age;
        this.score = score;
    }

    /**
     * get the athlete’s name
     *
     * @return the athlete’s name
     */
    public String getName() {
        return name;
    }

    /**
     * get the athlete’s nation
     *
     * @return the athlete’s nation
     */
    public String getNation() {
        return nation;
    }

    /**
     * get the athlete’s age
     *
     * @return the athlete’s age
     */
    public int getAge() {
        return age;
    }

    /**
     * get the athlete’s score
     *
     * @return the athlete’s score
     */
    public double getScore() {
        return score;
    }
}

