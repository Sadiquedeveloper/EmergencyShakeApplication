package com.sushant.EmergencyShake;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.buttonEmail)
    Button btnEmail;
    View view;

    public AboutFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_fragment, container, false);
        btnEmail = view.findViewById(R.id.buttonEmail);
        btnEmail.setOnClickListener(this);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonEmail) {


            try{
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "modgilsushant@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Problem in application");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }catch(Throwable e){
                System.out.println("Some Exception"+e);           }
        }
    }
}