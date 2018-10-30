package cloud.techstar.memorize.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<String> fromTimestamp(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
        // return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String arraylistToString(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        return json;
        // return date == null ? null : date.getTime();
    }
}