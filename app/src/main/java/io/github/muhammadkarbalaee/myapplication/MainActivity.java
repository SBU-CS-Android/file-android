package io.github.muhammadkarbalaee.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

  EditText textInput;
  Button dbButton;
  Button sendButton;
  Realm realmDB;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Realm.init(this);

    RealmConfiguration realmConfig = new RealmConfiguration.Builder().name("realmDB")
        .allowQueriesOnUiThread(true)
        .allowWritesOnUiThread(true)
        .build();
    realmDB = Realm.getInstance(realmConfig);


    textInput = findViewById(R.id.editTextTextPersonName);
    dbButton = findViewById(R.id.button);
    sendButton = findViewById(R.id.button2);

    dbButton.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        User newUser = new User(textInput.getText().toString());
        realmDB.beginTransaction();
        realmDB.copyToRealm(newUser);
        realmDB.commitTransaction();
        Toast.makeText(getApplicationContext(),"Saved to DB", Toast.LENGTH_LONG).show();
      }
    });

    sendButton.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        RealmQuery<User> usersQuery = realmDB.where(User.class);
        usersQuery.equalTo("username","hamid");
        RealmResults<User> userResults = usersQuery.findAll();
        //because realm objects can only be accessed at the thread they were generated
        ArrayList<User> users = new ArrayList<>(realmDB.copyFromRealm(userResults));
        SendServerTask sendServerTask = new SendServerTask(users);
        sendServerTask.execute();
        Toast.makeText(getApplicationContext(),"Sent to server", Toast.LENGTH_LONG).show();
      }
    });
  }
}
