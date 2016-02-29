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
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
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
public class DirectionsRoute extends JavascriptObject{
    
    public DirectionsRoute() {
        super(GMapObjectType.DIRECTIONS_ROUTE);
    }
    
    public DirectionsRoute(JSObject jsObject) {
        super(GMapObjectType.DIRECTIONS_ROUTE, jsObject);
    }
    
    public List<DirectionsLeg> getLegs(){
        final List<DirectionsLeg> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) 
                getJSObject().getMember("legs"));
        for (JSObject jsLocality : jsLocalities) {
            DirectionsLeg ll = new DirectionsLeg(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
    
    public String getWaypointOrder(){
        return getJSObject().getMember("waypoint_order").toString();
    }
    
    public List<LatLong> getOverviewPath(){
        final List<LatLong> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) 
                getJSObject().getMember("overview_path"));
        for (JSObject jsLocality : jsLocalities) {
            LatLong ll = new LatLong(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
    
    public LatLongBounds getBounds() {
        try {
            JSObject bounds = (JSObject) getJSObject().getMember("bounds");
            return new LatLongBounds((JSObject) bounds);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }    
}
