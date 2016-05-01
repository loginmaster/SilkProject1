package com.example.rohit.silkproject1;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CombineImages extends ActionBarActivity   implements  View.OnClickListener{
    Button multipleImageSelectionButton;
    Button combineImagesButton;
    Uri imageUri;
    public static final int RESULT_IMAGE_LOAD = 1;
    ClipData imageClipData;
    ListView listImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_images);

        listImageView=(ListView)findViewById(R.id.listImageView);
        multipleImageSelectionButton = (Button)findViewById(R.id.multipleImageSelectionBtn);
        combineImagesButton = (Button)findViewById(R.id.combineImagesBtn);

        multipleImageSelectionButton.setOnClickListener(this);
        combineImagesButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(R.id.multipleImageSelectionBtn == v.getId()){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_IMAGE_LOAD);
        }


        if(R.id.combineImagesBtn == v.getId()){
            Toast.makeText(getApplicationContext(),
                    "Development in Progress !! Thank You for your patient...:)",
                    Toast.LENGTH_SHORT).show();
        }
        /*if(R.id.combineImagesBtn == v.getId()){
            if(imageUri != null){
                Intent startAnotherActivity = new Intent(CombineImages.this, InvertImageView.class);
                startAnotherActivity.putExtra("image-uri", imageUri.toString());
                startActivity(startAnotherActivity);
            }else{
                Toast.makeText(getApplicationContext(),
                        "Please Select an Image !! ",
                        Toast.LENGTH_SHORT).show();
            }
        }*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==RESULT_IMAGE_LOAD && resultCode==RESULT_OK && data!=null && data.getClipData()!=null){
            List<String> imageUrlList = new ArrayList<String>();
            Boolean bmpNotSelected = false;
            imageClipData = data.getClipData();
           for(int i=0;i<imageClipData.getItemCount();i++){
               ClipData.Item items=imageClipData.getItemAt(i);
               if(!items.getUri().toString().contains(".BMP")){
                   bmpNotSelected = true;
                   break;
               }
               imageUrlList.add(items.getUri().toString());
           }
            if(!bmpNotSelected) {
                CustomList adapter = new CustomList(CombineImages.this, imageUrlList);
                listImageView.setAdapter(adapter);
            }else{
                Toast.makeText(getApplicationContext(),
                        "Please Select BMP images only !! ",
                        Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),
                    "Please Select Atleast Two OR More BMP image !! ",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
