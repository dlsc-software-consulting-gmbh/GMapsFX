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
package com.lynden.gmapsfx.javascript;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class JavascriptRuntime implements IJavascriptRuntime {
    
    protected static IJavascriptRuntime runtime = null;
    
       
    public static WebEngine engine;
    
    public static IJavascriptRuntime getInstance() {
        if( runtime == null ) {
            runtime = new JavascriptRuntime();
        }
        return runtime;
    }
    
    @Override
    public JSObject execute(String command) {
        Object returnValue = engine.executeScript(command);
        if( returnValue instanceof JSObject ) {
            return (JSObject) returnValue;
        }
        
        return null;
    }

    
    @Override
    public String getConstructor( Type type, Object... args ) {
        return getFunction( "new " + type, args );
    }
    
    @Override
    public String getFunction(String variable, String function, Object... args) {
        return getFunction( variable + "." + function, args );
    }
    
    @Override
    public String getFunction(String function, Object... args) {
        if( args == null ) {
            return function + "();";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(function).append("(");
        for (Object arg : args) {
            sb.append(getArgString(arg)).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");

        return sb.toString();
    }
    
    
    protected String getArgString( Object arg ) {
        if( arg instanceof JavascriptType ) {
            return ((JavascriptType) arg).getProperties();
        } else {
            return arg.toString();
        }
    }
}
