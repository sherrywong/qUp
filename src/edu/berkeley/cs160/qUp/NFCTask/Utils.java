package edu.berkeley.cs160.qUp.NFCTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidneyfeygin on 11/25/13.
 */
public class Utils {

    /**
     * Turns a JSONArray into a list of a specified class. Assumes that the class specified have
     * static method fromJSON() implemented. Otherwise NoSuchMethodException will be thrown.
     *
     * @param array
     * @param clss
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(JSONArray array, Class<T> clss) throws Exception {
        List<T> list = new ArrayList<T>(array.length());
        Method fromJSON = clss.getDeclaredMethod("fromJSON", JSONObject.class);
        for (int i = 0; i < array.length(); i++) {
            list.add((T) fromJSON.invoke(clss, new Object[]{array.getJSONObject(i)}));
        }
        return list;
    }

    /**
     * Turns a JSONArray into a string list.
     *
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<String> toStringList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<String>(array.length());
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }

    /**
     * Filters an array, removing all the entries that is equal to "filter".
     *
     * @param list
     * @param filter
     * @return
     */
    public static <T> List<T> filter(List<T> list, T filter) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(filter)) {
                list.remove(i);
                break;
            }
        }
        return list;
    }

    /**
     * Turns a byte array into a string of its hexadecimal value.
     * http://www.rgagnon.com/javadetails/java-0596.html
     *
     * @param b The byte array.
     * @return
     * @throws Exception
     */
    public static String toHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hexByte = Integer.toHexString((b[i] & 0xff) + 0x100).substring(1);
            result.append(hexByte);
        }
        return result.toString();
    }


}
