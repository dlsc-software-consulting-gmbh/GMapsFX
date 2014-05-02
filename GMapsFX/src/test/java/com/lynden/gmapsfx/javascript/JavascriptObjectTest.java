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

import com.lynden.gmapsfx.javascript.object.LatLong;
import netscape.javascript.JSObject;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author Rob Terpilowski
 */
public class JavascriptObjectTest {

    IJavascriptRuntime mockJSRuntime;
    JSObject mockJsObject;
    JavascriptObject testJavascriptObject;

    public JavascriptObjectTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mockJSRuntime = Mockito.mock(IJavascriptRuntime.class);
        mockJsObject = Mockito.mock(JSObject.class);
        JavascriptRuntime.runtime = mockJSRuntime;
        when(mockJSRuntime.execute(any(String.class))).thenReturn(mockJsObject);
         JavascriptObject.objectCounter = 0;
        testJavascriptObject = new JavascriptObject(JavascriptObjectType.OBJECT);
       
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetJSObject() {
        assertEquals(mockJsObject, testJavascriptObject.getJSObject());
    }
    
    @Test
    public void testGetNextVariableName() {
        JavascriptObject.objectCounter = 1;
        assertEquals( "JavascriptObject1", testJavascriptObject.getNextVariableName() );
    }
    
    @Test 
    public void testGetVariableName() {
        assertEquals( "JavascriptObject0", testJavascriptObject.getVariableName());
    }
    
    @Test
    public void testSetProperty_Object() {
        testJavascriptObject.setProperty("MyBoolProp", Boolean.FALSE);
        assertEquals( Boolean.FALSE, testJavascriptObject.properties.get("MyBoolProp" ) );
        verify(mockJsObject).setMember("MyBoolProp", Boolean.FALSE);
    }
    
    @Test
    public void testSetProperty_JavascriptObject() {
        LatLong latLong = new LatLong(1, 1);
        testJavascriptObject.setProperty("LatLong", latLong);
        assertEquals( latLong, testJavascriptObject.properties.get("LatLong" ) );
        verify(mockJsObject).setMember("LatLong", latLong.getVariableName());
    }    
    
    @Test
    public void testGetProperty() {
        testJavascriptObject.properties.put("myprop", "myvalue");
        assertEquals( testJavascriptObject.getProperty("myprop"), "myvalue");
    }
    
    @Test
    public void testGetPropertyGeneric() {
        LatLong latLong = new LatLong(1, 1);
        testJavascriptObject.setProperty("LatLong", latLong);    
        LatLong actualLatLong = testJavascriptObject.getProperty("LatLong", LatLong.class);
        assertEquals( latLong, actualLatLong);
    }    
    
    @Test
    public void testGetPropertyGeneric_Null() {
        assertNull(testJavascriptObject.getProperty("foo", String.class));
    }
    
    
    @Test
    public void testGetPropertyAsString_String() {
        testJavascriptObject.properties.put("MyString", "MyStringValue");
        assertEquals( "\"MyStringValue\"", testJavascriptObject.getPropertyAsString("MyString") );
    }
    
    @Test
    public void testGetPropertyAsString_Boolean() {
        testJavascriptObject.properties.put( "MyBool", true);
        assertEquals("true", testJavascriptObject.getPropertyAsString("MyBool"));
    }
    
    @Test
    public void testGetPropertyAsString_JavascriptType() {
        JavascriptObject myJSType = new JavascriptObject(JavascriptObjectType.OBJECT);
        myJSType.variableName = "MyJSType1";
        testJavascriptObject.properties.put("MyJSType",myJSType);
        assertEquals("MyJSType1", testJavascriptObject.getPropertyAsString("MyJSType"));
    }
    
    @Test
    public void testGetPropertiesAsString() {
        StringBuilder expectedString = new StringBuilder();
        expectedString.append("{\n").append("prop1: \"value1\",\n").append("prop2: true\n}");
        testJavascriptObject.setProperty("prop1", "value1");
        testJavascriptObject.setProperty("prop2", true);
        
        assertEquals( expectedString.toString(), testJavascriptObject.getPropertiesAsString() );
        
    }

    @Test
    public void testInvokeJavascript() {
        String arg = "myArg";
        Object[] argArray = new Object[] {arg};
        String function = "myFunction";
        String result = "result";
        
        when(mockJsObject.call(function, argArray)).thenReturn(result);
        Object returnValue = testJavascriptObject.invokeJavascript(function, arg);
        assertEquals( result, returnValue );
    }
    
    @Test
    public void testInvokeJavascript_JavascriptObject() {
        JSObject returnJSObject = mock(JSObject.class);
        JavascriptObject functionArg = mock(JavascriptObject.class);
        
        
        Object[] argArray = new Object[] {functionArg};
        Object[] jsCallArgArray = new Object[]{returnJSObject};
        String function = "myFunction";
        String result = "result";
        
        
        when(functionArg.getJSObject()).thenReturn(returnJSObject);
        when(mockJsObject.call(function, jsCallArgArray)).thenReturn(result);

        Object returnValue = testJavascriptObject.invokeJavascript(function, functionArg);
        assertEquals( result, returnValue );
    }    
    
    
    @Test
    public void testInvokeJavascriptReturnValue() {
        String arg = "myArg";
        Object[] argArray = new Object[] {arg};
        String function = "myFunction";
        String result = "result";
        
        when(mockJsObject.call(function, argArray)).thenReturn(result);
        String returnValue = testJavascriptObject.invokeJavascriptReturnValue(function, String.class, arg);
        assertEquals( result, returnValue );        
    }
    
    @Test
    public void testInvokeJavascriptReturnValue_Null() {
        String arg = "myArg";
        Object[] argArray = new Object[] {arg};
        String function = "myFunction";
        
        when(mockJsObject.call(function, argArray)).thenReturn(null);
        String returnValue = testJavascriptObject.invokeJavascriptReturnValue(function, String.class, arg);
        assertNull( returnValue );        
    }    

}
