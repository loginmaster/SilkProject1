package com.example.rohit.silkproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class StartupPage  extends ActionBarActivity implements  View.OnClickListener{
    Button removeWhiteLinePage;
    Button combineImagePage;
    Button createJaquardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_page);

        removeWhiteLinePage = (Button)findViewById(R.id.removeWhiteLinePage);
        combineImagePage = (Button)findViewById(R.id.combineImagePage);
        createJaquardImage = (Button)findViewById(R.id.createJaquardImage);

        removeWhiteLinePage.setOnClickListener(this);
        combineImagePage.setOnClickListener(this);
        createJaquardImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(R.id.removeWhiteLinePage == v.getId()) {
            Intent startAnotherActivity = new Intent(StartupPage.this, ImageSelection.class);
            startActivity(startAnotherActivity);
        }else if(R.id.combineImagePage == v.getId()) {
            Intent startAnotherActivity = new Intent(StartupPage.this, CombineImages.class);
            startActivity(startAnotherActivity);
        }else if(R.id.createJaquardImage == v.getId()){
            Intent startAnotherActivity = new Intent(StartupPage.this, CreateJaquardImage.class);
            startActivity(startAnotherActivity);
        }

    }

}
