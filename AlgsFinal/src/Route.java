import java.time.*;

public class Route extends Edge {

    private final int WEIGHT = 1;
    //weight is set to 1 as outlined in specification "1 if it comes from stop_times.txt"
    private Integer tripId;
    //Identifies a trip.
    private LocalTime arrivalTimePrevStop;
    //Arrival time at the previous stop for a specific trip on a route.
    private LocalTime departureTimePrevStop;
    //Departure time for the previous stop in the sequence.
    private LocalTime arrivalTimeCurrentStop;
    //Arrival time at the current stop for a specific trip on a route.
    private LocalTime departureTimeCurrentStop;
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

    public Route(BusTimetable bT,
                 Integer fromStopId,
                 Integer toStopId,
                 Integer tripId,
                 LocalTime arrivalTimePrevStop,
                 LocalTime departureTimePrevStop,
                 LocalTime arrivalTimeCurrentStop,
                 LocalTime departureTimeCurrentStop,
                 Integer stopSequence,
                 String stopHeadsign,
                 PickupType pickupType,
                 PickupType dropOffType,
                 Double shapeDistTraveled
    )
    {
        super(bT, fromStopId, toStopId);
        this.tripId = tripId;
        this.arrivalTimePrevStop = arrivalTimePrevStop;
        this.departureTimePrevStop = departureTimePrevStop;
        this.arrivalTimeCurrentStop = arrivalTimeCurrentStop;
        this.departureTimeCurrentStop = departureTimeCurrentStop;
        this.stopSequence = stopSequence;
        this.stopHeadsign = stopHeadsign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;
        this.shapeDistTraveled =shapeDistTraveled;
        Trip t = bT.getAllTripsOrderedById().get(this.tripId.toString());
        if (t != null) {
            t.addRoute(this);
        }
        else {
            bT.getAllTripsOrderedById().put(this.tripId.toString(), new Trip(this.tripId, this));
        }
    }
    //weight is set to 1 as outlined in specification "1 if it comes from stop_times.txt"
    public double getWeight() { return WEIGHT; }

    public Integer getTripId() {
        return this.tripId;
    }

    public LocalTime getArrival() {
        return this.arrivalTimeCurrentStop;
    }

    public LocalTime getPrevArrival() {
        return this.arrivalTimePrevStop;
    }

    public Integer getStopSequence() {
        return this.stopSequence;
    }

    @Override
    public String toString() {
        //using getStopId(), if i were to use a fancier gui i would use the stop description/ name as there would be more space
        StringBuilder ret = new StringBuilder();
        if (this.stopSequence == 2) {
            ret.append("Full route: " + this.getFromStop().getStopId());
            ret.append(" [" + this.getPrevArrival() + "]");
        }
        ret.append(" -> " + this.getToStop().getStopId());
        ret.append(" [" + this.getArrival() + "]");
        return ret.toString();
    }

}
