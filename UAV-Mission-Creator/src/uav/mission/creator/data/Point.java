/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mission.creator.data;

import java.io.Serializable;

/**
 *
 * @author Jesimar S. Arantes
 */
public abstract class Point implements Serializable{
    
    public abstract double distance(Point point);
    
}
