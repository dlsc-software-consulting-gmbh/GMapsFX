/*
 * Copyright 2014 Lynden, Inc.
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

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.zoom.MaxZoomResult;
import java.util.ArrayList;
import java.util.List;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class Geocoder extends JavascriptObject {

    private GeocodingServiceCallback callback;

    public Geocoder() {
        super(GMapObjectType.GEOCODER);
    }
    
    public void reverseGeocode(double lat, double lon,GeocodingServiceCallback callback){
        this.geocode(new GeocoderRequest(null, new LatLong(lat, lon),null, null, null, null), callback);
    }
    
    public void geocode(String address, GeocodingServiceCallback callback){
        this.geocode(new GeocoderRequest(address), callback);
    }

    public void geocode(GeocoderRequest request, GeocodingServiceCallback callback) {
        this.callback = callback;
        JSObject doc = (JSObject) getJSObject().eval("document");
        doc.setMember(getVariableName(), this);

        StringBuilder r = new StringBuilder(getVariableName())
                .append(".")
                .append("geocode(")
                .append(request.getVariableName())
                .append(", ")
                .append("function(results, status) {document.")
                .append(getVariableName())
                .append(".processResponse(results, status);});");

//        System.out.println("MaxZoomService direct call: " + r.toString());
        getJSObject().eval(r.toString());
        System.out.println("EVAL CALLED ");
    }

    
     public void processResponse(Object result, Object status) {
        if (result instanceof JSObject) {
            JSObject jsResults = (JSObject) result;
            
            final List<JSObject> jsGeocoderResults = GeocoderUtils.getJSObjectsFromArray((JSObject)result);
            
            final List<GeocodingResult> geocodingResults = new ArrayList<>();
            for (JSObject jsObject : jsGeocoderResults) {
                geocodingResults.add(new GeocodingResult(jsObject));
            }
            
            callback.geocodedResultsReceived(geocodingResults,GeocoderStatus.valueOf((String) status));
        }
    }
     
}
