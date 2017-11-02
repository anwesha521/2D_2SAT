package sat;

/**
 * Created by Anwesha Biswas on 1/11/2017.
 */

import java.util.*; // For HashMap, HashSet

public final class DirectedGraph<Literal> implements Iterable<Literal> {

    private final Map<Literal, Set<Literal>> dirGraph = new HashMap<Literal, Set<Literal>>();

    public boolean addNode(Literal node)
    {
        if (dirGraph.containsKey(node))
            return false;

        dirGraph.put(node, new HashSet<Literal>());
        return true;
    }
    public void addEdge(Literal from, Literal to) {

        if (!dirGraph.containsKey(from) || !dirGraph.containsKey(to)) {
            System.out.println("Both nodes must be in the graph.");
            return;
        }
        dirGraph.get(from).add(to);
    }

    public Set<Literal> edgesFrom(Literal node) {

        Set<Literal> arcs = dirGraph.get(node);
        if (arcs == null) {
            System.out.println("Source node does not exist.");
            return null;
        }

        return Collections.unmodifiableSet(arcs);
    }

    public Iterator<Literal> iterator() {
        return dirGraph.keySet().iterator();
    }
}

