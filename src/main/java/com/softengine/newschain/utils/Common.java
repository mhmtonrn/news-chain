package com.softengine.newschain.utils;

import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

//    public static <T> JsonObject modelToJsonObject(T t){
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
//        JsonElement jsonElement = gson.toJsonTree(t);
//        JsonObject jsonObject = (JsonObject) jsonElement;
//        jsonObject.remove("property");
//        return  jsonObject;
//    }

    public static <T> String collectionName(T t) {
        Document anatations = t.getClass().getAnnotation(Document.class);
        if (anatations == null)
            return t.getClass().getName();
        String temp = t.getClass().getAnnotation(Document.class).value();
        if (temp == null || temp.equals(""))
            return t.getClass().getSimpleName();
        return temp;
    }

	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
}