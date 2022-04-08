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

    public Route(BusTransfers bT,
                 Integer fromStopId,
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
        super(bT, fromStopId, toStopId);
        this.tripId = tripId;
        this.arrivalTimeCurrentStop = arrivalTimeCurrentStop;
        this.arrivalTimeNextStop = arrivalTimeNextStop;
        this.departureTime = departureTime;
        this.stopHeadsign = stopHeadsign;
        this.stopSequence = stopSequence;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;
        this.shapeDistTraveled =shapeDistTraveled;
        Trip t = bT.getTrips().get(this.tripId.toString());
        if (t != null) {
            t.addRoute(this);
        }
        else {
            bT.getTrips().put(this.tripId.toString(), new Trip(this.tripId, this));
        }
    }

    public double Weight(){
        /*
        this.journeyTime = this.arrivalTimeNextStop.toSecondOfDay() - this.arrivalTimeCurrentStop.toSecondOfDay();
        return journeyTime;
        */
        return 1;
        //TODO
    }

    public Integer getTripId() {
        return this.tripId;
    }

}
