/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.gcs;

import jsc.distributions.Normal;

/**
 *
 * @author jesimar
 */
public class Main {
    
    public static void main(String[] args) {
        double uncertainty = 0.20;// config.getUncerte;
        Normal normal = new Normal(0.0, 2.0);
        double delta = 0.05;//Double.parseDouble(config.getDeltaPlannerCCQSP4m());
        double radius = normal.inverseCdf(1.0 - delta/2);
        System.out.println("radius: " + radius);
    }
    
}
