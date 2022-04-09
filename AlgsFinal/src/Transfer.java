public class Transfer extends Edge {
    private TransferType transferType;
    //Indicates the type of connection for the specified
    private Double minTime;
    //Amount of time, in seconds, that must be available to permit a transfer between routes at the specified stops.

    public Transfer(BusTimetable bT, Integer fromStopId, Integer toStopId, TransferType transferType, Double minTime) {
        super(bT, fromStopId, toStopId);
        this.transferType = transferType;
        this.minTime = minTime;
    }
    /*
    weight is set to minimum time divided by 100 or 2 as outlined in specification

    "2 if it comes from transfers.txt with transfer type 0 (which is immediate transfer possible)
     and for transfer type 2 the cost is the minimum transfer time divided by 100."
    */
    public double getWeight(){
        return (this.transferType == TransferType.MinimumTime) ? minTime.doubleValue() / 100.0 : 2;
    }
}
