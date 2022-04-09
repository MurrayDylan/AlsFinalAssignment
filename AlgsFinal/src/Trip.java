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

    public ArrayList<Route> getJourney() {
        return journey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trip ID: ");
        sb.append(tripId.toString());
        sb.append(" - Starting Location: ");
        sb.append(journey.get(0).getFromStop().getStopName());
        sb.append(" - End Location: ");
        sb.append(journey.get(journey.size() - 1).getToStop().getStopName());
        int routeCounter = 0;
        for (Route route : journey) {
            if (routeCounter % 5 == 0) {
                sb.append("\n        ");
            }
            sb.append(route.toString());
            routeCounter++;
        }
        return sb.toString();
    }
}


