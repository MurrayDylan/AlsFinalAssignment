import java.time.LocalTime;
import java.io.*;
import java.util.ArrayList;

public class BusTimetable {
    static final int RECORD_STOP_STOP_ID = 0;
    static final int RECORD_STOP_STOP_CODE = 1;
    static final int RECORD_STOP_STOP_NAME = 2;
    static final int RECORD_STOP_STOP_DESC = 3;
    static final int RECORD_STOP_STOP_LAT = 4;
    static final int RECORD_STOP_STOP_LON = 5;
    static final int RECORD_STOP_ZONE_ID = 6;
    static final int RECORD_STOP_STOP_URL = 7;
    static final int RECORD_STOP_LOCATION_TYPE = 8;
    static final int RECORD_STOP_PARENT_STATION = 9;

    static final int RECORD_TRANSFERS_FROM_STOP_ID = 0;
    static final int RECORD_TRANSFERS_TO_STOP_ID = 1;
    static final int RECORD_TRANSFERS_TRANSFER_TYPE = 2;
    static final int RECORD_TRANSFERS_MIN_TRANSFER_TIME = 3;

    static final int RECORD_ROUTE_TRIP_ID = 0;
    static final int RECORD_ROUTE_ARRIVAL_TIME = 1;
    static final int RECORD_ROUTE_DEPARTURE_TIME = 2;
    static final int RECORD_ROUTE_STOP_ID = 3;
    static final int RECORD_ROUTE_STOP_SEQUENCE = 4;
    static final int RECORD_ROUTE_STOP_HEADSIGN = 5;
    static final int RECORD_ROUTE_PICKUP_TYPE = 6;
    static final int RECORD_ROUTE_DROP_OFF_TYPE = 7;
    static final int RECORD_ROUTE_SHAPE_DIST_TRAVELED = 8;



    //Temporary storage of stops, this needs to be implemented as TST
    private TST<Stop> stopsOrderedById;
    private TST<Stop> stopsOrderedByName;
    private TST<Trip> tripsOrderedById;
    public BusTimetable(String fileDirectory) {
        this.stopsOrderedById = new TST<Stop>();
        this.stopsOrderedByName = new TST<Stop>();
        this.tripsOrderedById = new TST<Trip>();
        this.loadFile(fileDirectory + "stops.txt", RecordType.Stop);
        this.loadFile(fileDirectory + "transfers.txt", RecordType.Transfer);
        this.loadFile(fileDirectory + "stop_times.txt", RecordType.Route);
    }

    private void loadFile(String fileName, RecordType type) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] currRecord;
            String[] prevRecord = null;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if(!isFirstLine) {
                    currRecord = line.split(",");
                    switch (type) {
                        case Stop:
                            loadStop(currRecord);
                            break;
                        case Route:
                            loadRoute(prevRecord, currRecord);
                            break;
                        case Transfer:
                            loadTransfer(currRecord);
                            break;
                    }
                    prevRecord = currRecord;
                }
                else{
                    isFirstLine = false;
                }
            }
        }
        catch (FileNotFoundException ex) {
            // TODO Handle Exception;
            System.out.println(ex.toString());
        }
        catch (IOException ie) {
            // TODO Handle Exception;
            System.out.println(ie.toString());
        }
    }

    private void loadStop(String[] record) {
        try {
            Integer stopId = (record.length > RECORD_STOP_STOP_ID) ? Integer.parseInt(record[RECORD_STOP_STOP_ID]) : null;
            String stopCode = (record.length > RECORD_STOP_STOP_CODE) ? record[RECORD_STOP_STOP_CODE] : null;
            String stopName = (record.length > RECORD_STOP_STOP_NAME) ? record[RECORD_STOP_STOP_NAME] : null;
            String stopDesc = (record.length > RECORD_STOP_STOP_DESC) ? record[RECORD_STOP_STOP_DESC] : null;
            Double stopLat = (record.length > RECORD_STOP_STOP_LAT) ? Double.parseDouble(record[RECORD_STOP_STOP_LAT]) : null;
            Double stopLon = (record.length > RECORD_STOP_STOP_LON) ? Double.parseDouble(record[RECORD_STOP_STOP_LON]) : null;
            String zoneId = (record.length > RECORD_STOP_ZONE_ID) ? record[RECORD_STOP_ZONE_ID] : null;
            String stopURL = (record.length > RECORD_STOP_STOP_URL) ? record[RECORD_STOP_STOP_URL] : null;
            LocationType locationType = (record.length > RECORD_STOP_LOCATION_TYPE) ? LocationType.values()[Integer.parseInt(record[RECORD_STOP_LOCATION_TYPE])] : null;
            Integer parentStation = (record.length > RECORD_STOP_PARENT_STATION) ? Integer.parseInt(record[RECORD_STOP_PARENT_STATION]) : null;

            if (stopId != null) {
                Stop stop = new Stop(
                        stopId,
                        stopCode,
                        stopName,
                        stopDesc,
                        stopLat,
                        stopLon,
                        zoneId,
                        stopURL,
                        locationType,
                        parentStation
                );
                this.stopsOrderedById.put(stop.getStopId().toString(), stop);
                this.stopsOrderedByName.put(stop.getTransformedStopName(), stop);
            }
        }
        catch (NumberFormatException nfe) {
            // TODO Handle Exception
            System.out.println(nfe.toString());
        }
    }

    private void loadTransfer(String[] record) {
        try {
            Integer fromStopId = (record.length > RECORD_TRANSFERS_FROM_STOP_ID) ? Integer.parseInt(record[RECORD_TRANSFERS_FROM_STOP_ID]) : null;
            Integer toStopId = (record.length > RECORD_TRANSFERS_TO_STOP_ID) ? Integer.parseInt(record[RECORD_TRANSFERS_TO_STOP_ID]) : null;
            TransferType transferType = (record.length > RECORD_TRANSFERS_TRANSFER_TYPE) ?  TransferType.values()[Integer.parseInt(record[RECORD_TRANSFERS_TRANSFER_TYPE])] : null;
            Double minTransferTime = (record.length > RECORD_TRANSFERS_MIN_TRANSFER_TIME) ? Double.parseDouble(record[RECORD_TRANSFERS_MIN_TRANSFER_TIME]) : null;

            if (fromStopId != null) {
                Transfer transfer = new Transfer(
                        this,
                        fromStopId,
                        toStopId,
                        transferType,
                        minTransferTime

                );

            }
        }
        catch (NumberFormatException nfe) {
            // TODO Handle Exception
            System.out.println(nfe.toString());
        }
    }

    private void loadRoute(String[] prevRecord, String[] currRecord) {

        try {
            Integer stopSequence = (currRecord.length > RECORD_ROUTE_STOP_SEQUENCE) ? Integer.parseInt(currRecord[RECORD_ROUTE_STOP_SEQUENCE]) : null;

            if(stopSequence >1) {
                Integer fromStopId = (prevRecord.length > RECORD_STOP_STOP_ID) ? Integer.parseInt(prevRecord[RECORD_ROUTE_STOP_ID]) : null;
                Integer toStopId = (currRecord.length > RECORD_STOP_STOP_ID) ? Integer.parseInt(currRecord[RECORD_ROUTE_STOP_ID]) : null;
                Integer tripID = (currRecord.length > RECORD_STOP_STOP_ID) ? Integer.parseInt(currRecord[RECORD_ROUTE_TRIP_ID]) : null;
                LocalTime arrivalTimePrevStop = (prevRecord.length > RECORD_ROUTE_ARRIVAL_TIME) ? Utility.parseLocalTime(prevRecord[RECORD_ROUTE_ARRIVAL_TIME]) : null;
                LocalTime departureTimePrevStop = (prevRecord.length > RECORD_ROUTE_DEPARTURE_TIME) ? Utility.parseLocalTime(prevRecord[RECORD_ROUTE_DEPARTURE_TIME]) : null;
                LocalTime arrivalTimeCurrentStop = (currRecord.length > RECORD_ROUTE_ARRIVAL_TIME) ? Utility.parseLocalTime(currRecord[RECORD_ROUTE_ARRIVAL_TIME]) : null;
                LocalTime departureTimeCurrentStop = (currRecord.length > RECORD_ROUTE_ARRIVAL_TIME) ? Utility.parseLocalTime(currRecord[RECORD_ROUTE_ARRIVAL_TIME]) : null;
                String stopHeadsign = (currRecord.length > RECORD_ROUTE_STOP_HEADSIGN) ? currRecord[RECORD_ROUTE_STOP_HEADSIGN] : null;
                PickupType pickupType = (currRecord.length > RECORD_ROUTE_PICKUP_TYPE) ? PickupType.values()[Integer.parseInt(currRecord[RECORD_ROUTE_PICKUP_TYPE])] : null;
                PickupType dropOffType = (currRecord.length > RECORD_ROUTE_DROP_OFF_TYPE) ? PickupType.values()[Integer.parseInt(currRecord[RECORD_ROUTE_DROP_OFF_TYPE])] : null;
                Double shapeDistTraveled = (currRecord.length > RECORD_ROUTE_SHAPE_DIST_TRAVELED) ? Double.parseDouble(currRecord[RECORD_ROUTE_SHAPE_DIST_TRAVELED]) : null;

                //TODO I chose to exclude any invalid bus times as they should be seen as errors. not enough documentation was provided in specifications so i chose myself
                if (arrivalTimeCurrentStop != null) {
                    Route route = new Route(
                            this,
                            fromStopId,
                            toStopId,
                            tripID,
                            arrivalTimePrevStop,
                            departureTimePrevStop,
                            arrivalTimeCurrentStop,
                            departureTimeCurrentStop,
                            stopSequence,
                            stopHeadsign,
                            pickupType,
                            dropOffType,
                            shapeDistTraveled
                    );
                }
            }
        }
        catch (NumberFormatException nfe) {
            // TODO Handle Exception
            System.out.println(nfe.toString());
        }
    }

    public TST<Stop> getAllStopsOrderedById() {return this.stopsOrderedById;};
    public TST<Stop> getAllStopsOrderedByName() {return this.stopsOrderedByName;};
    public TST<Trip> getAllTripsOrderedById() {return this.tripsOrderedById;};

    public ArrayList<Trip> getTripsByArrivalTime(LocalTime arrivalTime) {
        ArrayList<Trip> ret = new ArrayList<>();
        this.tripsOrderedById.toArrayList().forEach((t) -> {
            ArrayList<Route> journey = t.getJourney();
            for(int i = 0; i < journey.size(); i++) {
                Route route = journey.get(i);
                if(route.getArrival().equals(arrivalTime) || (route.getStopSequence() == 2 && route.getPrevArrival().equals(arrivalTime))) {
                    ret.add(t);
                    break;
                }
            }
        });
        return ret;
    }
}

