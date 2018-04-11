package ensemble;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 *
 * @author jesimar
 */
public class GA {
        
    private final int TIME_WAIT;
    private final String EXECUTABLE;
    private final String DIR_EXECUTABLE = "../GA4s/";
    
    public GA(int TIME_WAIT){
        this.TIME_WAIT = TIME_WAIT;
        EXECUTABLE = "java -jar ga4s.jar";
    }
    
    public DataSimulation execEnsembleGA(int ensembleSize)
            throws IOException, InterruptedException{
        long timeInitial = System.currentTimeMillis();
        DataSimulation datasim[] = new DataSimulation[ensembleSize];
        for (int i = 0; i < ensembleSize; i++){
            datasim[i] = new DataSimulation();
            exec_ga(datasim[i]);
        }
        Thread.sleep(TIME_WAIT);
        double bestObj = datasim[0].objective;
        int bestId = 0;
        for (int i = 1; i < ensembleSize; i++){
            if (datasim[i].objective < bestObj){
                bestObj = datasim[i].objective;
                bestId = i;
            }
        }
        long timeFinal = System.currentTimeMillis();
        datasim[bestId].timeTotalDecisionTree = (timeFinal - timeInitial);
        datasim[bestId].method = "GA";
        return datasim[bestId];
    }
    
    public DataSimulation execSimpleGA() throws IOException, InterruptedException{
        long timeInitial = System.currentTimeMillis();
        DataSimulation datasim = new DataSimulation();
        datasim.objective = Double.MAX_VALUE;
        exec_ga(datasim);
        Thread.sleep(TIME_WAIT);
        long timeFinal = System.currentTimeMillis();
        datasim.timeTotalDecisionTree = (timeFinal - timeInitial);
        datasim.method = "GA";
        return datasim;
    }
    
    public void exec_ga(DataSimulation data) throws IOException, InterruptedException{
        boolean print = true;
        File f = new File(DIR_EXECUTABLE);
        final Process comp = Runtime.getRuntime().exec(EXECUTABLE, null, f);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (print) {
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();
//                        System.out.println("str: " + str);
                        if (str.contains("#results$long$generations$")){
                            String s[] = str.split("generations");
                            String v = s[1].substring(1, s[1].length());
                            data.generations = Integer.parseInt(v);
                        } else if (str.contains("#results$long$eval tot$")){
                            String s[] = str.split("eval tot");
                            String v = s[1].substring(1, s[1].length());
                            data.evalTot = Integer.parseInt(v);
                        } else if (str.contains("#results$long$eval best$")){
                            String s[] = str.split("eval best");
                            String v = s[1].substring(1, s[1].length());
                            data.evalBest = Integer.parseInt(v);
                        } else if (str.contains("#results$dbl$time tot$")){
                            String s[] = str.split("time tot");
                            String v = s[1].substring(1, s[1].length());
                            data.timeTot = Double.parseDouble(v);
                        } else if (str.contains("#results$dbl$time best$")){
                            String s[] = str.split("time best");
                            String v = s[1].substring(1, s[1].length());
                            data.timeBest = Double.parseDouble(v);
                        } else if (str.contains("#results$dbl$objective$")){
                            String s[] = str.split("objective");
                            String v = s[1].substring(1, s[1].length());
                            data.objective = Double.parseDouble(v);
                        } else if (str.contains("#results$int$Time Used (K)$")){
                            String s[] = str.split("K");
                            String v = s[1].substring(2, s[1].length());
                            data.timeUsed = Integer.parseInt(v);
                        } else if (str.contains("#results$str$Landing Local$")){
                            String s[] = str.split("Landing Local");
                            String v = s[1].substring(1, s[1].length());
                            data.landingLocal = v;
                        } else if (str.contains("#results$str$Factivel$")){
                            String s[] = str.split("Factivel");
                            String v = s[1].substring(1, s[1].length());
                            data.factivel = v;
                        }
                    }
                }
                sc.close();
            }
        });
    }
    
}
