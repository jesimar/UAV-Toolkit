package uav.mission_creator.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Converter {
    
    public static final boolean IS_PRINT = false;

    public static String convertToJSON(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
    
    public static void convertToJSON(Object obj, String nameFile) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        File file = new File(nameFile);
        try {
            PrintStream print = new PrintStream(file);
            print.println(json);
            print.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (IS_PRINT){
            System.out.println(json);
        }
    }

    public static String convertToJSONinPrettyFormat(Object obj) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gsonPretty.toJson(json);
        return prettyJson;
    }
    
    public static void convertToJSONinPrettyFormat(Object obj, String nameFile) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gsonPretty.toJson(json);
        File file = new File(nameFile);
        try {
            PrintStream print = new PrintStream(file);
            print.println(prettyJson);
            print.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (IS_PRINT){
            System.out.println(prettyJson);
        }
    }
    
    public static String convertToXML(Object obj) {
        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(obj);
        return xml;
    }

    public static void convertToXML(Object obj, String nameFile) {
        XStream xstream = new XStream(new StaxDriver());
//        xstream.alias("map", Map.class);
//        xstream.alias("poly2D", Poly2D.class);
//        xstream.alias("point2D", Point2D.class);
        String xml = xstream.toXML(obj);
        File file = new File(nameFile);
        try {
            PrintStream print = new PrintStream(file);
            print.println(xml);
            print.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (IS_PRINT){
            System.out.println(xml);
        }
    }
    
    public static void convertToXMLinPrettyFormat(Object obj, String nameFile){
        XStream xstream = new XStream(new StaxDriver());
//        xstream.alias("map", Map.class);
//        xstream.alias("poly2D", Poly2D.class);
//        xstream.alias("point2D", Point2D.class);
        try {
            PrintStream print = new PrintStream(new File(nameFile));
            BufferedOutputStream output = new BufferedOutputStream(print);//System.out);
            xstream.marshal(obj, new PrettyPrintWriter(new OutputStreamWriter(output)));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (IS_PRINT){
            BufferedOutputStream output = new BufferedOutputStream(System.out);
            xstream.marshal(obj, new PrettyPrintWriter(new OutputStreamWriter(output)));
        }
    }

}
