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
import com.lynden.gmapsfx.service.geocoding.GeocoderUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author Andre
 */
public class DirectionsLeg extends JavascriptObject{
    
    public DirectionsLeg() {
        super(GMapObjectType.DIRECTIONS_LEG);
    }
    
    public DirectionsLeg(JSObject jsObject) {
        super(GMapObjectType.DIRECTIONS_LEG, jsObject);
    }
    
    public List<DirectionsSteps> getSteps(){
        final List<DirectionsSteps> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) getJSObject().getMember("steps"));
        for (JSObject jsLocality : jsLocalities) {
            DirectionsSteps ll = new DirectionsSteps(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
    
    public Distance getDistance(){
        try{
            JSObject distance = (JSObject) getJSObject().getMember("distance");
            return new Distance(distance);
        } catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public Duration getDuration(){
        try{
            JSObject duration = (JSObject) getJSObject().getMember("duration");
            return new Duration(duration);
        } catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public LatLong getStartLocation(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("start_location");
            return new LatLong((JSObject) location);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public LatLong getEndLocation(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("end_location");
            return new LatLong((JSObject) location);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public String getStartAddress(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("start_address");
            return location.toString();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public String getEndAddress(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("end_address");
            return location.toString();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Distancia: " + getDistance().getValue() + "\nDuracao: " + getDuration().getValue() + "\nInicio: " + getStartAddress()+"\nFim: " + getEndAddress();
    }
    
    
}
