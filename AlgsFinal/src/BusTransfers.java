import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.io.*;

public class BusTransfers {
    //Temporary storage of stops, this needs to be implemented as TST
    ArrayList<Stop> stops;
    public BusTransfers() {

    }

    private void loadFile(String fileName, RecordType type) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String currRecord;
            String prevRecord = null;
            while ((currRecord = br.readLine()) != null) {
               switch(type) {
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
        }
        catch (FileNotFoundException ex) {
            // TODO Handle File Not Found Exception
            ;
        }
        catch (IOException ie) {
            // TODO Handle IO Exception
            ;
        }
    }

    private void loadStop(String record) {

    }

    private void loadTransfer(String record) {

    }

    private void loadRoute(String prevRecord, String currRecord) {

    }
}
