package uav.generic.struct.geom;

/**
 * Classe que implementa um ponto em coordenadas geográficas.
 * @author Jesimar S. Arantes
 */
public class PointGeo extends Point{
    
    private final double lng;
    private final double lat;
    private final double alt;
    
    /**
     * Class constructor.
     * @param lng coordinate longitude
     * @param lat coordinate latitude
     * @param alt coordinate altitude
     */
    public PointGeo(double lng, double lat, double alt) {
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }
    
    /**
     * Class constructor.
     * @param point point with coordinates longitude, latitude, altitude (lng,lat,alt).
     */
    public PointGeo(String point) {
        String v[] = point.split(",");        
        this.lng = Double.parseDouble(v[0]);
        this.lat = Double.parseDouble(v[1]);
        this.alt = Double.parseDouble(v[2]);
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public double getAlt() {
        return alt;
    }
    
    @Override
    public String toString() {
        return "PointGeo{" + "lng=" + lng + ", lat=" + lat + ", alt=" + alt + '}';
    }

    /** 
     * Método que calcula a distância entre duas coordenadas. Cuidado com o 
     * retorno do método que não está em metros mas sim em graus. Dessa forma, 
     * deve-se converter para metros.
     * @param pointDestine - ponto de destino
     * @return distância (em graus) entre o ponto atual e o argumento.
     */
    @Override
    public double distance(Point pointDestine) {
        PointGeo p = (PointGeo) pointDestine;
        return Math.sqrt((p.lng - lng)*(p.lng - lng) + (p.lat - lat)*(p.lat - lat));
    }
        
}
