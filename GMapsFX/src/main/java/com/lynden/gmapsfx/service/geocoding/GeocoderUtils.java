/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lynden.gmapsfx.service.geocoding;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author jlstephens89
 */
public class GeocoderUtils {

    public static List<JSObject> getJSObjectsFromArray(JSObject jsArray) {
        final List<JSObject> result = new ArrayList<>();
        boolean keepLooking = true;
        int index = 0;
        while (keepLooking) {
            try {
                JSObject jsGeocoderResult = (JSObject) ((JSObject) jsArray).getSlot(index++);
                if (jsGeocoderResult != null) {
                    result.add(jsGeocoderResult);
                } else {
                    keepLooking = false;
                }
            } catch (Exception e) {
                keepLooking = false;
            }
        }
        return result;
    }

    public static <T extends Enum> List<T> convertJSObjectToListOfEnum(JSObject jsObject, Class<T> enumClass) {
        List<T> result = new ArrayList<>();
        if (jsObject != null) {
            try {
                String jsTypesString = jsObject.toString();

                for (T value : enumClass.getEnumConstants()) {
                    if (jsTypesString.toLowerCase().contains(value.name().toLowerCase())) {
                        result.add(value);
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(GeocoderUtils.class.getName()).log(Level.SEVERE, "", e);
            }
        }
        return result;
    }
    
}
