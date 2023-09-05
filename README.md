[![JFXCentral](https://img.shields.io/badge/Find_me_on-JFXCentral-blue?logo=googlechrome&logoColor=white)](https://www.jfx-central.com/libraries/gmapsfx)

#  GMapsFX

A pure JavaFX API which allows you to add Google Maps to your JavaFX application without the need to interact with the underlying Google Maps JavaScript API.

GMapsFX requires Java 11 and JavaFX 17.

![GMapsFX GUI](http://rterp.files.wordpress.com/2014/05/gmapsfx.png)

## Quick Start

### Build the framework

```bash
cd GMapsFX
./mvnw install
```

### Run the sample application

```bash
./mvnw exec:java
```

## Development Notes

The GMapsFX framework creates underyling JavaScript peer objects when their corresponding Java objects are instantiated.  For example when a new `com.dlsc.gmapsfx.javascript.object.LatLong` object is created, the object's constructor will also create a corresponding `LatLong` object in the JavaScript environment.

This means that you cannot instantiate JavaScript objects until the JavaScript engine has been fully initialized.  The JavaScript engine is intialized asynchronously when a new GoogleMapView component is created.  You can register a MapComponentInitializedListener to be notified when the map and JavaScript environment has been fully initialized.  

You can take a look at a small example code snippet [here.](https://dlsc-software-consulting-gmbh.github.io/GMapsFX/)

The latest Javadocs can be found [here.](https://dlsc-software-consulting-gmbh.github.io/GMapsFX/apidocs/)

## Authors

- Rob Terpilowski  ::  Twitter: [@RobTerpilowski](https://www.twitter.com/RobTerpilowski)
- Geoff Capper
- Dirk Lemmermann ::  Twitter: [@dlemmermann](https://www.twitter.com/dlemmermann)

## License

Apache License, Version 2.0 (current)
<http://www.apache.org/licenses/LICENSE-2.0>

Also, please be aware, in order to use this library, you must also accept the Google Terms of Service.
<https://developers.google.com/maps/faq#understanding-terms-of-service>
