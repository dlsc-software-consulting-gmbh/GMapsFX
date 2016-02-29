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
import com.lynden.gmapsfx.javascript.object.Polyline;
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
public class DirectionsSteps extends JavascriptObject{

    public DirectionsSteps() {
        super(GMapObjectType.DIRECTIONS_STEP);
    }
    
    public DirectionsSteps(JSObject jsObject) {
        super(GMapObjectType.DIRECTIONS_STEP, jsObject);
    }
    
    public String getInstructions(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("instructions");
            return location.toString();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
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
    /*
    public Polyline getPolyline(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("polyline");
            return new Polyline((JSObject) location);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    */
    
    public List<DirectionsSteps> getSteps(){
        final List<DirectionsSteps> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) 
                getJSObject().getMember("postcode_localities"));
        for (JSObject jsLocality : jsLocalities) {
            DirectionsSteps ll = new DirectionsSteps(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
    
    public TravelModes getTravelMode(){
        try {
            JSObject location = (JSObject) getJSObject().getMember("travel_mode");
            switch(location.toString()){
                case "DRIVING" : return TravelModes.DRIVING;
                case "BICYCLING": return  TravelModes.BICYCLING;
                case "TRANSIT": return  TravelModes.TRANSIT;
                case "WALKING": return  TravelModes.WALKING;
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public List<LatLong> getPath(){
        final List<LatLong> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) 
                getJSObject().getMember("path"));
        for (JSObject jsLocality : jsLocalities) {
            LatLong ll = new LatLong(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
}
