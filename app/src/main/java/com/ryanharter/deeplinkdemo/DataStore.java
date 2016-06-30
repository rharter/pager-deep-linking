package com.ryanharter.deeplinkdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class DataStore {

  private static final LinkedHashMap<String, List<String>> ITEMS = new LinkedHashMap<>();
  static {
    ITEMS.put("numbers", Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen"));
    ITEMS.put("colors", Arrays.asList("red", "orange", "yellow", "green", "blue", "indigo", "violet", "taupe?"));
    ITEMS.put("food", Arrays.asList("fish", "steak", "pasta", "vegetables", "fruit", "soups", "desserts"));
  }

  public static List<String> getItems(String categoryId) {
    return ITEMS.get(categoryId);
  }

  public static List<String> getCategories() {
    return new ArrayList<>(ITEMS.keySet());
  }

  public static List<String> getFeaturedItems() {
    return Arrays.asList("one", "orange", "fruit", "ten", "steak");
  }

}
