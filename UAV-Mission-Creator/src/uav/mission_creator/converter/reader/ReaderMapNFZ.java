package uav.mission_creator.converter.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import uav.mission_creator.converter.map.Map;
import uav.mission_creator.converter.map.Point2D;
import uav.mission_creator.converter.map.Poly2D;
import uav.mission_creator.converter.map.TypeRegion;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderMapNFZ {
    
    private final Map map;
    private final String path;

    public ReaderMapNFZ(Map map, String pathFile) {
        this.map = map;
        this.path = pathFile;
    }
    
    public void read(){
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                sc.nextLine();
                int amountOfPolygons = Integer.parseInt(sc.nextLine());
                for (int i = 0; i < amountOfPolygons; i++){
                    sc.nextLine();
                    String valueX = sc.nextLine();
                    String valueY = sc.nextLine();
                    String vX[] = valueX.split(",");
                    String vY[] = valueY.split(",");
                    Point2D points[] = new Point2D[vX.length];
                    double vetorX[] = new double[vX.length];
                    for (int j = 0; j < vX.length; j++){
                        vetorX[j] = Double.parseDouble(vX[j]);
                    }
                    double vetorY[] = new double[vY.length];
                    for (int j = 0; j < vY.length; j++){
                        vetorY[j] = Double.parseDouble(vY[j]);
                    }
                    for (int j = 0; j < vX.length; j++){
                        points[j] = new Point2D(vetorX[j], vetorY[j]);
                    }
                    map.addPoly(new Poly2D(""+i, TypeRegion.NFZ_REGION, points));
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            System.out.println("Path: " + path);
            ex.printStackTrace();
        }
    }
    
}
