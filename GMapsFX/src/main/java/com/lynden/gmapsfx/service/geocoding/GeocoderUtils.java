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
package com.lynden.gmapsfx.service.geocoding;

/**
 *
 * @author Andre
 */

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