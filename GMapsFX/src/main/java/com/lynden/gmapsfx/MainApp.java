package com.lynden.gmapsfx;

import com.lynden.gmapsfx.javascript.event.MapStateEventType;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapType;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;



/**
 * Example Application for creating and loading a GoogleMap into a JavaFX application
 *
 * @author Rob Terpilowski
 */
public class MainApp extends Application implements MapComponentInitializedListener {

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    
    private Button btnZoomIn;
    private Button btnZoomOut;
    private Label lblZoom;
    private Label lblCenter;
    private Label lblClick;
    
    
    @Override
    public void start(final Stage stage) throws Exception {
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);
        
        BorderPane bp = new BorderPane();
        ToolBar tb = new ToolBar();
        
        btnZoomIn = new Button("Zoom In");
        btnZoomIn.setOnAction(e -> {
            map.zoomProperty().set(map.getZoom() + 1);
        });
        btnZoomIn.setDisable(true);
        
        btnZoomOut = new Button("Zoom Out");
        btnZoomOut.setOnAction(e -> {
            map.zoomProperty().set(map.getZoom() - 1);
        });
        btnZoomOut.setDisable(true);
        
        lblZoom = new Label();
        lblCenter = new Label();
        lblClick = new Label();
        
        tb.getItems().addAll(btnZoomIn, btnZoomOut,
                new Label("Zoom: "), lblZoom,
                new Label("Center: "), lblCenter,
                new Label("Click: "), lblClick);
        
        bp.setTop(tb);
        bp.setCenter(mapComponent);
                
        Scene scene = new Scene(bp);
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
                    .animation(Animation.DROP)
                    .visible(true);

       final Marker myMarker = new Marker(markerOptions);
        
        MarkerOptions markerOptions2 = new MarkerOptions();
        //LatLong markerLatLong2 = ;
        markerOptions2.position( new LatLong(47.906189, -122.335842) )
                    .title("My new Marker")
                    .visible(true);
        
        Marker myMarker2 = new Marker(markerOptions2);        
        
        map.addMarker(myMarker);
        map.addMarker(myMarker2);
        
        
        
        InfoWindowOptions infoOptions = new InfoWindowOptions();
        infoOptions.content("<h2>Here's an info window</h2><h3>with some info</h3>")
                    .position(center);

        InfoWindow window = new InfoWindow(infoOptions);
        window.open(map,myMarker);
        
        lblCenter.setText(map.getCenter().toString());
        map.centerProperty().addListener((ObservableValue<? extends LatLong> obs, LatLong o, LatLong n) -> {
            lblCenter.setText(n.toString());
            //System.out.println("GoogleMap centre changed to: " + n);
        });
        
        lblZoom.setText(Integer.toString(map.getZoom()));
        map.zoomProperty().addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
            lblZoom.setText(n.toString());
//            System.out.println("GoogleMap zoom changed to: " + n);
        });
        
//      map.addStateEventHandler(MapStateEventType.center_changed, () -> {
//			System.out.println("center_changed: " + map.getCenter());
//		});
//        map.addStateEventHandler(MapStateEventType.tilesloaded, () -> {
//			System.out.println("We got a tilesloaded event on the map");
//		});

        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong(obj);
            //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            lblClick.setText(ll.toString());
        });
        
        btnZoomIn.setDisable(false);
        btnZoomOut.setDisable(false);
        
        
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
