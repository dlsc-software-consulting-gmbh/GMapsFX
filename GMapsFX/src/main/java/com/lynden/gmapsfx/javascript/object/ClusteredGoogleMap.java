package com.lynden.gmapsfx.javascript.object;

/**
 * User: twalcari
 * Date: 1/9/2015
 * Time: 11:59
 */
public class ClusteredGoogleMap extends GoogleMap {

    private MarkerClusterer markerClusterer;


    public ClusteredGoogleMap() {
    }

    public ClusteredGoogleMap(MapOptions mapOptions) {
        super(mapOptions);
    }

    @Override
    protected void initialize() {
        super.initialize();

        markerClusterer = new MarkerClusterer(this);
    }

    public void addClusterableMarker(Marker marker) {
        markerClusterer.addMarker(marker);

    }

    public void removeClusterableMarker(Marker marker) {
        markerClusterer.removeMarker(marker);
    }

    public MarkerClusterer getMarkerClusterer() {
        return markerClusterer;
    }
}