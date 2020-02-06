package com.example.mycamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnCapture;
    private ImageView ivImage;
    private int screenWidth;
    private float ratio = 0.5f; //取景框高宽比

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivImage = findViewById(R.id.iv_image);
        btnCapture = findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SAMPLE_CROPPED_IMAGE_NAME = "cropImage_"+System.currentTimeMillis()+".png";
                Uri destination = Uri.fromFile(new File(getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));
                EasyCamera.create(destination)
                        .withViewRatio(ratio)
                        .withMarginCameraEdge(50,50)
                        .start(MainActivity.this);
            }
        });

        screenWidth = (int) DisplayUtil.getScreenWidth(this);
        ivImage.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, (int) (screenWidth * ratio)));
        ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == EasyCamera.REQUEST_CAPTURE) {


                Uri resultUri = EasyCamera.getOutput(data);
                int width = EasyCamera.getImageWidth(data);
                int height = EasyCamera.getImageHeight(data);
                ivImage.setImageURI(resultUri);


                Log.i(TAG,"imageWidth:"+width);
                Log.i(TAG,"imageHeight:"+height);
            }
        }
    }
}
