package centralObject;

import track.Position;

/**
 * the central object
 *
 * @author 17895
 */
public abstract class CentralObject {
    final private Position position = new Position(0, 0);
    private int id;

    // Abstraction function:
    //   AF(position, id) = a CentralObject with position and id
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;
    //   And the position is immutable;

    public CentralObject(int id) {
        this.id = id;
    }

    /**
     * get the id of the central object
     *
     * @return the id of the object
     */
    public int getID() {
        return this.id;
    }

    /**
     * get the position of the object
     *
     * @return the position of the object
     */
    public Position getPosition() {
        return this.position;
    }
}