package applications;

import APIs.CircularOrbitHelper;
import APIs.GUI;
import APIs.RButton;
import APIs.RoundBorder;
import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * an atom structure
 *
 * @author 17895
 */
public class AtomStructure extends ConcreteCircularOrbit<Centron, Electron> implements Iterable<Electron> {
    private GUI gui = new AtomGUI("AtomStructure", "Atom.jpg");
    private int id = 1;
    private List<String[]> list;
    private Map<Integer, Integer> information = new HashMap<>();
    private Random ra = new Random();
    private Centron center;
    private String filename = "AtomicStructure.txt";
    private Caretaker mem = new Caretaker();
    //Rethread ghost = new Rethread(this);
    private int gap = 47;

    // Abstraction function:
    //   AF(id, list, information, ra, center, filename, mem, gap) = a AtomStructure made by a file
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //   the list and the information will not be achieved by client

    /**
     * construct a AtomStructure
     */
    public AtomStructure() {
        this.init();
    }

    /**
     * construct a AtomStructure
     *
     * @param filename the file's name
     */
    public AtomStructure(String filename) {
        this.filename = filename;
        this.init();
    }

    @Override
    public void visualize() {
        gui.visualize();
    }

    @Override
    public int getGap() {
        return gap;
    }

    /**
     * initial the AtomStructure
     */
    private void init() {

        int r = 80;
        int id0 = 1;
        list = CircularOrbitHelper.readContent(filename, "ElementName");
        center = new Centron(0, list.get(0)[0]);
        gui.addText("Type: " + list.get(0)[0]);
        list.clear();
        list = CircularOrbitHelper.readContent(filename, "NumberOfTracks");
        //System.out.println(list.size());
        for (int i = 0; i < Integer.valueOf(list.get(0)[0]); i++) {
            Track track = makeTrack(id0, r);
            this.addTrack(track);
            r += gap;
            id0++;
        }
        list = CircularOrbitHelper.readContent(filename, "NumberOfElectron");
        Start();

    }

    @Override
    public void addTrack(Track t) {
        super.addTrack(t);
        gui.addTrack((int) t.getR());
    }

    @Override
    public void addCenter(Centron cen) {
        super.addCenter(cen);
        gui.addPoint(0, 0, 0);
    }

    @Override
    public void removeAllPhysics() {
        super.removeAllPhysics();
        gui.removeAllPoint();
        gui.addPoint(0, 0, 0);
    }

    /**
     * restart the atom struct
     */
    private void Start() {
        //System.out.println("Start!");
        int trackid;
        int num;
        String[] info;
        for (int i = 0; i < list.get(0).length; i++) {
            info = list.get(0)[i].split("/");
            trackid = Integer.valueOf(info[0]);
            num = Integer.valueOf(info[1]);
            information.put(trackid, num);
        }
        id = 1;
        addCenter(center);

        removeAllPhysics();
        Configure();
        mem.addMemento(this.save());
    }

    /**
     * get the Memento's content
     *
     * @return the Memento's content
     */
    private Memento save() {
        return new Memento(information);
    }

    /**
     * restore the change
     */
    void restore() {
        if (mem.getSize() == 0) {

            System.out.println("Empty!");
            return;

        }
        information.clear();
        for (Map.Entry<Integer, Integer> entry : mem.getMemento().getState().entrySet()) {
            information.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Configure the data and gui
     */
    private void Configure() {
        for (Map.Entry<Integer, Integer> entry : information.entrySet()) {
            setPoint(entry.getKey(), entry.getValue());
        }
    }

    /**
     * set the point in the tracks
     *
     * @param trackid
     * @param num
     */
    private void setPoint(int trackid, int num) {
        double gap = Math.PI * 2 / num;
        double angle = 0.0;
        for (int i = 0; i < num; i++) {
            Electron electron = new Electron(id, trackid, angle);
            addPhysical(electron);
            gui.addPoint(getTracks(trackid - 1).getR(), angle, id);
            angle += gap;
            id++;
        }
    }

    /**
     * back to the last step
     */
    public void Back() {
        this.restore();
        id = 1;
        removeAllPhysics();
        Configure();
    }

    /**
     * a random jump
     */
    void Random() {
        id = 1;
        int num1 = ra.nextInt(getTracksSize()) + 1;
        int num2 = ra.nextInt(getTracksSize()) + 1;
        while (num1 == num2) {
            num2 = ra.nextInt(getTracksSize()) + 1;
        }
        if (information.containsKey(num1) && information.containsKey(num2)) {
            Jump(num1, num2);
        }
    }

    /**
     * jump from a track to a track
     *
     * @param id1 the track1's id
     * @param id2 the track2's id
     */
    public void Jump(int id1, int id2) {
        id = 1;
        removeAllPhysics();
        int num1 = information.get(id1) - 1;
        int num2 = information.get(id2) + 1;
        if (num1 >= 0) {
            information.put(id1, num1);
            information.put(id2, num2);
        }
        mem.addMemento(this.save());
        Configure();
    }

    /**
     * get Electron by id
     *
     * @param id0 the Electron's id
     * @return the Electron
     */
    public Electron getElectronByID(int id0) {
        for (Electron ele : physics) {
            if (ele.getID() == id0) {
                return ele;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
    	String str = "";
        for (Map.Entry<Integer, Integer> entry : information.entrySet()) {
            str += "(" + entry.getKey() + "," + entry.getValue() + ")";
        }
    	return str;
    }

    @Override
    public Iterator<Electron> iterator() {
        List<Electron> lis = new ArrayList<>();
        for (Electron ele : physics) {
            lis.add(ele);
        }
        lis.sort(Comparator.comparingInt(PhysicalObject::getTrackid));
        return lis.iterator();
    }

    /**
     * the atom struct's gui
     */
    class AtomGUI extends GUI {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        AtomGUI(String name, String filename) {
            super(name, filename);
            JPanel jp = new JPanel();
            jp.setBackground(Color.WHITE);
            JButton jb1 = new RButton("Start");
            JButton jb2 = new RButton("Back");
            JButton jb3 = new RButton("Random");
            JButton jb4 = new RButton("Jump");
            JTextField jt1 = new JTextField();
            JTextField jt2 = new JTextField();
            JLabel jl1 = new JLabel(CircularOrbitHelper.Render("Track1", "black", 4));
            JLabel jl2 = new JLabel(CircularOrbitHelper.Render("Track2", "black", 4));
            jl1.setBounds(190, 20, 50, 30);
            jl2.setBounds(340, 20, 50, 30);
            jt1.setBounds(240, 20, 80, 30);
            jt2.setBounds(390, 20, 80, 30);
            jt1.setBorder(new RoundBorder());
            jt2.setBorder(new RoundBorder());
            jb1.addActionListener(e -> Start());
            jb2.addActionListener(e -> Back());
            jb3.addActionListener(e -> Random());
            jb4.addActionListener(e -> {
                //System.out.println(information);
                if (CircularOrbitHelper.isDigital(jt1.getText()) && CircularOrbitHelper.isDigital(jt2.getText())) {
                    int num1 = Integer.valueOf(jt1.getText());
                    int num2 = Integer.valueOf(jt2.getText());
                    if (information.containsKey(num1) && information.containsKey(num2)) {
                        if (num1 != num2) {
                            Jump(num1, num2);
                        }
                    }
                }

            });
            jb1.setBounds(-10, 20, 100, 30);
            jb2.setBounds(600, 20, 100, 30);
            jb3.setBounds(710, 20, 100, 30);
            jb4.setBounds(490, 20, 100, 30);
            //add elements to the panel
            jp.add(jb1);
            jp.add(jb2);
            jp.add(jb3);
            jp.add(jb4);
            jp.add(jt1);
            jp.add(jt2);
            jp.add(jl1);
            jp.add(jl2);
            jp.setLayout(null);
            jp.setBounds(0, 800, 800, 100);
            this.add(jp);
        }
    }


}

/**
 * the central object
 */
class Centron extends CentralObject {
    String kind;

    // Abstraction function:
    //   AF(kind) = a Centron which elements is added a kind
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    /**
     * construct a Centron object
     *
     * @param id   the id of the Centron
     * @param kind the Centron's kind
     */
    public Centron(int id, String kind) {
        super(id);
        this.kind = kind;
    }

    /**
     * get the Centron's kind
     *
     * @return
     */
    public String getKind() {
        return this.kind;
    }
}

/**
 * the physical object
 */
class Electron extends PhysicalObject {

    /**
     * construct a Electron object
     *
     * @param id      the Electron's id
     * @param trackid the id of the track which has the Electron
     * @param angle   the angle of the Electron
     */
    public Electron(int id, int trackid, double angle) {
        super(id, trackid, angle);
    }

}

/**
 * the Caretaker
 */
class Caretaker {
    private List<Memento> mementos = new ArrayList<>();

    // Abstraction function:
    //   AF(mementos) = a Caretaker with mementos
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //   the list will not be achieved by client

    /**
     * add Memento to the Caretaker
     *
     * @param m
     */
    public void addMemento(Memento m) {
        mementos.add(m);
    }

    /**
     * get the Caretaker's size
     *
     * @return
     */
    public int getSize() {
        return mementos.size();
    }

    /**
     * get the Caretaker's last memento
     *
     * @return
     */
    public Memento getMemento() {
        return this.mementos.get(mementos.size() - 2);
    }
}

/**
 * the Memento
 */
class Memento {
    private Map<Integer, Integer> state = new HashMap<>();

    // Abstraction function:
    //   AF(state) = a memento with a state
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //   state is map, which is mutable so getState() make
    //   defensive copy

    /**
     * save the state to this
     *
     * @param state the state you want to save
     */
    public Memento(Map<Integer, Integer> state) {
        for (Map.Entry<Integer, Integer> entry : state.entrySet()) {
            this.state.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * to get the state
     *
     * @return the state
     */
    public Map<Integer, Integer> getState() {
        return Collections.unmodifiableMap(this.state);
    }
}


/**
 * some thing funny
 */
class Rethread extends Thread {
    AtomStructure at;

    public Rethread(AtomStructure at) {
        this.at = at;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            at.Random();
        }
    }
}