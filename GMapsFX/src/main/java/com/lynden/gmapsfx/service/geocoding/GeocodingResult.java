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
import com.lynden.gmapsfx.javascript.object.LatLong;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author jlstephens89
 */
public class GeocodingResult extends JavascriptObject {

    public GeocodingResult() {
        super(GMapObjectType.GEOCODER_RESULT);
    }

    public GeocodingResult(JSObject jsObject) {
        super(GMapObjectType.GEOCODER_RESULT, jsObject);
    }

    /**
     * partial_match: boolean,postcode_localities[]: string, geometry: {
     * location: LatLng, location_type: GeocoderLocationType viewport:
     * LatLngBounds, bounds: LatLngBounds }
     */
    public String getFormattedAddress() {
        return getJSObject().getMember("formatted_address").toString();
    }

    public String getPlaceId() {
        return getJSObject().getMember("place_id").toString();
    }

    public Boolean isPartialMatch() {
        Boolean result = null;
        String text = getJSObject().getMember("partial_match").toString();
        try {
            result = Boolean.parseBoolean(text);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "", e);
        }
        return result;
    }

    public List<String> getPostcodeLocalities() {
        final List<String> result = new ArrayList<>();
        List<JSObject> jsLocalities = GeocoderUtils.getJSObjectsFromArray((JSObject) jsObject.getMember("postcode_localities"));
        for (JSObject jsLocality : jsLocalities) {
            String text = jsLocality.toString();
            if (text != null && !text.isEmpty() && !text.equals("undefined")) {
                result.add(text);
            }
        }
        return result;
    }

    public List<GeocoderAddressComponent> getAddressComponents() {
        final List<GeocoderAddressComponent> components = new ArrayList<>();
        JSObject componentArray = (JSObject) getJSObject().getMember("address_components");
        List<JSObject> jsObjectsFromArray = GeocoderUtils.getJSObjectsFromArray(componentArray);
        for (JSObject obj : jsObjectsFromArray) {
            components.add(new GeocoderAddressComponent(obj));
        }
        return components;
    }

    public List<GeocoderAddressComponentType> getTypes() {
        JSObject jsTypes = (JSObject) getJSObject().getMember("types");
        return GeocoderUtils.convertJSObjectToListOfEnum(jsTypes, GeocoderAddressComponentType.class);
    }

    public GeocoderGeometry getGeometry() {
        try {
            JSObject geometry = (JSObject) getJSObject().getMember("geometry");
            return new GeocoderGeometry((JSObject) geometry);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "", e);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t Types: ").append(getTypes()).append("\n");
        builder.append("\t Formatted Address: ").append(getFormattedAddress()).append("\n");
        builder.append("\t Address Components: ").append(getAddressComponents()).append("\n");
        builder.append("\t Partial Match: ").append(isPartialMatch()).append("\n");
        builder.append("\t Place Id: ").append(getPlaceId()).append("\n");
        builder.append("\t Geometry: ").append(getGeometry()).append("\n");
        return builder.toString();
    }

}
