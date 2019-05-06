package circularOrbit;

import track.Track;

import java.util.Map;
import java.util.Set;

/**
 * @param <L> the type of the middle object
 * @param <E> the type of the orbit object
 * @author 17895
 */
public interface CircularOrbit<L, E> {

    /**
     * @return an instance of CircularOrbit
     */
    static CircularOrbit<?, ?> empty() {
        return null;
    }

    /**
     * take object from now track to t
     *
     * @param object an physical object already exist in tracks
     * @param t      a target track you want to move
     */
    void transit(E object, Track t);

    /**
     * change the object's tangle to sitha
     *
     * @param object an physical object already exist in tracks
     * @param sitha  the tangle you want to change the physical object to
     */
    void move(E object, double sitha);

    /**
     * add a track to the system
     *
     * @param t the track you want to add
     */
    void addTrack(Track t);

    /**
     * remove all tracks
     */
    void removeAllTracks();

    /**
     * remove all Physics
     */
    void removeAllPhysics();

    /**
     * remove the track
     *
     * @param t the track you want to remove
     */
    void removeTrack(Track t);

    /**
     * add a center object to the system
     *
     * @param object the object you want to add to center
     */
    void addCenter(L object);

    /**
     * add a physical object to the system
     *
     * @param object the object you want to add to system(except center)
     */
    void addPhysical(E object);

    /**
     * add a connect between two physical objects
     *
     * @param object1 the first object you want to connect
     * @param object2 the second object you want to connect
     */
    void addConnectPP(E object1, E object2);

    /**
     * add a connect between the center object and physical objects
     *
     * @param object the physical object
     */
    void addConnectCP(E object);

    /**
     * get the connections between two physical objects
     */
    Map<E, Set<E>> getConnectPP();

    /**
     * get the connections between the center object and physical objects
     */
    Set<E> getConnectCP();

    /**
     * configure a system from a file
     *
     * @param filename the file's name
     */
    void Configure(String filename);

    /**
     * get the track
     *
     * @param i the id of the track
     */
    Track getTracks(int i);

    /**
     * @return the size of the tracks
     */
    int getTracksSize();

    /**
     * get the center of the orbit
     *
     * @return the enter of the orbit
     */
    L getCenter();

    /**
     * get the physics in the orbit
     *
     * @return the physics in the orbit
     */
    Set<E> getPhysics();


    /**
     * remove all connect
     */
    void removeAllConnect();

    /**
     * visualize the application
     */
    void visualize();

    /**
     * get the tracks' gap
     *
     * @return
     */
    int getGap();

    /**
     * make a track
     * @param id the track's id
     * @param r the track's radius
     * @return a track
     */
    Track makeTrack(int id, double r);

}