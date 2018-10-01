package uav.gcs.struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The class that reads the map file
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class ReaderMap {
    
    private final String path;
    
    private double[][] vetX_NFZ;
    private double[][] vetY_NFZ;
    private double[][] vetX_Penalty;
    private double[][] vetY_Penalty;
    private double[][] vetX_Bonus;
    private double[][] vetY_Bonus;
    
    private int sizeTotal;
    private int sizeNFZ;
    private int sizePenalty;
    private int sizeBonus;

    /**
     * Class constructor.
     * @param pathFile the path of map
     * @since version 4.0.0
     */
    public ReaderMap(String pathFile) {
        path = pathFile;
    }
    
    /**
     * Method that reads the map file.
     * @since version 4.0.0
     */
    public void read(){
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {                
                sc.nextLine();
                sizeTotal = Integer.parseInt(sc.nextLine());
                sc.nextLine();
                sizeNFZ = Integer.parseInt(sc.nextLine());
                sc.nextLine();
                sizePenalty = Integer.parseInt(sc.nextLine());
                sc.nextLine();
                sizeBonus = Integer.parseInt(sc.nextLine());
                
                vetX_NFZ = new double[sizeNFZ][];
                vetY_NFZ = new double[sizeNFZ][];
                
                for (int i = 0; i < sizeNFZ; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    vetX_NFZ[i] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetX_NFZ[i][j] = Double.parseDouble(vX[j]);
                    }
                    String vY[] = valueY.split(",");
                    vetY_NFZ[i] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetY_NFZ[i][j] = Double.parseDouble(vY[j]);
                    }
                }
                
                vetX_Penalty = new double[sizePenalty][];
                vetY_Penalty = new double[sizePenalty][];
                
                for (int i = 0; i < sizePenalty; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    vetX_Penalty[i] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetX_Penalty[i][j] = Double.parseDouble(vX[j]);
                    }
                    String vY[] = valueY.split(",");
                    vetY_Penalty[i] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetY_Penalty[i][j] = Double.parseDouble(vY[j]);
                    }
                }
                
                vetX_Bonus = new double[sizeBonus][];
                vetY_Bonus = new double[sizeBonus][];
                
                for (int i = 0; i < sizeBonus; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    vetX_Bonus[i] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetX_Bonus[i][j] = Double.parseDouble(vX[j]);
                    }
                    String vY[] = valueY.split(",");
                    vetY_Bonus[i] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetY_Bonus[i][j] = Double.parseDouble(vY[j]);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public int getSizeTotal() {
        return sizeTotal;
    }

    public int getSizeNFZ() {
        return sizeNFZ;
    }

    public int getSizePenalty() {
        return sizePenalty;
    }

    public int getSizeBonus() {
        return sizeBonus;
    }

    public double[] getVetX_NFZ(int i) {
        return vetX_NFZ[i];
    }

    public double[] getVetY_NFZ(int i) {
        return vetY_NFZ[i];
    }

    public double[] getVetX_Penalty(int i) {
        return vetX_Penalty[i];
    }

    public double[] getVetY_Penalty(int i) {
        return vetY_Penalty[i];
    }

    public double[] getVetX_Bonus(int i) {
        return vetX_Bonus[i];
    }

    public double[] getVetY_Bonus(int i) {
        return vetY_Bonus[i];
    }

    public double[][] getVetX_NFZ() {
        return vetX_NFZ;
    }

    public double[][] getVetY_NFZ() {
        return vetY_NFZ;
    }

    public double[][] getVetX_Penalty() {
        return vetX_Penalty;
    }

    public double[][] getVetY_Penalty() {
        return vetY_Penalty;
    }

    public double[][] getVetX_Bonus() {
        return vetX_Bonus;
    }

    public double[][] getVetY_Bonus() {
        return vetY_Bonus;
    }
    
}
