package com.lynden.gmapsfx;

import com.lynden.gmapsfx.javascript.object.ClusteredGoogleMap;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.MapOptions;

/**
 * User: twalcari
 * Date: 1/9/2015
 * Time: 11:51
 */
public class ClusteredGoogleMapView extends GoogleMapView {

    public ClusteredGoogleMapView() {
        super();
    }

    public ClusteredGoogleMapView(boolean debug) {
        super(debug);
    }

    public ClusteredGoogleMapView(String mapResourcePath) {
        super(mapResourcePath);
    }

    public ClusteredGoogleMapView(String mapResourcePath, boolean debug) {
        super(mapResourcePath, debug);
    }

    public ClusteredGoogleMapView(String language, String key) {
        super(language, key);
    }

    public ClusteredGoogleMapView(String mapResourcePath, String language, String key, boolean debug) {
        super(mapResourcePath, language, key, debug);
    }

    public ClusteredGoogleMapView(String mapResourcePath, String language, String region, String key, boolean debug) {
        super(mapResourcePath, language, region, key, debug);
    }

    @Override
    public ClusteredGoogleMap createMap() {
        return (ClusteredGoogleMap) super.createMap();
    }

    @Override
    public ClusteredGoogleMap createMap(MapOptions mapOptions) {
        return (ClusteredGoogleMap) super.createMap(mapOptions);
    }

    @Override
    public ClusteredGoogleMap createMap(MapOptions mapOptions, boolean withDirectionsPanel) {
        return (ClusteredGoogleMap) super.createMap(mapOptions, withDirectionsPanel);
    }

    @Override
    protected GoogleMap internal_createMap() {
        return new ClusteredGoogleMap();
    }

    @Override
    protected GoogleMap internal_createMap(MapOptions mapOptions) {
        return new ClusteredGoogleMap(mapOptions);
    }

    @Override
    protected String getHtmlFile(boolean debug) {
        return debug ?
                "html/clustered/maps_clustered_debug.html"
                : "html/clustered/maps_clustered.html";
    }

    @Override
    public ClusteredGoogleMap getMap() {
        return (ClusteredGoogleMap) super.getMap();
    }
}