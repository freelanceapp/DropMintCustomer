package com.apporio.johnlaundry.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apporio.johnlaundry.R;

/**
 * Created by samir on 21/07/15.
 */
public class FragmentGoogleSignIn extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_google_sign_in, container, false);


        return v;
    }

    public static FragmentGoogleSignIn newInstance(String text) {

        FragmentGoogleSignIn f = new FragmentGoogleSignIn();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
