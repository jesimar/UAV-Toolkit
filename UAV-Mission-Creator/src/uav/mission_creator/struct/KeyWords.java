package uav.mission_creator.struct;

/**
 * A seguinte lista de comandos eh definida no software Google Earth
 * @author Jesimar S. Arantes
 */
public class KeyWords {
    
    /**
     * Define um ponto de referencia para a transformacao em coordenadas cartesianas
     */
    public static String GEO_BASE = "geo_base";
    
    /**
     * Define a fronteira da missao
     */
    public static String FRONTIER = "frontier";
    
    /**
     * Define um ponto de passagem da aeronave durante a missao
     */
    public static String WAYPOINT = "waypoint";
    
    /**
     * Define um ponto onde devera ocorrer a retirada de uma fotografia 
     */
    public static String PHOTO = "photo";        
    
    /**
     * Define um ponto a partir do qual se iniciara a filmagem da regiao
     */
    public static String VIDEO_BEGIN = "video_begin";
    
    /**
     * Define um ponto de termino da filmagem
     */
    public static String VIDEO_END = "video_end";
    
    /**
     * Define um ponto de termino da filmagem
     */
    public static String POINT_FAILURE = "point_failure";
    
    /**
     * Define uma regiao que representa um obstaculo
     */
    public static String MAP_OBSTACLE = "map_obstacle";
    
    /**
     * Define uma regiao que representa uma area penalizadora
     */
    public static String MAP_PENALTY = "map_penalty";
    
    /**
     * Define uma regiao que representa uma area bonificadora
     */
    public static String MAP_BONUS = "map_bonus";
    
}
