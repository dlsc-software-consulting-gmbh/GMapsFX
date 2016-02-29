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
import netscape.javascript.JSObject;

/**
 *
 * @author Andre
 */
public class DirectionsResult extends JavascriptObject{
    
    public DirectionsResult(){
        super(GMapObjectType.DIRECTIONS_RESULT);
    }
    
    public DirectionsResult(JSObject jsObject){
        super(GMapObjectType.DIRECTIONS_RESULT, jsObject);
    }
    
    public List<DirectionsGeocodedWaypoint> getGeocodedWaypoints(){
        final List<DirectionsGeocodedWaypoint> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) getJSObject().getMember("geocoded_waypoints"));
        for (JSObject jsLocality : jsLocalities) {
            DirectionsGeocodedWaypoint ll = new DirectionsGeocodedWaypoint(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
    
    public List<DirectionsRoute> getRoutes(){
        final List<DirectionsRoute> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) getJSObject().getMember("routes"));
        for (JSObject jsLocality : jsLocalities) {
            DirectionsRoute ll = new DirectionsRoute(jsLocality);
            if (!jsLocality.toString().equals("undefined")) {
                result.add(ll);
            }
        }
        return result;
    }
}
