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

import com.lynden.gmapsfx.javascript.object.MarkerOptions;
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
public class JavascriptTypeTest {

    IJavascriptRuntime mockJSRuntime;
    JSObject mockJsObject;
    JavascriptType testJavascriptType;

    public JavascriptTypeTest() {
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
        testJavascriptType = new JavascriptType(Type.OBJECT);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetProperty_Null() {
        assertNull(testJavascriptType.getProperty("foo"));
    }

    @Test
    public void testGetProperty_String() {
        testJavascriptType.properties.put("MyString", "MyStringValue");
        assertEquals( "\"MyStringValue\"", testJavascriptType.getProperty("MyString") );
    }
    
    @Test
    public void testGetProperty_Boolean() {
        testJavascriptType.properties.put( "MyBool", true);
        assertEquals("true", testJavascriptType.getProperty("MyBool"));
    }
    
    @Test
    public void testGetProperty_JavascriptType() {
        JavascriptType myJSType = new JavascriptType(Type.OBJECT);
        myJSType.variableName = "MyJSType1";
        testJavascriptType.properties.put("MyJSType",myJSType);
        assertEquals("MyJSType1", testJavascriptType.getProperty("MyJSType"));
    }

    
    @Test
    public void testGetProperties() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("mytitle")
                .visible(true)
                .variableName ="MyMapMarkerOptions";
                

        testJavascriptType.setProperty("booleanProp", Boolean.TRUE);
        testJavascriptType.setProperty("markOptionsProp", markerOptions);
        testJavascriptType.setProperty("stringProp", "myStringProp");

        String props = testJavascriptType.getProperties();
        
        StringBuilder sb = new StringBuilder();
        sb.append( "{\nbooleanProp: true,\nmarkOptionsProp: MyMapMarkerOptions,\nstringProp: \"myStringProp\"\n}");

        System.out.println(testJavascriptType.getProperties());
        assertEquals( sb.toString(), testJavascriptType.getProperties() );
        
    }

}
