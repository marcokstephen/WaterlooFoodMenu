package com.sm.waterloofood;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sm.waterloofood.OtherFragment.GetTimes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	public final static String CAF_ID = "com.sm.waterloo.CAF_ID";
	
    ViewPager viewPager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.pager);
        
        String internetAvailableNotice = "";

        final ActionBar actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        addTabs(actionBar);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                
            }
        });

        
        if(!isNetworkAvailable()){
        	internetAvailableNotice = "There is no available internet connection!";
            new AlertDialog.Builder(this)
    	    .setTitle("Network Required!")
    	    .setMessage(internetAvailableNotice)
    	    .setCancelable(true)
    	    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
    	    	public void onClick(DialogInterface dialog, int which) { 
    	    		dialog.cancel();
    	    	}
    	    })
    	    .setPositiveButton("Wi-Fi Settings", new DialogInterface.OnClickListener() {
    	    	public void onClick(DialogInterface dialog, int which) { 
    	    		startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    	    	}
    	    })
    	    .show();
        }
    }
    
    //returns true if there is connection, returns false if there is no connection
    public boolean isNetworkAvailable(){
    	ConnectivityManager cm = (ConnectivityManager)
    			getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    	//if no network is available, networkInfo will be null
    	if (networkInfo != null && networkInfo.isConnected()){
    		return true;
    	}
    	return false;
    }
    
    private void addTabs(ActionBar actionBar)
    {
        ActionBar.Tab tab1=actionBar.newTab();
        tab1.setText(R.string.title_section1);
        tab1.setTabListener(this);

        ActionBar.Tab tab2=actionBar.newTab();
        tab2.setText(R.string.title_section2);
        tab2.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main, menu);
      return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.refresh_option:
    		OtherFragment.refresh();
    		break;
    	default:
    		break;
    		
    	}
    	return true;
    }
    
    public void getDCmenu(View view){
    	getMenus(view,"dc");
    }
    
    public void getV1menu(View view){
    	getMenus(view,"v1");
    }
    
    public void getREVmenu(View view){
    	getMenus(view,"rev");
    }
    
    public void getSCHmenu(View view){
    	getMenus(view,"sch");
    }
    
    public void getHHmenu(View view){
    	getMenus(view,"hh");
    }
    
    public void getPASmenu(View view){
    	getMenus(view,"pas");
    }
    
    public void getBCMmenu(View view){
    	getMenus(view,"bcm");
    }
    
    public void getNHmenu(View view){
    	getMenus(view,"nh");
    }
    
    public void getMenus (View view, String cafID){
    	//when the buttons are pressed
    	Intent intent = new Intent(this, ReturnMenus.class);
    	intent.putExtra(CAF_ID,cafID);
    	startActivity(intent);
    }
}

class MyAdapter extends FragmentStatePagerAdapter
{

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        if(i==0)
        {
            fragment=new MenuFragment();
        }
        if(i==1)
        {
            fragment=new OtherFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}