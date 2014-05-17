/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lynden.gmapsfx.zoom;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import netscape.javascript.JSObject;

/**
 *
 * @author Geoff Capper
 */
public class MaxZoomResult extends JavascriptObject {
	
    private MaxZoomStatus status;
    
    public MaxZoomResult() {
        super(GMapObjectType.MAX_ZOOM_RESULT);
    }
    
    public MaxZoomResult(JSObject obj) {
        super(GMapObjectType.MAX_ZOOM_RESULT, obj);
    }
    
    public MaxZoomResult(MaxZoomStatus status) {
        super(GMapObjectType.MAX_ZOOM_RESULT);
        this.status = status;
    }
    
    public MaxZoomStatus getStatus() {
        if (status == null) {
            status = MaxZoomStatus.valueOf((String) getJSObject().getMember("status"));
        }
        return status;
    }
    
    public int getMaxZoom() {
        return (int) getJSObject().getMember("zoom");
    }
    
}
