package com.lynden.gmapsfx.javascript.object;

import com.lynden.gmapsfx.javascript.JavascriptArray;
import com.lynden.gmapsfx.javascript.JavascriptObject;

/**
 * User: twalcari
 * Date: 1/9/2015
 * Time: 12:01
 */
public class MarkerClusterer extends JavascriptObject {

    public MarkerClusterer(GoogleMap map) {
        super(MarkerClustererObjectType.MARKER_CLUSTERER, map);
    }

    public MarkerClusterer(GoogleMap map, JavascriptArray markers) {
        super(MarkerClustererObjectType.MARKER_CLUSTERER, map, markers);
    }

    /**
     * Adds a marker to the clusterer and redraws if needed.
     *
     * @param marker
     */
    public void addMarker(Marker marker) {
        invokeJavascript("addMarker", marker);
    }

    /**
     * Add an array of markers to the clusterer.
     *
     * @param markers
     */
    public void addMarkers(JavascriptArray markers) {
        invokeJavascript("addMarkers", markers);
    }

    /**
     * Clears all clusters and markers from the clusterer.
     */
    public void clearMarkers() {
        invokeJavascript("clearMarkers");
    }

    /**
     * Returns the array of markers in the clusterer.
     *
     * @return
     */
    public JavascriptArray getMarkers() {
        return invokeJavascriptReturnValue("getMarkers", JavascriptArray.class);
    }

    /**
     * Gets the max zoom for the clusterer.
     *
     * @return
     */
    public int getMaxZoom() {
        return invokeJavascriptReturnValue("getMaxZoom", Integer.class);
    }

    public void redraw() {
        invokeJavascript("redraw");
    }

    /**
     * Sets the max zoom for the clusterer.
     *
     * @param number
     * @return
     */
    public void setMaxZoom(int number) {
        invokeJavascript("setMaxZoom", number);
    }

    public boolean removeMarker(Marker marker) {
        return invokeJavascriptReturnValue("removeMarker", Boolean.class, marker);
    }

    /**
     * Gets the max zoom for the clusterer.
     *
     * @return
     */
    public int getGridSize() {
        return invokeJavascriptReturnValue("getGridSize", Integer.class);
    }

    /**
     * Sets the max zoom for the clusterer.
     *
     * @param number
     * @return
     */
    public void setGridSize(int number) {
        invokeJavascript("setGridSize", number);
    }

}