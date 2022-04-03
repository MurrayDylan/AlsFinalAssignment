import java.util.ArrayList;

public class Stop {

    //Data used to identify properties taken from https://www.translink.ca/about-us/doing-business-with-translink/app-developer-resources/gtfs/gtfs-data and https://developers.google.com/transit/gtfs/reference#stopstxt
    private int stop_id;
    //Identifies a stop, station, or station entrance.
    private int stop_code;
    //Short text or a number that identifies the location for riders.
    //The stop_code can be the same as stop_id if it is public facing. This field should be left empty for locations without a code presented to riders.
    private String stop_name;
    //Name of the location. Use a name that people will understand in the local and tourist vernacular.
    private String stop_desc;
    //Description of the location that provides useful, quality information.
    private double stop_lat;
    //Latitude of the location.
    private double stop_lon;
    //Longitude of the location.
    private String zone_id;
    //Identifies the fare zone for a stop.
    private String stop_url;
    //URL of a web page about the location.
    private enum location_type{};
    /*
    Type of the location:
    • 0 (or empty): Stop (or Platform). A location where passengers board or disembark from a transit vehicle. Is called a platform when defined within a parent_station.
    • 1: Station. A physical structure or area that contains one or more platform.
     */
    private int parent_station;
    //the stops parent station
    ArrayList<Edge> edges;
    //The list of all routes/transfers from this stop;

    public Stop() {

    }

}