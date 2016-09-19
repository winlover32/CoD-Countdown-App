package com.project.countdownapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<ArticleTop> {

    private final Activity context;
    private ArrayList<ArticleTop> articleList;

    public CustomListAdapter(Activity context, ArrayList<ArticleTop> articleList) {
        super(context, R.layout.listfeed, articleList);
        // TODO Auto-generated constructor stub

        this.articleList = articleList;
        this.context = context;

    }

    private static class PlanetHolder {
        public TextView txtTitle;
        public TextView txtDate;
        public ImageView img;

    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        PlanetHolder holder = new PlanetHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listfeed, null);

            // Now we can fill the layout with the right values
            TextView tv = (TextView) v.findViewById(R.id.txtListTitle);
            TextView date = (TextView) v.findViewById(R.id.txtListDate);
            ImageView img = (ImageView) v.findViewById(R.id.icon);

            holder.txtTitle = tv;
            holder.txtDate = date;
            //holder.txtAuthor = reporter;
            holder.img = img;

            v.setTag(holder);
        } else {
            holder = (PlanetHolder) v.getTag();
        }

        String imageUrl = articleList.get(position).getImage();
        String header = articleList.get(position).getTitle();
        //String author = articleList.get(position).getAuthor();
        String pub = articleList.get(position).getDate();

        holder.txtDate.setText(pub);
        holder.txtTitle.setText(header);

        if (imageUrl.equals("")) {
            //holder.img.setBackgroundResource(R.drawable.noimg);
        }
        else {

            Picasso.with(context)
                        .load(imageUrl)
                        .resize(450,300)
                        //.transform(new RoundedCornersTransform())
                        //now we have an ImageView, that we can use as target
                        .into(holder.img);



        }


        v.setBackgroundColor(Color.WHITE);
        return v;
    }


    //we need this class as holder object
    public static class PlaceViewHolder {
        private ImageView placeIcon;
        private TextView title;
    }
}