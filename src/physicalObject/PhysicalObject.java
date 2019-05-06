package physicalObject;

/**
 * the PhysicalObject of tracks
 *
 * @author 17895
 */
public abstract class PhysicalObject {
    private int id;
    private int trackid;
    private double angle;

    // Abstraction function:
    //   AF(id, trackid, angle) = a physical object with it's id, trackid and angle
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    /**
     * Construct a PhysicalObject
     *
     * @param id      the object's id
     * @param trackid the id of the track which the PhysicalObject with
     * @param angle   the PhysicalObject begin's angle
     */
    public PhysicalObject(int id, int trackid, double angle) {
        this.id = id;
        this.trackid = trackid;
        this.angle = angle;
    }

    /**
     * get the PhysicalObject's id
     *
     * @return the PhysicalObject's id
     */
    public int getID() {
        return id;
    }

    /**
     * get the track's id
     *
     * @return the track's id
     */
    public int getTrackid() {
        return trackid;
    }

    /**
     * get the angle of the PhysicalObject
     *
     * @return the angle of the PhysicalObject
     */
    public double getAngle() {
        return angle;
    }

}