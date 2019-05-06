package applications;

import APIs.CircularOrbitHelper;
import APIs.GUI;
import APIs.RButton;
import APIs.RoundBorder;
import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import javafx.util.Pair;
import otherDirectory.graph.ConcreteVerticesGraph;
import otherDirectory.graph.Graph;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * a social network circle
 *
 * @author 17895
 */
public class SocialNetworkCircle extends ConcreteCircularOrbit<Centre, Person> implements Iterable<Person> {
    private SocialGUI gui = new SocialGUI("SocialNetworkCircle", "people.png");
    private int id = 1;
    private List<String[]> list;
    private Map<String, Pair<Integer, Gender>> information = new HashMap<>();
    private Centre center;
    private String filename = "SocialNetworkCircle.txt";
    private Graph<String> newgraph = new ConcreteVerticesGraph<>();    //Initialize data (used during initialization)
    private Graph<String> graph = new ConcreteVerticesGraph<>();   //Edge data source (can be changed for later use)
    private List<Pair<String, String>> relation = new ArrayList<>();    //I'm going to put some edges in there
    private int gap = 97;

    // Abstraction function:
    //   AF(gui, id, list, information, center, filename, newgraph, graph, relation) =
    //   a social network which is made by a file
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //    some data is mutable but the client can's achieve it

    /**
     * construct a SocialNetworkCircle object
     */
    public SocialNetworkCircle() {
        this.init();
    }

    /**
     * construct a SocialNetworkCircle
     *
     * @param filename the file's name
     */
    public SocialNetworkCircle(String filename) {
        this.filename = filename;
        this.init();
    }

    @Override
    public Iterator<Person> iterator() {
        List<Person> lis = new ArrayList<>();
        for (Person per : physics) {
            lis.add(per);
        }
        lis.sort(Comparator.comparingInt(PhysicalObject::getTrackid));
        return lis.iterator();
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
     * initial the social network
     */
    private void init() {
        int i;
        list = CircularOrbitHelper.readContent(filename, "CentralUser");
        graph.add(list.get(0)[0]);  //Add center
        newgraph.add(list.get(0)[0]);   //functions as above
        center = new Centre(0, list.get(0)[0], Integer.valueOf(list.get(0)[1]), Gender.valueOf(list.get(0)[2]));
        list.clear();
        list = CircularOrbitHelper.readContent(filename, "Friend");
        for (i = 0; i < list.size(); i++) { //add people
            Pair<Integer, Gender> pair = new Pair<>(Integer.valueOf(list.get(i)[1]), Gender.valueOf(list.get(i)[2]));
            information.put(list.get(i)[0], pair);
            graph.add(list.get(i)[0]);
            newgraph.add(list.get(i)[0]);
        }
        list.clear();
        list = CircularOrbitHelper.readContent(filename, "SocialTie");

        for (i = 0; i < list.size(); i++) { //Add links (without subtracting)
            graph.set(list.get(i)[0], list.get(i)[1], 1);
            newgraph.set(list.get(i)[0], list.get(i)[1], 1);
            graph.set(list.get(i)[1], list.get(i)[0], 1);
            newgraph.set(list.get(i)[1], list.get(i)[0], 1);
        }
        Start();
    }


    /**
     * set tracks for the Social network and the gui
     *
     * @param graph the Social network's graph
     */
    private void setTracks(Graph<String> graph) {    //Determine and set the number of orbits according to the furthest
        // distance from the inner center point of graph to all the points
        int tracknum = -1;
        for (String cur : graph.vertices()) {
            int distance = graph.getDistance(getCenter().getName(), cur);
            if (distance > tracknum) {
                tracknum = distance;
            }
        }
        removeAllTracks();
        int r = 150;
        int id0 = 1;
        for (int i = 0; i < tracknum; i++) {
            Track track = makeTrack(id0, r);
            this.addTrack(track);
            r += gap;
            id0++;
        }
    }


    /**
     * restart the Social network
     */
    public void Start() {  //初始化
        id = 1;
        addCenter(center);
        setTracks(newgraph);
        removeAllPhysics();
        relation.clear();
        for (int i = 0; i < list.size(); i++) { //Graph to figure out which vertices are inside the edge.
            if (graph.getDistance(getCenter().getName(), list.get(i)[0]) > 0 || graph.getDistance(getCenter().getName(), list.get(i)[1]) > 0) {
                relation.add(new Pair<>(list.get(i)[0], list.get(i)[1]));
                relation.add(new Pair<>(list.get(i)[1], list.get(i)[0]));
            }
        }
        Configure(newgraph);
        Print();
    }

    /**
     * set physical and gui for the social network
     *
     * @param graph the social network's graph
     */
    private void Configure(Graph<String> graph) {

        double angle = Math.PI;
        for (String cur : graph.vertices()) {   //Graph the vertices
            int distance = graph.getDistance(getCenter().getName(), cur);
            if (distance > 0) {
                angle = angle * 1.618;
                int age = information.get(cur).getKey();
                Gender gender = information.get(cur).getValue();
                Person person = new Person(id, distance, angle, cur, age, gender);
                addPhysical(person);
                gui.addPoint(getTracks(distance - 1).getR(), angle, id);
                id++;
            }
        }

        for (int i = 0; i < relation.size(); i++) { //Line by relation
            if (relation.get(i).getKey().equals(getCenter().getName())) {
                addConnectCP(getPersonByName(relation.get(i).getValue()));
                continue;
            }
            if (relation.get(i).getValue().equals(getCenter().getName())) {
                addConnectCP(getPersonByName(relation.get(i).getKey()));
                continue;
            }
            if (physics.contains(getPersonByName(relation.get(i).getKey())) && physics.contains(getPersonByName(relation.get(i).getValue()))) {
                addConnectPP(getPersonByName(relation.get(i).getKey()), getPersonByName(relation.get(i).getValue()));
            }
        }

    }

    /**
     * add a relation to the social network
     *
     * @param id1 should in the physics
     * @param id2 should in the physics
     */
    public void Add(int id1, int id2) { //Add a link to ADT
        if (id1 == 0 || id2 == 0) {
            if (id1 == 0) {
                graph.set(getCenter().getName(), getPersonByID(id2).getName(), 1);
                graph.set(getPersonByID(id2).getName(), getCenter().getName(), 1);
                relation.add(new Pair<>(getCenter().getName(), getPersonByID(id2).getName()));
                relation.add(new Pair<>(getPersonByID(id2).getName(), getCenter().getName()));
            } else {
                graph.set(getPersonByID(id1).getName(), getCenter().getName(), 1);
                graph.set(getCenter().getName(), getPersonByID(id1).getName(), 1);
                relation.add(new Pair<>(getPersonByID(id1).getName(), getCenter().getName()));
                relation.add(new Pair<>(getCenter().getName(), getPersonByID(id1).getName()));
            }
        } else {
            graph.set(getPersonByID(id1).getName(), getPersonByID(id2).getName(), 1);
            graph.set(getPersonByID(id2).getName(), getPersonByID(id1).getName(), 1);
            relation.add(new Pair<>(getPersonByID(id1).getName(), getPersonByID(id2).getName()));
            relation.add(new Pair<>(getPersonByID(id2).getName(), getPersonByID(id1).getName()));
        }
        id = 1;
        removeAllTracks();
        setTracks(graph);
        removeAllPhysics();
        Configure(graph);
        Print();
    }

    /**
     * delete a relation
     *
     * @param id1 should in the physics
     * @param id2 should in the physics
     */
    public void Delete(Integer id1, Integer id2) {  //Delete a connection
        for (int i = relation.size() - 1; i >= 0; i--) {
            if (relation.get(i).getKey().equals(getCenter().getName()) || relation.get(i).getValue().equals(getCenter().getName())) {
                if (id1 == 0 || id2 == 0) {
                    if (id1 == 0) {
                        if (getPersonByName(relation.get(i).getValue()) != null) {
                            if (getPersonByName(relation.get(i).getValue()).getID() == id2) {
                                graph.removeEdge(relation.get(i).getKey(), relation.get(i).getValue());
                                graph.removeEdge(relation.get(i).getValue(), relation.get(i).getKey());
                                relation.remove(new Pair<>(relation.get(i).getValue(), relation.get(i).getKey()));
                            }
                        }
                    } else {
                        if (getPersonByName(relation.get(i).getKey()) != null) {
                            if (getPersonByName(relation.get(i).getKey()).getID() == id1) {
                                graph.removeEdge(relation.get(i).getKey(), relation.get(i).getValue());
                                graph.removeEdge(relation.get(i).getValue(), relation.get(i).getKey());
                                relation.remove(new Pair<>(relation.get(i).getValue(), relation.get(i).getKey()));
                            }
                        }
                    }
                }
            } else {
                if (getPersonByName(relation.get(i).getKey()) != null && getPersonByName(relation.get(i).getValue()) != null)
                    if (getPersonByName(relation.get(i).getKey()).getID() == id1 && getPersonByName(relation.get(i).getValue()).getID() == id2) {
                        graph.removeEdge(relation.get(i).getKey(), relation.get(i).getValue());
                        graph.removeEdge(relation.get(i).getValue(), relation.get(i).getKey());
                        relation.remove(new Pair<>(relation.get(i).getValue(), relation.get(i).getKey()));
                    }
            }
        }

        for (int i = relation.size() - 1; i >= 0; i--) {
            if (relation.get(i).getKey().equals(getCenter().getName()) || relation.get(i).getValue().equals(getCenter().getName())) {
                if (id1 == 0 || id2 == 0) {
                    if (id1 == 0) {
                        if (getPersonByName(relation.get(i).getValue()) != null) {
                            if (getPersonByName(relation.get(i).getValue()).getID() == id2) {
                                relation.remove(new Pair<>(relation.get(i).getKey(), relation.get(i).getValue()));
                            }
                        }
                    } else {
                        if (getPersonByName(relation.get(i).getKey()) != null) {
                            if (getPersonByName(relation.get(i).getKey()).getID() == id1) {
                                relation.remove(new Pair<>(relation.get(i).getKey(), relation.get(i).getValue()));
                            }
                        }
                    }
                }
            } else {
                if (getPersonByName(relation.get(i).getKey()) != null && getPersonByName(relation.get(i).getValue()) != null)
                    if (getPersonByName(relation.get(i).getKey()).getID() == id1 && getPersonByName(relation.get(i).getValue()).getID() == id2) {
                        relation.remove(new Pair<>(relation.get(i).getKey(), relation.get(i).getValue()));
                    }
            }
        }
        id = 1;
        removeAllPhysics();
        setTracks(graph);
        Configure(graph);
        Print();
    }

    /**
     * print the information to the gui
     */
    private void Print() {
        gui.removeText();
        gui.addText(0 + ":\t" + getCenter().getName());
        for (Person per : physics) {
            gui.addText(per.getID() + ":\t" + per.getName());
        }
    }

    /**
     * get the num of the person's knowings
     *
     * @param id0 the people's id
     * @return the num of the person's knowings
     */
    public int Info(int id0) {
        int cnt = 0;
        if (id0 >= 0 && id0 < id) {
            if (id0 == 0) {
                for (String per : graph.vertices()) {
                    if (graph.getDistance(getCenter().getName(), per) > 0) {
                        cnt++;
                    }
                }
            } else {
                for (String per : graph.vertices()) {
                    if (graph.getDistance(getPersonByID(id0).getName(), per) > 0) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    @Override
    public void addTrack(Track t) {
        super.addTrack(t);
        gui.addTrack((int) t.getR());
    }

    @Override
    public void addCenter(Centre cen) {
        super.addCenter(cen);
        gui.addPoint(0, 0, 0);
    }

    @Override
    public void removeAllPhysics() {
        super.removeAllPhysics();
        removeAllConnect();
        gui.removeAllPoint();
        gui.addPoint(0, 0, 0);
    }

    @Override
    public void removeAllTracks() {
        super.removeAllTracks();
        gui.removeAllTracks();
    }

    @Override
    public void addConnectCP(Person person) {
        super.addConnectCP(person);
        gui.addRelation(getCenter().getID(), person.getID());
    }

    @Override
    public void addConnectPP(Person p1, Person p2) {
        super.addConnectPP(p1, p2);
        gui.addRelation(p1.getID(), p2.getID());
    }

    @Override
    public void removeAllConnect() {
        super.removeAllConnect();
        gui.removeAllRelation();
    }

    @Override
    public String toString(){
        return relation.toString();
    }

    /**
     * the gui for the social network
     */
    class SocialGUI extends GUI {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         * construct a SocialGUI object
         *
         * @param name
         * @param filename
         */
        SocialGUI(String name, String filename) {
            super(name, filename);
            JPanel jp = new JPanel();
            jp.setBackground(Color.WHITE);
            JButton jb1 = new RButton("Start");
            JButton jb2 = new RButton("Distance");
            JButton jb3 = new RButton("Add");
            JButton jb4 = new RButton("Delete");
            JButton jb5 = new RButton("Info");
            JTextField jt1 = new JTextField();
            JTextField jt2 = new JTextField();
            JTextField jt3 = new JTextField();
            JLabel jl1 = new JLabel(CircularOrbitHelper.Render("id1", "black", 4));
            JLabel jl2 = new JLabel(CircularOrbitHelper.Render("id2", "black", 4));
            JLabel jl3 = new JLabel(CircularOrbitHelper.Render("id", "black", 4));
            JLabel jl4 = new JLabel(CircularOrbitHelper.Render("Output: null", "black", 4));
            jl1.setBounds(80, 20, 20, 30);
            jl2.setBounds(160, 20, 20, 30);
            jl3.setBounds(536, 20, 20, 30);
            jl4.setBounds(696, 20, 100, 30);
            jt1.setBounds(105, 20, 50, 30);
            jt2.setBounds(185, 20, 50, 30);
            jt3.setBounds(556, 20, 50, 30);
            jt1.setBorder(new RoundBorder());
            jt2.setBorder(new RoundBorder());
            jt3.setBorder(new RoundBorder());
            jb1.addActionListener(e -> Start());
            jb2.addActionListener(e -> {
                if (CircularOrbitHelper.isDigital(jt1.getText()) && CircularOrbitHelper.isDigital(jt2.getText())) {
                    int distance = 0;
                    int id1 = Integer.valueOf(jt1.getText());
                    int id2 = Integer.valueOf(jt2.getText());
                    if (id1 >= 0 && id2 >= 0 && id1 != id2 && id1 < id && id2 < id) { //no unsafely input
                        if (id1 == 0) {
                            distance = graph.getDistance(getCenter().getName(), getPersonByID(id2).getName());
                        } else if (id2 == 0) {
                            distance = graph.getDistance(getPersonByID(id1).getName(), getCenter().getName());
                        } else {
                            distance = graph.getDistance(getPersonByID(id1).getName(), getPersonByID(id2).getName());
                        }
                    }
                    if (distance != -1 && distance != 0) {
                        jl4.setText(CircularOrbitHelper.Render("Output: " + distance + "m", "black", 4));
                    } else {
                        jl4.setText(CircularOrbitHelper.Render("Output: ∞", "black", 4));
                    }

                }
            });
            jb3.addActionListener(e -> {
                if (CircularOrbitHelper.isDigital(jt1.getText()) && CircularOrbitHelper.isDigital(jt2.getText())) {
                    int id1 = Integer.valueOf(jt1.getText());
                    int id2 = Integer.valueOf(jt2.getText());
                    if (id1 >= 0 && id2 >= 0 && id1 != id2 && id1 < id && id2 < id) {
                        Add(id1, id2);
                        Add(id2, id1);
                    }
                }
            });
            jb4.addActionListener(e ->
            {
                if (CircularOrbitHelper.isDigital(jt1.getText()) && CircularOrbitHelper.isDigital(jt2.getText())) {
                    int id1 = Integer.valueOf(jt1.getText());
                    int id2 = Integer.valueOf(jt2.getText());
                    if (id1 >= 0 && id2 >= 0 && id1 != id2 && id1 < id && id2 < id) {
                        Delete(id1, id2);
                        Delete(id2, id1);
                    }
                }
            });
            jb5.addActionListener(e ->
            {
                int cnt = 0;
                if (CircularOrbitHelper.isDigital(jt3.getText())) {
                    int id0 = Integer.valueOf(jt3.getText());
                    cnt = Info(id0);
                    jl4.setText(CircularOrbitHelper.Render("Output: " + cnt + "人", "black", 4));
                }
            });

            jb1.setBounds(-10, 20, 80, 30);
            jb2.setBounds(430, 20, 100, 30);
            jb3.setBounds(246, 20, 80, 30);
            jb4.setBounds(333, 20, 90, 30);
            jb5.setBounds(611, 20, 80, 30);
            jp.add(jb1);
            jp.add(jb2);
            jp.add(jb3);
            jp.add(jb4);
            jp.add(jb5);
            jp.add(jt1);
            jp.add(jt2);
            jp.add(jt3);
            jp.add(jl1);
            jp.add(jl2);
            jp.add(jl3);
            jp.add(jl4);
            jp.setLayout(null);
            jp.setBounds(0, 800, 800, 100);
            this.add(jp);
        }
    }

    /**
     * get the person by name
     *
     * @param name the name you want to get
     * @return the person with the name
     */
    private Person getPersonByName(String name) {
        for (Person per : physics) {
            if (per.getName().equals(name)) {
                return per;
            }
        }
        return null;
    }

    /**
     * get person by id
     *
     * @param id0 the person's id
     * @return the person
     */
    public Person getPersonByID(int id0) {
        for (Person per : physics) {
            if (per.getID() == id0) {
                return per;
            }
        }
        return null;
    }

}

enum Gender {
    F, M
}

class Centre extends CentralObject {
    private String name;
    private int age;
    private Gender gender;

    // Abstraction function:
    //   AF(name, age, gender) = a Centre with it's name age and gender
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    /**
     * construct a Centre object
     *
     * @param id     the Centre's id
     * @param name   the Centre's name
     * @param age    the Centre's age
     * @param gender the Centre's gender
     */
    public Centre(int id, String name, int age, Gender gender) {
        super(id);
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    /**
     * get the Centre's age
     *
     * @return the Centre's age
     */
    public int getAge() {
        return age;
    }

    /**
     * get the Centre's name
     *
     * @return the Centre's name
     */
    public String getName() {
        return name;
    }

    /**
     * get the Centre's gender
     *
     * @return the Centre's gender
     */
    public Gender getGender() {
        return gender;
    }


}


class Person extends PhysicalObject {
    private String name;
    private int age;
    private Gender gender;

    // Abstraction function:
    //   AF(name, age, gender) = a Person with it's name age and gender
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    /**
     * construct a Person object
     *
     * @param id      the Person's id
     * @param trackid the track which has the Person
     * @param angle   the Person's angle
     * @param name    the Person's name
     * @param age     the Person's name
     * @param gender  the Person's gender
     */
    public Person(int id, int trackid, double angle, String name, int age, Gender gender) {
        super(id, trackid, angle);
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    /**
     * get the Person's age
     *
     * @return the Person's age
     */
    public int getAge() {
        return age;
    }

    /**
     * get the Person's name
     *
     * @return the Person's name
     */
    public String getName() {
        return name;
    }


    /**
     * get the Person's gender
     *
     * @return the Person's gender
     */
    public Gender getGender() {
        return gender;
    }
}