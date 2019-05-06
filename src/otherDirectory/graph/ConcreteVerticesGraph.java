/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package otherDirectory.graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices) = every vertices in the graph which has source and target
    // Representation invariant:
    //   vertices are all vertices of the graph and each vertex can only appear once
    // Safety from rep exposure:
    //   All fields are private
    //   vertices are list but there is no method which can call it so it is safe

    // TODO constructor
    public ConcreteVerticesGraph() {

    }

    // TODO checkRep
    private void checkRep() {
		/*for (int i = 0; i < vertices.size(); i++) {
			assert vertices.indexOf(vertices.get(i)) == i;
		}*/
    }

    @Override
    public boolean add(L vertex) {
        checkRep();
        for (Vertex<L> ver : vertices) {
            if (ver.getVertex().equals(vertex)) {
                return false;
            }
        }
        Vertex<L> ver = new Vertex<>(vertex);
        vertices.add(ver);
        checkRep();
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        checkRep();
        boolean flag = false;
        int oldWeight = 0;
        if (weight == 0) {
            for (Vertex<L> ver : vertices) {
                if (ver.getVertex().equals(source)
                        && ver.getTarget().containsKey(target)) {
                    oldWeight = ver.getTarget().get(target);
                    ver.removeTarget(target);
                    flag = true;
                }
                if (ver.getVertex().equals(target)
                        && ver.getResource().containsKey(source)) {
                    oldWeight = ver.getResource().get(source);
                    ver.removeResource(source);
                    flag = true;
                }
            }
            if (flag) {
                checkRep();
                return oldWeight;
            }
            checkRep();
            return 0;
        } else {
            for (Vertex<L> ver : vertices) {
                if (ver.getVertex().equals(source)) {
                    if (ver.getTarget().containsKey(target)) {
                        oldWeight = ver.getTarget().get(target);
                        ver.putTarget(target, weight);
                        flag = true;
                    } else {
                        ver.putTarget(target, weight);
                    }
                }
                if (ver.getVertex().equals(target)) {
                    if (ver.getResource().containsKey(source)) {
                        oldWeight = ver.getResource().get(source);
                        ver.putResource(source, weight);
                        flag = true;
                    } else {
                        ver.putResource(source, weight);
                    }
                }
            }
            if (flag) {
                checkRep();
                return oldWeight;
            }
            checkRep();
            return 0;
        }
    }

    @Override
    public boolean remove(L vertex) {
        checkRep();
        boolean flag = false;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getVertex().equals(vertex)) {
                vertices.remove(i);
            }
        }
        for (Vertex<L> ver : vertices) {
            if (ver.getTarget().containsKey(vertex)) {
                ver.removeTarget(vertex);
                flag = true;
            }
            if (ver.getResource().containsKey(vertex)) {
                ver.removeResource(vertex);
                flag = true;
            }
        }
        if (flag) {
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    @Override
    public Set<L> vertices() {
        checkRep();
        Set<L> set = new HashSet<>();
        for (Vertex<L> ver : vertices) {
            set.add(ver.getVertex());
        }
        checkRep();
        return set;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        checkRep();
        Map<L, Integer> map = new HashMap<>();
        for (Vertex<L> ver : vertices) {
            if (ver.getVertex().equals(target)) {
                return ver.getResource();
            }
        }
        checkRep();
        return map;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        checkRep();
        Map<L, Integer> map = new HashMap<>();
        for (Vertex<L> ver : vertices) {
            if (ver.getVertex().equals(source)) {
                return ver.getTarget();
            }
        }
        checkRep();
        return map;
    }

    @Override
    public int getDistance(L p1, L p2) {
        int dis = 0;
        if (p1.equals(p2))
            return 0;
        L p;
        Map<L, Integer> visited = new HashMap<>();
        Queue<L> Q = new LinkedList<>();
        for (L per : this.vertices()) {
            visited.put(per, -1);
        }
        visited.put(p1, 0);
        Q.add(p1);
        while (!Q.isEmpty()) {
            p = Q.remove();
            //System.out.println(p);
            dis = visited.get(p) + 1;
            Set<L> set = this.targets(p).keySet();
            for (L per : set) {
                if (visited.get(per) < 0) {
                    visited.put(per, dis);
                    Q.add(per);
                }
            }
        }
        return visited.get(p2);
    }

    @Override
    public void removeEdge(L source, L target) {
        for (Vertex<L> ver : vertices) {
            if (ver.getVertex().equals(source)) {
                if (ver.getTarget().containsKey(target)) {
                    ver.removeTarget(target);
                }
            }
            if (ver.getVertex().equals(target)) {
                if (ver.getResource().containsKey(source)) {
                    ver.removeResource(source);
                }
            }
        }
    }

    // TODO toString()
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < vertices.size(); i++) {
            str += vertices.get(i).toString();
            if (vertices.get(i).toString().length() != 0) {
                str += ",";
            }
        }
        if (str.length() != 0) {
            while (str.lastIndexOf(",") == str.length() - 1) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }
}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 *
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {
    // TODO fields
    private L vertex;
    private Map<L, Integer> resource = new HashMap<>();
    private Map<L, Integer> target = new HashMap<>();
    // Abstraction function:
    //   AF(vertex, resource, target) = a vertex which has sources and targets
    // Representation invariant:
    //   vertex is a vertex which has some sources and targets
    // Safety from rep exposure:
    //   resource and target are map which is mutable so getResource() and
    //   getTarget() make a defensive copy

    // TODO constructor
    public Vertex(L vertex) {
        this.vertex = vertex;
    }

    // TODO checkRep
    private void checkRep() {
        /*for (Map.Entry<L, Integer> entry : resource.entrySet()) {
            assert resource.get(entry.getKey()).equals(entry.getValue());
        }
        for (Map.Entry<L, Integer> entry : target.entrySet()) {
            assert target.get(entry.getKey()).equals(entry.getValue());
        }*/
    }

    // TODO methods

    /**
     * Get the vertex
     *
     * @return the vertex
     */
    public L getVertex() {
        return vertex;
    }

    /**
     * Add a resource of vertex to vertex's resources' set
     *
     * @param resource resource of vertex and it's weight
     */
    public void putResource(L resource, int weight) {
        checkRep();
        this.resource.put(resource, weight);
        checkRep();
    }

    /**
     * Add a target of vertex to vertex's targets' set
     *
     * @param target target of vertex and it's weight
     */
    public void putTarget(L target, int weight) {
        checkRep();
        this.target.put(target, weight);
        checkRep();
    }

    /**
     * Remove a resource of vertex to vertex's resources' set
     *
     * @param resource resource of vertex
     */
    public void removeResource(L resource) {
        checkRep();
        this.resource.remove(resource);
        checkRep();
    }

    /**
     * Remove a target of vertex to vertex's targets' set
     *
     * @param target target of vertex
     */
    public void removeTarget(L target) {
        checkRep();
        this.target.remove(target);
        checkRep();
    }

    /**
     * Get the vertex's resourses' set
     *
     * @return the vertex's Resources' set
     */
    public Map<L, Integer> getResource() {
        checkRep();
        return new HashMap<>(resource);
    }

    /**
     * Get the vertex's targets' set
     *
     * @return the vertex's targets' set
     */
    public Map<L, Integer> getTarget() {
        return new HashMap<>(target);
    }

    // TODO toString()
    @Override
    public String toString() {
        String str = "";
        for (L ta : target.keySet()) {
            str += "(";
            str += this.vertex.toString() + "->" + ta.toString() + ","
                    + target.get(ta);
            str += "),";
        }
        if (str.length() != 0) {
            while (str.lastIndexOf(",") == str.length() - 1) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }
}
