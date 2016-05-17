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
import com.lynden.gmapsfx.service.geocoding.GeocoderAddressComponentType;
import com.lynden.gmapsfx.service.geocoding.GeocoderUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author Andre
 */
public class DirectionsGeocodedWaypoint extends JavascriptObject{
    
    public DirectionsGeocodedWaypoint() {
        super(GMapObjectType.DIRECTIONS_GEOCODED_WAYPOINT);
    }
    
    public DirectionsGeocodedWaypoint(JSObject jsObject) {
        super(GMapObjectType.DIRECTIONS_GEOCODED_WAYPOINT, jsObject);
    }
    
    public DirectionsGeocodedWaypointStatus getStatus(){
        String s = getJSObject().getMember("geocoder_status").toString();
        if(s.equals(DirectionsGeocodedWaypointStatus.OK.toString())) 
            return DirectionsGeocodedWaypointStatus.OK;
        else if(s.equals(DirectionsGeocodedWaypointStatus.ZERO_RESULTS.toString())) 
            return DirectionsGeocodedWaypointStatus.ZERO_RESULTS;
        return null;
    }
    
    public Boolean isPartialMatch() {
        Boolean result = null;
        String text = getJSObject().getMember("partial_match").toString();
        try {
            result = Boolean.parseBoolean(text);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "", e);
        }
        return result;
    }
    
    public String getPlaceId(){
        return getJSObject().getMember("place_id").toString();
    }
    
    public List<GeocoderAddressComponentType> getTypes() {
        JSObject jsTypes = (JSObject) getJSObject().getMember("types");
        return GeocoderUtils.convertJSObjectToListOfEnum(jsTypes, GeocoderAddressComponentType.class);
    }
}