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

package com.lynden.gmapsfx.javascript.object;

import com.lynden.gmapsfx.javascript.JavascriptType;
import com.lynden.gmapsfx.javascript.ObjectType;

/**
 *
 * @author Rob Terpilowski
 */
public class GoogleMap extends JavascriptType {

    protected LatLong latLong;
    protected int zoom;
    protected MapOptions options;
    protected static String divArg = "document.getElementById('map-canvas')";
    
    public GoogleMap() {
        super(ObjectType.MAP, divArg );
    }
    
    public GoogleMap( MapOptions mapOptions ) {
        super(ObjectType.MAP, new Object[]{ divArg, mapOptions} );
    }
    
    
    public void setZoom( int zoom ) {
        invokeJavascript("setZoom", zoom);
        this.zoom = zoom;
    }
    
    public void setCenter( LatLong latLong ) {
        invokeJavascript("setCenter", latLong);
        this.latLong = latLong;
    }
    
    public LatLong getLatLong() {
        return latLong;
    }

    public int getZoom() {
        return zoom;
    }
    
    
    public void addMarker( Marker marker ) {
        marker.setMap(this);
    }
    
    public void removeMarker( Marker marker ) {
        marker.setMap(null);
    }
    
    public void setMapType( MapType type ) {
        invokeJavascript("setMapTypeId", type.toString() );
    }
    
    
}
