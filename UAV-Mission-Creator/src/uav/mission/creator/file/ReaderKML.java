/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mission.creator.file;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import uav.mission.creator.data.LineGeo;
import uav.mission.creator.data.Mission;
import uav.mission.creator.data.PointGeo;
import uav.mission.creator.data.PolyGeo;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderKML {

    private final Mission mission;
    private final File file;
    
    public ReaderKML(File file, Mission mission){
        this.file = file;
        this.mission = mission;
    }
    
    public void reader() {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(file);
            Element rootNode = document.getRootElement();
            Element elemDoc = (Element)rootNode.getChildren("Document").get(0);
            Element elemFolder = elemDoc.getChild("Folder");
            if (elemFolder != null){
                List list = elemFolder.getChildren();
                for (int i = 0; i < list.size(); i++) {
                    Element node = (Element) list.get(i);
                    if (node.getName().equals("Placemark")){
                        String name = node.getChildText("name");
                        Element elemPoly = node.getChild("Polygon");
                        if (elemPoly != null){                        
                            Element eleLinRing = elemPoly.getChild("outerBoundaryIs").getChild("LinearRing");
                            String coord = eleLinRing.getChildText("coordinates");
                            LinkedList<PointGeo> listPoint = new LinkedList<>();
                            coord = coord.replaceAll(" \n", "");
                            for (String point : coord.split(" ")){                                
                                point = point.replaceAll("\n", "");
                                point = point.replaceAll("\t", "");
                                listPoint.addFirst(new PointGeo(point));
                            }
                            listPoint.removeFirst();
                            PolyGeo poly = new PolyGeo(name, listPoint);
                            mission.addPoly(poly);
                        }
                        Element elemLineString = node.getChild("LineString");
                        if (elemLineString != null){
                            Element elemCoord = elemLineString.getChild("coordinates");
                            String coord = elemCoord.getValue();
                            String str[] = split(coord);
                            double px[] = new double[str.length/3];
                            double py[] = new double[str.length/3];
                            double pz[] = new double[str.length/3];
                            int j = 0;
                            for (int w = 0; w < str.length-1; w+=3){
                                px[j] = Double.parseDouble(str[w]);
                                py[j] = Double.parseDouble(str[w+1]);
                                pz[j] = Double.parseDouble(str[w+2]);
                                j++;
                            }
                            LineGeo line = new LineGeo(name, px, py, pz);
                            mission.addLine(line);
                        }
                        Element elemPoint = node.getChild("Point");
                        if (elemPoint != null){
                            Element elemCoord = elemPoint.getChild("coordinates");
                            String str[] = split(elemCoord.getValue());
                            double lng = Double.parseDouble(str[0]);
                            double lat = Double.parseDouble(str[1]);
                            double alt = Double.parseDouble(str[2]);                            
                            PointGeo point = new PointGeo(name, lng, lat, alt);
                            mission.addPoint(point);
                        }
                    }
                }
            }
            mission.pointGeoTo3D();
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }
    
    private String[] split(String string){
        String stringSplit[] = string.split("[, ]");
        boolean debug = false;
        if (debug){
            String string0[] = stringSplit[0].split("[\n\t]");
            System.out.printf("Split string %d: %s\n", 0, string0[string0.length-1]);
            for (int i = 1; i < stringSplit.length-1; i++){
                System.out.printf("Split string %d: %s\n", i, stringSplit[i]);
            }                
        }
        return stringSplit;
    }
}
