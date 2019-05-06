package track;

/**
 * the track
 *
 * @author 17895
 */
public class Track {
    private double r;
    private int id;

    // Abstraction function:
    //   AF(r, id) = a track with it's radius and id
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    public Track(int id, double r) {
        this.id = id;
        this.r = r;
    }

    /**
     * get the id of the track
     *
     * @return the track's id
     */
    public int getID() {
        return this.id;
    }

    /**
     * get the radius of the track
     *
     * @return the radius
     */
    public double getR() {
        return this.r;
    }

}