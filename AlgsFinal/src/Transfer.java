public class Transfer extends Edge {
    private TransferType transferType;
    //Indicates the type of connection for the specified
    private Double minTime;
    //Amount of time, in seconds, that must be available to permit a transfer between routes at the specified stops.

    public Transfer(BusTransfers bT, Integer fromStopId, Integer toStopId, TransferType transferType, Double minTime) {
        super(bT, fromStopId, toStopId);
        this.transferType = transferType;
        this.minTime = minTime;
    }
    public double Weight(){
        return (this.transferType == TransferType.MinimumTime) ? minTime.doubleValue() / 100.0 : 2;
    }
}
