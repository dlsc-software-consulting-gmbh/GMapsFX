/*
 * Copyright 2016
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
package com.lynden.gmapsfx.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Geoff Capper
 */
public class MarkerImageFactory {
    
    /** Takes a URI for an image contained within an application jar file and 
     * converts it into a data URI for use in a MarkerOptions object.
     * <p>
     * Usage:
     * <p>
     * markerOptions.icon(MarkerImageFactory.createMarkerImage("/path/to/my/image.jpg", "jpg"));
     * <p>
     * Currently tested to work with "jpg" and "png" files.
     * 
     * @param uri
     * @param type
     * @return 
     */
    public static String createMarkerImage(String uri, String type) {
        Logger.getLogger(MarkerImageFactory.class.getName()).log(Level.FINEST, "createMarkerImage using: {0}", uri);
        
        String dataURI = null;
        
        if (uri.startsWith("file:")) {
            return createMarkerImageFromFile(uri, type);
        }
        
        URL myURL = MarkerImageFactory.class.getResource(uri);
        
        if (myURL != null) {
            String myURI = myURL.toExternalForm();
            Image img = new Image(myURI);
            
            String imageMimeType = "image/" + type;
            
            try {
                dataURI = "data:" + imageMimeType + ";base64,(" + 
                        javax.xml.bind.DatatypeConverter.printBase64Binary(getImageBytes(SwingFXUtils.fromFXImage(img, null), type)) + ")";
            } catch (IOException ioe) {
                Logger.getLogger(MarkerImageFactory.class.getName()).log(Level.WARNING, "Cannot create marker image", ioe);
                dataURI = null;
            }
        }
        
        return dataURI;
        
    }
    
    private static String createMarkerImageFromFile(String uri, String type) {
        
        try {
            String dataURI = null;
            
            URL myURL = URI.create(uri).toURL();
            
            if (myURL != null) {
                String myURLext = myURL.toExternalForm();
                Image img = new Image(myURLext);
                
                String imageMimeType = "image/" + type;
                
                try {
                    dataURI = "data:" + imageMimeType + ";base64," +
                            javax.xml.bind.DatatypeConverter.printBase64Binary(getImageBytes(SwingFXUtils.fromFXImage(img, null), type)) + "";
                } catch (IOException ioe) {
                    Logger.getLogger(MarkerImageFactory.class.getName()).log(Level.WARNING, "Cannot create marker image", ioe);
                    dataURI = null;
                }
            }
            
            return dataURI;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(MarkerImageFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    
    private static byte[] getImageBytes(BufferedImage image, String type) throws IOException {
        byte[] imageBytes;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, type, bos);
            imageBytes = bos.toByteArray();
        }
        return imageBytes;
		
	}
    
    
}
