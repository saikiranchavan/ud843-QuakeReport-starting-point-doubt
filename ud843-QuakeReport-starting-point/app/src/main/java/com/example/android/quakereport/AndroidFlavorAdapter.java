package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by user on 6/8/2017.
 */

public class AndroidFlavorAdapter extends ArrayAdapter<AndroidFlavor>{
private static final String LOG_TAG=AndroidFlavorAdapter.class.getSimpleName();
    public AndroidFlavorAdapter(Activity context, ArrayList<AndroidFlavor> androidFlavors)
    {
        super(context,0,androidFlavors);
    }
    public static class ViewHolderItems
    {

        private TextView scaleText;
        private TextView cityText;
        private TextView cityText1;
        private TextView timeText;
        private TextView dateText;
        ViewHolderItems(View v)
        {
            scaleText=(TextView)v.findViewById(R.id.scale);
            cityText=(TextView)v.findViewById(R.id.city);
            cityText1=(TextView)v.findViewById(R.id.city1);
            dateText=(TextView)v.findViewById(R.id.date);
            timeText=(TextView)v.findViewById(R.id.time);

        }

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        ViewHolderItems holder;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder =new ViewHolderItems(listItemView);
            listItemView.setTag(holder);
        }
        else {
            holder=(ViewHolderItems)listItemView.getTag();
        }
        AndroidFlavor currentAndroidFlavor = getItem(position);
       // TextView scaleText=(TextView)listItemView.findViewById(R.id.scale);

        String total2 = String.valueOf(currentAndroidFlavor.getScale());
        holder.scaleText.setText(total2);
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.scaleText.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentAndroidFlavor.getScale());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        //TextView cityText=(TextView)listItemView.findViewById(R.id.city);
        String string=currentAndroidFlavor.getCity();
        String[] parts =string.split("(?<=of)");
        int l=parts.length;

        String part1 =parts[0];
        String part2 =parts[1];
        holder.cityText.setText(part1);

        //TextView cityText1=(TextView)listItemView.findViewById(R.id.city1);
        //TextView dateText=(TextView)listItemView.findViewById(R.id.date);
        holder.cityText1.setText(part2);
        Date dateObject=new Date(currentAndroidFlavor.getDate());
        SimpleDateFormat formatter=new SimpleDateFormat("MMM DD, yyyy");
        String formattedDate=formatter.format(dateObject);
        holder.dateText.setText(formattedDate);
        //TextView timeText=(TextView)listItemView.findViewById(R.id.time);
        SimpleDateFormat formatter1=new SimpleDateFormat("h:mm a");
        String formattedDate1=formatter1.format(dateObject);
        holder.timeText.setText(formattedDate1);




        return listItemView;

    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


}
