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

import com.lynden.gmapsfx.javascript.JavascriptRuntime;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Map;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class GoogleMapComponent extends AnchorPane {

    protected WebView webview;
    protected WebEngine webengine;
    protected boolean loaded = false;
    protected final CyclicBarrier barrier = new CyclicBarrier(2);
    protected final List<MapInitializedListener> mapInitializedListeners = new ArrayList<>();
    protected Map map;
    
    public GoogleMapComponent() {
        webview = new WebView();
        webengine = webview.getEngine();
        JavascriptRuntime.engine = webengine;
        getChildren().add(webview);

        webengine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        System.out.println("new state: " + newState );
                        if (newState == Worker.State.SUCCEEDED) {
                            // webengine.executeScript("initialize()");
                            map = new Map();
                            map.setCenter(new LatLong(10, 150));
                            map.setZoom(4);
                            setLoaded(true);
                            MarkerOptions options = new MarkerOptions();
                            options.setTitle("My Title" );
                            Marker marker = new Marker(options);
                            fireMapInitializedListeners();
                            try {
                          //      barrier.await(10, TimeUnit.SECONDS);
//                                setCenter(50, 50);
//                                setZoom(10);
                            } catch (Exception ex) {
                                throw new IllegalStateException(ex);
                            }
                        }
                    }
                });
        
        System.out.println("Start loading");
        webengine.load(getClass().getResource("/html/maps.html").toExternalForm());

        System.out.println("done loading");
        
//        while (! loaded ) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }

    }

    public void setZoom(int zoom) {
        map.setZoom(zoom);
    }

    public void setCenter(double latitude, double longitude) {
        LatLong latLong = new LatLong(latitude, longitude);
        map.setCenter(latLong);
    }

    
    public void addMapInializedListener( MapInitializedListener listener ) {
        synchronized(mapInitializedListeners) {
            mapInitializedListeners.add(listener);
        }
    }
    
    public void removeMapInitializedListener( MapInitializedListener listener ) {
        synchronized(mapInitializedListeners) {
            mapInitializedListeners.remove(listener);
        }
    }
    
    protected void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    
    protected void fireMapInitializedListeners() {
        synchronized(mapInitializedListeners){
            for( MapInitializedListener listener : mapInitializedListeners ) {
                listener.mapInitialized();
            }
        }
    }
    

    protected JSObject executeJavascript(String function) {
        Object returnObject = webengine.executeScript(function);
        System.out.println("Return object: " + returnObject );
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

}
