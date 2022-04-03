public enum LocationType{
    Stop(0),
    Station(1)
    ;

    private final int val;
    private LocationType(int val) { this.val = val; }
    public int getValue() { return this.val; }
};
    /*
    Type of the location:
    • 0 (or empty): Stop (or Platform). A location where passengers board or disembark from a transit vehicle. Is called a platform when defined within a parent_station.
    • 1: Station. A physical structure or area that contains one or more platform.
     */
