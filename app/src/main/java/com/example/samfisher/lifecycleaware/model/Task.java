package com.example.samfisher.lifecycleaware.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Samfisher on 19/01/2018.
 */

public class Task {

  @SerializedName("id")
  @Expose
  private int id;

  @SerializedName("title")
  @Expose
  private String title;

  @SerializedName("description")
  @Expose
  private String description;

  public Task() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
