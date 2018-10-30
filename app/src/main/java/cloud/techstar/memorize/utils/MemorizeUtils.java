package cloud.techstar.memorize.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MemorizeUtils {

    public static String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

}
