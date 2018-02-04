/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader.data;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesimar
 */
public class Mission {
    
    private final List<LineGeo> listLineGeo = new ArrayList<>();
    private final List<Line3D> listLine3D = new ArrayList<>();
    private final List<PolyGeo> listPolyGeo = new ArrayList<>();
    private final List<Poly3D> listPoly3D = new ArrayList<>();
    private PointGeo pointBase;
    
    public Mission(){
        
    }
    
    public void pointGeoTo3D(){
        specifyBase();
        for (PolyGeo poly : listPolyGeo){
            List<Point3D> listPoint = new ArrayList<>();
            for (int i = 0; i < poly.getNpoints(); i++){
                Point3D point = new Point3D(pointBase, poly.getPointGeo(i));
                listPoint.add(point);
            }
            Poly3D poly3d = new Poly3D(poly.getName(), listPoint);
            listPoly3D.add(poly3d);
        }
        for (LineGeo line : listLineGeo){
            List<Point3D> listPoint = new ArrayList<>();
            for (int i = 0; i < line.getNpoints(); i++){
                Point3D point = new Point3D(pointBase, line.getPointGeo(i));
                listPoint.add(point);
            }
            Line3D line3d = new Line3D(line.getName(), listPoint);
            listLine3D.add(line3d);
        }
    }
    
    public void addLine(LineGeo line){
        listLineGeo.add(line);
    }
    
    public void addFirstLine(LineGeo line){
        listLineGeo.add(0, line);
    }
    
    public void addPoly(PolyGeo poly){
        listPolyGeo.add(poly);
    }
    
    public void addFirstPoly(PolyGeo poly){
        listPolyGeo.add(0, poly);
    }

    public List<LineGeo> getListLine() {
        return listLineGeo;
    }
    
    public List<Line3D> getListLine3D() {
        return listLine3D;
    }

    public List<PolyGeo> getListPoly() {
        return listPolyGeo;
    }   
    
    public List<Poly3D> getListPoly3D() {
        return listPoly3D;
    } 
    
    public int getSizeListPolyGeo() {
        return listPolyGeo.size();
    } 
    
    public int getSizeListLineGeo() {
        return listLineGeo.size();
    }
    
    public PointGeo getPointBase(){
        return pointBase;
    }
    
    public int getSizeObstacle() {
        int size = 0;
        for (PolyGeo p : listPolyGeo){
            if (p.getName().contains("obstacle")){
                size++;
            }
        }
        return size;
    } 
    
    public int getSizeBonus() {
        int size = 0;
        for (PolyGeo p : listPolyGeo){
            if (p.getName().contains("bonus")){
                size++;
            }
        }
        return size;
    } 
    
    public int getSizePenalty() {
        int size = 0;
        for (PolyGeo p : listPolyGeo){
            if (p.getName().contains("penalty")){
                size++;
            }
        }
        return size;
    } 
    
    public int getSizeFeature() {
        int size = 0;
        for (PolyGeo p : listPolyGeo){
            if (p.getName().contains("feature")){
                size++;
            }
        }
        return size;
    }
    
    public int getSizePontoPassagem() {
        int size = 0;
        for (LineGeo p : listLineGeo){
            if (p.getName().contains("ponto_passagem")){
                size++;
            }
        }
        return size;
    }
    
    public int getSizePattern(String pattern) {
        int size = 0;
        for (PolyGeo p : listPolyGeo){
            if (p.getName().contains(pattern)){
                size++;
            }
        }
        return size;
    }     

    public double getHeightFly() {
        double heightFly = 0;
        for (PolyGeo poly : getListPoly()) {
            if (poly.getName().contains("fly")) {
                heightFly = poly.getVetz()[0];
            }
        }
        return heightFly;
    }
    
    public String getPolyVetX(int i){
        String str = "";
        for (int j = 0; j < listPolyGeo.get(i).getNpoints()-1; j++){
            str += listPolyGeo.get(i).getVetx()[j] + ",";
        }
        str += listPolyGeo.get(i).getVetx()[listPolyGeo.get(i).getNpoints()-1];
        return str;
    }
    
    public String getPoly3DVetX(int i){
        String str = "";
        for (int j = 0; j < listPoly3D.get(i).getNpoints()-1; j++){
            str += listPoly3D.get(i).getVetx()[j] + ",";
        }
        str += listPoly3D.get(i).getVetx()[listPoly3D.get(i).getNpoints()-1];
        return str;
    }
    
    public String getPolyVetY(int i){
        String str = "";
        for (int j = 0; j < listPolyGeo.get(i).getNpoints()-1; j++){
            str += listPolyGeo.get(i).getVety()[j] + ",";
        }
        str += listPolyGeo.get(i).getVety()[listPolyGeo.get(i).getNpoints()-1];
        return str;
    }
    
    public String getPoly3DVetY(int i){
        String str = "";
        for (int j = 0; j < listPoly3D.get(i).getNpoints()-1; j++){
            str += listPoly3D.get(i).getVety()[j] + ",";
        }
        str += listPoly3D.get(i).getVety()[listPoly3D.get(i).getNpoints()-1];
        return str;
    }
    
    private void specifyBase(){        
        for (LineGeo line : listLineGeo) {
            if (line.getName().contains("airport")) {
                double lon = line.getCenterX();                
                double lat = line.getCenterY();
                double alt = line.getCenterZ();
                pointBase = new PointGeo(lon, lat, alt);
            }
        }
    }
    
    public String getPontosPassagem(){
        String str = "";
        for (int i = 0; i < listLine3D.size(); i++){
            if (listLine3D.get(i).getName().contains("ponto_passagem")){
                str += listLine3D.get(i).toString(0);
            }
        }
        return str;
    }
}
