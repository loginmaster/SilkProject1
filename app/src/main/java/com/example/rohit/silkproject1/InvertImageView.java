package com.example.rohit.silkproject1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class InvertImageView extends ActionBarActivity implements ImageView.OnClickListener{

    ImageView invertedImage;
    Bitmap selectedBitMapImage;
    Button saveImageButton;
    Button cancelButton;
    Bitmap newImage;
    EditText getImageName;
    Button submitImageNameBtn;
    Button cancelImageNameBtn;
    View topLevelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invert_image_view);

        saveImageButton= (Button)findViewById(R.id.saveImage);
        cancelButton= (Button)findViewById(R.id.backToImageSelection);
        submitImageNameBtn= (Button)findViewById(R.id.submitImageNameBtn);
        cancelImageNameBtn= (Button)findViewById(R.id.canceCombinelImageNameBtn);
        topLevelLayout = findViewById(R.id.top_layout);

        getImageName =(EditText) findViewById(R.id.getImageNameCombine);

        saveImageButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        submitImageNameBtn.setOnClickListener(this);
        cancelImageNameBtn.setOnClickListener(this);

        invertedImage = (ImageView) findViewById(R.id.silkImage);
        Intent intent = getIntent();
        Uri imageUri = Uri.parse(intent.getStringExtra("image-uri"));

        try {
            selectedBitMapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Image not able to load, please try again ! ",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        newImage = invertedImage(selectedBitMapImage);
        invertedImage.setImageBitmap(newImage);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.saveImage) {
            getImageName.setVisibility(View.VISIBLE);
            submitImageNameBtn.setVisibility(View.VISIBLE);
            cancelImageNameBtn.setVisibility(View.VISIBLE);
            topLevelLayout.setVisibility(View.VISIBLE);
        }

        if (v.getId() == R.id.canceCombinelImageNameBtn) {
            clearPage();
        }

        if (v.getId() == R.id.submitImageNameBtn) {
            if (!getImageName.getText().toString().matches("")) {
                String imageName = getImageName.getText().toString();
                String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/";
                File sdIconStorageDir = new File(iconsStoragePath);

                //create storage directories, if they don't exist
                sdIconStorageDir.mkdirs();

                try {
                    String filePath = sdIconStorageDir.toString() + "/" + imageName + ".BMP";
                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);

                    BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

                    //choose another format if PNG doesn't suit you
                    newImage.compress(Bitmap.CompressFormat.PNG, 100, bos);

                    bos.flush();
                    bos.close();

                    Toast.makeText(getApplicationContext(),
                            imageName + " is saved ! ",
                            Toast.LENGTH_SHORT).show();
                    clearPage();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            " Image is not saved ! ",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Saving image error",e.getMessage());
                    e.printStackTrace();

                }

            } else {
                Toast.makeText(getApplicationContext(),
                        " Please Enter the name of Image to save ",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if(v.getId()==R.id.backToImageSelection){
            Intent startAnotherActivity = new Intent(InvertImageView.this, ImageSelection.class);
            startActivity(startAnotherActivity);
        }

    }

    private void clearPage() {
        getImageName.setVisibility(View.GONE);
        submitImageNameBtn.setVisibility(View.GONE);
        cancelImageNameBtn.setVisibility(View.GONE);
        topLevelLayout.setVisibility(View.GONE);
        getImageName.setText("");
    }

    public Bitmap invertedImage(Bitmap image){
        Bitmap finalImage = Bitmap.createBitmap(image.getWidth(),image.getHeight(),image.getConfig());
        int A,R,G,B;
        int pixelColour;
        int height= image.getHeight();
        int width = image.getWidth();
        int white = 255;
        int[][] multiD = new int[4][width];
        int column=0;
        for(int y = 0; y< height;y++){
            boolean fill = false;
            for(int x = 0; x< width;x++){
                pixelColour = image.getPixel(x, y);
                A = Color.alpha(pixelColour);
                R = Color.red(pixelColour);
                G = Color.green(pixelColour);
                B = Color.blue(pixelColour);
                if (white !=R && white !=G && white !=B ){
                    fill = true;
                }
                multiD[0][x]=A;
                multiD[1][x]=R;
                multiD[2][x]=G;
                multiD[3][x]=B;
                if(x==width-1){
                    if(fill){
                        for (int t=0;t<width;t++){
                            A=multiD[0][t];
                            R=multiD[1][t];
                            G=multiD[2][t];
                            B=multiD[3][t];
                            finalImage.setPixel(t,y-column,Color.argb(A,R,G,B));
                        }
                    }  else {
                        column++;
                    }
                }
            }
        }
        return getFinalImage(finalImage, column);
    }

    private Bitmap getFinalImage(Bitmap finalImage, int column) {
        Bitmap finalImageBMP= Bitmap.createBitmap(finalImage.getWidth(),(finalImage.getHeight()-column),finalImage.getConfig());
        int A,R,G,B;
        int pixelColour;
        int height= (finalImage.getHeight()-column);
        int width = finalImage.getWidth();
        for(int y = 0; y< height;y++){
            for(int x = 0; x< width;x++){
                pixelColour = finalImage.getPixel(x, y);
                A = Color.alpha(pixelColour);
                R = Color.red(pixelColour);
                G = Color.green(pixelColour);
                B = Color.blue(pixelColour);
                finalImageBMP.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return finalImageBMP;
    }
}