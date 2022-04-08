import java.util.ArrayList;
import java.util.Locale;

public class Stop {

    //Data used to identify properties taken from https://www.translink.ca/about-us/doing-business-with-translink/app-developer-resources/gtfs/gtfs-data and https://developers.google.com/transit/gtfs/reference#stopstxt
    private Integer stop_id;
    //Identifies a stop, station, or station entrance.
    private String stop_code;
    //Short text or a number that identifies the location for riders.
    //The stop_code can be the same as stop_id if it is public facing. This field should be left empty for locations without a code presented to riders.
    private String stop_name;
    //Name of the location. Use a name that people will understand in the local and tourist vernacular.
    private String stop_desc;
    //Description of the location that provides useful, quality information.
    private Double stop_lat;
    //Latitude of the location.
    private Double stop_lon;
    //Longitude of the location.
    private String zone_id;
    //Identifies the fare zone for a stop.
    private String stop_url;
    //URL of a web page about the location.
    private LocationType location_type;
    //Type of the location:
    private Integer parent_station;
    //the stops parent station
    private ArrayList<Edge> edges;
    //The list of all routes/transfers from this stop;

    public Stop(Integer stop_id, String stop_code, String stop_name, String stop_desc, Double stop_lat, Double stop_lon, String zone_id, String stop_url, LocationType location_type, Integer parent_station) {
        this.stop_id = stop_id;
        this.stop_code = stop_code;
        this.stop_name = stop_name;
        this.stop_desc = stop_desc;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.zone_id = zone_id;
        this.stop_url = stop_url;
        this.location_type = location_type;
        this.parent_station = parent_station;
        this.edges = new ArrayList<Edge>();
    }

    public Integer getStopId() {
        return this.stop_id;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public String getStopName() {
        return stop_name;
    }

    public String getTransformedStopName() {
        String ret;
        switch (this.stop_name.substring(0,2).toUpperCase(Locale.ROOT))
        {
            case "WB":
            case "FS":
            case "NB":
            case "SB":
            case "EB":
                ret = this.stop_name.substring(3) + " " + this.stop_name.substring(0,2);
                break;
            default:
                if(this.stop_name.substring(0,8).toUpperCase(Locale.ROOT) == "FLAGSTOP") {
                    ret = this.stop_name.substring(9) + " " + this.stop_name.substring(0,8);
                }
                else {
                    ret = this.stop_name;
                }
                break;
        }
        return ret.toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("********************************************************");
        ret.append("\n");
        ret.append("Name: " + stop_name);
        ret.append("\n");
        ret.append("Description: " + stop_desc);
        ret.append("\n");
        ret.append("GPS: " + stop_lon + "N " + stop_lat + "W");
        ret.append("\n");
        ret.append("Zone ID: " + ((zone_id != null) ? zone_id : "N/A"));
        ret.append(" - Stop ID: " +  ((stop_id != null) ? stop_id : "N/A"));
        ret.append(" - Stop Code: " + ((stop_code != null) ? stop_code : "N/A"));
        ret.append("\n");
        ret.append("Stop URL: " + (!(stop_url == null || stop_url.trim().equals("")) ? stop_url : "N/A"));
        ret.append("\n");
        ret.append("Location Type: " + ((location_type != null) ? location_type : "N/A"));
        ret.append(" - Parent Station: " + ((parent_station != null) ? parent_station : "N/A"));
        ret.append("\n");
        ret.append("********************************************************");
        return ret.toString();
    }
}
