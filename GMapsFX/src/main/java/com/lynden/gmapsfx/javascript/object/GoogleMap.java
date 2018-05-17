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
import com.lynden.gmapsfx.javascript.JavascriptRuntime;

import com.lynden.gmapsfx.javascript.event.EventHandlers;
import com.lynden.gmapsfx.javascript.event.GFXEventHandler;
import com.lynden.gmapsfx.javascript.event.MapStateEventType;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.StateEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.util.Callback;
import netscape.javascript.JSObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Rob Terpilowski
 */
public class GoogleMap extends JavascriptObject {

    private boolean userPromptedZoomChange;
    private boolean mapPromptedZoomChange;
    protected MapOptions options;
    protected static String divArg = "document.getElementById('map-canvas')";

    private ReadOnlyObjectWrapper<LatLong> center;
    private IntegerProperty zoom;
    private ReadOnlyObjectWrapper<LatLongBounds> bounds;

    private final EventHandlers jsHandlers = new EventHandlers();
    private boolean registeredOnJS;

    private Set<Marker> markers;

    public GoogleMap() {
        super(GMapObjectType.MAP, divArg);

        initialize();
    }

    public GoogleMap(MapOptions mapOptions) {
        super(GMapObjectType.MAP, divArg, mapOptions);

        initialize();
    }

    protected void initialize(){
        zoom = new SimpleIntegerProperty(internalGetZoom());
        addStateEventHandler(MapStateEventType.zoom_changed, () -> {
            if (!userPromptedZoomChange) {
                mapPromptedZoomChange = true;
                zoom.set(internalGetZoom());
                mapPromptedZoomChange = false;
            }
        });
        zoom.addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
            if (!mapPromptedZoomChange) {
                userPromptedZoomChange = true;
                internalSetZoom(n.intValue());
                userPromptedZoomChange = false;
            }
        });

        center = new ReadOnlyObjectWrapper<>(getCenter());
        addStateEventHandler(MapStateEventType.center_changed, () -> {
            center.set(getCenter());
        });
    }


    public void setZoom(int zoom) {
        zoomProperty().set(zoom);
    }

    public int getZoom() {
        return zoomProperty().get();
    }

    private int internalGetZoom() {
        return (int) invokeJavascript("getZoom");
    }

    private void internalSetZoom(int zoom) {
        invokeJavascript("setZoom", zoom);
    }

    public void showDirectionsPane() {
        JavascriptRuntime.getInstance().execute("showDirections()");
    }

    public void hideDirectionsPane() {
        JavascriptRuntime.getInstance().execute("hideDirections()");
    }

    public IntegerProperty zoomProperty() {
        return zoom;
    }

//    This method was calling setCenter anyway, so would never have worked.
//    public LatLong getLatLong() {
//        return getProperty("setCenter", LatLong.class);
//    }
    public final ReadOnlyObjectProperty<LatLong> centerProperty() {
        return center.getReadOnlyProperty();
    }

    public LatLong getCenter() {
        return new LatLong((JSObject) invokeJavascript("getCenter"));
    }

    public void setCenter(LatLong latLong) {
        invokeJavascript("setCenter", latLong);
    }

    /**
     * Returns the LatLongBounds of the visual area. Note: on zoom changes the
     * bounds are reset after the zoom event is fired, which can cause
     * unexpected results.
     *
     * @return
     */
    public LatLongBounds getBounds() {
        return invokeJavascriptReturnValue("getBounds", LatLongBounds.class);
    }

    /**
     * Moves the map to ensure the given bounds fit within the viewport.
     * <p>
     * Note that the Google Maps API will add a buffer around this value, so
     * assuming you can store this and use it to later restore the view will
     * give incorrect results. Calling map.fitBounds(map.getBounds()); will
     * result in the map gradually zooming outward.
     * <p>
     *
     * @param bounds
     */
    public void fitBounds(LatLongBounds bounds) {
        invokeJavascript("fitBounds", bounds);
    }

    public void panToBounds(LatLongBounds bounds) {
        invokeJavascript("panToBounds", bounds);
    }

    /**
     * A property tied to the map, updated when the idle state event is fired.
     *
     * @return
     */
    public final ReadOnlyObjectProperty<LatLongBounds> boundsProperty() {
        if (bounds == null) {
            bounds = new ReadOnlyObjectWrapper<>(getBounds());
            addStateEventHandler(MapStateEventType.idle, () -> {
                bounds.set(getBounds());
            });
        }
        return bounds.getReadOnlyProperty();
    }

    public void setHeading(double heading) {
        invokeJavascript("setHeading", heading);
    }

    public double getHeading() {
        return invokeJavascriptReturnValue("getHeading", Double.class);
    }

    /**
     * Adds the supplied marker to the map.
     *
     * @param marker
     */
    public void addMarker(Marker marker) {
        if (markers == null) {
            markers = new HashSet<>();
        }
        markers.add(marker);
        marker.setMap(this);
    }

    /**
     * Removes the supplied marker from the map.
     *
     * @param marker
     */
    public void removeMarker(Marker marker) {
        if (markers != null && markers.contains(marker)) {
            markers.remove(marker);
        }
        marker.setMap(null);
    }

    /**
     * Removes all of the markers from the map.
     *
     */
    public void clearMarkers() {
        if (markers != null && !markers.isEmpty()) {
            markers.forEach((m) -> {
                m.setMap(null);
            });
            markers.clear();
        }
    }

    /**
     * Adds all of the markers in the supplied collection to the map. Existing
     * markers, if any, are retained.
     *
     * @param col
     */
    public void addMarkers(Collection<Marker> col) {
        if (markers == null) {
            markers = new HashSet<>(col);
        } else {
            markers.addAll(col);
        }
        col.forEach((m) -> {
            m.setMap(this);
        });
    }

    public void addMarkers(Collection<Marker> col, UIEventType type, Callback<Marker, UIEventHandler> h) {
        if (markers == null) {
            markers = new HashSet<>(col);
        } else {
            markers.addAll(col);
        }
        col.forEach((m) -> {
            m.setMap(this);
            addUIEventHandler(m, type, h.call(m));
        });
    }

    /**
     * Removes the markers in the supplied collection from the map.
     *
     * @param col
     */
    public void removeMarkers(Collection<Marker> col) {
        if (markers != null && !markers.isEmpty()) {
            markers.removeAll(col);
            col.forEach((m) -> {
                m.setMap(null);
            });
        }
    }

    /**
     * Sets the map type. This is equivalent to the javascript method
     * setMapTypeId.
     *
     * @param type
     */
    public void setMapType(MapTypeIdEnum type) {
        invokeJavascript("setMapTypeId", type);
    }

    public void addMapShape(MapShape shape) {
        shape.setMap(this);
    }

    public void removeMapShape(MapShape shape) {
        shape.setMap(null);
    }

    public Projection getProjection() {
        Object obj = invokeJavascript("getProjection");
        return (obj == null) ? null : new Projection((JSObject) obj);
    }

    /**
     * Pans the map by the supplied values.
     *
     * @param x delta x value in pixels.
     * @param y delta y value in pixels.
     */
    public void panBy(double x, double y) {
//        System.out.println("panBy x: " + x + ", y: " + y);
        invokeJavascript("panBy", new Object[]{x, y});
    }

    /**
     * Pans the map to the specified latitude and longitude.
     *
     * @param latLong
     */
    public void panTo(LatLong latLong) {
        invokeJavascript("panTo", latLong);
    }

    /**
     * Returns the screen point for the provided LatLong. Note: Unexpected
     * results can be obtained if this method is called as a result of a zoom
     * change, as the zoom event is fired before the bounds are updated, and
     * bounds need to be used to obtain the answer!
     * <p>
     * One workaround is to only operate off bounds_changed events.
     *
     * @param loc
     * @return
     */
    public Point2D fromLatLngToPoint(LatLong loc) {
//        System.out.println("GoogleMap.fromLatLngToPoint loc: " + loc);
        Projection proj = getProjection();
        //System.out.println("map.fromLatLngToPoint Projection: " + proj);
        LatLongBounds llb = getBounds();
//        System.out.println("GoogleMap.fromLatLngToPoint Bounds: " + llb);

        GMapPoint topRight = proj.fromLatLngToPoint(llb.getNorthEast());
//        System.out.println("GoogleMap.fromLatLngToPoint topRight: " + topRight);
        GMapPoint bottomLeft = proj.fromLatLngToPoint(llb.getSouthWest());
//        System.out.println("GoogleMap.fromLatLngToPoint bottomLeft: " + bottomLeft);

        double scale = Math.pow(2, getZoom());
        GMapPoint worldPoint = proj.fromLatLngToPoint(loc);
//        System.out.println("GoogleMap.fromLatLngToPoint worldPoint: " + worldPoint);

        double x = (worldPoint.getX() - bottomLeft.getX()) * scale;
        double y = (worldPoint.getY() - topRight.getY()) * scale;

//        System.out.println("GoogleMap.fromLatLngToPoint x: " + x + " y: " + y);
        return new Point2D(x, y);
    }

    /**
     * Registers an event handler in the repository shared between Javascript
     * and Java.
     *
     * @param h Event handler to be registered.
     * @return Callback key that Javascript will use to find this handler.
     */
    private String registerEventHandler(GFXEventHandler h) {
        //checkInitialized();
        if (!registeredOnJS) {
            JSObject doc = (JSObject) runtime.execute("document");
            doc.setMember("jsHandlers", jsHandlers);
            registeredOnJS = true;
        }
        return jsHandlers.registerHandler(h);
    }

    /**
     * Adds a handler for a mouse type event on the map.
     *
     * @param type Type of the event to register against.
     * @param h Handler that will be called when the event occurs.
     */
    public void addUIEventHandler(UIEventType type, UIEventHandler h) {
        this.addUIEventHandler(this, type, h);
    }

    /**
     * Adds a handler for a mouse type event on the map.
     *
     * @param obj The object that the event should be registered on.
     * @param type Type of the event to register against.
     * @param h Handler that will be called when the event occurs.
     */
    public void addUIEventHandler(JavascriptObject obj, UIEventType type, UIEventHandler h) {
        addUIHandler(obj, type, h);
    }

    /**
     * Adds a handler for a mouse type event and returns an object that does not require interaction with
     * the underlying Javascript API.
     * 
     * @param type The type of event to listen for
     * @param h The MouseEventHandler that will handle the event.
     */
    public void addMouseEventHandler(UIEventType type, MouseEventHandler h) {
        addUIHandler(this, type, h);
    }

    protected void addUIHandler(JavascriptObject obj, UIEventType type, GFXEventHandler h) {
        String key = registerEventHandler(h);
        String mcall = "google.maps.event.addListener(" + obj.getVariableName() + ", '" + type.name() + "', "
                + "function(event) {document.jsHandlers.handleUIEvent('" + key + "', event);});";//.latLng
        //System.out.println("addUIEventHandler mcall: " + mcall);
        runtime.execute(mcall);
    }

    /**
     * Adds a handler for a state type event on the map.
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
