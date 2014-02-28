package com.sm.waterloofood;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StringArrayAdapter extends BaseAdapter {

	ArrayList<String[]> foods;
	Context ctxt;
	LayoutInflater myInflater;
	
	public StringArrayAdapter(ArrayList<String[]> menuList2, Context c){
		foods = menuList2;
		ctxt = c;
		myInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return foods.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return foods.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1==null){
			arg1 = myInflater.inflate(R.layout.menu_list_item, arg2, false);
		}
		TextView menu_date = (TextView)arg1.findViewById(R.id.menu_date);
		TextView lunch_title = (TextView)arg1.findViewById(R.id.lunch_title_tv);
		TextView lunch_item1 = (TextView)arg1.findViewById(R.id.lunch_item1_tv);
		TextView lunch_item2 = (TextView)arg1.findViewById(R.id.lunch_item2_tv);
		TextView lunch_item3 = (TextView)arg1.findViewById(R.id.lunch_item3_tv);
		TextView lunch_item4 = (TextView)arg1.findViewById(R.id.lunch_item4_tv);
		TextView dinner_title = (TextView)arg1.findViewById(R.id.dinner_title_tv);
		TextView dinner_item1 = (TextView)arg1.findViewById(R.id.dinner_item1_tv);
		TextView dinner_item2 = (TextView)arg1.findViewById(R.id.dinner_item2_tv);
		TextView dinner_item3 = (TextView)arg1.findViewById(R.id.dinner_item3_tv);
		TextView dinner_item4 = (TextView)arg1.findViewById(R.id.dinner_item4_tv);
		
		String[] dailyStringArray = foods.get(arg0);
		
		menu_date.setText(dailyStringArray[0]);
		lunch_title.setText("Lunch");
		lunch_item1.setText(dailyStringArray[1]);
		lunch_item2.setText(dailyStringArray[2]);
		lunch_item3.setText(dailyStringArray[3]);
		lunch_item4.setText(dailyStringArray[4]);
		dinner_title.setText("Dinner");
		dinner_item1.setText(dailyStringArray[5]);
		dinner_item2.setText(dailyStringArray[6]);
		dinner_item3.setText(dailyStringArray[7]);
		dinner_item4.setText(dailyStringArray[8]);
		
		if ((dailyStringArray[1].equals("null"))&&(dailyStringArray[2].equals("null"))&&(dailyStringArray[3].equals("null"))&&
				(dailyStringArray[4].equals("null"))&&(dailyStringArray[5].equals("null"))&&(dailyStringArray[6].equals("null"))&&
				(dailyStringArray[7].equals("null"))&&(dailyStringArray[8].equals("null"))){
			lunch_title.setText("Closed!");
		}
		
		if (dailyStringArray[1].equals("null")){
			lunch_title.setVisibility(View.GONE);
			lunch_item1.setVisibility(View.GONE);
		}
		if (dailyStringArray[2].equals("null")){
			lunch_item2.setVisibility(View.GONE);
		}
		if (dailyStringArray[3].equals("null")){
			lunch_item3.setVisibility(View.GONE);
		}
		if (dailyStringArray[4].equals("null")){
			lunch_item4.setVisibility(View.GONE);
		}
		if (dailyStringArray[5].equals("null")){
			dinner_item1.setVisibility(View.GONE);
			dinner_title.setVisibility(View.GONE);
		}
		if (dailyStringArray[6].equals("null")){
			dinner_item2.setVisibility(View.GONE);
		}
		if (dailyStringArray[7].equals("null")){
			dinner_item3.setVisibility(View.GONE);
		}
		if (dailyStringArray[8].equals("null")){
			dinner_item4.setVisibility(View.GONE);
		}
				
		return arg1;
	}

}
