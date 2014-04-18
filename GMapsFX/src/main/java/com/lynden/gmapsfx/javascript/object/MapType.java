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

/**
 * Various types of maps that are available.
 * 
 * @author Rob Terpilowski
 */
public enum MapType {
    
    ROADMAP( "google.maps.MapTypeId.ROADMAP" ),
    SATELLITE( "google.maps.MapTypeId.SATELLITE"),
    HYBRID( "google.maps.MapTypeId.HYBRID" ),
    TERRAIN( "google.maps.MapTypeId.TERRAIN");
    
    
    protected String typeString;
    
    MapType( String typeString ) {
        this.typeString = typeString;
    }
    
    @Override
    public String toString() {
        return typeString;
    }
}
