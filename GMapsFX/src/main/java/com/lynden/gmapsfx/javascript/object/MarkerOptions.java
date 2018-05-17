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
 *
 * @author Rob Terpilowski
 */
public class MarkerOptions extends JavascriptObject {
    
    protected LatLong position;
    protected String title;
    protected boolean visible = true;
    protected String icon;
    protected Animation animation;
    //protected String label;
    
    public MarkerOptions() {
        super(GMapObjectType.OBJECT);
    }
    
    public MarkerOptions title( String title ) {
        setProperty("title", title);
        this.title = title;
        return this;
    }
    
    public MarkerOptions visible( Boolean visible ) {
        setProperty( "visible", visible );
        this.visible = visible;
        return this;
    }
    
    
    public MarkerOptions position( LatLong latLong ) {
        setProperty("position", latLong);
        position = latLong;
        return this;
    }
    
    
    public MarkerOptions icon( String iconPath ) {
        setProperty("icon", iconPath);
        return this;
    }
    
    public MarkerOptions animation( Animation animation ) {
        setProperty("animation", animation);
        return this;
    }
    
    public MarkerOptions label( String label ) {
        setProperty("label", label);
        return this;
    }
    
    
}
