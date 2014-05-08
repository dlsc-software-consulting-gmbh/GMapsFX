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

package com.lynden.gmapsfx.javascript;

/**
 *
 * @author Rob Terpilowski
 */
public enum JavascriptObjectType {
    
   OBJECT("Object"),
   
   INFO_WINDOW("google.maps.InfoWindow"),
   LAT_LNG("google.maps.LatLng"),
   MAP("google.maps.Map"),
   MARKER("google.maps.Marker"),
   SIZE("google.maps.Size"),
   POLYLINE("google.maps.Polyline"),
   POLYGON("google.maps.Polygon"),
   RECTANGLE("google.maps.Rectangle"),
   CIRCLE("google.maps.Circle"),
   MVC_ARRAY("google.maps.MVCArray"),
   LAT_LNG_BOUNDS("google.maps.LatLngBounds");
   
   protected String typeString;
   
   JavascriptObjectType( String typeString ) {
       this.typeString = typeString;
   }

    @Override
    public String toString() {
        return typeString;
    }
   
   

   
}
