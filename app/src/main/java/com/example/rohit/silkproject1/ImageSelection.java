package com.example.rohit.silkproject1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageSelection extends ActionBarActivity implements  View.OnClickListener {
    ImageView imageToUpload;
    Button imageSelectionButton;
    Button invertImageButton;
    Button backToMainPageBtn;
    public static final int RESULT_IMAGE_LOAD = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);

        imageToUpload = (ImageView) findViewById(R.id.imageSelected);

        imageSelectionButton = (Button)findViewById(R.id.imageSelectionButton);
        invertImageButton = (Button)findViewById(R.id.invertImageButton);
        backToMainPageBtn = (Button)findViewById(R.id.backToMainPageBtn);

        imageSelectionButton.setOnClickListener(this);
        backToMainPageBtn.setOnClickListener(this);
        imageToUpload.setOnClickListener(this);
        invertImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(R.id.imageSelectionButton == v.getId()){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, RESULT_IMAGE_LOAD);
        }

        if(R.id.invertImageButton == v.getId()){
            if(imageUri != null){
                Intent startAnotherActivity = new Intent(ImageSelection.this, InvertImageView.class);
                startAnotherActivity.putExtra("image-uri", imageUri.toString());
                startActivity(startAnotherActivity);
            }else{
                Toast.makeText(getApplicationContext(),
                        "Please Select an Image !! ",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if(R.id.backToMainPageBtn == v.getId()){
            Intent startAnotherActivity;
            startAnotherActivity = new Intent(ImageSelection.this, StartupPage.class);
            startActivity(startAnotherActivity);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==RESULT_IMAGE_LOAD && resultCode==RESULT_OK && data!=null){
            imageUri= data.getData();
            imageToUpload.setImageURI(imageUri);
        }else{
            Toast.makeText(getApplicationContext(),
                    "Not able to load Image, Please try again !! ",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
