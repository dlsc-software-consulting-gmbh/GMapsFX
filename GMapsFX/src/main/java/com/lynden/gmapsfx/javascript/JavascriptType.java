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

import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class JavascriptType {
    
    protected JavascriptRuntime runtime;
    protected JSObject javascriptObject;
    protected static int objectCounter = 0;
    protected String variableName;
    
    public JavascriptType(String type, Object... args ) {
        runtime = JavascriptRuntime.getInstance();
        variableName = getNextVariableName();
        runtime.execute( "var " + variableName + " = " + runtime.getConstructor(type, args) );
        javascriptObject = runtime.execute( variableName );
    }
    
    
    public JSObject getJSObject() {
        return javascriptObject;
    }
    
    protected final String getNextVariableName() {
        return getClass().getSimpleName() + (objectCounter++);
    }
    
    public String getVariableName() {
        return variableName;
    }
    
    public void invokeJavascript( String method, Object... args ) {
        Object[] jsArgs = new Object[args.length];
        for( int i = 0; i < jsArgs.length; i++ ) {
            if( args[i] instanceof JavascriptType ) {
                jsArgs[i] = ((JavascriptType) args[i]).getJSObject();
            } else {
                jsArgs[i] = args[i];
            }
        }
        javascriptObject.call(method, (Object[]) jsArgs);
    }
}
    