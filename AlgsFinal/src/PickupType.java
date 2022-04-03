public enum PickupType {
    Regular(0),
    None(1),
    MustArrange(2),
    CoordinateWithDriver(3)
    ;

    private final int val;
    private PickupType(int val) { this.val = val; }
    public int getValue() { return this.val; }
}

  /*
    Indicates pickup method. Valid options are:

    0 or empty - Regularly scheduled pickup.
    1 - No pickup available.
    2 - Must phone agency to arrange pickup.
    3 - Must coordinate with driver to arrange pickup.
     */