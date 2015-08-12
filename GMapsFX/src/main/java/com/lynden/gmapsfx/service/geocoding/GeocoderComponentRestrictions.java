/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lynden.gmapsfx.service.geocoding;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;

/**
 *
 * @author jlstephens89
 */
public class GeocoderComponentRestrictions extends JavascriptObject {
    
    public GeocoderComponentRestrictions(String administrativeArea, String country,
            String locality, String postalCode, String route){
        super(GMapObjectType.GEOCODER_COMPONENT_RESTRICTIONS, 
                buildJavascriptString(administrativeArea, country,locality,
                        postalCode, route));
    }
    
    public static String buildJavascriptString(String administrativeArea, String country,
            String locality, String postalCode, String route){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if(administrativeArea != null){
            builder.append("administrativeArea: '").append(administrativeArea).append("'");
        }
        builder.append(",");
                if(country != null){
            builder.append("country: '").append(country).append("'");
        }
        builder.append(",");
                if(locality != null){
            builder.append("locality: '").append(locality).append("'");
        }
        builder.append(",");
                if(postalCode != null){
            builder.append("postalCode: '").append(postalCode).append("'");
        }
        builder.append(",");
                if(route != null){
            builder.append("route: '").append(route).append("'");
        }
        builder.append("}");
        System.out.println("COMPONENT " + builder.toString());
        return builder.toString();
    }
    
}
