package com.bvp.matchcutassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ConfirmTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_two);

        File oneImage = CameraOneActivity.getOutputMediaFile();
        File twoImage = CameraTwoActivity.getOutputMediaFile();

        if(oneImage.exists() && twoImage.exists()){
            Bitmap oneBitmap = BitmapFactory.decodeFile(oneImage.getAbsolutePath());
            ImageView oneImageView = (ImageView) findViewById(R.id.image_one);
            oneImageView.setImageBitmap(oneBitmap);

            Bitmap twoBitmap = BitmapFactory.decodeFile(twoImage.getAbsolutePath());
            ImageView twoImageView = (ImageView) findViewById(R.id.image_two);
            twoImageView.setImageBitmap(twoBitmap);
        }
    }

    public void downloadImages(View v){
        /*Intent cameraOneIntent = new Intent(getApplicationContext(), CameraOneActivity.class);
        startActivity(cameraOneIntent);*/
        Toast.makeText(getApplicationContext(), "Downloaded check mca folder in file system.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void share(View v){
        /*Intent cameraTwoIntent = new Intent(getApplicationContext(), CameraTwoActivity.class);
        startActivity(cameraTwoIntent);*/
        Toast.makeText(getApplicationContext(), "Exiting application.", Toast.LENGTH_LONG).show();
        finish();
    }
}
