public abstract class Edge {
    private Stop fromStop;
    private Stop toStop;
    // the id of the stop the edge is going to

    //the time in seconds that this journey to the next stop will take

    public Edge(BusTransfers bT, Integer fromStopId, Integer toStopId) {
        this.fromStop = bT.getStopsById().get(fromStopId.toString());
        this.toStop = bT.getStopsById().get(toStopId.toString());
        if (this.fromStop != null) {
            this.fromStop.addEdge(this);
        }
    }

    public abstract double Weight();
}
