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
import java.util.ArrayList;
import java.util.List;


public class CombineImagesResult extends ActionBarActivity implements ImageView.OnClickListener{
    Button saveImageButton;
    Button cancelButton;
    Button submitCombineImageNameBtn;
    Button cancelCombineImageNameBtn;
    Bitmap newImage;
    EditText getImageNameCombine;
    ImageView combinedImageView;
    List<Bitmap> selectedBitMapImageList = new ArrayList<Bitmap>();
    View topLevelLayoutCombine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_images_result);

        saveImageButton= (Button)findViewById(R.id.saveConvertedImageImageBtn);
        cancelButton= (Button)findViewById(R.id.cancelConvertedImageBtn);
        submitCombineImageNameBtn = (Button)findViewById(R.id.submitConvertedImageNameBtn);
        cancelCombineImageNameBtn = (Button)findViewById(R.id.cancelConvertedImageNameBtn);
        topLevelLayoutCombine = findViewById(R.id.top_layout_combine);
        getImageNameCombine = (EditText)findViewById(R.id.selectBMPImage);

        saveImageButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        submitCombineImageNameBtn.setOnClickListener(this);
        cancelCombineImageNameBtn.setOnClickListener(this);

        combinedImageView = (ImageView) findViewById(R.id.combinedImageView);
        Intent intent = getIntent();
        String Uris=intent.getStringExtra("image-uri");
        String[] imageUriString=Uris.split(";");
        try {
            for(String uri:imageUriString){
               if(uri!="null"){
                   Uri imageUri = Uri.parse(uri);
                   selectedBitMapImageList.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
               }
            }
            newImage = combinedImageMethod(selectedBitMapImageList);
            combinedImageView.setImageBitmap(newImage);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Image not able to load, please try again ! ",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void clearPage() {
        getImageNameCombine.setVisibility(View.GONE);
        submitCombineImageNameBtn.setVisibility(View.GONE);
        cancelCombineImageNameBtn.setVisibility(View.GONE);
        topLevelLayoutCombine.setVisibility(View.GONE);
        getImageNameCombine.setText("");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.saveConvertedImageImageBtn) {
            getImageNameCombine.setVisibility(View.VISIBLE);
            submitCombineImageNameBtn.setVisibility(View.VISIBLE);
            cancelCombineImageNameBtn.setVisibility(View.VISIBLE);
            topLevelLayoutCombine.setVisibility(View.VISIBLE);
        }

        if (v.getId() == R.id.cancelConvertedImageNameBtn) {
            clearPage();
        }

        if (v.getId() == R.id.submitConvertedImageNameBtn) {
            if (!getImageNameCombine.getText().toString().matches("")) {
                String imageName = getImageNameCombine.getText().toString();
                String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myCombinedImages/";
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

        if(v.getId()==R.id.cancelConvertedImageBtn){
            Intent startAnotherActivity = new Intent(CombineImagesResult.this, CombineImages.class);
            startActivity(startAnotherActivity);
        }

    }

    public Bitmap combinedImageMethod(List<Bitmap> bitMapImagelist){
        int noOfImages=bitMapImagelist.size();
        int width=0;
        int height=0;
        width= bitMapImagelist.get(0).getWidth();
        height= bitMapImagelist.get(0).getHeight();
        Bitmap finalImage = Bitmap.createBitmap(width,(noOfImages*height), Bitmap.Config.ARGB_8888);
        int A,R,G,B;
        int pixelColour;
        try {

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < noOfImages; z++) {
                        pixelColour = bitMapImagelist.get(z).getPixel(x, y);
                        A = Color.alpha(pixelColour);
                        R = Color.red(pixelColour);
                        G = Color.green(pixelColour);
                        B = Color.blue(pixelColour);
                        finalImage.setPixel(x, ((noOfImages * y) + z), Color.argb(A, R, G, B));
                    }
                }
                Log.w("hi" + x, "y");
            }
        }catch (Exception e){
            Log.e("hellp",e.getMessage());
        }
        return finalImage;
    }
}
