import java.time.*;

public class Route extends Edge {
    private int trip_id;
    //Identifies a trip.
    private LocalTime arrival_time;
    //Arrival time at a specific stop for a specific trip on a route.
    private LocalTime departure_time;
    //Departure time from a specific stop for a specific trip on a route.
    private int stop_sequence;
    //Order of stops for a particular trip. The values must increase along the trip but do not need to be consecutive.
    private String stop_headsign;
    //Text that appears on signage identifying the trip's destination to riders.
    private enum pickup_type{};
    /*
    Indicates pickup method. Valid options are:

    0 or empty - Regularly scheduled pickup.
    1 - No pickup available.
    2 - Must phone agency to arrange pickup.
    3 - Must coordinate with driver to arrange pickup.
     */
    private enum drop_off_type{};
    /*
    Indicates drop off method. Valid options are:

    0 or empty - Regularly scheduled drop off.
    1 - No drop off available.
    2 - Must phone agency to arrange drop off.
    3 - Must coordinate with driver to arrange drop off.
     */
    private Double shape_dist_travelled;
    //Actual distance traveled along the associated shape, from the first stop to the stop specified in this record.

    public Route() {

    }

}
