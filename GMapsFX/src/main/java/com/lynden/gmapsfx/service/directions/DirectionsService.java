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
public class DirectionsService extends JavascriptObject{
    public DirectionsServiceCallback callback;
    public DirectionsRenderer renderer;
    
    public DirectionsService(){
        super(GMapObjectType.DIRECTIONS_SERVICE);
    }
    
    public void getRoute(DirectionsRequest req, DirectionsServiceCallback callback, DirectionsRenderer renderer){
        this.callback = callback;
        this.renderer = renderer;
        
        JSObject doc = (JSObject) getJSObject().eval("document");
        doc.setMember(getVariableName(), this);
        StringBuilder r = new StringBuilder(getVariableName())
                .append(".")
                .append("route(")
                .append(req.getVariableName())
                .append(", ")
                .append("function(results, status) {\n")
                .append("if(status === 'OK'){\n")
                .append(renderer.getVariableName())
                .append(".setDirections(results);\ndocument.")
                .append(getVariableName())
                .append(".processResponse(results, status);\n}")
                .append("});");
        
        System.out.println("Directions direct call: " + r.toString());
        try{
            getJSObject().eval(r.toString());
        } catch(Throwable t){
            System.out.println(t.getMessage());
        }
    }
    
     public void processResponse(Object results, Object status) {
        System.out.println("STATUS: " + (String) status);
        DirectionStatus pStatus = DirectionStatus.UNKNOWN_ERROR;
        if (status instanceof String && results instanceof JSObject) {
            pStatus = DirectionStatus.valueOf((String) status);
            if (DirectionStatus.OK.equals(pStatus)) {
                System.out.println("\n\nResults: " + results);
                DirectionsResult ers = new DirectionsResult((JSObject) results);
                callback.directionsReceived(ers, pStatus);
                return;
            }
        }
        callback.directionsReceived(new DirectionsResult(), pStatus);
    }
}
