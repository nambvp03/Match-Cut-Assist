package com.bvp.matchcutassist;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraOneActivity extends AppCompatActivity {

    ImageButton takePictureButton;
    FrameLayout frameLayout;
    Camera camera;
    ShowCamera showCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_one);


        takePictureButton = (ImageButton) findViewById(R.id.capture1);
        frameLayout = (FrameLayout) findViewById(R.id.camera_frame);

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //Toast.makeText(getApplicationContext(), "Taking pic yay!", Toast.LENGTH_LONG).show();
            File pictureFile = getOutputMediaFile();

            if(null == pictureFile){
                return;
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
                fileOutputStream.write(data);
                fileOutputStream.close();

                Intent intent = new Intent(getApplicationContext(),ConfirmOneActivity.class);
                startActivity(intent);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED)){
            return null;
        }
        else {
            File folderPath = new File(Environment.getExternalStorageDirectory() + File.separator + "mca");
            if(!folderPath.exists()){
                folderPath.mkdirs();
            }

            File outputImageFile = new File(folderPath, "tmp_1.jpg");
            return outputImageFile;
        }
    }

    public void captureImage(View v){
        if(null != camera){
            camera.takePicture(null, null, mPictureCallback);
        }
    }
}
