package io.github.muhammadkarbalaee.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
  ImageView imageView;
  Button pickImageButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    pickImageButton = findViewById(R.id.button);
    imageView = findViewById(R.id.imageView);

    pickImageButton.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        /*
        Genius!!! this opens up the gallery
         */
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        gallery.putExtra("crop", "true");
        gallery.putExtra("scale", true);
        gallery.putExtra("outputX", 256);
        gallery.putExtra("outputY", 256);
        gallery.putExtra("aspectX", 1);
        gallery.putExtra("aspectY", 1);
        gallery.putExtra("return-data", true);
        startActivityForResult(gallery,1);
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
      Uri imageUri = data.getData();
      imageView.setImageURI(imageUri);
      loadImage(imageUri);
    }
  }

  private void loadImage(Uri imageUri) {
    try {
      InputStream inputStream = this.getContentResolver().openInputStream(imageUri);
      byte[] fileByte = new byte[inputStream.available()];
      System.out.println("chosen image file size:   " + inputStream.available());
      inputStream.read(fileByte);
      inputStream.close();
      saveImage(fileByte);
      loadFromFolder();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadFromFolder() throws IOException {
    FileInputStream fileInputStream = openFileInput("image.png");
    System.out.println("new image file size:   " + fileInputStream.available());
  }

  private void saveImage(byte[] fileByte) throws IOException {
    FileOutputStream fileOutputStream = openFileOutput("image.png", MODE_PRIVATE);
    fileOutputStream.write(fileByte);
    fileOutputStream.flush();
    fileOutputStream.close();
  }
}
