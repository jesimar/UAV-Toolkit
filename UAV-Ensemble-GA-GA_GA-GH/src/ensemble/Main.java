package ensemble;

import java.io.IOException;

/**
 *
 * @author jesimar
 */
public class Main {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        int TIME_WAIT = 2000;
        int ENSEMBLE_SIZE = 2;
        
        GA ga = new GA(TIME_WAIT);
//        DataSimulation dataGA = ga.execEnsembleGA(ENSEMBLE_SIZE);
//        dataGA.printInfo();
        
        GH gh = new GH(TIME_WAIT);
//        DataSimulation dataGH = gh.execSimpleHG();
//        dataGH.printInfo();
        
        EnsembleMethods ensemble = new EnsembleMethods(ga, gh, TIME_WAIT);
        DataSimulation dataEnsemble = ensemble.execEnsembleParallel();
        dataEnsemble.printInfo();
        
        System.exit(0);
    }
    
}
