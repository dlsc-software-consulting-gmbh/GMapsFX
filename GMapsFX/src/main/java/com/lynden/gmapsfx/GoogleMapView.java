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
package com.lynden.gmapsfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.lynden.gmapsfx.javascript.JavaFxWebEngine;
import com.lynden.gmapsfx.javascript.JavascriptRuntime;
import com.lynden.gmapsfx.javascript.event.MapStateEventType;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.util.MarkerImageFactory;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class GoogleMapView extends AnchorPane {
	
    protected static final String GOOGLE_MAPS_API_LINK = "https://maps.googleapis.com/maps/api/js?v=3.exp";
    protected static final String GOOGLE_MAPS_API_VERSION = "3.exp";

    private boolean usingCustomHtml;

    protected final String language;
    protected final String region;
    protected String key;
    protected WebView webview;
    protected JavaFxWebEngine webengine;
    protected boolean initialized = false;
    protected final CyclicBarrier barrier = new CyclicBarrier(2);
    protected final List<MapComponentInitializedListener> mapInitializedListeners = new ArrayList<>();
    protected final List<MapReadyListener> mapReadyListeners = new ArrayList<>();
    protected GoogleMap map;
    protected DirectionsPane direc;
    protected boolean disableDoubleClick = false;

    public GoogleMapView() {
        this(false);
    }
    
    /**
     * Allows for the creation of the map using external resources from another
     * jar for the html page and markers. The map html page must be sourced from
     * the jar containing any marker images for those to function.
     * <p>
     * The html page is, at it's simplest: null     {@code 
	 * <!DOCTYPE html>
     * <html>
     *   <head>
     *     <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
     *     <meta charset="utf-8">
     *     <title>My Map</title>
     *     <style>
     *     html, body, #map-canvas {
     *       height: 100%;
     *       margin: 0px;
     *       padding: 0px
     *     }
     * </style>
     * <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
     * </head>
     * <body>
     * <div id="map-canvas"></div>
     * </body>
     * </html> }
     * <p>
     * If you store this file in your project jar, under
     * my.gmapsfx.project.resources as mymap.html then you should call using
     * "/my/gmapsfx/project/resources/mymap.html" for the mapResourcePath.
     * <p>
     * Your marker images should be stored in the same folder as, or below the
     * map file. You then reference them using relative notation. If you put
     * them in a subpackage "markers" you would create your MarkerOptions object
     * as follows: null     {@code
	 * myMarkerOptions.position(myLatLong)
     *     .title("My Marker")
     *     .icon("markers/mymarker.png")
     *     .visible(true);
     * }
     *
     * @param mapResourcePath
     */
    public GoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath) {
        this(mapResourcePath, false);
    }

    public GoogleMapView(@NamedArg("debug") boolean debug) {
        this(null, debug);
    }
    
    /**
     * Creates a new map view and specifies if the FireBug pane should be
     * displayed in the WebView
     *
     * @param mapResourcePath
     * @param debug true if the FireBug pane should be displayed in the WebView.
     */
	public GoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath, @NamedArg("debug") boolean debug) {
		this(mapResourcePath, "en", "US", null, debug);
	}

    /**
     * Creates a new map view and specifies the display language and API key.
     *
     * @param mapResourcePath
     * @param key Google Maps API key or null
     * @param debug true if the FireBug pane should be displayed in the WebView.
     */
    public GoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath, @NamedArg("key") String key, @NamedArg("debug") boolean debug) {
        this(mapResourcePath, "en", "US", key, debug);
    }
    
    public GoogleMapView(@NamedArg("key") String key, @NamedArg("language") String language, @NamedArg("region") String region) {
    	this(null, language, region, key, false);
    }

    /**
     * Creates a new map view and specifies the display language and API key.
     * <p>
     * If you are specifying your own HTML page for mapResourcePath in a jar of
     * your own then you should include a script element to pull in the Google
     * Maps API with any API keys, language and region parameters.
     *
     * @param mapResourcePath
     * @param language map display language, null for default
     * @param region
     * @param key Google Maps API key or null
     * @param debug true if the FireBug pane should be displayed in the WebView.
     */
	public GoogleMapView(@NamedArg("mapResourcePath") String mapResourcePath, @NamedArg("language") String language, @NamedArg("region") String region, @NamedArg("key") String key, @NamedArg("debug") boolean debug) {
        this.language = language;
        this.region = region;
        this.key = key;

        String htmlFile;
        if (mapResourcePath == null) {
            htmlFile = getHtmlFile(debug);
        } else {
            htmlFile = mapResourcePath;
            usingCustomHtml = true;
        }

        CountDownLatch latch = new CountDownLatch(1);
        Runnable initWebView = () -> {
            try {
                webview = new WebView();
                EventDispatcher originalDispatcher = webview.getEventDispatcher();
                webview.setEventDispatcher(new MyEventDispatcher(originalDispatcher));
                webengine = new JavaFxWebEngine(webview.getEngine());
                JavascriptRuntime.setDefaultWebEngine(webengine);
                setFont(webview.getEngine());

                setTopAnchor(webview, 0.0);
                setLeftAnchor(webview, 0.0);
                setBottomAnchor(webview, 0.0);
                setRightAnchor(webview, 0.0);
                getChildren().add(webview);

                webview.widthProperty().addListener(e -> mapResized());
                webview.heightProperty().addListener(e -> mapResized());

                webengine.setOnAlert(e -> Logger.getLogger(MarkerImageFactory.class.getName()).log(Level.INFO, "Alert: " + e.getData()));
                webengine.setOnError(e -> Logger.getLogger(MarkerImageFactory.class.getName()).log(Level.FINEST, "Error: " + e.getMessage()));

                webengine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            initialiseScript();
                            //setInitialized(true);
                            //fireMapInitializedListeners();

                        }
                    }
                });
                webengine.load(getClass().getResource(htmlFile).toExternalForm());
            } finally {
                latch.countDown();
            }
        };

        if (Platform.isFxApplicationThread()) {
            initWebView.run();
        } else {
            Platform.runLater(initWebView);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    protected String getHtmlFile(boolean debug) {
        if (debug) {
           return "html/maps-debug.html";
        } else {
           return "html/maps.html";
        }
    }

    private void initialiseScript() {
        if (!usingCustomHtml) {
            JSObject window = (JSObject) webengine.executeScript("window");
            window.setMember("libLoadBridge", new MapLibraryLoadBridge());

            String script = "loadMapLibrary('" + GOOGLE_MAPS_API_VERSION + "','" + key + "','" + language + "','" + region + "');";
            webengine.executeScript(script);
        } else {
            setInitialized(true);
            fireMapInitializedListeners();
        }
    }

    private void setFont(WebEngine webEngine) {

        webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {

            if (newValue == Worker.State.SUCCEEDED) {
                Document document = (Document) webEngine.executeScript("document");

                Element styleNode = document.createElement("style");
                Text styleContent = document.createTextNode("* { font-family: Arial, Helvetica, san-serif !important; }");
                styleNode.appendChild(styleContent);
                document.getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);
            }

        });

    }

    private void mapResized() {
        if (initialized && map != null) {
            webengine.executeScript("google.maps.event.trigger(" + map.getVariableName() + ", 'resize')");
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setZoom(int zoom) {
        checkInitialized();
        map.setZoom(zoom);
    }

    public void setCenter(double latitude, double longitude) {
        checkInitialized();
        LatLong latLong = new LatLong(latitude, longitude);
        map.setCenter(latLong);
    }

    public GoogleMap getMap() {
        checkInitialized();
        return map;
    }

    public GoogleMap createMap(MapOptions mapOptions) {
        return createMap(mapOptions, false);
    }

    public GoogleMap createMap() {
        return createMap(null, false);
    }

    public GoogleMap createMap(boolean withDirectionsPanel) {
        return createMap(null, withDirectionsPanel);
    }

    public GoogleMap createMap(MapOptions mapOptions, boolean withDirectionsPanel) {
        checkInitialized();
        if (mapOptions != null) {
            map = internal_createMap(mapOptions);
        } else {
            map = internal_createMap();
        }

        direc = new DirectionsPane();
        if (withDirectionsPanel) {
            map.showDirectionsPane();
        }

        map.addStateEventHandler(MapStateEventType.projection_changed, () -> {
            if (map.getProjection() != null) {
                mapResized();
                fireMapReadyListeners();
            }
        });

        return map;
    }

    protected GoogleMap internal_createMap() {
        return new GoogleMap();
    }

    protected GoogleMap internal_createMap(MapOptions mapOptions) {
        return new GoogleMap(mapOptions);
    }

    public DirectionsPane getDirec() {
        return direc;
    }

    public void addMapInitializedListener(MapComponentInitializedListener listener) {
        synchronized (mapInitializedListeners) {
            mapInitializedListeners.add(listener);
        }
    }

    public void removeMapInitializedListener(MapComponentInitializedListener listener) {
        synchronized (mapInitializedListeners) {
            mapInitializedListeners.remove(listener);
        }
    }

    public void addMapReadyListener(MapReadyListener listener) {
        synchronized (mapReadyListeners) {
            mapReadyListeners.add(listener);
        }
    }

    public void removeReadyListener(MapReadyListener listener) {
        synchronized (mapReadyListeners) {
            mapReadyListeners.remove(listener);
        }
    }

    public Point2D fromLatLngToPoint(LatLong loc) {
        checkInitialized();
        return map.fromLatLngToPoint(loc);
    }

    public void panBy(double x, double y) {
        checkInitialized();
        map.panBy(x, y);
    }

    public boolean isDisableDoubleClick() {
        return disableDoubleClick;
    }

    public void setDisableDoubleClick(boolean disableDoubleClick) {
        this.disableDoubleClick = disableDoubleClick;
    }

    protected void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    protected void fireMapInitializedListeners() {
        synchronized (mapInitializedListeners) {
            for (MapComponentInitializedListener listener : mapInitializedListeners) {
                listener.mapInitialized();
            }
        }
    }

    protected void fireMapReadyListeners() {
        synchronized (mapReadyListeners) {
            for (MapReadyListener listener : mapReadyListeners) {
                listener.mapReady();
            }
        }
    }

    protected JSObject executeJavascript(String function) {
        Object returnObject = webengine.executeScript(function);
        return (JSObject) returnObject;
    }

    protected String getJavascriptMethod(String methodName, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodName).append("(");
        for (Object arg : args) {
            sb.append(arg).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");

        return sb.toString();
    }

    protected void checkInitialized() {
        if (!initialized) {
            throw new MapNotInitializedException();
        }
    }

    public WebView getWebview() {
        return webview;
    }

    public class MapLibraryLoadBridge {

        public MapLibraryLoadBridge() {
        }

        public void mapLibraryLoaded() {
            setInitialized(true);
            fireMapInitializedListeners();
        }

    }

    public class MyEventDispatcher implements EventDispatcher {

        private final EventDispatcher originalDispatcher;

        public MyEventDispatcher(EventDispatcher originalDispatcher) {
            this.originalDispatcher = originalDispatcher;
        }

        @Override
        public Event dispatchEvent(Event event, EventDispatchChain tail) {

            if (event instanceof MouseEvent) {
                MouseEvent mouseEvent = (MouseEvent) event;
                if (mouseEvent.getClickCount() > 1) {
                    if (disableDoubleClick) {
                        mouseEvent.consume();
                    }
                }
            }
            return originalDispatcher.dispatchEvent(event, tail);
        }
    }
    
}
