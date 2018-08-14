package com.sushant.EmergencyShake;

import android.Manifest;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {
     // Views in application declared
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining layout ans setting up the names of the layout to be used
        tablayout = findViewById(R.id.tabid);
        appBarLayout =  findViewById(R.id.appbarid);
        viewPager = findViewById(R.id.viewpager);

        //Adding views to the ViewPagerAdapter
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new ActivateFragment(),"Welcome");
        adapter.AddFragment(new SelectContacts(),"Select Contacts");
        adapter.AddFragment(new AboutFragment(),"About");

        //Setting up Adapter
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);




    }
}
