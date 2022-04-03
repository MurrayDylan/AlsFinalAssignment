public class Transfer extends Edge {
    private TransferType transfer_type;
    //Indicates the type of connection for the specified
    private int minTime;
    //Amount of time, in seconds, that must be available to permit a transfer between routes at the specified stops.
    private int from_stop_id;
    //Identifies a stop or station where a connection between routes begins.

    public Transfer() {

    }

}
