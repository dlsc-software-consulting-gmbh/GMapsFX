/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lynden.gmapsfx.javascript;

import com.lynden.gmapsfx.javascript.object.LatLong;
import netscape.javascript.JSObject;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author robt
 */
public class JavascriptRuntimeTest {
    
     IJavascriptRuntime mockJSRuntime;
     IWebEngine mockWebEngine;
     JSObject mockJsObject;
    
    public JavascriptRuntimeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mockJSRuntime = Mockito.mock( IJavascriptRuntime.class );
        mockWebEngine = Mockito.mock( IWebEngine.class );
        mockJsObject = Mockito.mock( JSObject.class );
        JavascriptRuntime.runtime = null;
        JavascriptRuntime.setDefaultWebEngine( mockWebEngine );
    }
    
    @After
    public void tearDown() {
    }
    
    
    
    @Test
    public void testGetInstance_NoDefaultSet() {
        assertTrue( JavascriptRuntime.getInstance() != mockJSRuntime );
                
    }
    
    @Test
    public void testSetDefaultWebEngine() {
        JavascriptRuntime.setDefaultWebEngine(mockWebEngine);
        assertEquals( mockWebEngine, JavascriptRuntime.engine );
    }
    
    @Test
    public void testExecute() {
        String command = "myCommand";
        
        when(mockWebEngine.executeScript(command)).thenReturn(mockJsObject);
        assertEquals( JavascriptRuntime.getInstance().execute(command), mockJsObject);
    }
    
    @Test
    public void testExecute_NoJSObjectReturned() {
        String command = "myCommand";
        
        when(mockWebEngine.executeScript(command)).thenReturn("Hello");
        assertNull( JavascriptRuntime.getInstance().execute(command));
    }
    
    
    @Test
    public void testGetArgString() {
        LatLong arg = new LatLong(15,15);
        arg.variableName = "myVariable";
        JavascriptRuntime rt = (JavascriptRuntime) JavascriptRuntime.getInstance();
        assertEquals( "myVariable", rt.getArgString( arg ) );
    }
    
    
    @Test
    public void testGetArgString_JavascriptObject() {
        JavascriptObject object = Mockito.mock(JavascriptObject.class);
        String myProps = "myProps";
        
        when(object.getVariableName()).thenReturn("PropVar1");
                
        JavascriptRuntime rt = (JavascriptRuntime) JavascriptRuntime.getInstance();
        assertEquals( "PropVar1", rt.getArgString( object ) );
    }
    
    
    @Test
    public void testGetArgString_OtherObjectType() {
        String object = "myString";
        
        JavascriptRuntime rt = (JavascriptRuntime) JavascriptRuntime.getInstance();
        assertEquals( "myString", rt.getArgString( object ) );        
        
    }


}
