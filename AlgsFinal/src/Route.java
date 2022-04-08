import java.time.*;

public class Route extends Edge {

    private Integer tripId;
    //Identifies a trip.
    private LocalTime arrivalTimeCurrentStop;
    //Arrival time at the current stop for a specific trip on a route.
    private LocalTime arrivalTimeNextStop;
    //Arrival time at the next stop for a specific trip on a route.
    private LocalTime departureTime;
    //Departure time from a specific stop for a specific trip on a route.
    private Integer stopSequence;
    //Order of stops for a particular trip. The values must increase along the trip but do not need to be consecutive.
    private String stopHeadsign;
    //Text that appears on signage identifying the trip's destination to riders.
    private PickupType pickupType;
    //Indicates pickup method.
    private PickupType dropOffType;
    //Indicates drop off method.
    private Double shapeDistTraveled;
    //Actual distance traveled along the associated shape, from the first stop to the stop specified in this record.
    private Integer journeyTime;
    //Time it takes for Journey, uses weight function below
    private Integer fromStopId;
    //The StopId of the stop it leaves from
    private Integer toStopId;
    //the StopId of the stop it will arrive at

    public Route(Integer fromStopId,
                 Integer toStopId,
                 Integer tripId,
                 LocalTime arrivalTimeCurrentStop,
                 LocalTime arrivalTimeNextStop,
                 LocalTime departureTime,
                 Integer stopSequence,
                 String stopHeadsign,
                 PickupType pickupType,
                 PickupType dropOffType,
                 Double shapeDistTraveled
    )
    {
        super(fromStopId, toStopId);

    }

    public double Weight(){
        /*
        this.journeyTime = this.arrivalTimeNextStop.toSecondOfDay() - this.arrivalTimeCurrentStop.toSecondOfDay();
        return journeyTime;
        */
        return 0;
        //TODO
    }

}
