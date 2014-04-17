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
public class JavascriptRuntime {
    
    protected static JavascriptRuntime runtime = null;
       
    public static WebEngine engine;
    
    public static JavascriptRuntime getInstance() {
        if( runtime == null ) {
            runtime = new JavascriptRuntime();
        }
        return runtime;
    }
    
    public JSObject execute(String command) {
        Object returnValue = engine.executeScript(command);
        if( returnValue instanceof JSObject ) {
            return (JSObject) returnValue;
        }
        
        return null;
    }

    
    public String getConstructor( String type, Object... args ) {
        return getFunction( "new " + type, args );
    }
    
    public String getFunction(String variable, String function, Object... args) {
        return getFunction( variable + "." + function, args );
    }
    
    public String getFunction(String function, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(function).append("(");
        for (Object arg : args) {
            sb.append(arg).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");

        return sb.toString();
    }
}
