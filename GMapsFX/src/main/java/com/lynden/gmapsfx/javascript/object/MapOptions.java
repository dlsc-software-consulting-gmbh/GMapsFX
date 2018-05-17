/*
 * Copyright 2014 Lynden, Inc.
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

/**
 * @see <a href="https://developers.google.com/maps/documentation/javascript/3.exp/reference#MapOptions">Google MapOptions API Documentation</a>
 * @author robt
 */
public class MapOptions extends JavascriptObject {
    
    protected LatLong center;
    protected MapTypeIdEnum mapType;
    protected boolean mapMaker;
    protected boolean overviewMapControl;
    protected boolean panControl;
    protected boolean rotateControl;
    protected boolean scaleControl;
    protected boolean streetViewControl;
    protected double zoom;
    protected double maxZoom;
    protected double minZoom;
    protected boolean zoomControl;
    protected boolean mapTypeControl;
    protected boolean scrollWheel;
    protected boolean fullscreenControl;
    protected boolean clickableIcons;
    protected boolean disableDefaultUI;
    protected boolean disableDoubleClickZoom;
    protected boolean keyboardShortcuts;
		
    private String styleString;
    
    /*
    [{"featureType":"landscape","stylers":[{"saturation":-100},{"lightness":65},{"visibility":"on"}]},{"featureType":"poi","stylers":[{"saturation":-100},{"lightness":51},{"visibility":"simplified"}]},{"featureType":"road.highway","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"road.arterial","stylers":[{"saturation":-100},{"lightness":30},{"visibility":"on"}]},{"featureType":"road.local","stylers":[{"saturation":-100},{"lightness":40},{"visibility":"on"}]},{"featureType":"transit","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"administrative.province","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":-25},{"saturation":-100}]},{"featureType":"water","elementType":"geometry","stylers":[{"hue":"#ffff00"},{"lightness":-25},{"saturation":-97}]}]
    */
    
	private GestureHandlingType gestureHandling;
	

    public MapOptions() {
        super(GMapObjectType.OBJECT);
    }
    
    
    public MapOptions center( LatLong center ) {
        setProperty("center", center);
        this.center = center;
        return this;
    }
    
	/** Determines whether the fullscreen control is displayed on the map.
	 * 
	 * @param fullscreenControl
	 * @return 
	 */
	public MapOptions fullscreenControl( boolean fullscreenControl ) {
		setProperty("fullscreenControl", fullscreenControl);
		this.fullscreenControl = fullscreenControl;
		return this;
	}
	
	
    /** This is a mistyping of MapMaker which is the option to use MapMaker tiles.
     * 
     * @param mapMarker
     * @return 
     */
    @Deprecated
    public MapOptions mapMarker( boolean mapMarker ) {
//        setProperty("mapMarker", mapMarker );
//        this.mapMarker = mapMarker;
        return this;
    }
    
    /** Use MapMaker tiles.
     * 
     * @param mapMaker
     * @return 
     */
    public MapOptions mapMaker(boolean mapMaker) {
        setProperty("mapMaker", mapMaker);
        this.mapMaker = mapMaker;
        return this;
    }
    
    public MapOptions mapType( MapTypeIdEnum mapType ) {
        setProperty( "mapTypeId", mapType );
        this.mapType = mapType;
        return this;
    }
    
    public MapOptions overviewMapControl( boolean overviewMapControl ) {
        setProperty("overviewMapControl", overviewMapControl );
        this.overviewMapControl = overviewMapControl;
        return this;
    }
    
    public MapOptions panControl( boolean panControl ) {
        setProperty( "panControl", panControl);
        this.panControl = panControl;
        return this;
    }
    
    public MapOptions rotateControl( boolean rotateControl ) {
        setProperty( "rotateControl", rotateControl );
        this.rotateControl = rotateControl;
        return this;
    }
    
    public MapOptions scaleControl( boolean scaleControl ) {
        setProperty( "scaleControl", scaleControl );
        this.scaleControl = scaleControl;
        return this;
    }
    
    public MapOptions streetViewControl( boolean streetViewControl ) {
        setProperty( "streetViewControl", streetViewControl );
        this.streetViewControl = streetViewControl;
        return this;
    }
    
    public MapOptions zoom( double zoom ) {
        setProperty( "zoom", zoom );
        this.zoom = zoom;
        return this;
    }
    
    public MapOptions maxZoom( double maxZoom ) {
        setProperty( "maxZoom", maxZoom );
        this.maxZoom = maxZoom;
        return this;
    }
    public MapOptions minZoom( double minZoom ) {
        setProperty("minZoom", minZoom);
        this.minZoom = minZoom;
        return this;
    }
    
    public MapOptions zoomControl( boolean zoomControl ) {
        setProperty( "zoomControl", zoomControl );
        this.zoomControl = zoomControl;
        return this;
    }
    
    public MapOptions mapTypeControl( boolean mapTypeControl ) {
        setProperty( "mapTypeControl", mapTypeControl);
        this.mapTypeControl = mapTypeControl;
        return this;
    }
    
    public MapOptions scrollWheel(boolean scrollWheel ) {
        setProperty( "scrollwheel", scrollWheel );
        this.scrollWheel = scrollWheel;
        return this;
    }

    public MapOptions clickableIcons(boolean clickableIcons) {
        setProperty( "clickableIcons", clickableIcons );
        this.clickableIcons = clickableIcons;
        return this;
    }

    public MapOptions disableDefaultUI( boolean disableDefaultUI ) {
        setProperty( "disableDefaultUI", disableDefaultUI );
        this.disableDefaultUI = disableDefaultUI;
        return this;
    }

    public MapOptions disableDoubleClickZoom( boolean disableDoubleClickZoom ) {
        setProperty( "disableDoubleClickZoom", disableDoubleClickZoom );
        this.disableDoubleClickZoom = disableDoubleClickZoom;
        return this;
    }

    public MapOptions keyboardShortcuts( boolean keyboardShortcuts ) {
        setProperty( "keyboardShortcuts", keyboardShortcuts );
        this.keyboardShortcuts = keyboardShortcuts;
        return this;
    }
    
    /**
     * @param styleString the styleString to set. This parameter is assigned by 
     * calling eval() and should be of the format: "[{'featureType':'landscape','stylers':[{'saturation':-100}, ...etc..."
     * @return 
     */
    public MapOptions styleString(String styleString) {
        setProperty("styles", this.getJSObject().eval(styleString));
        this.styleString = styleString;
        return this;
    }
    
	/** Sets the gestureHandling option, used to control how Google Maps deals 
	 * with touch screen devices. We offer no warranty as to whether this 
	 * actually works given that any touch events are being passed through 
	 * JavaFX WebView to the map, and vital information may be lost in 
	 * transmission/translation.
	 * <p>
	 * See <a href="https://developers.google.com/maps/documentation/javascript/interaction">Google Maps API - Controlling Zoom and Pan</a>
	 * 
	 * @param gestureHandling
	 * @return 
	 */
	public MapOptions gestureHandling(GestureHandlingType gestureHandling) {
		setProperty("gestureHandling", gestureHandling.getOptionValue());
		this.gestureHandling = gestureHandling;
		return this;
	}
	
}
