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
import com.lynden.gmapsfx.javascript.JavascriptObjectType;
import com.lynden.gmapsfx.javascript.event.EventHandlers;
import com.lynden.gmapsfx.javascript.event.GFXEventHandler;
import com.lynden.gmapsfx.javascript.event.MapStateEventType;
import com.lynden.gmapsfx.javascript.event.StateEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class GoogleMap extends JavascriptObject {

    protected LatLong latLong;
//    protected int zoom;
    protected MapOptions options;
    protected static String divArg = "document.getElementById('map-canvas')";
    
    private ReadOnlyObjectWrapper<LatLong> center;
    private IntegerProperty zoom;
    
    private final EventHandlers jsHandlers = new EventHandlers();
    private boolean registeredOnJS;
    
    
    public GoogleMap() {
        super(JavascriptObjectType.MAP, divArg );
    }
    
    public GoogleMap( MapOptions mapOptions ) {
        super(JavascriptObjectType.MAP, new Object[]{ divArg, mapOptions} );
    }
    
    
    public void setZoom( int zoom ) {
        zoomProperty().set(zoom);
//        invokeJavascript("setZoom", zoom);
//        this.zoom = zoom;
    }
    
    public int getZoom() {
        return zoomProperty().get();
//        return zoom;
//        return (int) invokeJavascript("getZoom");
    }
    
    private int internalGetZoom() {
        return (int) invokeJavascript("getZoom");
    }
    
    private void internalSetZoom(int zoom) {
        invokeJavascript("setZoom", zoom);
    }
        
    private boolean userPromptedZoomChange;
    private boolean mapPromptedZoomChange;
    
    public IntegerProperty zoomProperty() {
        if (zoom == null) {
            zoom = new SimpleIntegerProperty(internalGetZoom());
            addStateEventHandler(MapStateEventType.zoom_changed, () -> {
                if (! userPromptedZoomChange) {
                    mapPromptedZoomChange = true;
                    zoom.set(internalGetZoom());
                    mapPromptedZoomChange = false;
                }
            });
            zoom.addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
                if (! mapPromptedZoomChange) {
                    userPromptedZoomChange = true;
                    internalSetZoom(n.intValue());
                    userPromptedZoomChange = false;
                }
            });
        }
        return zoom;
    }
    
    
    public void setCenter( LatLong latLong ) {
        invokeJavascript("setCenter", latLong);
        this.latLong = latLong;
    }
    
    public LatLong getLatLong() {
        return latLong;
    }
    
    public final ReadOnlyObjectProperty<LatLong> centerProperty() {
        if (center == null) {
            center = new ReadOnlyObjectWrapper<>(getCenter());
            addStateEventHandler(MapStateEventType.center_changed, () -> {
                center.set(getCenter());
            });
        }
        return center.getReadOnlyProperty();
    }
    
    public LatLong getCenter() {
        return new LatLong((JSObject) invokeJavascript("getCenter"));
    }
    
    public void addMarker( Marker marker ) {
        marker.setMap(this);
    }
    
    public void removeMarker( Marker marker ) {
        marker.setMap(null);
    }
    
    public void setMapType( MapType type ) {
        invokeJavascript("setMapTypeId", type.toString() );
    }
    
    public void addMapShape(MapShape shape) {
        shape.setMap(this);
    }
    
    public void removeMapShape(MapShape shape) {
        shape.setMap(null);
    }
    
    /** Registers an event handler in the repository shared between Javascript 
     * and Java.
     * 
     * @param h Event handler to be registered.
     * @return Callback key that Javascript will use to find this handler.
     */
    private String registerEventHandler(GFXEventHandler h) {
        //checkInitialized();
        if (! registeredOnJS) {
            JSObject doc = (JSObject) runtime.execute("document");
            doc.setMember("jsHandlers", jsHandlers);
            registeredOnJS = true;
        }
        return jsHandlers.registerHandler(h);
    }
    
    /** Adds a handler for a mouse type event on the map.
     * 
     * @param type Type of the event to register against.
     * @param h Handler that will be called when the event occurs.
     */
    public void addUIEventHandler(UIEventType type, UIEventHandler h) {
        this.addUIEventHandler(this, type, h);
//        String key = registerEventHandler(h);
//        String mcall = "google.maps.event.addListener(" + getVariableName() + ", '" + type.name() + "', "
//                + "function(event) {document.jsHandlers.handleUIEvent('" + key + "', event.latLng);});";
//        //System.out.println("addUIEventHandler mcall: " + mcall);
//        runtime.execute(mcall);

    }

    /** Adds a handler for a mouse type event on the map.
     * 
     * @param obj The object that the event should be registered on.
     * @param type Type of the event to register against.
     * @param h Handler that will be called when the event occurs.
     */
    public void addUIEventHandler(JavascriptObject obj, UIEventType type, UIEventHandler h) {
        String key = registerEventHandler(h);
        String mcall = "google.maps.event.addListener(" + obj.getVariableName() + ", '" + type.name() + "', "
                + "function(event) {document.jsHandlers.handleUIEvent('" + key + "', event);});";//.latLng
        //System.out.println("addUIEventHandler mcall: " + mcall);
        runtime.execute(mcall);
    }
    
    
    /** Adds a handler for a state type event on the map.
     * <p>
     * We could allow this to handle any state event by adding a parameter 
     * JavascriptObject obj, but we would then need to loosen up the event type 
     * and either accept a String value, or fill an enum with all potential 
     * state events.
     * 
     * @param type Type of the event to register against.
     * @param h Handler that will be called when the event occurs.
     */
    public void addStateEventHandler(MapStateEventType type, StateEventHandler h) {
        String key = registerEventHandler(h);
        String mcall = "google.maps.event.addListener(" + getVariableName() + ", '" + type.name() + "', "
                + "function() {document.jsHandlers.handleStateEvent('" + key + "');});";
        //System.out.println("addStateEventHandler mcall: " + mcall);
        runtime.execute(mcall);

    }
    
}
