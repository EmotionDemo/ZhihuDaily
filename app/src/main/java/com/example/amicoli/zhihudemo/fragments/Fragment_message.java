package com.example.amicoli.zhihudemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amicoli.zhihudemo.R;

public class Fragment_message extends Fragment {
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_message,container,false);


       return rootView;

   }
}
