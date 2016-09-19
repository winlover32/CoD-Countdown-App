package com.project.countdownapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Luke on 15/09/2016.
 */
public class OneFragment extends android.support.v4.app.Fragment {

    TextView txtDays;
    TextView txtHours;
    TextView txtMins;
    TextView txtSeconds;

    public static int SECONDS_IN_A_DAY = 24 * 60 * 60;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set countdown
        try{
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(getActivity()!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCountdown();
                            }
                        });
                    }
                }
            }, 0, 1000);//1000 milliseconds=1 second

        }catch (Exception e){
            e.toString();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    public void setCountdown()
    {
        //Get textviews
        txtDays = (TextView) getActivity().findViewById(R.id.txtDays);
        txtHours = (TextView) getActivity().findViewById(R.id.txtHours);
        txtMins = (TextView) getActivity().findViewById(R.id.txtMins);
        txtSeconds = (TextView) getActivity().findViewById(R.id.txtSeconds);


        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0)); /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH,4);
        thatDay.set(Calendar.MONTH,11); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 2016);

        Calendar today = Calendar.getInstance();
        long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        //System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);

        if(hours < 10){
            txtHours.setText("0"+hours);
        }else{
            txtHours.setText(""+hours);
        }

        if(minutes < 10){
            txtMins.setText("0"+minutes);
        }else{
            txtMins.setText(""+minutes);
        }

        if(days < 10){
            txtDays.setText("0"+days);
        }else{
            txtDays.setText(""+days);
        }

        if(seconds < 10) {
            txtSeconds.setText("0" + seconds);
        }else{
            txtSeconds.setText(""+seconds);
        }


    }

}