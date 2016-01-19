/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lynden.gmapsfx.service.geocoding;

/**
 *
 * @author jlstephens89
 */
public enum GeocoderStatus {
    OK, ZERO_RESULTS, OVER_QUERY_LIMIT, REQUEST_DENIED, INVALID_REQUEST,
    UNKNOWN_ERROR;
}
