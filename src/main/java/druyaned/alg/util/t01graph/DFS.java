package druyaned.alg.util.t01graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Depth-first search.
 * @author druyaned
 */
public class DFS {
    
    /*
    1 -> 3 -> 1 -+-> 3 -> 3
    |         ^  |
    v         |  v
    1         |  3 -> 3 -> 3
    |         |
    v         |
    3 -> 3 -> 3
    */
    public static void main(String[] args) {
        // Initialize values and edges
        int[] values = { 1, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3};
        int[][] edges = {
            { 0, 1 }, { 0, 5 }, { 1, 2 }, { 2, 3 },
            { 2, 9 }, { 3, 4 }, { 5, 6 }, { 6, 7 },
            { 7, 8 }, { 8, 2 }, { 9, 10 }, { 10, 11 },
        };
        // Print values and edges
        System.out.println("values=" + Arrays.toString(values));
        System.out.println("edges:");
        for (int[] edge : edges)
            System.out.printf("  %d -> %d\n", edge[0], edge[1]);
        int result = new DFS().longestPath(values, edges);
        System.out.println("result=" + result);
    }
    
    private static final int HAS_CYCLE = (int)2e9;
    
    private int n; // amount of vertices
    private int[] values; // values for each vertex
    private int[][] graph; // vertex to descendants
    private int[] visit; // 1 -> in process, 2 -> passed
    private int[] paths; // length of pathes for each vertex
    private final List<String> list = new ArrayList<>(128);//TODO:show
    private final StringBuilder sb = new StringBuilder();//TODO:show
    
    public int longestPath(int[] values, int[][] edges) {
        // Initialization
        n = values.length;
        this.values = values;
        graph = makeGraph(n, edges);
        visit = new int[n];
        paths = new int[n];
        // Depth-first search
        int maxPath = 0;
        for (int i = 0; i < n && maxPath != HAS_CYCLE; i++)
            maxPath = Math.max(maxPath, dfs(i));
        System.out.println("paths:");//TODO:show
        for (int i = 0; i < n; i++)//TODO:show
            System.out.printf("  %d[%02d]=%d\n", values[i], i, paths[i]);//TODO:show
        return maxPath;
    }
    
    private int[][] makeGraph(int n, int[][] edges) {
        final int m = edges.length;
        int[][] g = new int[n][];
        int[] sizes = new int[n];
        for (int i = 0; i < m; i++)
            sizes[edges[i][0]]++;
        for (int i = 0; i < n; i++)
            g[i] = new int[sizes[i]];
        Arrays.fill(sizes, 0);
        for (int i = 0; i < m; i++) {
            int start = edges[i][0];
            g[start][sizes[start]++] = edges[i][1];
        }
        sb.setLength(0);//TODO:show
        sb.append("graph:\n");//TODO:show
        for (int i = 0; i < n; i++) {//TODO:show
            sb.append(String.format("  %d[%02d]={", values[i], i));//TODO:show
            if (g[i].length > 0)//TODO:show
                sb.append(g[i][0]);//TODO:show
            for (int j = 1; j < g[i].length; j++)//TODO:show
                sb.append(", ").append(g[i][j]);//TODO:show
            sb.append("}\n");//TODO:show
        }//TODO:show
        System.out.print(sb.toString());//TODO:show
        return g;
    }
    
    private int dfs(int node) {
        list.add(Integer.toString(node));//TODO:show
        if (visit[node] == 1)//TODO:show
            list.remove(list.size() - 1);//TODO:show
        if (visit[node] == 1)
            return HAS_CYCLE;
        if (visit[node] == 2) {//TODO:show
            printTraverse();//TODO:show
            list.remove(list.size() - 1);//TODO:show
        }//TODO:show
        if (visit[node] == 2)
            return paths[node];
        visit[node] = 1;
        int maxChildPath = 0;
        for (int child : graph[node]) {
            int childPath = dfs(child);
            if (maxChildPath < childPath)
                maxChildPath = childPath;
        }
        visit[node] = 2;
        if (graph[node].length == 0)//TODO:show
            printTraverse();//TODO:show
        //TODO:show
        list.remove(list.size() - 1);//TODO:show
        return paths[node] = maxChildPath + values[node];
    }
    //TODO:show
    private void printTraverse() {//TODO:show
        sb.setLength(0);//TODO:show
        sb.append("traverse={");//TODO:show
        if (!list.isEmpty())//TODO:show
            sb.append(list.get(0));//TODO:show
        for (int i = 1; i < list.size(); i++)//TODO:show
            sb.append(", ").append(list.get(i));//TODO:show
        sb.append("}\n");//TODO:show
        System.out.print(sb.toString());//TODO:show
    }//TODO:show
    
}
