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
package com.lynden.gmapsfx.service.directions;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andre
 */
public class DirectionsRequest extends JavascriptObject{
    private static final Logger LOG = LoggerFactory.getLogger(DirectionsRequest.class);

    static boolean opt = true;

    public void setOpt(boolean opt) {
        this.opt = opt;
    }
        
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes, boolean provideRouteAlternatives){
        this(addressOrigin, null, addressDestination, null, travelModes, null, null, provideRouteAlternatives);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes, boolean provideRouteAlternatives){
        this(null, latLongOrigin, null, latLongDestination, travelModes, null, null, provideRouteAlternatives);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions, boolean provideRouteAlternatives){
        this(null, latLongOrigin, null, latLongDestination, travelModes, drivingOpttions, null, provideRouteAlternatives);
    }
    
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions, boolean provideRouteAlternatives){
        this(addressOrigin, null, addressDestination, null, travelModes, drivingOpttions, null, provideRouteAlternatives);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions, DirectionsWaypoint[] waypoints, boolean provideRouteAlternatives){
        this(null, latLongOrigin, null, latLongDestination, travelModes, drivingOpttions, waypoints, provideRouteAlternatives);
    }
    
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions, DirectionsWaypoint[] waypoints, boolean provideRouteAlternatives){
        this(addressOrigin, null, addressDestination, null, travelModes, drivingOpttions, waypoints, provideRouteAlternatives);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes,
            DirectionsWaypoint[] waypoints, boolean provideRouteAlternatives){
        this(null, latLongOrigin, null, latLongDestination, travelModes, null, waypoints, provideRouteAlternatives);
    }
    
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes,
            DirectionsWaypoint[] waypoints, boolean provideRouteAlternatives){
        this(addressOrigin, null, addressDestination, null, travelModes, null, waypoints, provideRouteAlternatives);
    }
    
    public DirectionsRequest(String addressOrigin, LatLong latLongOrigin, 
            String addressDestination, LatLong latLongDestination, 
            TravelModes travelmode, 
            // transit options
            DrivingOptions drivingOptions,
            //unit system
            DirectionsWaypoint[] waypoints, boolean provideRouteAlternatives){
        super(GMapObjectType.DIRECTIONS_REQUESTS, convertToJavascriptString(addressOrigin, latLongOrigin, addressDestination, latLongDestination, travelmode, drivingOptions, waypoints, provideRouteAlternatives));
    }
    
    private static String convertToJavascriptString(String addressOrigin, LatLong latLongOrigin, 
            String addressDestination, LatLong latLongDestination, 
            TravelModes travelmode, 
            // transit options
            DrivingOptions drivingOptions,
            //unit system
            DirectionsWaypoint[] waypoints, boolean provideRouteAlternatives){
        
        
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        boolean something = false;
        if (addressOrigin != null && latLongOrigin == null) {
            something = true;
            builder.append("origin: '").append(addressOrigin).append("'");
        }
        if (addressOrigin == null && latLongOrigin != null) {
            something = true;
            builder.append("origin: ").append(latLongOrigin.getVariableName());
        }
        if (addressDestination != null && latLongDestination == null) {
            builder.append(something ? "," : "");
            builder.append("destination: '").append(addressDestination).append("'");
        }
        if (addressDestination == null && latLongDestination != null) {
            builder.append(something ? "," : "");
            builder.append("destination: ").append(latLongDestination.getVariableName());
        }
        if (travelmode != null) {
            builder.append(something ? "," : "");
            builder.append("travelMode: ").append("google.maps.TravelMode.").append(travelmode.toString());
            something = true;
        }
        if (drivingOptions != null) {
            builder.append(something ? "," : "");
            builder.append("drivingOptions: ").append(drivingOptions.getVariableName());
            something = true;
        }
        if (provideRouteAlternatives) {
            builder.append(something ? "," : "");
            builder.append("provideRouteAlternatives: true");
            something = true;
        }
        if(waypoints != null){
            builder.append(something ? "," : "");
            builder.append("optimizeWaypoints: ");
            builder.append(opt);
            builder.append(",");
            builder.append("waypoints: [");
            for(DirectionsWaypoint w : waypoints){
                something = true;
                builder.append(w.getVariableName());
                builder.append(something ? "," : "");
            }
            builder.append("]");
        }
        builder.append("}");
        LOG.trace("REQUEST " + builder.toString());
        return builder.toString();
    }

    @Override
    public String toString() {
        return "REQUEST: \n" 
                + "origin: " + getJSObject().getMember("origin").toString() + "\n"
                + "destination: " + getJSObject().getMember("destination").toString() + "\n"
                + "travelMode: " + getJSObject().getMember("travelMode").toString() + "\n"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
