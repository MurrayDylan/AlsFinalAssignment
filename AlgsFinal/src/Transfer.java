public class Transfer extends Edge {
    private TransferType transferType;
    //Indicates the type of connection for the specified
    private Integer minTime;
    //Amount of time, in seconds, that must be available to permit a transfer between routes at the specified stops.

    public Transfer(Integer fromStopId, Integer toStopId, TransferType transferType, Integer minTime) {
        super(fromStopId, toStopId);
        this.transferType = transferType;
        this.minTime = minTime;
    }
    public int Weight(){
        return (this.transferType == TransferType.MinimumTime) ? minTime.intValue() : 0;
    }
}
