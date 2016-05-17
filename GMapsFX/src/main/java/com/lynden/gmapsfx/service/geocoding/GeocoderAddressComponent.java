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
import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.GMapObjectType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author jlstephens89
 */
public class GeocoderAddressComponent extends JavascriptObject {

//    private final List<String> postcodeLocalities = new ArrayList<>();
    public GeocoderAddressComponent() {
        super(GMapObjectType.GEOCODER_ADDRESS_COMPONENT);
    }

    public GeocoderAddressComponent(JSObject jsObject) {
        super(GMapObjectType.GEOCODER_ADDRESS_COMPONENT, jsObject);
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return getJSObject().getMember("short_name").toString();
    }

    /**
     * @return the longName
     */
    public String getLongName() {
        return getJSObject().getMember("long_name").toString();
    }

    /**
     * @return the types
     */
    public List<GeocoderAddressComponentType> getTypes() {
        JSObject jsType = (JSObject) getJSObject().getMember("types");
        return GeocoderUtils.convertJSObjectToListOfEnum(jsType, GeocoderAddressComponentType.class);
    }

    /**
     * @return the postcodeLocalities
     */
    public List<String> getPostcodeLocalities() {
        final List<String> result = new ArrayList<>();
        try {
            if (!(jsObject.getMember("postcode_localities") instanceof String)) {
                List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) jsObject.getMember("postcode_localities"));
                for (JSObject jsLocality : jsLocalities) {
                    String text = jsLocality.toString();
                    if (text != null && !text.isEmpty() && !text.equals("undefined")) {
                        result.add(text);
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "", e);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t Short Name: ").append(getShortName()).append("\n");
        builder.append("\t Long Name: ").append(getLongName()).append("\n");
        builder.append("\t Component Types: ").append(getTypes()).append("\n");
        builder.append("\t Postcode Localities: ").append(getPostcodeLocalities()).append("\n");
        return builder.toString();
    }

}