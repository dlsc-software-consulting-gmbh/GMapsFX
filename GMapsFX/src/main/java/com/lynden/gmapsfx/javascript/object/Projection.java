/*
 * Copyright 2014 GMapsFX.
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
import netscape.javascript.JSObject;

/**
 *
 * @author Geoff Capper
 */
public class Projection extends JavascriptObject {
    
    public Projection(JSObject obj) {
        super(GMapObjectType.PROJECTION, obj);
    }
    
    public GMapPoint fromLatLngToPoint(LatLong loc) {
//        System.out.println("Projection.fromLatLngToPoint: " + loc);
        Object res = invokeJavascript("fromLatLngToPoint", loc);
//        System.out.println("Projection.fromLatLngToPoint.res: " + res);
        if (res != null && res instanceof JSObject) {
            return new GMapPoint((JSObject) res);
        }
        return null;
    }
    
}
