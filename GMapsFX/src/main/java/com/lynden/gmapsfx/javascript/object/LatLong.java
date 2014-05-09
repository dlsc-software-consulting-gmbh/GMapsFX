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

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.JavascriptObjectType;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class LatLong extends JavascriptObject {
    
    protected double latitude;
    protected double longitude;

    public LatLong(double latitude, double longitude) {
        super(JavascriptObjectType.LAT_LNG, latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }
	
	public LatLong(JSObject jsObject) {
		super(JavascriptObjectType.LAT_LNG, jsObject);
		this.latitude = (double) jsObject.call("lat", (Object) null);
		this.longitude = (double) jsObject.call("lng", (Object) null);
	}
	
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LatLong other = (LatLong) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "lat: " + String.format("%.8G", getLatitude()) + " lng: " + String.format("%.8G", getLongitude());
    }
    
    
}
