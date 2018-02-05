package com.example.samfisher.lifecycleaware;

/**
 * Created by Samfisher on 22/01/2018.
 */

public class TaskEntity {

  public static final int TYPE_NORMAL = 0;
  public static final int TYPE_BIG = 1;
  public static final int TYPE_FEATURED = 2;

  private int id;

  private String title;

  private String description;

  private boolean done;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }
}
