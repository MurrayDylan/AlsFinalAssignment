import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
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
    HashMap<Integer, Stop> stops;
    public BusTransfers() {
        this.stops = new HashMap<Integer, Stop>();
        this.loadFile("/Users/dylanmurray/Downloads/input files/stops.txt", RecordType.Stop);
        this.loadFile("/Users/dylanmurray/Downloads/input files/transfers.txt", RecordType.Transfer);
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
                    if (stops.size() % 200 == 0) {
                        System.out.println("Records loaded " + String.valueOf(stops.size()));
                    }
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
                this.stops.put(stop.getStop_id(), stop);
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
                Stop stop = this.stops.get(transfer.getFromStopId());
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

    }
}
