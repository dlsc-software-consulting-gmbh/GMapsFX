package com.lynden.gmapsfx;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapType;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Example Application for creating and loading a GoogleMap into a JavaFX application
 *
 * @author Rob Terpilowski
 */
public class MainApp extends Application implements MapComponentInitializedListener {

    protected GoogleMapView mapComponent;
    protected GoogleMap map;

    @Override
    public void start(final Stage stage) throws Exception {
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);

        Scene scene = new Scene(mapComponent);
        stage.setScene(scene);
        stage.show();
    }

    
    @Override
    public void mapInitialized() {
        //Once the map has been loaded by the Webview, initialize the map details.
        LatLong center = new LatLong(47.606189, -122.335842);
        MapOptions options = new MapOptions();
        options.center(center)
                .mapMarker(true)
                .zoom(11)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapType.ROADMAP);

        map = mapComponent.createMap(options);

        
        MarkerOptions markerOptions = new MarkerOptions();
        LatLong markerLatLong = new LatLong(47.606189, -122.335842);
        markerOptions.position( markerLatLong )
                    .title("My new Marker")
                    .visible(true);

        Marker myMarker = new Marker(markerOptions);
        
        MarkerOptions markerOptions2 = new MarkerOptions();
        //LatLong markerLatLong2 = ;
        markerOptions2.position( new LatLong(47.906189, -122.335842) )
                    .title("My new Marker")
                    .visible(true);
        
    Marker myMarker2 = new Marker(markerOptions2);        
        
        map.addMarker(myMarker);
        map.addMarker(myMarker2);
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }

}
