package uav.mission_creator.struct;

/**
 * The following list of commands is defined in the Google Earth software.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class KeyWords {
    
    /**
     * Define um ponto de referencia para a transformacao em coordenadas cartesianas
     */
    public static String GEO_BASE = "geo_base";
    
    /**
     * Define um ponto de passagem da aeronave durante a missao
     */
    public static String WAYPOINT = "waypoint";
    
    /**
     * Define um ponto de falha na aeronave
     */
    public static String POINT_FAILURE = "point_failure";
    
    /**
     * Comandos que definem comandos a serem executados sao iniciados com keyword "cmd".
     */
    
    /**
     * Define um ponto onde devera ocorrer a retirada de uma fotografia 
     */
    public static String CMD_PICTURE = "cmd_picture";        
    
    /**
     * Define um ponto a partir do qual se iniciara a filmagem da regiao
     */
    public static String CMD_VIDEO = "cmd_video";
    
    /**
     * Define um ponto a partir do qual se iniciara um conjunto de fotos da regiao.
     */
    public static String CMD_PHOTO_IN_SEQUENCE = "cmd_photo_seq";
    
    /**
     * Define um ponto a partir do qual se iniciara a pulverizacao da regiao
     */
    public static String CMD_SPRAYING_BEGIN = "cmd_spraying_begin";
    
    /**
     * Define um ponto de termino da pulverizacao
     */
    public static String CMD_SPRAYING_END = "cmd_spraying_end";
    
    /**
     * Comandos que definem regioes em um mapa sao iniciados com keyword "map".
     */
    
    /**
     * Define uma regiao que representa um obstaculo
     */
    public static String MAP_OBSTACLE = "map_obstacle";
    
    /**
     * Define uma regiao que representa um obstaculo, mais especificamente um objeto
     */
    public static String MAP_OBSTACLE_OBJ = "map_obstacle_obj";
    
    /**
     * Define uma regiao que representa uma area penalizadora
     */
    public static String MAP_PENALTY = "map_penalty";
    
    /**
     * Define uma regiao que representa uma area bonificadora
     */
    public static String MAP_BONUS = "map_bonus";
    
    /**
     * Define a fronteira da missao
     */
    public static String MAP_FRONTIER = "map_frontier";
    
}
