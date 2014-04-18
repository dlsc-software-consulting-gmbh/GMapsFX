package com.lynden.gmapsfx;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application implements MapInitializedListener {

    GoogleMapComponent map;

    @Override
    public void start(final Stage stage) throws Exception {
        System.out.println("Creating map");
        map = new GoogleMapComponent();
        map.addMapInializedListener(this);
        System.out.println("Map created");
        Scene scene = new Scene(map);

        System.out.println("center set");

        //webView.getEngine().executeScript("Alert(\"Hello!\")");
//        System.out.println("Starting load");
        //webView.getEngine().load(getClass().getResource("/html/maps.html").toExternalForm());
//        System.out.println("Loaded hit");
        //      
        // 
        //window.setMember("app", this);
        //JSObject jsobj = (JSObject) webView.getEngine().executeScript("hello()");
        //jsobj.call("alert", "Hello");
        stage.setScene(scene);
        stage.show();
        //barrier.await(10, TimeUnit.SECONDS);

        System.out.println("Got here");
    }

    @Override
    public void mapInitialized() {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        System.out.println("Moving to new location");
                        //map.setCenter(80, 150);
                    }
                });
            }
        });
        thread.start();

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
