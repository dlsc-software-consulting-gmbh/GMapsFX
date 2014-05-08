/*
 * Copyright 2014 Geoff Capper.
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
import com.lynden.gmapsfx.javascript.JavascriptObjectType;
import netscape.javascript.JSObject;

/**
 *
 * @author Geoff Capper
 */
public class MVCArray extends JavascriptObject {
    
    public MVCArray() {
        this(new Object[]{});
    }
    
    public MVCArray(Object[] ary) {
        super(JavascriptObjectType.MVC_ARRAY, ary, true);
    }
    
    public MVCArray(JSObject obj) {
        super(JavascriptObjectType.MVC_ARRAY, obj);
    }
    
    // Removes all elements from the array.
    public void clear() {
        invokeJavascript("clear");
    }
    
    //forEach(callback:function(*, number)) 	None 	Iterate over each element, calling the provided callback. The callback is called for each element like: callback(element, index).
    //getArray() 	Array 	Returns a reference to the underlying Array. Warning: if the Array is mutated, no events will be fired by this object.
    
    //getAt(i:number) 	* 	Returns the element at the specified index.
//    public JSObject getAt(int i) {
//        return invokeJavascriptReturnValue("getAt", i, JSObject.class);
//    }
    //getLength() 	number 	Returns the number of elements in this array.
    public int getLength() {
        return (int) invokeJavascript("getLength");
    }
    
    //insertAt(i:number, elem:*) 	None 	Inserts an element at the specified index.
    
    //pop() 	* 	Removes the last element of the array and returns that element.
    
    //push(elem:*) 	number 	Adds one element to the end of the array and returns the new length of the array.
    
    //removeAt(i:number) 	* 	Removes an element from the specified index.
    
    //setAt(i:number, elem:*)
    
}
