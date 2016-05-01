package com.example.rohit.silkproject1;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final List<String> imageUrlList;
    public CustomList(Activity context, List<String> imageUrlList) {
        super(context, R.layout.list_single,imageUrlList);
        this.context = context;
        this.imageUrlList = imageUrlList;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        String uri=imageUrlList.get(position);
        imageView.setImageURI(Uri.parse(uri));
        return rowView;
    }
}