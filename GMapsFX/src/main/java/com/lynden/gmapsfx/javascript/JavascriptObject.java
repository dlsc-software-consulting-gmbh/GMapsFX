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

import java.util.LinkedHashMap;
import java.util.Map;
import netscape.javascript.JSObject;

/**
 * Base class for any Google JavaScript object.
 * 
 * @author Rob Terpilowski
 */
public class JavascriptObject {

    protected IJavascriptRuntime runtime;
    protected JSObject jsObject;
    protected static int objectCounter = 0;
    protected String variableName;
    protected Map<String, Object> properties = new LinkedHashMap<>();
    
    

    /**
     * 
     * @param type The type of underlying Javascript object to create
     */
    protected JavascriptObject(JavascriptObjectType type) {
        this(type, (Object[]) null);
    }

    /**
     * @param type The type of underlying Javascript object to create.
     * @param args Any arguments required to create the object.
     */
    protected JavascriptObject(JavascriptObjectType type, Object... args) {
        runtime = JavascriptRuntime.getInstance();
        variableName = getNextVariableName();
        runtime.execute("var " + variableName + " = " + runtime.getConstructor(type, args));
        jsObject = runtime.execute(variableName);
    }

    /** Wraps a Javascript JSObject returned from a function.
     * 
     * @param type Type of Javascript object to create.
	 * @param jsObject Object returned from Javascript.
     */
    protected JavascriptObject(JavascriptObjectType type, JSObject jsObject) {
        runtime =  JavascriptRuntime.getInstance();
        variableName = getNextVariableName();
		this.jsObject = jsObject;
    }
    
    /**
     * Get the underlying object used by the Javascript runtime.
     * @return The underlying Javascript object
     */
    protected JSObject getJSObject() {
        return jsObject;
    }

    
    /**
     * Gets the name of the next variable which will be the objectname plus a unique number
     * @return The name of the next variable to create
     */
    protected final String getNextVariableName() {
        return getClass().getSimpleName() + (objectCounter++);
    }


    /**
     * Gets the name of this variable within the Javascript runtime
     * @return The name of this variable.
     */
    public String getVariableName() {
        
        return variableName;
    }

    
    /**
     * Sets a property on this Javascript object.  
     * @param propertyName The property name
     * @param propertyValue The property value.
     */
    protected void setProperty(String propertyName, Object propertyValue) {
        jsObject.setMember(propertyName, propertyValue);
        properties.put(propertyName, propertyValue);
    }

    /**
     * Sets a property on this Javascript object for which the value is a Javascript object itself.
     * @param propertyName The name of the property.
     * @param propertyValue The value of the property.
     */
    protected void setProperty(String propertyName, JavascriptObject propertyValue) {
        jsObject.setMember(propertyName, propertyValue.getVariableName());
        properties.put(propertyName, propertyValue);
    }
    

    /**
     * Get the specified property for this object.
     * @param key The property name
     * @return The value of the property
     */
    protected Object getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Gets the property and casts to the appropriate type
     * @param <T>
     * @param key The property name
     * @param type The property type
     * @return The value of the property
     */
    protected <T> T getProperty(String key, Class<T> type) {
        Object returnValue = getProperty(key);
        if (returnValue != null) {
            return (T) returnValue;
        } else {
            return null;
        }
    }
    
    
    /**
     * Builds a string representation of a single property.  If a property is a JavascriptObject, that object's variable name
     * is returned.
     * @param key The property name
     * @return The property as a string
     */
    protected String getPropertyAsString(String key) {
        String valueString = "";
        Object value = getProperty(key);
        if (value != null) {
            if (value instanceof String) {
                valueString = "\"" + value + "\"";
            } else if (value instanceof JavascriptObject) {
                valueString = ((JavascriptObject) value).getVariableName();
            } else {
                valueString = value.toString();
            }
        }
        return valueString;
    }    

    
    /**
     * Builds a JavaScript string representation of all the properties this object contains
     * @return A string representation of all the properties.
     */
    public String getPropertiesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (String key : properties.keySet()) {
            sb.append(key).append(": ").append(getPropertyAsString(key)).append(",\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "\n}");
        return sb.toString();
    }

    
    
    /**
     * Invokes a JavaScript function that takes no arguments.
     * @param function The function to invoke
     * @return The return value of the function call.
     */
    protected Object invokeJavascript( String function ) {
        return invokeJavascript( function, (Object) null );
    }
    
    
    /**
     * Invoke the specified JavaScript function in the JavaScript runtime.
     * @param function The function to invoke
     * @param args Any arguments to pass to the function
     * @return The result of the function.
     */
    protected Object invokeJavascript(String function, Object... args) {
        Object[] jsArgs = new Object[args.length];
        for (int i = 0; i < jsArgs.length; i++) {
            if (args[i] instanceof JavascriptObject) {
                jsArgs[i] = ((JavascriptObject) args[i]).getJSObject();
            } else {
                jsArgs[i] = args[i];
            }
        }
        return jsObject.call(function, (Object[]) jsArgs);
    }
    
    
    /**
     * Invokes a JavaScript function that takes no arguments.
     * 
     * @param <T>
     * @param function The function to invoke
     * @param returnType The type of object to return
     * @return The result of the function.
     */
    protected <T> T invokeJavascriptReturnValue(String function, Class<T> returnType ) {
        Object returnObject = invokeJavascript(function, (Object) null);
        if (returnObject != null) {
            return (T) returnObject;
        } else {
            return null;
        }
    }    

    
    /**
     * Invoke the specified JavaScript function in the JavaScript runtime.
     * 
     * @param <T>
     * @param function The function to invoke
     * @param returnType The type of object to return
     * @param args Any arguments to pass to the function
     * @return The result of the function.
     */
    protected <T> T invokeJavascriptReturnValue(String function, Class<T> returnType, Object... args) {
        Object returnObject = invokeJavascript(function, args);
        if (returnObject != null) {
            return (T) returnObject;
        } else {
            return null;
        }
    }
}
