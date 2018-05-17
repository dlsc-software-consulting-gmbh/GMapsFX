package com.lynden.gmapsfx;

import com.lynden.gmapsfx.javascript.object.ClusteredGoogleMap;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.MapOptions;

import javafx.beans.NamedArg;

/**
 * User: twalcari
 * Date: 1/9/2015
 * Time: 11:51
 */
public class ClusteredGoogleMapView extends GoogleMapView {

    public ClusteredGoogleMapView() {
        super();
    }

	public ClusteredGoogleMapView(@NamedArg("debug") boolean debug) {
		super(debug);
	}

	public ClusteredGoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath) {
		super(mapResourcePath);
	}

	public ClusteredGoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath, @NamedArg("debug") boolean debug) {
		super(mapResourcePath, debug);
	}

	public ClusteredGoogleMapView(@NamedArg("language") String language, @NamedArg("region") String region, @NamedArg("key") String key) {
		super(language, region, key);
	}

	public ClusteredGoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath, @NamedArg("language") String language, @NamedArg("region") String region, @NamedArg("key") String key, @NamedArg("debug") boolean debug) {
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