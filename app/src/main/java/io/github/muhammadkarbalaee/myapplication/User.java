package io.github.muhammadkarbalaee.myapplication;

import io.realm.RealmObject;

public class User extends RealmObject {
  private String username;

  public User() {

  }

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
