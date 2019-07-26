package com.bvp.matchcutassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ConfirmTwoActivity extends AppCompatActivity {

    File oneImage = CameraOneActivity.getOutputMediaFile();
    File twoImage = CameraTwoActivity.getOutputMediaFile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_two);

        //Showing both the images on a page
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

    public void share(View v) {

        ArrayList<Uri> imageUris = new ArrayList();

        imageUris.add(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, oneImage));
        imageUris.add(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, twoImage));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share images via"));

        Toast.makeText(getApplicationContext(), "Exiting application.", Toast.LENGTH_LONG).show();
        //finish();
    }
}
