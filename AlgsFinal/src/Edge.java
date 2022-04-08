public abstract class Edge {
    private Integer fromStopId;
    private Integer toStopId;
    // the id of the stop the edge is going to

    //the time in seconds that this journey to the next stop will take

    public Edge(Integer fromStopId, Integer toStopId) {
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
    }

    public Integer getFromStopId() {
        return this.fromStopId;
    }

    public abstract double Weight();
}
