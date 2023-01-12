package com.app.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class JSON<T> {

    public T fromJson(String requestMessage, Class<T> objectClass) throws JsonProcessingException {
        return (new ObjectMapper()).readValue(requestMessage,objectClass);
    }

    public String toJson(T object) throws IOException {
        StringWriter stringWriter=new StringWriter();
         (new ObjectMapper()).writeValue(stringWriter,object);
        return stringWriter.toString();
    }
    public String toJsonArray(ArrayList<T> list, Class<T> objectClass) throws IOException {
        JSONArray jsArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsArray.put(list.get(i));
        }
        StringWriter stringWriter=new StringWriter();
        (new ObjectMapper()).writeValue(stringWriter,jsArray);
        return stringWriter.toString();
    }


}
