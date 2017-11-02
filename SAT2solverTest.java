package sat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;


import sat.env.Environment;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;
import sat.formula.Clause;

/**
 * Created by Anwesha Biswas on 2/11/2017.
 */

public class SAT2solverTest {
    public static void main(String args[]) {
        try {
            FileInputStream inp = new FileInputStream("C:/Users/Anwesha Biswas/Documents/sat2test.cnf");

            Scanner input = new Scanner(inp);
            Literal a;

            int noclauses = 0;
            int nolits = 0;
            int countlit;
            int countclause = 0;
            Boolean flag=false;

           /* while (input.hasNext()) {
                String s = input.nextLine();
                if (s.startsWith("c"))
                    continue;

                if (s.startsWith("p")) {
                    String[] n = s.split(" ");
                    noclauses = Integer.parseInt(n[3]);
                    nolits = Integer.parseInt(n[2]);

                    break;

                }
            } */
            //System.out.println(noclauses + " " + nolits);
            ArrayList<Clause> cl = new ArrayList<Clause>();
            ArrayList<Literal> lit = new ArrayList<Literal>();


            while (input.hasNext()) {
                String s = input.next();


                if (s.equalsIgnoreCase("c") || s.equalsIgnoreCase("p") || s.isEmpty()) {
                    s = input.nextLine();
                    continue;
                } else {
                    if (!s.equalsIgnoreCase("0")) {
                        if (s.startsWith("-")) {
                            a = NegLiteral.make(s.substring(1));
                        } else {
                            a = PosLiteral.make(s);
                        }
                        lit.add(a);


                    } else {
                        Literal[] litarr = new Literal[lit.size()];
                       /*for (Literal b : lit) {
                           if (b == null)
                               System.out.println("null");
                           else
                               System.out.println(b.toString());
                       }*/
                        cl.add(makeCl(lit.toArray(litarr)));
                        lit = new ArrayList<Literal>();
                    }

                }

            }
            Map<Literal,Integer> sol=new HashMap<>();
            Clause[] clarr = new Clause[cl.size()];
            Formula f = makeFm(cl.toArray(clarr));

            try {

                System.out.println("SAT solver starts!!!");
                long started = System.nanoTime();
                sol = SAT2Solver.isSatisfiable(f);
                long time = System.nanoTime();
                long timeTaken = time - started;
                System.out.println("Time:" + timeTaken / 1000000.0 + "ms");

            } catch (NullPointerException ex) {
                System.out.println("Unsatisfiable");
                System.exit(0);
            }
              finally {
                     if(sol==null)
                     {
                         System.out.println("Unsatisfiable");

                     }
                     else {
                         System.out.println("Satisfiable");
                         System.out.println(sol);
                     }
                    /*String env = e.toString();
                    System.out.println(env);
                    env = env.substring(13, env.length() - 1);
                    PrintWriter writer = new PrintWriter("2Dout.cnf");
                    String[] frag = env.split(", ");

                    for (String word : frag) {
                        String wrd1 = word.charAt(0) + ":" + word.substring(3);
                        writer.println(wrd1);
                    }

                    writer.close();
                  */



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }

    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
}
