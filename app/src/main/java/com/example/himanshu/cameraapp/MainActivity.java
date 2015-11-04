package com.example.himanshu.cameraapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String logtag="CameraApp8";
    private static int TAKE_PICTURE=1;
    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button cameraButton = (Button)findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(cameraListener);
    }

    private View.OnClickListener cameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takePhoto(v);

        }
    };

    private void takePhoto(View v)
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"pictures.jpg");
        imageuri =Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        startActivityForResult(intent,TAKE_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);

        if(resultCode== Activity.RESULT_OK) {

            Uri selectedimage = imageuri;
            getContentResolver().notifyChange(selectedimage, null);

            ImageView imageView = (ImageView) findViewById(R.id.image_camera);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedimage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, selectedimage.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(logtag, e.toString());


            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
