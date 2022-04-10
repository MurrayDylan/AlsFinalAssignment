import java.util.ArrayList;
import java.util.Locale;

public class Stop implements Comparable<Stop>{

    //Data used to identify properties taken from https://www.translink.ca/about-us/doing-business-with-translink/app-developer-resources/gtfs/gtfs-data and https://developers.google.com/transit/gtfs/reference#stopstxt
    private Integer stop_id;
    //Identifies a stop, station, or station entrance.
    private String stop_code;
    //Short text or a number that identifies the location for riders.
    //The stop_code can be the same as stop_id if it is public facing. This field should be left empty for locations without a code presented to riders.
    private String stopName;
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

    // The following are needed to calculate AStar
    private Edge pathFindingEdge;
    private Double estimateCost;
    private Double actualCost;

    public Stop(Integer stop_id, String stop_code, String stopName, String stop_desc, Double stop_lat, Double stop_lon, String zone_id, String stop_url, LocationType location_type, Integer parent_station) {
        this.stop_id = stop_id;
        this.stop_code = stop_code;
        this.stopName = stopName;
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
        return stopName;
    }

    public String getStopCode() { return stop_code; }

    public String getTransformedStopName() {
        String ret;
        switch (this.stopName.substring(0,2).toUpperCase(Locale.ROOT))
        {
            case "WB":
            case "FS":
            case "NB":
            case "SB":
            case "EB":
                ret = this.stopName.substring(3) + " " + this.stopName.substring(0,2);
                break;
            default:
                if(this.stopName.substring(0,8).toUpperCase(Locale.ROOT) == "FLAGSTOP") {
                    ret = this.stopName.substring(9) + " " + this.stopName.substring(0,8);
                }
                else {
                    ret = this.stopName;
                }
                break;
        }
        return ret.toUpperCase(Locale.ROOT);
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }
    public Edge getPathFindingEdge() { return this.pathFindingEdge; }
    public Double getActualCost() { return this.actualCost; }

    public Double getCost() {
        return ((this.estimateCost == null) ? Double.POSITIVE_INFINITY : this.estimateCost) + ((this.actualCost == null) ? Double.POSITIVE_INFINITY : this.actualCost);
    }

    public Stop setCosts(Stop toStop, Edge pathFindingEdge, double previousActualCost, boolean forceSet) {
        double eCost = Math.hypot(this.stop_lon - toStop.stop_lon, this.stop_lat - toStop.stop_lat);
        double aCost = previousActualCost + ((pathFindingEdge == null) ? 0 : pathFindingEdge.getWeight());

        if (forceSet || this.getCost() > eCost + aCost) {
            this.estimateCost = eCost;
            this.actualCost = aCost;
            this.pathFindingEdge = pathFindingEdge;
        }

        return this;
    }

    public int compareTo(Stop that) {
        return (int)(this.getCost() - that.getCost());
    }


    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("********************************************************");
        ret.append("\n");
        ret.append("Name: " + stopName);
        ret.append("\n");
        ret.append("Description: " + stop_desc);
        ret.append("\n");
        ret.append("GPS: " + stop_lat + "N " + Math.abs(stop_lon) + "W");
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
