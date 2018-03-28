/*
 * Copyright 2015 Andre.
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
package com.lynden.gmapsfx.service.directions;

import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andre
 */
public class DirectionsRenderer extends JavascriptObject{
    private static final Logger LOG = LoggerFactory.getLogger(DirectionsRenderer.class);

    public DirectionsRenderer() {
        super(GMapObjectType.DIRECTIONS_DISPLAY);
    }
    
    public DirectionsRenderer(JSObject type) {
        super(GMapObjectType.DIRECTIONS_DISPLAY, type);
        
        LOG.trace("map: " + (getJSObject().getMember("map").toString()) + "\n"+
                "draggable: " + getJSObject().getMember("draggable").toString());
    }
    
    public DirectionsRenderer(boolean drag, GoogleMap map, DirectionsPane panel){
        super(GMapObjectType.DIRECTIONS_DISPLAY);
        
        getJSObject().eval(getVariableName()+".setOptions({draggable:" +drag+"});");
        getJSObject().eval(getVariableName()+".setMap("+map.getVariableName()+");");
        getJSObject().eval(getVariableName()+".setPanel("+panel.getVariableName()+");");
    }
    
    /*
     * Enables to set the stroke color of the routes created using this renderer
    */
    public DirectionsRenderer(boolean drag, GoogleMap map, DirectionsPane panel, String strokeColor){
        super(GMapObjectType.DIRECTIONS_DISPLAY);
        
        getJSObject().eval(getVariableName()+".setOptions({draggable:" +drag+", polylineOptions: { strokeColor: '"+strokeColor+"'}});");
        getJSObject().eval(getVariableName()+".setMap("+map.getVariableName()+");");
        getJSObject().eval(getVariableName()+".setPanel("+panel.getVariableName()+");");
    }
    
    public void setMap(GoogleMap map){
        if( map == null ) {
            getJSObject().eval(getVariableName()+".setMap(null);");
        } else {
            getJSObject().eval(getVariableName()+".setMap("+map.getVariableName()+");");
        }
    }
    
    public void clearDirections() {
        setMap(null);
    }

    public void setOptions(String options){
        getJSObject().eval(getVariableName()+".setOptions({draggable:" +options+"});");
    }
    
    public void setPanel(DirectionsPane panel){
        getJSObject().eval(getVariableName()+".setPanel("+panel.getVariableName()+");");
    }
	
	public int getRoutesCount(){
		return Integer.valueOf(getJSObject().eval(getVariableName()+".directions.routes.length").toString());
	}
	
	public void setRouteIndex(int index){
		getJSObject().eval(getVariableName()+".setRouteIndex("+index+");");
	}

    @Override
    public String toString() {
        return "map: " + getJSObject().getMember("map").toString() 
                + "\n" + "draggable: " + getJSObject().getMember("draggable").toString() 
                + "\n" + "panel: " + getJSObject().getMember("panel").toString()
                + "\n" + "directions: " + getJSObject().getMember("directions").toString();
    }
    
    
}
