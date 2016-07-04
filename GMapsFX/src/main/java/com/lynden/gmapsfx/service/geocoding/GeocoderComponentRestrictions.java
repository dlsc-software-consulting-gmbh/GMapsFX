/*
 * Copyright 2015 Andre.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lynden.gmapsfx.service.geocoding;

/**
 *
 * @author Andre
 */
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
