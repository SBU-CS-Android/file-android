package io.github.muhammadkarbalaee.myapplication;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class SendServerTask extends AsyncTask<Void,Void,Void> {

  private final ArrayList<User> users;

  public SendServerTask(ArrayList<User> users) {
    this.users = users;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    try {
      Socket socket = new Socket("172.20.113.8", 5000);
      DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
      dataOutputStream.writeUTF(users.size() + "");
      for (User user : users) {
        dataOutputStream.writeUTF(user.getUsername());
        dataOutputStream.flush();
      }
      dataOutputStream.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
