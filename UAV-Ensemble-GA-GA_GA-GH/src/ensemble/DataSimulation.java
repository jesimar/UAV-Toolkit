package ensemble;

/**
 *
 * @author jesimar
 */
public class DataSimulation {
    
    public long generations;
    public long evalTot;
    public long evalBest;
    public double timeTot;
    public double timeBest;
    public double objective;
    public int timeUsed;
    public String landingLocal;
    public String factivel = "false";
    public long timeTotalDecisionTree;
    public String method;
    
    public void printInfo(){
        System.out.println("Generations: " + generations);
        System.out.println("EvalTot: " + evalTot);
        System.out.println("EvalBest: " + evalBest);
        System.out.println("TimeTot: " + timeTot);
        System.out.println("TimeBest: " + timeBest);
        System.out.println("Objective: " + objective);
        System.out.println("TimeUsed: " + timeUsed);
        System.out.println("LandingLocal: " + landingLocal);
        System.out.println("Factivel: " + factivel);
        System.out.println("TimeTotalDecisionTree(ms): " + timeTotalDecisionTree);
        System.out.println("Method: " + method);
    }
        
    public static String printHeader(){
        return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", 
                "generations", "evalTot", "evalBest", "timeTot", "timeBest", 
                "objective", "timeUsed", "landingLocal", "factivel", 
                "timeTotalDecisionTree", "method");
    }
    
    @Override
    public String toString(){
        return String.format("%d\t%d\t%d\t%f\t%f\t%f\t%d\t%s\t%s\t%d\t%s", 
                generations, evalTot, evalBest, timeTot, timeBest, objective, 
                timeUsed, landingLocal, factivel, timeTotalDecisionTree, method);
    }
}
