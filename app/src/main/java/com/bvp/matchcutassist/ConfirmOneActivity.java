package com.bvp.matchcutassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.bvp.matchcutassist.CameraOneActivity.getOutputMediaFile;
import static org.opencv.core.Core.merge;
import static org.opencv.core.Core.split;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.threshold;

public class ConfirmOneActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static {
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV not loaded");
        } else {
            Log.d(TAG, "OpenCV loaded");
        }
    }

    String edgeImageFilePath = Environment.getExternalStorageDirectory() + File.separator + "mca/edge.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_one);

        Bitmap inputBitmap = BitmapFactory.decodeFile(getOutputMediaFile().getAbsolutePath());
        detectEdges(inputBitmap);
        backgroundTransparent();

        File edgeImageFile = new File(edgeImageFilePath);
        if(edgeImageFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(edgeImageFilePath);
            ImageView myImage = (ImageView) findViewById(R.id.edge_image);
            myImage.setImageBitmap(myBitmap);
        }
    }

    private void detectEdges(Bitmap bitmap) {

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Mat rgba = new Mat();
        Utils.bitmapToMat(bitmap, rgba);

        Mat edges = new Mat(rgba.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4);
        Imgproc.Canny(edges, edges, 80, 100);

        imwrite(edgeImageFilePath, edges);
    }

    private void backgroundTransparent(){
        Mat src= Imgcodecs.imread(edgeImageFilePath,1);
        Mat dst = new Mat();//(src.rows,src.cols,CV_8UC4);
        Mat alpha = new Mat();
        Mat tmp = new Mat();

        Imgproc.cvtColor(src,tmp,Imgproc.COLOR_RGB2GRAY);
        threshold(tmp,alpha,100,255,THRESH_BINARY);

        List<Mat> rgb = new ArrayList<>();//[3]
        rgb.add(new Mat());
        rgb.add(new Mat());
        rgb.add(new Mat());

        split(src,rgb);

        List<Mat> rgbaList = new ArrayList<>();//[4]={rgb[0],rgb[1],rgb[2],alpha};
        rgbaList.add(rgb.get(0));
        rgbaList.add(rgb.get(0));
        rgbaList.add(rgb.get(0));
        rgbaList.add(alpha);

        merge(rgbaList, dst);
        imwrite(edgeImageFilePath,dst);
    }

    public void captureAgain(View v){
        Intent cameraOneIntent = new Intent(getApplicationContext(), CameraOneActivity.class);
        startActivity(cameraOneIntent);
        finish();
    }

    public void captureSecond(View v){
        Intent cameraTwoIntent = new Intent(getApplicationContext(), CameraTwoActivity.class);
        startActivity(cameraTwoIntent);
        finish();
    }

}
