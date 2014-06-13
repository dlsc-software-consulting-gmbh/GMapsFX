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

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author robt
 */
public class JavascriptFunctionLineTest {
    
    public JavascriptFunctionLineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Attempt to create a function as an object
     * 
     * var myCallback = function( argX ) {
     *  someVariable.someMethod( argX );
     *  someOtherVariable.someOtherMethod();
     * }
     */
    
    
    @Test
    public void testGetFunctionLine_StringArg() {
        JavascriptObject mockObject = Mockito.mock(JavascriptObject.class);
        String method = "myMethod";
        List<Object> args = new ArrayList<>();
        args.add( "arg1" );
        
        when(mockObject.getVariableName()).thenReturn("myVar");
        JavascriptFunctionLine line = new JavascriptFunctionLine(mockObject, method, args);
        
        assertEquals( "myVar.myMethod(arg1);\n", line.getFunctionLine() );
        
    }
    
        @Test
    public void testGetFunctionLine_JavascriptArg() {
        JavascriptObject mockObject = Mockito.mock(JavascriptObject.class);
        JavascriptObject javascriptArg = Mockito.mock(JavascriptObject.class);
        String method = "myMethod";
        List<Object> args = new ArrayList<>();
        args.add( javascriptArg );
        
        when(mockObject.getVariableName()).thenReturn("myVar");
        when(javascriptArg.getVariableName()).thenReturn("myJavascriptArg");
        JavascriptFunctionLine line = new JavascriptFunctionLine(mockObject, method, args);
        
        assertEquals( "myVar.myMethod(myJavascriptArg);\n", line.getFunctionLine() );
        
    }
}
