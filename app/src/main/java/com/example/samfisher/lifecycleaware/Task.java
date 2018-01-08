package com.example.samfisher.lifecycleaware;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Samfisher on 08/01/2018.
 */

public class Task extends RealmObject {
  @Required
  @PrimaryKey
  private String id;
  @Required
  private String name;
  private boolean done;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }
}
