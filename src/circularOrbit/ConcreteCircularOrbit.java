package circularOrbit;

import track.Track;

import java.util.*;

/**
 * the implementation of CircularOrbit
 *
 * @param <L>
 * @param <E>
 * @author 17895
 */
public abstract class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {
    protected L center;
    protected List<Track> tracks = new ArrayList<>();
    protected Set<E> physics = new HashSet<>();
    protected Set<E> cp = new HashSet<>();
    protected Map<E, Set<E>> pp = new HashMap<>();
    protected Map<Track, Set<E>> ptrack = new HashMap<>();
    protected Map<E, Double> pcon = new HashMap<>();

    // Abstraction function:
    //   AF(tracks, physics, cp, pp, ptrack, pcon) = a track orbit with tracks and physics, which has relations and angle
    // Representation invariant:
    //   all relations's content will in the physics
    // Safety from rep exposure:
    //   All fields are protected;
    //   all date may being changed are made defensive copy

    private void checkRep() {
        for (E e : cp) {
            assert physics.contains(e);
        }
        for (Map.Entry<E, Set<E>> entry : pp.entrySet()) {
            assert physics.contains(entry.getKey());
            for (E e : entry.getValue()) {
                assert physics.contains(e);
            }
        }
        for (Map.Entry<Track, Set<E>> entry : ptrack.entrySet()) {
            for (E e : entry.getValue()) {
                assert physics.contains(e);
            }
        }
    }

    @Override
    public void transit(E object, Track t) {
        checkRep();
        for (Set<E> set : ptrack.values()) {
            if (set.contains(object)) {
                set.remove(object);
            }
        }
        Set<E> temp = ptrack.get(t);
        temp.add(object);
        checkRep();
    }

    @Override
    public void move(E object, double sitha) {
        pcon.put(object, sitha);
    }

    @Override
    public void addTrack(Track t) {
        checkRep();
        tracks.add(t);
        ptrack.put(t, new HashSet<>());
        checkRep();
    }

    @Override
    public void removeAllPhysics() {
        physics.clear();
    }

    @Override
    public void removeTrack(Track t) {
        checkRep();
        tracks.remove(t);
        ptrack.remove(t);
        checkRep();
    }

    @Override
    public void addCenter(L object) {
        this.center = object;
    }

    @Override
    public void addPhysical(E object) {
        physics.add(object);
    }

    @Override
    public void addConnectPP(E object1, E object2) {
        checkRep();
        Set<E> set;
        if (pp.get(object1) != null) {
            set = pp.get(object1);
            set.add(object2);
        } else {
            set = new HashSet<>();
            set.add(object2);
        }
        pp.put(object1, set);
        checkRep();
    }

    @Override
    public void addConnectCP(E object) {
        checkRep();
        cp.add(object);
        checkRep();
    }

    @Override
    public Map<E, Set<E>> getConnectPP() {
        return Collections.unmodifiableMap(pp);
    }

    @Override
    public Set<E> getConnectCP() {
        return Collections.unmodifiableSet(cp);
    }

    @Override
    public void Configure(String filename) {
        //should be achieved in the instance
    }

    @Override
    public Track getTracks(int i) {
        return tracks.get(i);
    }

    @Override
    public int getTracksSize() {
        return tracks.size();
    }

    @Override
    public L getCenter() {
        return this.center;
    }

    @Override
    public Set<E> getPhysics() {
        return Collections.unmodifiableSet(physics);
    }

    @Override
    public void removeAllConnect() {
        pp.clear();
        cp.clear();
    }

    @Override
    public void visualize() {
        //will be achieved in the instance
    }

    @Override
    public int getGap() {
        return 0;
    }

    @Override
    public void removeAllTracks() {
        tracks.clear();
    }

    @Override
    public Track makeTrack(int id, double r){
        return new Track(id ,r);
    }

}
