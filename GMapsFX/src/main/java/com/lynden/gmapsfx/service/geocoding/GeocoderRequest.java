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
import com.lynden.gmapsfx.javascript.object.LatLongBounds;

/**
 *
 * @author jlstephens89
 */
public class GeocoderRequest extends JavascriptObject {

    public GeocoderRequest(String address) {
        this(address, null, null,null,null, null);
    }

    public GeocoderRequest(String address, LatLong latLong, String placeId,
            LatLongBounds bounds, GeocoderComponentRestrictions restrictions,
            String region) {
        super(GMapObjectType.GEOCODER_REQUEST,
                convertToJavascriptString(address, latLong, placeId, bounds,
                        restrictions, region));
    }

    private static String convertToJavascriptString(String address,
            LatLong location, String placeId, LatLongBounds bounds,
            GeocoderComponentRestrictions restrictions, String region) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        boolean something = false;
        if (address != null) {
            something = true;
            builder.append("address: '").append(address).append("'");
        }
        if (restrictions != null) {
            builder.append(something ? "," : "");
            builder.append("componentRestrictions: ").append(restrictions.getVariableName());
            something = true;
        }
        if (location != null) {
            builder.append(something ? "," : "");
            builder.append("location: ").append(location.getVariableName());
            something = true;
        }
        if (placeId != null) {
            builder.append(something ? "," : "");
            something = true;
            builder.append("placeId: '").append(placeId).append("'");
        }
        if (bounds != null) {
            builder.append(something ? "," : "");
            builder.append("bounds: ").append(bounds.getVariableName());
            something = true;
        }
        if (region != null) {
            builder.append(something ? "," : "");
            something = true;
            builder.append("region: '").append(region).append("'");
        }
        builder.append("}");
        System.out.println("REQUEST " + builder.toString());
        return builder.toString();
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return invokeJavascriptReturnValue("address", String.class);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("address: '").append(getAddress()).append("'");
        return builder.toString();
    }

}