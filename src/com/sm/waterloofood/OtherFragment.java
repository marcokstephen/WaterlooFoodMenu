package com.sm.waterloofood;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class OtherFragment extends Fragment {
	

	// URL to get contacts JSON
    private static String url = "https://api.uwaterloo.ca/v2/foodservices/locations.json?key=[YOUR_API_KEY_HERE]";
    private static String fullDate;
    private static String TAG_WEEKDAY = "sunday";

    
 
    // JSON Node names
    private static final String TAG_DATA="data";
    private static final String TAG_OUTLET_NAME="outlet_name";
    private static final String TAG_BUILDING="building";
    private static final String TAG_IS_OPEN_NOW="is_open_now";
    private static final String TAG_OPENING_HOURS="opening_hours";
    private static final String TAG_OPENING_HOUR="opening_hour";
    private static final String TAG_CLOSING_HOUR="closing_hour";
    private static final String TAG_SPECIAL_HOURS="special_hours";
    private static final String TAG_DATES_CLOSED="dates_closed";
    private static final String TAG_DATE="date";
    
    private static View LoadingView;
    private static View ContentView;
    
    
	static TextView slc_hours, subway_hours, dp_hours, ceit_hours, opt_hours, ml_hours, thdc_hours, thdc2_hours,
		thml_hours, thslc_hours, e3_hours,thsch_hours,dc_hours,hh_hours,sch_hours,v1_hours,pas_hours,
		bcm_hours,nh_hours,tcpp_hours,rev_hours;
	static TextView slc,subway,dp,ceit,opt,ml,thdc,thdc2,thml,thslc,e3,thsch,dc,hh,sch,v1,pas,bcm,nh,tcpp,rev;
	
	static TextView[] hourArray = {dc_hours,dp_hours,thslc_hours,ml_hours,slc_hours,
			subway_hours,ceit_hours,opt_hours,hh_hours,e3_hours,sch_hours,v1_hours,pas_hours,bcm_hours,
			nh_hours,tcpp_hours,rev_hours,thml_hours,thdc_hours,thsch_hours,thdc_hours};
	
	static String[] outputInfo = new String[21];
	
	static Boolean[] openNow = new Boolean[21];

    static String slcTime,subwayTime,dpTime,ceitTime,optTime,mlTime,thdcTime,thdc2Time,thmlTime,thslcTime,e3Time,
    	thschTime,dcTime,hhTime,schTime,v1Time,pasTime,bcmTime,nhTime,tcppTime,revTime;
    
    static JSONArray outlets = null;
    static String hourString = "No data";
    
    static int SpecialHoursArrayLocation;
    
    private static Boolean didItLoad = false;      

	public OtherFragment() {
		// Required empty public constructor
	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View otherFragmentView = inflater.inflate(R.layout.fragment_other, container, false);
		
		
		slc_hours = (TextView)otherFragmentView.findViewById(R.id.slc_hours);
		subway_hours = (TextView)otherFragmentView.findViewById(R.id.subway_hours);
		dp_hours = (TextView)otherFragmentView.findViewById(R.id.dp_hours);
		ceit_hours = (TextView)otherFragmentView.findViewById(R.id.ceit_hours);
		opt_hours = (TextView)otherFragmentView.findViewById(R.id.opt_hours);
		ml_hours = (TextView)otherFragmentView.findViewById(R.id.ml_hours);
		thdc_hours = (TextView)otherFragmentView.findViewById(R.id.thdc_hours);
		thdc2_hours = (TextView)otherFragmentView.findViewById(R.id.thdc2_hours);
		thml_hours = (TextView)otherFragmentView.findViewById(R.id.thml_hours);
		thslc_hours = (TextView)otherFragmentView.findViewById(R.id.thslc_hours);
		thsch_hours = (TextView)otherFragmentView.findViewById(R.id.thsch_hours);
		dc_hours = (TextView)otherFragmentView.findViewById(R.id.dc_hours);
		hh_hours = (TextView)otherFragmentView.findViewById(R.id.hh_hours);
		sch_hours = (TextView)otherFragmentView.findViewById(R.id.sch_hours);
		v1_hours = (TextView)otherFragmentView.findViewById(R.id.v1_hours);
		pas_hours = (TextView)otherFragmentView.findViewById(R.id.pas_hours);
		bcm_hours = (TextView)otherFragmentView.findViewById(R.id.bcm_hours);
		nh_hours = (TextView)otherFragmentView.findViewById(R.id.nh_hours);
		tcpp_hours = (TextView)otherFragmentView.findViewById(R.id.tcpp_hours);
		rev_hours = (TextView)otherFragmentView.findViewById(R.id.rev_hours);
		e3_hours = (TextView)otherFragmentView.findViewById(R.id.e3_hours);
		
		slc = (TextView)otherFragmentView.findViewById(R.id.slc_header_textview);
		subway = (TextView)otherFragmentView.findViewById(R.id.subway_textview);
		dp = (TextView)otherFragmentView.findViewById(R.id.dp_header_textview);
		ceit = (TextView)otherFragmentView.findViewById(R.id.ceit_header_textview);
		opt = (TextView)otherFragmentView.findViewById(R.id.opt_header_textview);
		ml = (TextView)otherFragmentView.findViewById(R.id.ml_header_textview);
		thdc = (TextView)otherFragmentView.findViewById(R.id.thdc_header_textview);
		thdc2 = (TextView)otherFragmentView.findViewById(R.id.thdc2_header_textview);
		thml = (TextView)otherFragmentView.findViewById(R.id.thml_header_textview);
		thslc = (TextView)otherFragmentView.findViewById(R.id.thslc_header_textview);
		e3 = (TextView)otherFragmentView.findViewById(R.id.e3_header_textview);
		thsch = (TextView)otherFragmentView.findViewById(R.id.thsch_header_textview);
		dc = (TextView)otherFragmentView.findViewById(R.id.dc_header_textview);
		hh = (TextView)otherFragmentView.findViewById(R.id.hh_header_textview);
		sch = (TextView)otherFragmentView.findViewById(R.id.sch_header_textview);
		v1 = (TextView)otherFragmentView.findViewById(R.id.v1_header_textview);
		pas = (TextView)otherFragmentView.findViewById(R.id.pas_header_textview);
		bcm = (TextView)otherFragmentView.findViewById(R.id.bcm_header_textview);
		nh = (TextView)otherFragmentView.findViewById(R.id.nh_header_textview);
		tcpp = (TextView)otherFragmentView.findViewById(R.id.tcpp_header_textview);
		rev = (TextView)otherFragmentView.findViewById(R.id.rev_header_textview);
		
		LoadingView = (ProgressBar)otherFragmentView.findViewById(R.id.loading_spinner2);
		ContentView = (ScrollView)otherFragmentView.findViewById(R.id.scrollView1);
		return otherFragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("Resposne: ", "> about to get data");
		
		new GetTimes().execute();

	}
	
	  @Override
	  public void onResume() {
		  Log.e("DEBUG", "onResume of LoginFragment");
		  super.onResume();
		  new GetTimes().execute();
	  }
	  
	  public static void refresh() {
		  new GetTimes().execute();
	  }
	
	protected static class GetTimes extends AsyncTask<Void, Void, Void>{
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
    		LoadingView.setVisibility(View.VISIBLE);
    		ContentView.setVisibility(View.GONE);
    		
    	    {
    	        Calendar c = Calendar.getInstance();
    	        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    	        Date dt = new Date();
    	        int year = c.get(Calendar.YEAR);
    	        int month = c.get(Calendar.MONTH) + 1;
    	        int day = c.get(Calendar.DAY_OF_MONTH);
    	        fullDate = ""+year+"-"+ String.format("%02d",month) + "-" + String.format("%02d",day); 

    	        if (Calendar.MONDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "monday";
    	        } else if (Calendar.TUESDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "tuesday";
    	        } else if (Calendar.WEDNESDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "wednesday";
    	        } else if (Calendar.THURSDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "thursday";
    	        } else if (Calendar.FRIDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "friday";
    	        } else if (Calendar.SATURDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "saturday";
    	        } else if (Calendar.SUNDAY == dayOfWeek) {
    	        	TAG_WEEKDAY = "sunday";
    	        }
    	    }
        }

		@Override
		protected Void doInBackground(Void... args0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            
            if (jsonStr != null){
            	didItLoad = true;
            	try{
            		JSONObject jsonObj = new JSONObject(jsonStr);
            		outlets = jsonObj.getJSONArray(TAG_DATA);
            		
	            	for (int i=0;i<20;i++){
	            		JSONObject caf = outlets.getJSONObject(i);
	            		openNow[i] = caf.getBoolean(TAG_IS_OPEN_NOW);
	            		JSONObject openingHours = caf.getJSONObject(TAG_OPENING_HOURS);
	            		JSONObject weekdayHours = openingHours.getJSONObject(TAG_WEEKDAY);
	            		String openingHour = weekdayHours.getString(TAG_OPENING_HOUR);
	            		String closingHour = weekdayHours.getString(TAG_CLOSING_HOUR);
	            		
	            		if (openingHour == "null"){
	            			hourString = "Closed today!";
	            		} else {
	            			hourString = reformatTime(openingHour) + " - " + reformatTime(closingHour);
	            		}

	            		if (specialClosedDates(caf, fullDate)){
	            			hourString = "Irregular closure today!";
	            		}
	            		
	            		if (specialHourDates(caf, fullDate)){
	            			JSONArray special_hours = caf.getJSONArray(TAG_SPECIAL_HOURS);
	            			
	            			JSONObject specialDay = special_hours.getJSONObject(SpecialHoursArrayLocation);
	            			openingHour = specialDay.getString(TAG_OPENING_HOUR);
	            			closingHour = specialDay.getString(TAG_CLOSING_HOUR);
	            			hourString = reformatTime(openingHour) + " - " + reformatTime(closingHour) + " (Irregular)";
	            		}
	            		
	            		
	            		outputInfo[i] = hourString;

	            	}
	            	
	            	//exclusively for timhortons dc2 becuase of the api structure
            		JSONObject caf = outlets.getJSONObject(22);
            		openNow[20] = caf.getBoolean(TAG_IS_OPEN_NOW);
            		JSONObject openingHours = caf.getJSONObject(TAG_OPENING_HOURS);
            		JSONObject weekdayHours = openingHours.getJSONObject(TAG_WEEKDAY);
            		String openingHour = weekdayHours.getString(TAG_OPENING_HOUR);
            		String closingHour = weekdayHours.getString(TAG_CLOSING_HOUR);
            		
            		if (openingHour == "null"){
            			hourString = "Closed today!";
            		} else {
            			hourString = reformatTime(openingHour) + " - " + reformatTime(closingHour);
            		}
            		
            		if (specialClosedDates(caf, fullDate)){
            			hourString = "Irregular closure today!";
            		}
            		
            		if (specialHourDates(caf, fullDate)){
            			JSONArray special_hours = caf.getJSONArray(TAG_SPECIAL_HOURS);
            			
            			JSONObject specialDay = special_hours.getJSONObject(SpecialHoursArrayLocation);
            			openingHour = specialDay.getString(TAG_OPENING_HOUR);
            			closingHour = specialDay.getString(TAG_CLOSING_HOUR);
            			hourString = reformatTime(openingHour) + " - " + reformatTime(closingHour) + " (Irregular)";
            		}
            		
            		outputInfo[20] = hourString;
	            		
            		
            		
            		
            	}catch (JSONException e){
            		Log.d("Resposne: ", "> something messed up");
            	}
            } else {
            	didItLoad = false;
            	Log.d("Response: ", "> no data collected online");
            }
            
			return null;
            
		}
		
		@Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (didItLoad){
	            dc_hours.setText(outputInfo[0]);
	            dp_hours.setText(outputInfo[1]);
	            thslc_hours.setText(outputInfo[2]);
	            ml_hours.setText(outputInfo[3]);
	            slc_hours.setText(outputInfo[4]);
	            subway_hours.setText(outputInfo[5]);
	            ceit_hours.setText(outputInfo[6]);
	            opt_hours.setText(outputInfo[7]);
	            hh_hours.setText(outputInfo[8]);
	            e3_hours.setText(outputInfo[9]);
	            sch_hours.setText(outputInfo[10]);
	            v1_hours.setText(outputInfo[11]);
	            pas_hours.setText(outputInfo[12]);
	            bcm_hours.setText(outputInfo[13]);
	            nh_hours.setText(outputInfo[14]);
	            tcpp_hours.setText(outputInfo[15]);
	            rev_hours.setText(outputInfo[16]);
	            thml_hours.setText(outputInfo[17]);
	            thdc_hours.setText(outputInfo[18]);
	            thsch_hours.setText(outputInfo[19]);
	            thdc2_hours.setText(outputInfo[20]);
	            
	            if (openNow[0]) dc.setTextColor(Color.parseColor("#009900"));
	            if (openNow[1]) dp.setTextColor(Color.parseColor("#009900"));
	            if (openNow[2]) thslc.setTextColor(Color.parseColor("#009900"));
	            if (openNow[3]) ml.setTextColor(Color.parseColor("#009900"));
	            if (openNow[4]) slc.setTextColor(Color.parseColor("#009900"));
	            if (openNow[5]) subway.setTextColor(Color.parseColor("#009900"));
	            if (openNow[6]) ceit.setTextColor(Color.parseColor("#009900"));
	            if (openNow[7]) opt.setTextColor(Color.parseColor("#009900"));
	            if (openNow[8]) hh.setTextColor(Color.parseColor("#009900"));
	            if (openNow[9]) e3.setTextColor(Color.parseColor("#009900"));
	            if (openNow[10]) sch.setTextColor(Color.parseColor("#009900"));
	            if (openNow[11]) v1.setTextColor(Color.parseColor("#009900"));
	            if (openNow[12]) pas.setTextColor(Color.parseColor("#009900"));
	            if (openNow[13]) bcm.setTextColor(Color.parseColor("#009900"));
	            if (openNow[14]) nh.setTextColor(Color.parseColor("#009900"));
	            if (openNow[15]) tcpp.setTextColor(Color.parseColor("#009900"));
	            if (openNow[16]) rev.setTextColor(Color.parseColor("#009900"));
	            if (openNow[17]) thml.setTextColor(Color.parseColor("#009900"));
	            if (openNow[18]) thdc.setTextColor(Color.parseColor("#009900"));
	            if (openNow[19]) thsch.setTextColor(Color.parseColor("#009900"));
	            if (openNow[20]) thdc2.setTextColor(Color.parseColor("#009900"));
            }
    		LoadingView.setVisibility(View.GONE);
    		ContentView.setVisibility(View.VISIBLE);
            
        }
		
		private String reformatTime(String time){
			String[] parts = time.split(":"); //parts[0] == hours, parts[1] == minutes
			String sign = "AM";
			
			int hour = Integer.parseInt(parts[0]);
			//int minutes = Integer.parseInt(parts[1]);
			
			if (hour > 12){
				hour = hour - 12;
				sign = "PM";
			} else if (hour == 0){
				hour = 12;
			}
			
			return ""+hour+":"+parts[1]+" "+sign;
		}
		
		private Boolean specialClosedDates(JSONObject caf, String fullDate){
			try {
				JSONArray dates_closed = caf.getJSONArray(TAG_DATES_CLOSED);
				for (int i=0;i<dates_closed.length(); i++){
					String dateToCheck = dates_closed.getString(i);
					
					if (dateToCheck.equals(fullDate)){
						return true;
					}
				}
				
			} catch (JSONException e) {
			}
			
			return false;
		}
		
		private Boolean specialHourDates(JSONObject caf, String fullDate){
			try {
				JSONArray special_hours = caf.getJSONArray(TAG_SPECIAL_HOURS);
				
				for (int i=0;i<special_hours.length();i++){
					JSONObject DateObjectToCheck = special_hours.getJSONObject(i);
					String dateToCheck = DateObjectToCheck.getString(TAG_DATE);
					
					if (dateToCheck.equals(fullDate)){
						SpecialHoursArrayLocation = i;
						return true;
					}
					
				}
				
			}catch (JSONException e){
			}
			return false;
		}
		
	}
}
