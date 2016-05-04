package br.com.ufg.buscadorfacebook.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class JsonConverter {

    private static ObjectMapper objMapper = new ObjectMapper();

    public static <T> ArrayList<T> JsonArrayToObjectArray(JSONArray jsonArray, Class<T> tClass)
    {
        ArrayList<JSONObject> jsonObjectArrayList = JsonArrayToJsonObjectArrayList(jsonArray);
        ArrayList<T> objectArrayList = new ArrayList<T>();

        for(JSONObject jsonObject : jsonObjectArrayList)
        {
            T object = JsonObjectToObject(jsonObject, tClass);
            objectArrayList.add(object);
        }

        return objectArrayList;
    }

    public static <T> T JsonObjectToObject(JSONObject jsonObject, Class<T> tClass)
    {
        Exception exception;


            try {
                T genericClass = objMapper.readValue(jsonObject.toString(), tClass);
                return genericClass;
            } catch (JsonGenerationException e) {
                exception = e;
                e.printStackTrace();
                return  null;
            } catch (JsonMappingException e) {
                exception = e;
                e.printStackTrace();
                return  null;
            } catch (JsonParseException e) {
                exception = e;
                e.printStackTrace();
                return  null;
            } catch (IOException e) {
                exception = e;
                e.printStackTrace();
                return  null;
            }


    }

    public static ArrayList<JSONObject> JsonArrayToJsonObjectArrayList(JSONArray jsonArray)
    {
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                jsonObjectArrayList.add(jsonObject);

            } catch (JSONException e) {

                e.printStackTrace();
                continue;

            }
        }

        return  jsonObjectArrayList;
    }

}
