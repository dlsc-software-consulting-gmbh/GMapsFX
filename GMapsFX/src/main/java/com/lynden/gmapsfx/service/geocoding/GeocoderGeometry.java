/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lynden.gmapsfx.service.geocoding;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author jlstephens89
 */
public class GeocoderGeometry extends JavascriptObject {

    public GeocoderGeometry() {
        super(GMapObjectType.GEOCODER_GEOMETRY);
    }

    public GeocoderGeometry(JSObject jsObject) {
        super(GMapObjectType.GEOCODER_GEOMETRY, jsObject);
    }

    public LatLong getLocation() {
        try {
            JSObject location = (JSObject) getJSObject().getMember("location");
            return new LatLong((JSObject) location);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }

    public GeocoderLocationType getLocationType() {
        try {
            String locationType = getJSObject().getMember("location_type").toString();
            return GeocoderLocationType.valueOf(locationType.toUpperCase());

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }

    public LatLongBounds getViewPort() {
        try {
            JSObject viewPort = (JSObject) getJSObject().getMember("viewport");
            return new LatLongBounds((JSObject) viewPort);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }

    public LatLongBounds getBounds() {
        try {
            JSObject bounds = (JSObject) getJSObject().getMember("bounds");
            return new LatLongBounds((JSObject) bounds);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "", e);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t Location: ").append(getLocation()).append("\n");
        builder.append("\t Location Type: ").append(getLocationType()).append("\n");
        builder.append("\t View Port: ").append(getViewPort()).append("\n");
        builder.append("\t Bounds: ").append(getBounds()).append("\n");
        return builder.toString();
    }

}
