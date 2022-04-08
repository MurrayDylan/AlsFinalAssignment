import java.util.ArrayList;

public class Trip {
    private Integer tripId;
    private ArrayList<Route> journey;

    public Trip(Integer tripId, Route route) {
        this.tripId = tripId;
        this.journey = new ArrayList<>();
        this.addRoute(route);
    }

    public void addRoute(Route route) {
        this.journey.add(route);
    }
}
