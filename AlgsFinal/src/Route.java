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
    private PickupType pickup_type;
    //Indicates pickup method.
    private PickupType drop_off_type;

    private Double shape_dist_travelled;
    //Actual distance traveled along the associated shape, from the first stop to the stop specified in this record.

    public Route(Integer fromStopId, Integer toStopId) {
        super(fromStopId, toStopId);

    }

    public int Weight(){
        return 0;

        //TODO
    }

}
