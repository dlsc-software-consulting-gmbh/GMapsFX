package com.lynden.gmapsfx;

import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapType;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.ArcBuilder;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.lynden.gmapsfx.shapes.Polygon;
import com.lynden.gmapsfx.shapes.PolygonOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import com.lynden.gmapsfx.shapes.Rectangle;
import com.lynden.gmapsfx.shapes.RectangleOptions;
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
 * Example Application for creating and loading a GoogleMap into a JavaFX
 * application
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
                .zoom(9)
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
        markerOptions.position(markerLatLong)
                .title("My new Marker")
                .animation(Animation.DROP)
                .visible(true);

        final Marker myMarker = new Marker(markerOptions);

        MarkerOptions markerOptions2 = new MarkerOptions();
        LatLong markerLatLong2 = new LatLong(47.906189, -122.335842);
        markerOptions2.position(markerLatLong2)
                .title("My new Marker")
                .visible(true);

        Marker myMarker2 = new Marker(markerOptions2);

        map.addMarker(myMarker);
        map.addMarker(myMarker2);

        InfoWindowOptions infoOptions = new InfoWindowOptions();
        infoOptions.content("<h2>Here's an info window</h2><h3>with some info</h3>")
                .position(center);

        InfoWindow window = new InfoWindow(infoOptions);
        window.open(map, myMarker);

        lblCenter.setText(map.getCenter().toString());
        map.centerProperty().addListener((ObservableValue<? extends LatLong> obs, LatLong o, LatLong n) -> {
            lblCenter.setText(n.toString());
        });

        lblZoom.setText(Integer.toString(map.getZoom()));
        map.zoomProperty().addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
            lblZoom.setText(n.toString());
        });

//      map.addStateEventHandler(MapStateEventType.center_changed, () -> {
//			System.out.println("center_changed: " + map.getCenter());
//		});
//        map.addStateEventHandler(MapStateEventType.tilesloaded, () -> {
//			System.out.println("We got a tilesloaded event on the map");
//		});
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            lblClick.setText(ll.toString());
        });

        btnZoomIn.setDisable(false);
        btnZoomOut.setDisable(false);

        LatLong[] ary = new LatLong[]{markerLatLong, markerLatLong2};
        MVCArray mvc = new MVCArray(ary);

        PolylineOptions polyOpts = new PolylineOptions()
                .path(mvc)
                .strokeColor("red")
                .strokeWeight(2);

        Polyline poly = new Polyline(polyOpts);
        map.addMapShape(poly);
        map.addUIEventHandler(poly, UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            System.out.println("You clicked the line at LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
        });

        LatLong poly1 = new LatLong(47.429945, -122.84363);
        LatLong poly2 = new LatLong(47.361153, -123.03040);
        LatLong poly3 = new LatLong(47.387193, -123.11554);
        LatLong poly4 = new LatLong(47.585789, -122.96722);
        LatLong[] pAry = new LatLong[]{poly1, poly2, poly3, poly4};
        MVCArray pmvc = new MVCArray(pAry);

        PolygonOptions polygOpts = new PolygonOptions()
                .paths(pmvc)
                .strokeColor("blue")
                .strokeWeight(2)
                .editable(false)
                .fillColor("lightBlue")
                .fillOpacity(0.5);

        Polygon pg = new Polygon(polygOpts);
        map.addMapShape(pg);
        map.addUIEventHandler(pg, UIEventType.click, (JSObject obj) -> {
            //polygOpts.editable(true);
            pg.setEditable(!pg.getEditable());
        });

        LatLong centreC = new LatLong(47.545481, -121.87384);
        CircleOptions cOpts = new CircleOptions()
                .center(centreC)
                .radius(5000)
                .strokeColor("green")
                .strokeWeight(2)
                .fillColor("orange")
                .fillOpacity(0.3);

        Circle c = new Circle(cOpts);
        map.addMapShape(c);
        map.addUIEventHandler(c, UIEventType.click, (JSObject obj) -> {
            c.setEditable(!c.getEditable());
        });

        LatLongBounds llb = new LatLongBounds(new LatLong(47.533893, -122.89856), new LatLong(47.580694, -122.80312));
        RectangleOptions rOpts = new RectangleOptions()
                .bounds(llb)
                .strokeColor("black")
                .strokeWeight(2)
                .fillColor("null");

        Rectangle rt = new Rectangle(rOpts);
        map.addMapShape(rt);

        LatLong arcC = new LatLong(47.227029, -121.81641);
        double startBearing = 0;
        double endBearing = 30;
        double radius = 30000;

        MVCArray path = ArcBuilder.buildArcPoints(arcC, startBearing, endBearing, radius);
        path.push(arcC);

        Polygon arc = new Polygon(new PolygonOptions()
                .paths(path)
                .strokeColor("blue")
                .fillColor("lightBlue")
                .fillOpacity(0.3)
                .strokeWeight(2)
                .editable(false));

        map.addMapShape(arc);
        map.addUIEventHandler(arc, UIEventType.click, (JSObject obj) -> {
            arc.setEditable(!arc.getEditable());
        });

        /*
         LatLong poly1a = new LatLong(47.429945, -122.84363);
         LatLong poly2a = new LatLong(47.361153, -123.03040);
         LatLong poly3a = new LatLong(47.387193, -123.11554);
         LatLong poly4a = new LatLong(47.585789, -122.96722);
		
         LatLong poly5a = new LatLong(47.585789, -121.96722);
		
         LatLong[] pArya = new LatLong[]{poly1a, poly2a, poly3a, poly4a};
         MVCArray pmvca = new MVCArray(pArya);
		
         System.out.println("pmvca.length: " + pmvca.getLength());
         pmvca.clear();
         System.out.println("pmvca.length post clear: " + pmvca.getLength());
         pmvca = new MVCArray(pArya);
         System.out.println("pmvca.length: " + pmvca.getLength());
         System.out.println("pmvca.getArray: " + pmvca.getArray());
		
         System.out.println("pmvca.getAt(2): " + pmvca.getAt(2));
         pmvca.insertAt(2, poly5a);
         System.out.println("pmvca.length after insert: " + pmvca.getLength());
         System.out.println("pmvca.getAt(2): " + pmvca.getAt(2));
		
         System.out.println("pmvca.pop: " + pmvca.pop());
         System.out.println("pmvca.length after pop: " + pmvca.getLength());
		
         System.out.println("pmvca.push: " + pmvca.push(poly4a));
         System.out.println("pmvca.length after push: " + pmvca.getLength());
         System.out.println("pmvca.getArray after push: " + pmvca.getArray());
		
         pmvca.removeAt(2);
         System.out.println("pmvca.length after removeAt: " + pmvca.getLength());
         System.out.println("pmvca.getArray after removeAt: " + pmvca.getArray());
		
         pmvca.setAt(2, poly5a);
         System.out.println("pmvca.length after setAt: " + pmvca.getLength());
         System.out.println("pmvca.getArray after setAt: " + pmvca.getArray());
         */
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
