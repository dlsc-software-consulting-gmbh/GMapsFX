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
 * Marker which can be placed on a GoogleMap.
 * 
 * @author Rob Terpilowski
 */
public class Marker extends JavascriptObject {
    
    /**
     * Contructs a new map Marker with the specified options
     * @param markerOptions The options to use when constructing this marker.
     */
    public Marker(MarkerOptions markerOptions) {
        super(GMapObjectType.MARKER, markerOptions);
    }
    
    
    public String getTitle() {
        return invokeJavascriptReturnValue("getTitle", String.class);
    }
    
    /**
     * Sets the title of this Marker
     * @param title The Marker's new title
     */
    public void setTitle( String title ) {
        invokeJavascript("setTitle", title);
    }
    
    
    /**
     * This method is called from the GoogleMap.addMarker() method, it should not be invoked directly.
     *
     * @param map The map to add this Marker to.
     */
    protected void setMap( GoogleMap map ) {
        invokeJavascript("setMap", map);
    }
    
    
    /**
     * Sets how the marker should be animated.  To clear the animation use Animation.NULL
     * @param animation The animation to use for this marker.
     */
    public void setAnimation( Animation animation ) {
        invokeJavascript("setAnimation", animation);
    }
    
    
    public void setPosition( LatLong latLong ) {
        invokeJavascript( "setPosition", latLong );
    }

	public void setOptions(MarkerOptions markerOptions2) {
		invokeJavascript("setOptions", markerOptions2);
	}
    
	public void setVisible(boolean visible) {
		invokeJavascript("setVisible", visible);
	}
	
	public boolean getVisible() {
		return invokeJavascriptReturnValue("getVisible", Boolean.class );
	}
    
}
