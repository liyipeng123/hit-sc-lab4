package APIs;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Difference {
    private int track = 0;
    private List<Pair<Integer, String>> ele = new ArrayList<>();

    // Abstraction function:
    //   AF(ele) = all the difference
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //   ele is set, but client can't get it

    /**
     * construct a Difference by track difference
     *
     * @param trackdifference the track difference
     */
    Difference(int trackdifference) {
        this.track = trackdifference;
    }

    @Override
    public String toString() {
        String str = "轨道数量差异: " + track + "\n";
        for (int i = 0; i < ele.size(); i++) {
            str += "轨道" + i + "的物体数量差异: " + ele.get(i).getKey() + " 物体差异: " + ele.get(i).getValue() + "\n";
        }
        return str;
    }

    /**
     * add difference to the Difference
     *
     * @param num the id of the track
     * @param dif the physics's difference between the two physics
     */
    public void addTrackDifference(int num, String dif) {
        ele.add(new Pair<>(num, dif));
    }
}
