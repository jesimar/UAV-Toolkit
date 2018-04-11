package ensemble;

import java.io.IOException;

/**
 *
 * @author jesimar
 */
public class EnsembleMethods {        
    
    private final int TIME_WAIT;
    private final GA ga;
    private final GH gh;
    
    public EnsembleMethods(GA ga, GH gh, int TIME_WAIT){
        this.ga = ga;
        this.gh = gh;
        this.TIME_WAIT = TIME_WAIT;
    }
    
    public DataSimulation execEnsembleParallel() throws IOException, InterruptedException{
        DataSimulation simHG = new DataSimulation();
        simHG.method = "GH";
        gh.exec_gh(simHG);
        
        
        DataSimulation simGA = new DataSimulation();
        simGA.objective = Double.MAX_VALUE;
        simGA.method = "GA";
        ga.exec_ga(simGA);
        
        Thread.sleep(TIME_WAIT);
        
        DataSimulation data = getBestDataSim(simHG, simGA);
            
        return data;
    }
    
    private DataSimulation getBestDataSim(DataSimulation s1, DataSimulation s2){
        if (s1.objective < s2.objective){
            return s1;
        }else{
            return s2;
        }
    }
    
}
