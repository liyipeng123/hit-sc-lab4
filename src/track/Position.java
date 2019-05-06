package track;

/**
 * the position of the object
 *
 * @author 17895
 */
public class Position {
    private double r = 0;
    private double angle = 0;

    // Abstraction function:
    //   AF(r, angle) = a position's r and angle
    // Representation invariant:
    //   null
    // Safety from rep exposure:
    //   All fields are private;

    /**
     * construct a position by r and angle
     *
     * @param r
     * @param angle
     */
    public Position(double r, double angle) {
        this.r = r;
        this.angle = angle;
    }

    /**
     * sonstruct a position by x and y
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.r = Math.sqrt(x * x + y * y);
        this.angle = Math.atan((double) y / (double) x);
    }

    /**
     * get the radius
     *
     * @return the radius
     */
    public double getR() {
        return this.r;
    }

    /**
     * get the angle
     *
     * @return the angle
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * get the position's x
     *
     * @return the position's x
     */
    public int getX() {
        return (int) Math.round(r * Math.cos(angle));
    }

    /**
     * get the position's y
     *
     * @return the position's y
     */
    public int getY() {
        return (int) Math.round(r * Math.sin(angle));
    }
}
