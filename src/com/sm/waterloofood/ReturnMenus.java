package com.sm.waterloofood;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v4.app.NavUtils;
import android.app.ListActivity;
import android.widget.ListView;

public class ReturnMenus extends ListActivity {
	
	private static String url = "https://api.uwaterloo.ca/v2/foodservices/menu.json?key=[YOUR_API_KEY_HERE]";
	
	private static final String TAG_DATA = "data";
	private static final String TAG_OUTLETS = "outlets";
	private static final String TAG_MENU = "menu";
	private static final String TAG_DAY = "day";
	private static final String TAG_MEALS = "meals";
	private static final String TAG_LUNCH = "lunch";
	private static final String TAG_PRODUCT_NAME_LUNCH = "product_name";
	private static final String TAG_PRODUCT_NAME_DINNER = "product_name";
	//private static final String TAG_DIET_TYPE = "diet_type"; //future update perhaps, adding in diet icons
	private static final String TAG_DINNER = "dinner";
	
	private int json_array_location;
	
	private static View loadingView;
	private static View contentView;

    ArrayList<String[]> menuList2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_menus);
		
		Intent intent = getIntent();
		String cafID = intent.getStringExtra(MainActivity.CAF_ID);
		
		loadingView = (ProgressBar)findViewById(R.id.loading_spinner_menus);
		contentView = (ListView)findViewById(android.R.id.list);
		
		Log.d("NOTICE", "the selected place is " + cafID);
		
		if (cafID.equals("dc")){
			setTitle("Bon Appetit");
			json_array_location = 0;
		} else if (cafID.equals("v1")) {
			setTitle("Mudie's");
			json_array_location = 1;
		} else if (cafID.equals("sch")) {
			setTitle("Festival Fare");
			json_array_location = 2;
		} else if (cafID.equals("rev")){
			setTitle("REVelation");
			json_array_location = 3;
		} else if (cafID.equals("pas")){
			setTitle("PAS Lounge");
			json_array_location = 4;
		} else if ((cafID.equals("bcm")) || (cafID.equals("nh"))){
			setTitle("Pastry Plus");
			json_array_location = 5;
		} else {
			setTitle("Liquid Assets Cafe");
			json_array_location = 6;
		}
		

		menuList2 = new ArrayList<String[]>();
				
		new GetMenus().execute();
	}
	
	private class GetMenus extends AsyncTask<Void,Void,Void>{
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);

        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            if (jsonStr != null) {
            	Log.d("Response:", "Online data was fetched.");
                try {
                    JSONObject output = new JSONObject(jsonStr);
                    JSONObject data = output.getJSONObject(TAG_DATA);
                    JSONArray outlets = data.getJSONArray(TAG_OUTLETS);
                    JSONObject caf = outlets.getJSONObject(json_array_location);
                    
                    JSONArray menu = caf.getJSONArray(TAG_MENU);
                    
                    for (int i = 0; i < menu.length(); i++){
                        Log.d("Response:", "The loop is infinite because i is " + i);
                    	String[] outputStringArray = {"null", "null", "null", "null", "null", "null", "null", "null", "null", };//length 9
                    	JSONObject day_menu = menu.getJSONObject(i);
                    	String day_name = day_menu.getString(TAG_DAY);
                    	outputStringArray[0] = day_name;
                    	
                    	JSONObject meals = day_menu.getJSONObject(TAG_MEALS);
                    	JSONArray lunch = meals.getJSONArray(TAG_LUNCH);
                    	
                    	String lunch_item;
                    	String dinner_item;

                    	int iterativeCount = 0;
                    	for (int x = 0; x < lunch.length(); x++){
                    		JSONObject lunchObject = lunch.getJSONObject(x);
                    		lunch_item = lunchObject.getString(TAG_PRODUCT_NAME_LUNCH);
                    		if (iterativeCount == 0){
                    			outputStringArray[1] = lunch_item;
                    		} else if (iterativeCount == 1){
                    			outputStringArray[2] = lunch_item;
                    		} else if (iterativeCount == 2){
                    			outputStringArray[3] = lunch_item;
                    		} else if (iterativeCount > 2){
                    			outputStringArray[4] = lunch_item;
                    		}
                    		iterativeCount++;
                    	}
                    	
                    	JSONArray dinner = meals.getJSONArray(TAG_DINNER);
                    	iterativeCount = 0;
                    	for (int q = 0; q < dinner.length(); q++){
                    		JSONObject dinnerObject = dinner.getJSONObject(q);
                    		dinner_item = dinnerObject.getString(TAG_PRODUCT_NAME_DINNER);
                    		if (iterativeCount == 0){
                    			outputStringArray[5] = dinner_item;
                    		} else if (iterativeCount == 1){
                    			outputStringArray[6] = dinner_item;
                    		} else if (iterativeCount == 2){
                    			outputStringArray[7] = dinner_item;
                    		} else if (iterativeCount > 2){
                    			outputStringArray[8] = dinner_item;
                    		}
                    		iterativeCount++;
                    	}
                    	
                    	menuList2.add(outputStringArray);
                    }
                    
                    
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            StringArrayAdapter ad = new StringArrayAdapter(menuList2, ReturnMenus.this);
            
            
            setListAdapter(ad);
            loadingView.setVisibility(View.GONE);
            contentView.setVisibility(View.VISIBLE);
        }
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
