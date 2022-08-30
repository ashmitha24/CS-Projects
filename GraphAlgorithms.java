import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/**
 * Your implementation of various different graph algorithms.
 *
 * @author Ashmitha Julius Aravind
 * @userid aaravind7
 * @GTID 903700995
 * @version 11.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in the graph");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        List<Vertex<T>> list = new ArrayList<>();
        visitedSet.add(start);
        q.add(start);
        list.add(start);
        while (!q.isEmpty()) {
            Vertex<T> curr = q.remove();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            List<VertexDistance<T>> distances = adjList.get(curr);
            for (int i = 0; i < distances.size(); i++) {
                Vertex<T> v = distances.get(i).getVertex();
                if (!visitedSet.contains(v)) {
                    visitedSet.add(v);
                    list.add(v);
                    q.add(v);
                }
            }
        }
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in the graph");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> list = new ArrayList<>();
        visitedSet.add(start);
        list.add(start);
        return dfsHelper(graph, visitedSet, list, start);
    }

    /**
     * Helper method for depth first search.
     * @param graph the graph to search through
     * @param visited the set of visited vertices
     * @param list list of vertices in visited order
     * @param vertex the vertex to go dfs on
     * @param <T> the generic typing of the data
     * @return list of vertices in visited order
     */
    private static <T> List<Vertex<T>> dfsHelper(Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> list,
                                                 Vertex<T> vertex) {
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<VertexDistance<T>> distances = adjList.get(vertex);
        for (int i = 0; i < distances.size(); i++) {
            Vertex<T> v = distances.get(i).getVertex();
            if (!visited.contains(v)) {
                visited.add(v);
                list.add(v);
                dfsHelper(graph, visited, list, v);
            }
        }
        return list;
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in the graph");
        }
        Set<Vertex<T>> vs = new HashSet<>();
        Map<Vertex<T>, Integer> dm = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        for (Vertex<T> v : graph.getVertices()) {
            dm.put(v, Integer.MAX_VALUE);
        }
        VertexDistance<T> temp = new VertexDistance<>(start, 0);
        pq.add(temp);
        while (!pq.isEmpty() && vs.size() != graph.getVertices().size()) {
            VertexDistance<T> temp2 = pq.remove();
            Vertex<T> u = temp2.getVertex();
            int d = temp2.getDistance();
            if (!vs.contains(u)) {
                vs.add(u);
                dm.put(u, d);
                Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
                List<VertexDistance<T>> distances = adjList.get(u);
                for (int i = 0; i < distances.size(); i++) {
                    Vertex<T> v = distances.get(i).getVertex();
                    int distance = d + distances.get(i).getDistance();
                    if (!vs.contains(v)) {
                        VertexDistance<T> temp3 = new VertexDistance<>(v, distance);
                        pq.add(temp3);
                    }
                }
            }
        }
        return dm;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in the graph");
        }
        Set<Vertex<T>> vs = new HashSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<VertexDistance<T>> values = adjList.get(start);
        for (int i = 0; i < values.size(); i++) {
            Edge<T> e = new Edge<>(start, values.get(i).getVertex(), values.get(i).getDistance());
            pq.add(e);
        }
        vs.add(start);
        while (!pq.isEmpty() && vs.size() != graph.getVertices().size()) {
            Edge<T> temp = pq.remove();
            Vertex<T> w = temp.getV();
            if (!vs.contains(w)) {
                vs.add(w);
                mst.add(temp);
                Edge<T> reverse = new Edge(w, temp.getU(), temp.getWeight());
                mst.add(reverse);
                List<VertexDistance<T>> distances = adjList.get(w);
                for (int i = 0; i < distances.size(); i++) {
                    Vertex<T> x = distances.get(i).getVertex();
                    int distance = distances.get(i).getDistance();
                    if (!vs.contains(x)) {
                        Edge<T> e = new Edge<T>(w, x, distance);
                        pq.add(e);
                    }
                }
            }
        }
        if (vs.size() != graph.getVertices().size()) {
            return null;
        }
        return mst;
    }
}
