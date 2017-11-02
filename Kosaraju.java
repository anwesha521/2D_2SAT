package sat;

/**
 * Created by Anwesha Biswas on 1/11/2017.
 */

import java.util.*;

import sat.formula.Literal;

public final class Kosaraju {

    public static Map<Literal, Integer> sCC(DirectedGraph<Literal> g) {

        Stack<Literal> visit = new Stack();
        Set<Literal> visited = new HashSet();


        for (Literal l: g)
            recTraverse(l, g, visit, visited);


        Map<Literal, Integer> result = new HashMap();
        int i = 0;

        while (!visit.isEmpty())
        {

            Literal start = visit.pop();
            if (result.containsKey(start))
                continue;
            markReachableNodes(start, g, result, i);
             i++;
        }

        return result;
    }

    public static <Literal> DirectedGraph<Literal> graphReverse(DirectedGraph<Literal> g) {
        DirectedGraph<Literal> result = new DirectedGraph<Literal>();

        for (Literal l: g)
            result.addNode(l);

        for (Literal node: g)
            for (Literal end: g.edgesFrom(node))
                result.addEdge(end, node);

        return result;
    }


    public static  Stack<Literal> dfs(DirectedGraph<Literal> g) {
        Stack<Literal> result = new Stack();
        Set<Literal> visited = new HashSet();
        for (Literal l: g)
            recTraverse(l, g, result, visited);

        return result;
    }

    private static void recTraverse(Literal node, DirectedGraph<Literal> g,
                                       Stack<Literal> st, Set<Literal> visited) {
        if (visited.contains(node)) return;

        visited.add(node);

        for (Literal l: g.edgesFrom(node))
            recTraverse(l, g, st, visited);


        st.push(node);
    }


    private static <Literal> void markReachableNodes(Literal node, DirectedGraph<Literal> g,
                                               Map<Literal, Integer> result,
                                               int label)
    {
        if (result.containsKey(node)) return;
        result.put(node, label);
        for (Literal end: g.edgesFrom(node))
            markReachableNodes(end, g, result, label);
    }
}