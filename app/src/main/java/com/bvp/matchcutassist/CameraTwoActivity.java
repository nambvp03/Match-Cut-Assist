package com.bvp.matchcutassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraTwoActivity extends AppCompatActivity {

    ImageButton takePictureButton;
    FrameLayout frameLayout;
    Camera camera;
    ShowCamera showCamera;

    String edgeImageFilePath = Environment.getExternalStorageDirectory() + File.separator + "mca/edge.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_two);



        takePictureButton = (ImageButton) findViewById(R.id.capture2);
        frameLayout = (FrameLayout) findViewById(R.id.camera_frame2);

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

        File edgeImageFile = new File(edgeImageFilePath);
        if(edgeImageFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(edgeImageFilePath);
            ImageView myImage = (ImageView) findViewById(R.id.edge_image_overlay);
            myImage.setImageBitmap(myBitmap);
        }
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

                Intent intent = new Intent(getApplicationContext(),ConfirmTwoActivity.class);
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

            File outputImageFile = new File(folderPath, "tmp_2.jpg");
            return outputImageFile;
        }
    }

    public void captureImage(View v){
        if(null != camera){
            camera.takePicture(null, null, mPictureCallback);
        }
    }
}
