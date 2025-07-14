package druyaned.alg.util.t01graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    
    public static void main(String[] args) {
        int n = 7; // anount of vertices
        int[][] edges = { // edges[i] = { v1, v2, w }
            {0, 6, 7}, {0, 1, 2}, {1, 2, 3}, {1, 3, 3},
            {6, 3, 3}, {3, 5, 1}, {6, 5, 1}, {2, 5, 1},
            {0, 4, 5}, {4, 6, 2}
        };
        System.out.printf("n=%d\n", n);
        System.out.println("edges:");
        for (int[] edge : edges)
            System.out.println("  " + Arrays.toString(edge));
        System.out.println("shortestPath=" + new Dijkstra().shortestPath(n, edges));
    }
    
    public long shortestPath(int n, int[][] edges) {
        Vertex[] graph = initGraph(n, edges);
        System.out.println("graph:");//TODO:show
        for (int i = 0; i < n; i++)//TODO:show
            System.out.printf("  %02d: %s\n", i, graph[i]);//TODO:show
        boolean[] passed = new boolean[n];
        Target[] targets = new Target[n];
        targets[0] = new Target(0, 0L);
        PriorityQueue<Target> heap = new PriorityQueue<>(n + edges.length,
                (t1, t2) -> Long.compare(t1.dist, t2.dist));
        heap.add(targets[0]);
        while (!heap.isEmpty()) {
            Target target = heap.remove();
            int now = target.index;
            if (passed[now])
                continue;
            passed[now] = true;
            Vertex vertex = graph[now];
            for (int i = 0; i < vertex.size; i++) {
                int next = vertex.neighbors[i];
                if (passed[next])
                    continue;
                long dist = target.dist + vertex.weights[i];
                if (targets[next] == null || targets[next].dist > dist) {
                    targets[next] = new Target(next, dist);
                    heap.add(targets[next]);
                }
            }
        }
        System.out.println("targets:");//TODO:show
        for (Target target : targets) {//TODO:show
            System.out.println("  " + target);//TODO:show
        }//TODO:show
        return targets[n - 1].dist;
    }
    
    private Vertex[] initGraph(int n, int[][] edges) {
        int[] sizes = new int[n];
        for (int[] edge : edges) {
            sizes[edge[0]]++;
            sizes[edge[1]]++;
        }
        Vertex[] graph = new Vertex[n];
        for (int i = 0; i < n; i++)
            graph[i] = new Vertex(sizes[i]);
        final int m = edges.length;
        for (int i = 0; i < m; i++) {
            int v1 = edges[i][0];
            int v2 = edges[i][1];
            int w = edges[i][2];
            graph[v1].add(v2, w);
            graph[v2].add(v1, w);
        }
        return graph;
    }
    
    private static class Target {
        private final int index;
        private final long dist;
        private Target(int index, long dist) {
            this.index = index;
            this.dist = dist;
        }
        @Override public String toString() {//TODO:show
            return "Target{index=" + index//TODO:show
                    + ", dist=" + dist//TODO:show
                    + '}';//TODO:show
        }//TODO:show
    }
    
    private static class Vertex {
        private final int[] neighbors;
        private final int[] weights;
        private int size = 0;
        private Vertex(int neighborCount) {
            neighbors = new int[neighborCount];
            weights = new int[neighborCount];
        }
        private void add(int neighbor, int weight) {
            neighbors[size] = neighbor;
            weights[size] = weight;
            size++;
        }
        @Override public String toString() {//TODO:show
            return "Vertex{neighbors=" + Arrays.toString(neighbors)//TODO:show
                    + ", weights=" + Arrays.toString(weights)//TODO:show
                    + ", size=" + size//TODO:show
                    + '}';//TODO:show
        }//TODO:show
    }
    
}
