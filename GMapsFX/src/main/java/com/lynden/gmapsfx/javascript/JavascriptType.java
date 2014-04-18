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
 *
 * @author Rob Terpilowski
 */
public class JavascriptType {

    protected IJavascriptRuntime runtime;
    protected JSObject javascriptObject;
    protected static int objectCounter = 0;
    protected String variableName;
    protected Map<String, Object> properties = new LinkedHashMap<>();

    protected JavascriptType(Type type) {
        this(type, (Object[]) null);
    }

    protected JavascriptType(Type type, Object... args) {
        runtime = JavascriptRuntime.getInstance();
        variableName = getNextVariableName();
        runtime.execute("var " + variableName + " = " + runtime.getConstructor(type, args));
        javascriptObject = runtime.execute(variableName);
        System.out.println("JS Object: " + javascriptObject.toString());
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

    protected void setProperty(String propertyName, Object propertyValue) {
        javascriptObject.setMember(propertyName, propertyValue);
        properties.put(propertyName, propertyValue);
    }

//    protected void setProperty(String propertyName, String propertyValue) {
//        javascriptObject.setMember(propertyName, propertyValue);
//        properties.put(propertyName, propertyValue);
//    }
//
//    protected void setProperty(String propertyName, Boolean propertyValue) {
//        javascriptObject.setMember(propertyName, propertyValue);
//        properties.put(propertyName, propertyValue);
//    }

    protected void setProperty(String propertyName, JavascriptType propertyValue) {
        javascriptObject.setMember(propertyName, propertyValue.getVariableName());
        properties.put(propertyName, propertyValue);
    }

    public String getProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (String key : properties.keySet()) {
            sb.append(key).append(": ").append(getProperty(key)).append(",\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "\n}");

        return sb.toString();

    }

    protected Object getProperty(String key) {
        Object returnValue = null;
        Object value = properties.get(key);
        if (value != null) {
            if (value instanceof String) {
                returnValue = "\"" + value + "\"";
            } else if (value instanceof JavascriptType) {
                returnValue = ((JavascriptType) value).getVariableName();
            } else {
                returnValue = value.toString();
            }
        }
        return returnValue;
    }

    protected void invokeJavascript(String method, Object... args) {
        Object[] jsArgs = new Object[args.length];
        for (int i = 0; i < jsArgs.length; i++) {
            if (args[i] instanceof JavascriptType) {
                jsArgs[i] = ((JavascriptType) args[i]).getJSObject();
            } else {
                jsArgs[i] = args[i];
            }
        }
        javascriptObject.call(method, (Object[]) jsArgs);
    }
}
