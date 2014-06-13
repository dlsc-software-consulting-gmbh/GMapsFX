/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lynden.gmapsfx.service.elevation;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 *
 * @author Geoff Capper
 */
public class PathElevationRequest extends JavascriptObject {
    
    public PathElevationRequest() {
        super(GMapObjectType.OBJECT);
    }
    
    public PathElevationRequest(LatLong[] path, int samples) {
        super(GMapObjectType.OBJECT);
        getJSObject().setMember("path", getJSObject().eval("[]"));
        for (int i = 0; i < path.length; i++) {
            getJSObject().eval(getVariableName()+".path.push("+path[i].getVariableName()+")");
        }
        getJSObject().setMember("samples", samples);
    }
    
}
