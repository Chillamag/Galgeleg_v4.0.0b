package com.attosec.galgeleg_v400b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Hjaelp_Frag extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Hjælp");
        return inflater.inflate(R.layout.hjaelp_frag, container, false);
    }



}
