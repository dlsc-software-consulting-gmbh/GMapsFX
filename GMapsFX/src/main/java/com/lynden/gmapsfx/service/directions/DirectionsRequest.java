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

/**
 *
 * @author Andre
 */
public class DirectionsRequest extends JavascriptObject{
    static boolean opt = true;

    public void setOpt(boolean opt) {
        this.opt = opt;
    }
        
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes){
        this(addressOrigin, null, addressDestination, null, travelModes, null, null);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes){
        this(null, latLongOrigin, null, latLongDestination, travelModes, null, null);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions){
        this(null, latLongOrigin, null, latLongDestination, travelModes, drivingOpttions, null);
    }
    
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions){
        this(addressOrigin, null, addressDestination, null, travelModes, drivingOpttions, null);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions, DirectionsWaypoint[] waypoints){
        this(null, latLongOrigin, null, latLongDestination, travelModes, drivingOpttions, waypoints);
    }
    
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes,
            DrivingOptions drivingOpttions, DirectionsWaypoint[] waypoints){
        this(addressOrigin, null, addressDestination, null, travelModes, drivingOpttions, waypoints);
    }
    
    public DirectionsRequest(LatLong latLongOrigin, LatLong latLongDestination, TravelModes travelModes,
            DirectionsWaypoint[] waypoints){
        this(null, latLongOrigin, null, latLongDestination, travelModes, null, waypoints);
    }
    
    public DirectionsRequest(String addressOrigin, String addressDestination, TravelModes travelModes,
            DirectionsWaypoint[] waypoints){
        this(addressOrigin, null, addressDestination, null, travelModes, null, waypoints);
    }
    
    public DirectionsRequest(String addressOrigin, LatLong latLongOrigin, 
            String addressDestination, LatLong latLongDestination, 
            TravelModes travelmode, 
            // transit options
            DrivingOptions drivingOptions,
            //unit system
            DirectionsWaypoint[] waypoints){
        super(GMapObjectType.DIRECTIONS_REQUESTS, convertToJavascriptString(addressOrigin, latLongOrigin, addressDestination, latLongDestination, travelmode, drivingOptions, waypoints));
//        
//        if(addressOrigin != null)getJSObject().setMember("origin", addressOrigin);
//        else if(latLongOrigin != null)getJSObject().setMember("origin", latLongOrigin);
//        
//        if(addressDestination != null)getJSObject().setMember("destination", addressDestination);
//        else if(latLongDestination != null)getJSObject().setMember("destination", latLongDestination);
//        
//        getJSObject().setMember("travelMode", "google.maps.TravelMode."+travelmode);
//        
//        // transit options
//        
//        if(drivingOptions != null && travelmode == TravelModes.DRIVING){
//            getJSObject().setMember("drivingOptions", drivingOptions);
//        }
//        
//        //unit system
//        
//        if(waypoints != null){
//            getJSObject().setMember("waypoints", getJSObject().eval("[]"));
//            for(int i = 0; i < waypoints.length; i++){
//                getJSObject().eval(getVariableName()+".waypoints.push("+waypoints[i].getVariableName()+")");
//            }
//        }
        /*
        System.out.println("REQUEST: \n" 
                + "origin: " + getJSObject().getMember("origin").toString() + "\n"
                + "destination: " + getJSObject().getMember("destination").toString() + "\n"
                + "travelMode: " + getJSObject().getMember("travelMode").toString() + "\n");*/
    }
    
    private static String convertToJavascriptString(String addressOrigin, LatLong latLongOrigin, 
            String addressDestination, LatLong latLongDestination, 
            TravelModes travelmode, 
            // transit options
            DrivingOptions drivingOptions,
            //unit system
            DirectionsWaypoint[] waypoints){
        
        
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        boolean something = false;
        if (addressOrigin != null && latLongOrigin == null) {
            something = true;
            builder.append("origin: '").append(addressOrigin).append("'");
        }
        if (addressOrigin == null && latLongOrigin != null) {
            something = true;
            builder.append("origin: '").append(latLongOrigin.getVariableName()).append("'");
        }
        if (addressDestination != null && latLongDestination == null) {
            builder.append(something ? "," : "");
            builder.append("destination: '").append(addressDestination).append("'");
        }
        if (addressDestination == null && latLongDestination != null) {
            builder.append(something ? "," : "");
            builder.append("destination: '").append(latLongDestination.getVariableName()).append("'");
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
        System.out.println("REQUEST " + builder.toString());
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
