package com.project.countdownapp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Luke on 15/09/2016.
 */
public class TwoFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    String charlieURL = ("http://charlieintel.com/modern-warfare-remastered/");
    ArrayList<ArticleTop> articleList;
    CustomListAdapter listAdapter;

    //Views
    ListView mainListView;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleList = new ArrayList<>();

        //Display loading symbol
        try{
            getActivity().findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        //Perform network task
        //SS
        new extractData().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_two, container, false);

        //List view
        mainListView = (ListView) rootview.findViewById(R.id.list);
        return rootview;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /**
         * For big main article
        mainListView.setItemChecked(position, true);

          mainListView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
        mainListView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
        parent.setBackgroundColor(Color.TRANSPARENT);

        for (int i = 0; i < mainListView.getChildCount(); i++) {
            if(position == i){
                mainListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }else{
                mainListView.setBackgroundColor(Color.WHITE);
                //mainListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }
        }*/

        //Open article in chrome
        String linkTo = articleList.get(position).getLink();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(linkTo));
        startActivity(intent);
        listAdapter.notifyDataSetChanged();

    }

    public void addItemsToList()
    {
        try{
            getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //Add data to the main list
        listAdapter=new CustomListAdapter(getActivity(), articleList);
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(this);
    }

    //Asycn task to retrieve webpage
    class extractData extends AsyncTask<String, Void, String>
    {
        //Main page details
        org.jsoup.nodes.Document doc;
        Elements docElements;
        String content;
        Elements images;


        @Override
        protected String doInBackground(String... arg0){
            try {
                doc = Jsoup.connect(charlieURL)
                        .header("Accept-Language", "pt-BR,pt;q=0.8") // missing
                        .header("Accept-Encoding", "gzip,deflate,sdch") // missing
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                        .maxBodySize(0)
                        .timeout(6000000)
                        .get();


                doc.select("div.textwidget").remove();

                //Retrieve all info for class in HTML
                docElements = doc.getElementsByClass("td-module-thumb");
                content = docElements.text();

                //Images
                images = doc.select("span.td-post-date");

                publishProgress();

            }catch (IOException e){
                e.printStackTrace();
            }
            return "Executed";

        }
        protected void onPostExecute(String result)
        {

            try{
                for(int i = 0; i< docElements.size(); i++){

                    if(i<12) {
                        String title;
                        String date;
                        String link;
                        String imageURL;

                        //title
                        title = docElements.get(i).select("a").attr("title").toString();

                        //date
                        date = images.get(i).text();

                        //link
                        link = docElements.get(i).select("a").attr("href").toString();

                        //image
                        imageURL = docElements.get(i).select("img").attr("src").toString();


                        articleList.add(new ArticleTop(title, link, imageURL, date));
                    }
                }

                //Remove duplicates
                for(int j = 0; j<articleList.size(); j++)
                {
                    //Current object
                    ArticleTop currObj = articleList.get(j);
                    int count = 0;

                    for(int k = 0; k<articleList.size(); k++){
                        ArticleTop compareObj = articleList.get(k);
                        if (currObj.getTitle().equals(compareObj.getTitle())) {
                            count++;
                        }
                        if(count >1){
                            articleList.remove(k);
                        }
                    }

                }
                addItemsToList();

            }catch (Exception e){

                Toast.makeText(getActivity(), "Please connect device to the internet",
                        Toast.LENGTH_LONG).show();
            }
        }
        protected void onPreExecute(String res){

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}