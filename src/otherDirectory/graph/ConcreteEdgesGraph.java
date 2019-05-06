/*
 * Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved. Redistribution of original
 * or derived work requires permission of course staff.
 */
package otherDirectory.graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices, edges) = a graph which has vertices, with edges made by
    //   vertices
    // Representation invariant:
    //   every edge's source and target will in vertices
    // Safety from rep exposure:
    //   All fields are private;
    //   vertices is set and edges is list, which are mutable so vertices() make
    //   defensive copy


    public ConcreteEdgesGraph() {

    }

    @Override
    public boolean add(L vertex) {

        if (vertices.add(vertex)) {
            return true;
        }
        return false;
    }

    @Override
    public int set(L source, L target, int weight) {

        Edge<L> newEdge = new Edge<>(source, target, weight);
        if (weight == 0) {
            for (Edge<L> edge : edges) {
                if (edge.equals(newEdge)) {
                    edges.remove(edge);
                    return edge.getWeight();
                }
            }
            return 0;
        } else {
            for (Edge<L> edge : edges) {
                if (edge.equals(newEdge)) {
                    edges.remove(edge);
                    edges.add(newEdge);

                    return edge.getWeight();
                }
            }
            edges.add(newEdge);
            return 0;
        }
    }

    @Override
    public boolean remove(L vertex) {

        Set<Edge<L>> set = new HashSet<>();
        for (int i = 0; i < edges.size(); i++) {
            set.add(edges.get(i));
        }

        if (vertices.remove(vertex)) {
            for (L ver : vertices) {
                for (Edge<L> edge : set) {
                    Edge<L> edge1 = new Edge<>(ver, vertex, 0);
                    Edge<L> edge2 = new Edge<>(vertex, ver, 0);
                    if (edge1.equals(edge)) {
                        edges.remove(edge);
                    }
                    if (edge2.equals(edge)) {
                        edges.remove(edge);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {

        Map<L, Integer> map = new HashMap<>();
        for (L ver : vertices) {
            for (int i = 0; i < edges.size(); i++) {
                if (new Edge<>(ver, target, 0).equals(edges.get(i))) {
                    map.put(ver, edges.get(i).getWeight());
                }
            }
        }

        return map;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> map = new HashMap<>();
        for (L ver : vertices) {
            for (int i = 0; i < edges.size(); i++) {
                if (edges.get(i).equals(new Edge<>(source, ver, 0))) {
                    map.put(ver, edges.get(i).getWeight());
                }
            }
        }
        return map;
    }

    @Override
    public String toString() {
        String str = "";
        for (Edge<L> e : edges) {
            str += "(";
            str += e.toString();
            str += ")";
            str += ",";
        }
        if (str.contains(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
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
            System.out.println(p);
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
        Edge<L> edge = new Edge<>(source, target, 0);
        edges.remove(edge);
    }
}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 *
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {
    // TODO fields
    private L source;
    private L target;
    private int weight = 0;
    // Abstraction function:
    //   AF(source, target, weight) = a edge with two points which are source and
    //   target, which weight is integer
    // Representation invariant:
    //   source and target are two points of an edge, weight is the edge's weight
    // Safety from rep exposure:
    //   All fields are private;
    //   source, target and weight are all immutable;


    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    /**
     * Get the source of the edge
     *
     * @return the source of the edge
     */
    public L getSource() {
        return source;
    }

    /**
     * Get the target of the edge
     *
     * @return the target of the edge
     */
    public L getTarget() {
        return target;
    }

    /**
     * Get the weight of the edge
     *
     * @return the weight of the edge
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object e) {
        if (e == null) {
            return false;
        }
        Edge<?> edge = (Edge<?>) e;
        if (this.source.equals(edge.getSource())
                && this.target.equals(edge.getTarget())) {
            return true;
        }
        return false;
    }

    // TODO toString()

    @Override
    public String toString() {
        return source.toString() + "->" + target.toString() + "," + weight;
    }

    @Override
    public int hashCode() {
        return source.hashCode() + target.hashCode();
    }
}
