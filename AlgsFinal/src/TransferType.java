public enum TransferType {
    Recommended(0),
    ZeroTime(1),
    MinimumTime(2),
    NotPossible(3)
    ;

    private final int val;
    private TransferType(int val) { this.val = val; }
    public int getValue() { return this.val; }
};