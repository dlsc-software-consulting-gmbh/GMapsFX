/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lynden.gmapsfx.elevation;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.JavascriptObjectType;
import com.lynden.gmapsfx.javascript.object.LatLong;

/** Creates a request that can be passed in to the {@link ElevationService} to 
 * get the elevations for a number of locations.
 *
 * @author Geoff Capper
 */
public class LocationElevationRequest extends JavascriptObject {
    
    public LocationElevationRequest() {
        super(JavascriptObjectType.OBJECT);
    }
    
    public LocationElevationRequest(LatLong[] locations) {
        super(JavascriptObjectType.OBJECT);
        getJSObject().setMember("locations", getJSObject().eval("[]"));
        for (int i = 0; i < locations.length; i++) {
            getJSObject().eval(getVariableName()+".locations.push("+locations[i].getVariableName()+")");
        }
    }
    
}
