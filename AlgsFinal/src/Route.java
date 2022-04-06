import java.time.*;

public class Route extends Edge {
    private int tripID;
    //Identifies a trip.
    private LocalTime arrivalTime;
    //Arrival time at a specific stop for a specific trip on a route.
    private LocalTime departureTime;
    //Departure time from a specific stop for a specific trip on a route.
    private int stopSequence;
    //Order of stops for a particular trip. The values must increase along the trip but do not need to be consecutive.
    private String stopHeadsign;
    //Text that appears on signage identifying the trip's destination to riders.
    private PickupType pickupType;
    //Indicates pickup method.
    private PickupType dropOffType;

    private Double shapeDistTraveled;
    //Actual distance traveled along the associated shape, from the first stop to the stop specified in this record.

    public Route(Integer fromStopId, Integer toStopId) {
        super(fromStopId, toStopId);

    }

    public int Weight(){
        return 0;

        //TODO
    }

}
