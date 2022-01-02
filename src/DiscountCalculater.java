import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

public class DiscountCalculater {
    public static boolean birthDate(LocalDate reqDate, LocalDate clientDate){
        if(reqDate.getDayOfMonth() == clientDate.getDayOfMonth() && reqDate.getMonth() == clientDate.getMonth()){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean PublicHoliday(LocalDate ReqDate){
        if(ReqDate.getDayOfMonth() == 18 && ReqDate.getMonth() == Month.JUNE){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean TwoPassenger(int passengers){
        if(passengers > 1){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean onDiscount(String Destination){
        ArrayList<String> areas = SqlDb.getInstance().getDiscountAreas();
        for(String area : areas){
            if(area.equalsIgnoreCase(Destination)) {
                return true;
            }
        }
        return false;
    }

    public static boolean FirstRide(String username){
        if(SqlDb.getInstance().FirstRide(username)){
            return true;
        }else {
            return false;
        }
    }

}
