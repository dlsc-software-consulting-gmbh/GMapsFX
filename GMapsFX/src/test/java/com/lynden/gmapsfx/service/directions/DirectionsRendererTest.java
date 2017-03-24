/*
 * Copyright 2017 robt.
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

import com.lynden.gmapsfx.javascript.IJavascriptRuntime;
import com.lynden.gmapsfx.javascript.JavascriptRuntime;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import netscape.javascript.JSObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author robt
 */
@RunWith(MockitoJUnitRunner.class)
public class DirectionsRendererTest {
    
    @Mock
    protected JSObject mockJSObject;
    
    protected DirectionsRenderer testRenderer;
    
    @Mock
    protected IJavascriptRuntime mockJavascriptRuntime;
    
    @Mock
    protected GoogleMap mockMap;
    
    public DirectionsRendererTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        JavascriptRuntime.setJavascriptRuntime(mockJavascriptRuntime);
        testRenderer = spy(DirectionsRenderer.class);
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testClearDirections() {
        doNothing().when(testRenderer).setMap(null);
        
        testRenderer.clearDirections();
        verify(testRenderer).clearDirections();
    }
    
    @Test
    public void testSetMap_WithMap() {
        doReturn(mockJSObject).when(testRenderer).getJSObject();
        doReturn("MyVariable").when(testRenderer).getVariableName();
        when(mockMap.getVariableName()).thenReturn("MyMap");
        
        testRenderer.setMap(mockMap);
        
        verify(mockJSObject).eval("MyVariable.setMap(MyMap);");
        
    }
    
    
    @Test
    public void testSetMap_NullMap() {
        doReturn(mockJSObject).when(testRenderer).getJSObject();
        doReturn("MyVariable").when(testRenderer).getVariableName();
        
        testRenderer.setMap(null);
        
        verify(mockJSObject).eval("MyVariable.setMap(null);");
        
    }    
}
