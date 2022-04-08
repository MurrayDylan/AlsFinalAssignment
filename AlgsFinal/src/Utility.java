import java.time.LocalTime;

public class Utility {
    public static LocalTime parseLocalTime(String time) {
        try{
            String t = time.trim();
            return LocalTime.parse(((t.length() == 7 ) ? "0" : "") + t);
        }
        catch(Exception e){
            return null;
        }
    }
}
