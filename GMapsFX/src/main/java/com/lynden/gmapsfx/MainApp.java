package com.lynden.gmapsfx;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        Scene scene = new Scene(new GoogleMapComponent());
        


        //webView.getEngine().executeScript("Alert(\"Hello!\")");
        System.out.println("Starting load");
        //webView.getEngine().load(getClass().getResource("/html/maps.html").toExternalForm());
        System.out.println("Loaded hit");
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
