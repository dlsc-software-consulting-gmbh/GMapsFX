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
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import netscape.javascript.JSObject;

/**
 *
 * @author Andre
 */
public class Duration extends JavascriptObject{
    
    public Duration(JSObject jsObject){
        super(GMapObjectType.DURATION, jsObject);
    }
        
    public Duration(double value, String text){
        super(GMapObjectType.DURATION, value, text);   
    }
    
    public double getValue(){
        return invokeJavascriptReturnValue("value", Number.class).doubleValue();
    }
    
    public String getText(){
        return invokeJavascriptReturnValue("text", String.class);
    }
    
}
