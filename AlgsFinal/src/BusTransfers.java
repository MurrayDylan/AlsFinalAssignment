import java.nio.file.FileSystemNotFoundException;
import java.time.LocalTime;
import java.io.*;


public class BusTransfers {
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
    //Temporary storage of stops, this needs to be implemented as TST
    TST<Stop> stopsById;
    TST<Stop> stopsByName;
    public BusTransfers() {
        this.stopsById = new TST<Stop>();
        this.stopsByName = new TST<Stop>();
        this.loadFile("/Users/dylanmurray/Downloads/input files/stops.txt", RecordType.Stop);
        this.loadFile("/Users/dylanmurray/Downloads/input files/transfers.txt", RecordType.Transfer);
        this.loadFile("/Users/dylanmurray/Downloads/input files/stop_times.txt", RecordType.Route);
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
                            loadRoute(currRecord, prevRecord);
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
                this.stopsById.put(stop.getStopId().toString(), stop);
                this.stopsByName.put(stop.getTransformedStopName(), stop);
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
            Integer minTransferTime = (record.length > RECORD_TRANSFERS_MIN_TRANSFER_TIME) ? Integer.parseInt(record[RECORD_TRANSFERS_MIN_TRANSFER_TIME]) : null;

            if (fromStopId != null) {
                Transfer transfer = new Transfer(
                        fromStopId,
                        toStopId,
                        transferType,
                        minTransferTime

                );
                Stop stop = this.stopsById.get(transfer.getFromStopId().toString());
                if(stop != null) {
                    stop.addEdge(transfer);
                }
            }
        }
        catch (NumberFormatException nfe) {
            // TODO Handle Exception
            System.out.println(nfe.toString());
        }
    }

    private void loadRoute(String[] prevRecord, String[] currRecord) {

       /*
        try {
            Integer tripID = (record.length > RECORD_STOP_STOP_ID) ? Integer.parseInt(record[RECORD_STOP_STOP_ID]) : null;
            LocalTime arrivalTime = (record.length > RECORD_STOP_STOP_CODE) ? record[RECORD_STOP_STOP_CODE] : null;
            LocalTime departureTime = (record.length > RECORD_STOP_STOP_NAME) ? record[RECORD_STOP_STOP_NAME] : null;
            int stopSequence = (record.length > RECORD_STOP_STOP_DESC) ? record[RECORD_STOP_STOP_DESC] : null;
            String stopHeadsign = (record.length > RECORD_STOP_STOP_LAT) ? Double.parseDouble(record[RECORD_STOP_STOP_LAT]) : null;
            PickupType pickupType = (record.length > RECORD_STOP_STOP_LON) ? Double.parseDouble(record[RECORD_STOP_STOP_LON]) : null;
            PickupType dropOffType = (record.length > RECORD_STOP_ZONE_ID) ? record[RECORD_STOP_ZONE_ID] : null;
            Double shapeDistTraveled = (record.length > RECORD_STOP_STOP_URL) ? record[RECORD_STOP_STOP_URL] : null;

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
                this.stops.put(stop.getStop_id(), stop);
            }
        }
        catch (NumberFormatException nfe) {
            // TODO Handle Exception
            System.out.println(nfe.toString());
        }

        */
    }


}
