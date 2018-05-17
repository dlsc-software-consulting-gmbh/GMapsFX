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
public enum GeocoderAddressComponentType {
    STREET_ADDRESS,ROUTE,INTERSECTION,POLITICAL,COUNTRY,
    ADMINISTRATIVE_AREA_LEVEL_1,ADMINISTRATIVE_AREA_LEVEL_2,
    ADMINISTRATIVE_AREA_LEVEL_3,ADMINISTRATIVE_AREA_LEVEL_4,
    ADMINISTRATIVE_AREA_LEVEL_5,COLLOQUIAL_AREA,LOCALITY, SUBLOCALITY,
    NEIGHBORHOOD,PREMISE, SUBPREMISE, POSTAL_CODE, NATURAL_FEATURE,AIRPORT,
    PARK,POST_BOX,STREET_NUMBER,FLOOR,ROOM;
}