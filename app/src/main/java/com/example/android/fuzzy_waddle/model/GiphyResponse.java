package com.example.android.fuzzy_waddle.model;

/*
This module borrowed from open source Signal android app https://github.com/signalapp/Signal-Android
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GiphyResponse {

  @JsonProperty
  private List<GiphyImage> data;

  public List<GiphyImage> getData() {
    return data;
  }

}
