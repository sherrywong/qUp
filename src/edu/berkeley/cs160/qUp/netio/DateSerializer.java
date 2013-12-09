package edu.berkeley.cs160.qUp.netio;

/**
 * Purpose of Class:
 * <p/>
 * qUp ==> edu.berkeley.cs160.qUp.netio
 * Date: 12/8/13
 * Time: 1:45 AM
 * Version: 1.0
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;



class DateSerializer implements JsonDeserializer<Date> {
    private static final String[] DATE_FORMATS = new String[] {
            "yyyy-mm-dd'T'HH:mm:ss",
            "yyyy-mm-dd'T'HH:mm"
    };
    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF,
                            JsonDeserializationContext context) throws JsonParseException {
        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
            } catch (ParseException e) {
            }
        }
        throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
    }
}
