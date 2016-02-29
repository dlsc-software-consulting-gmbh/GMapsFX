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
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author Andre
 */
public class DirectionsWaypoint extends JavascriptObject{

    public DirectionsWaypoint() {
        super(GMapObjectType.DIRECTIONS_WAYPOINT);
    }   

    public DirectionsWaypoint(String add){
        super(GMapObjectType.DIRECTIONS_WAYPOINT, "{location: '" + add + "'}");
        //getJSObject().setMember("location", add);
    }
    
    public DirectionsWaypoint(LatLong l){
        super(GMapObjectType.DIRECTIONS_WAYPOINT, "{location: " + l + "}");
        //getJSObject().setMember("location", l);
    }
    
    public DirectionsWaypoint(JSObject jsObject) {
        super(GMapObjectType.DIRECTIONS_WAYPOINT, jsObject);
    }
    
    public void setLocation(LatLong l){
        getJSObject().setMember("location", l);
    }
    
    public void setLocation(String add){
        getJSObject().setMember("location", add);
    }
    
    public void setStopOver(boolean b){        
        getJSObject().setMember("stopover", b);
    }
    
    public LatLong getLocation() {
        try {
            JSObject location = (JSObject) getJSObject().getMember("location");
            return new LatLong((JSObject) location);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }
    
    public Boolean getStopOver() {
        try {
            JSObject StopOver = (JSObject) getJSObject().getMember("stopover");
            return Boolean.valueOf(((JSObject) StopOver).toString());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return false;
    }
    
}
