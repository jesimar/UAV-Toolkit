package uav.gcs.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class ReaderMap {
    
    private final String path;
    
    private double vetorX_NFZ[][];
    private double vetorY_NFZ[][];
    private double vetorX_Penalty[][];
    private double vetorY_Penalty[][];
    private double vetorX_Bonus[][];
    private double vetorY_Bonus[][];
    
    private int sizeTotal;
    private int sizeNFZ;
    private int sizePenalty;
    private int sizeBonus;

    public ReaderMap(String pathFile) {
        path = pathFile;
    }
    
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
                
                vetorX_NFZ = new double[sizeNFZ][];
                vetorY_NFZ = new double[sizeNFZ][];
                
                for (int i = 0; i < sizeNFZ; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    vetorX_NFZ[i] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetorX_NFZ[i][j] = Double.parseDouble(vX[j]);
                    }
                    String vY[] = valueY.split(",");
                    vetorY_NFZ[i] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetorY_NFZ[i][j] = Double.parseDouble(vY[j]);
                    }
                }
                
                vetorX_Penalty = new double[sizePenalty][];
                vetorY_Penalty = new double[sizePenalty][];
                
                for (int i = 0; i < sizePenalty; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    vetorX_Penalty[i] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetorX_Penalty[i][j] = Double.parseDouble(vX[j]);
                    }
                    String vY[] = valueY.split(",");
                    vetorY_Penalty[i] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetorY_Penalty[i][j] = Double.parseDouble(vY[j]);
                    }
                }
                
                vetorX_Bonus = new double[sizeBonus][];
                vetorY_Bonus = new double[sizeBonus][];
                
                for (int i = 0; i < sizeBonus; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    vetorX_Bonus[i] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetorX_Bonus[i][j] = Double.parseDouble(vX[j]);
                    }
                    String vY[] = valueY.split(",");
                    vetorY_Bonus[i] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetorY_Bonus[i][j] = Double.parseDouble(vY[j]);
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

    public double[] getVetorX_NFZ(int i) {
        return vetorX_NFZ[i];
    }

    public double[] getVetorY_NFZ(int i) {
        return vetorY_NFZ[i];
    }

    public double[] getVetorX_Penalty(int i) {
        return vetorX_Penalty[i];
    }

    public double[] getVetorY_Penalty(int i) {
        return vetorY_Penalty[i];
    }

    public double[] getVetorX_Bonus(int i) {
        return vetorX_Bonus[i];
    }

    public double[] getVetorY_Bonus(int i) {
        return vetorY_Bonus[i];
    }

    public double[][] getVetorX_NFZ() {
        return vetorX_NFZ;
    }

    public double[][] getVetorY_NFZ() {
        return vetorY_NFZ;
    }

    public double[][] getVetorX_Penalty() {
        return vetorX_Penalty;
    }

    public double[][] getVetorY_Penalty() {
        return vetorY_Penalty;
    }

    public double[][] getVetorX_Bonus() {
        return vetorX_Bonus;
    }

    public double[][] getVetorY_Bonus() {
        return vetorY_Bonus;
    }
    
}
