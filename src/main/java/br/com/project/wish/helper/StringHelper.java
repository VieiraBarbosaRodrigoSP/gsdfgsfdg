package br.com.project.wish.helper;

import com.google.gson.Gson;

public class StringHelper {
  public static String convertObjectTo(Object object) {
    Gson gson = new Gson();
    var payload = gson.toJson(object);
    return payload;
  }
}