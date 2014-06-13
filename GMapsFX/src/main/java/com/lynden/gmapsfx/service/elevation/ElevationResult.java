/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lynden.gmapsfx.service.elevation;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import netscape.javascript.JSObject;

/** Encapsulates the response from the ElevationService for one particular 
 * location.
 *
 * @author Geoff Capper
 */
public class ElevationResult extends JavascriptObject {
    
    private LatLong location;
    
    public ElevationResult(JSObject jsObject) {
        super(GMapObjectType.ELEVATION_RESULT, jsObject);
    }
    
    /** The elevation returned from the ElevationService.
     * 
     * @return The elevation in metres.
     */
    public double getElevation() {
        return (double) getJSObject().getMember("elevation");
    }
    
    /** The location for this elevation.
     * 
     * @return 
     */
    public LatLong getLocation() {
        if (location == null) {
            location = new LatLong((JSObject) (getJSObject().getMember("location")));
        }
        return location;
    }
    
    /** The resolution for the elevation, which is the distance in metres
     * between the points that were used to interpolate the elevation.
     * 
     * @return 
     */
    public double getResolution() {
        return (double) getJSObject().getMember("resolution");
    }
    
}
