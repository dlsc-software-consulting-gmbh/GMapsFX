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
    protected JSObject map;
    protected boolean loaded = false;
    
    
    public GoogleMapComponent() {
        webview = new WebView();
        webengine = webview.getEngine();
        JavascriptRuntime.engine = webengine;
        getChildren().add(webview);
        webengine.load(getClass().getResource("/html/maps.html").toExternalForm());

        webengine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        System.out.println("new state: " + newState);
                        if (newState == Worker.State.SUCCEEDED) {
                           // webengine.executeScript("initialize()");
                            Map map = new Map();
                            map.setCenter( new LatLong(10, 150));
                            map.setZoom(4);
                            setLoaded(true);
                            try {
                                //barrier.await(10,TimeUnit.SECONDS);
//                                setCenter(50, 50);
//                                setZoom(10);
                            } catch (Exception ex) {
                                throw new IllegalStateException(ex);
                            }
                        }
                    }
                });
    }
    
    
    
    public void setZoom( int zoom ) {
        getMap().call("setZoom", zoom);
    }
    
    
//    public void setCenter( double latitude, double longitude ) {
//        LatLong latLong = new LatLong(latitude, longitude);
//        JSObject latLongJS = executeJavascript( latLong.getJavascriptConstructor() );
//        getMap().call("setCenter", latLongJS );
//    }
    
    
    protected void setLoaded( boolean loaded ) {
        this.loaded = loaded;
    }
    
    protected JSObject getMap() {
        if( map == null ) {
             map = executeJavascript("map");
        }
        return map;
    }
    
    protected JSObject executeJavascript( String function ) {
        return (JSObject) webengine.executeScript( function );
    }
    
    protected String getJavascriptMethod( String methodName, Object... args ) {
        StringBuilder sb = new StringBuilder();
        sb.append( methodName ).append("(" );
        for( Object arg : args ) {
            sb.append(arg).append(",");
        }
        sb.replace(sb.length()-1, sb.length(), ")");
        
        return sb.toString();
    }
    
    
    

}
