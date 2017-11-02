package sat;

/**
 * Created by Anwesha Biswas on 1/11/2017.
 */

import java.text.Normalizer;
import java.util.*; // For List, Set
import java.util.List;

import immutable.ImList;
import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Literal;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.PosLiteral;


public final class SAT2Solver {

    public static  Map<Literal, Integer> isSatisfiable(Formula formula) {

        ImList<Clause> clauses=formula.getClauses();
        System.out.println(clauses);
        Set<Literal> lits = new HashSet<>();
        for (Clause clause: clauses) {
            lits.add(clause.chooseLiteral());
           lits.add(clause.chooseSecond());
        }
        System.out.println(lits);

        DirectedGraph<Literal> iGraph = new DirectedGraph<Literal>();
        for (Literal l: lits) {
            iGraph.addNode(l);
            iGraph.addNode(l.getNegation());
        }
        for (Clause clause: clauses) {

            Literal a = clause.chooseLiteral();
            Literal na=a.getNegation();
            Literal b=clause.chooseSecond();
            Literal nb=b.getNegation();
            System.out.println(a+" "+na+" "+b+" "+nb);
            iGraph.addEdge(na, b);
            iGraph.addEdge(nb, a);
        }

        Map<Literal, Integer> scc = Kosaraju.sCC(iGraph);
        System.out.println("map="+scc);
        Stack<Literal> topsort=Kosaraju.dfs(Kosaraju.graphReverse(iGraph));
        Map<Literal, Integer> res=new HashMap<>();

        for (Literal l: lits)
        {   System.out.println("scc.get:"+scc.get(l));
            System.out.println("scc.getnegation:"+scc.get(l.getNegation()));

            if (scc.get(l).equals(scc.get(l.getNegation())))
            {
                System.out.println("reached");
                return null;
            }
            else
            {
               continue;

            }

        }
        for(Literal l1: topsort)
        {
            if(l1 instanceof PosLiteral)
                res.put(l1,1);
            else
                res.put(l1,0);

        }
       return res;
    }


}

