/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lynden.gmapsfx.service.geocoding;

import com.lynden.gmapsfx.zoom.MaxZoomResult;
import java.util.List;

/**
 *
 * @author jlstephens89
 */
public interface GeocodingServiceCallback {

    public void geocodedResultsReceived(List<GeocodingResult> results, GeocoderStatus status);

}
