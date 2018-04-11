package ensemble;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 *
 * @author jesimar
 */
public class GH {   
    
    private final int TIME_WAIT;    
    private final String EXECUTABLE;    
    private final String DIR_EXECUTABLE = "../GH4s/";
    
    public GH(int TIME_WAIT){       
        this.TIME_WAIT = TIME_WAIT;
        EXECUTABLE = "java -jar gh4s.jar";
    }
    
    public DataSimulation execSimpleGH() throws IOException, InterruptedException {
        long timeInitial = System.currentTimeMillis();
        DataSimulation datasim = new DataSimulation();
        exec_gh(datasim);      
        Thread.sleep(TIME_WAIT);
        long timeFinal = System.currentTimeMillis();
        datasim.timeTotalDecisionTree = (timeFinal - timeInitial);
        datasim.method = "GH";
        return datasim;
    }
    
    public void exec_gh(DataSimulation data) throws IOException, InterruptedException{
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
                        if (str.contains("#results$dbl$Time Total$")){
                            String s[] = str.split("Total");
                            String v = s[1].substring(1, s[1].length());
                            data.timeTot = Double.parseDouble(v);
                        } else if (str.contains("#results$dbl$Time to Best$")){
                            String s[] = str.split("Best");
                            String v = s[1].substring(1, s[1].length());
                            data.timeBest = Double.parseDouble(v);
                        } else if (str.contains("#results$dbl$Best Fitness$")){
                            String s[] = str.split("Fitness");
                            String v = s[1].substring(1, s[1].length());
                            data.objective = Double.parseDouble(v);
                        } else if (str.contains("#results$int$Time Used (K)$")){
                            String s[] = str.split("K");
                            String v = s[1].substring(2, s[1].length());
                            data.timeUsed = Integer.parseInt(v);
                        } else if (str.contains("#results$str$Landing Local$")){
                            String s[] = str.split("Local");
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
