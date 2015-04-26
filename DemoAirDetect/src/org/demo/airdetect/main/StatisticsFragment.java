package org.demo.airdetect.main;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class StatisticsFragment extends Fragment {

	private static StatisticsFragment singleton = null;
	private static final int XAXIS_LABEL_TYPE_DAY = 1;
	private static final int XAXIS_LABEL_TYPE_WEEK = 2;
	private static final int XAXIS_LABEL_TYPE_MONTH = 3;
	//local data
	private static final String[] WEEK = new String[]{"Mon","Tue", "Wed", "Thu","Fri","Sat","Sun"};
	private BarData data;
	//widgets
	private Button btn_day;
	private Button btn_week;
	private Button btn_month;
	private BarChart bc_statistics;
	
	
	public static StatisticsFragment getInstance(){
		if(singleton == null ){
			singleton = new StatisticsFragment();
		}
		
		return singleton;
	}
			
	public StatisticsFragment(){
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
		btn_day = (Button) rootView.findViewById(R.id.btn_day);
		btn_week = (Button) rootView.findViewById(R.id.btn_week);
		btn_month = (Button) rootView.findViewById(R.id.btn_month);
		bc_statistics = (BarChart) rootView.findViewById(R.id.bc_statistics);
		
		btn_day.setOnClickListener(btnListener);
		btn_week.setOnClickListener(btnListener);
		btn_month.setOnClickListener(btnListener);
		
		btn_day.setBackgroundColor(Color.rgb(217, 80, 138));
		btn_day.setTextColor(getResources().getColor(R.color.white));
		btn_week.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
		btn_week.setBackgroundResource(R.color.transparent);
		btn_month.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
		btn_month.setBackgroundResource(R.color.transparent);
		
				
	     XAxis xAxis = bc_statistics.getXAxis();
         xAxis.setPosition(XAxisPosition.BOTTOM);
         xAxis.setDrawGridLines(false);
         
         
         YAxis leftAxis = bc_statistics.getAxisLeft();
         leftAxis.setLabelCount(5);
         leftAxis.setSpaceTop(15f);
         data = generateData("Data of Day", 24, 100, getXLabels(XAXIS_LABEL_TYPE_DAY));
 //        bc_statistics.setData(data);
		return rootView;
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		bc_statistics.setData(data);
		bc_statistics.animateY(500);
		bc_statistics.setDescription("");
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	/**
	 * event listeners
	 */
	
	private OnClickListener btnListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btn_day://Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, 247, 120)
				btn_day.setBackgroundColor(Color.rgb(217, 80, 138));
				btn_day.setTextColor(getResources().getColor(R.color.white));
				btn_week.setBackgroundResource(R.color.transparent);
				btn_week.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
				btn_month.setBackgroundResource(R.color.transparent);
				btn_month.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
				data = generateData("Data of Day", 24, 100, getXLabels(XAXIS_LABEL_TYPE_DAY));
				break;
			case R.id.btn_week:
				btn_day.setBackgroundResource(R.color.transparent);
				btn_week.setBackgroundColor(Color.rgb(254, 149, 7));
				btn_week.setTextColor(getResources().getColor(R.color.white));				
				btn_month.setBackgroundResource(R.color.transparent);
				data = generateData("Data of Week", 7, 100, getXLabels(XAXIS_LABEL_TYPE_WEEK));
				btn_month.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
				btn_day.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
				break;
			case R.id.btn_month:
				btn_day.setBackgroundResource(R.color.transparent);
				btn_week.setBackgroundResource(R.color.transparent);
				btn_month.setBackgroundColor( Color.rgb(64, 89, 128));
				btn_month.setTextColor(getResources().getColor(R.color.white));		
				data = generateData("Data of Month", 31, 100, getXLabels(XAXIS_LABEL_TYPE_MONTH));
				btn_day.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
				btn_week.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
				break;				
			}
			bc_statistics.setData(data);
			bc_statistics.animateY(500);
		}};

	/**
	 * Local methods
	 */
	
	
	private BarData generateData(String label, int xCount, int yMax, List<String> xList) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < xCount; i++) {
            entries.add(new BarEntry((int) (Math.random() * yMax) , i));
        }

        BarDataSet d = new BarDataSet(entries, label);    
        d.setBarSpacePercent(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));
        
        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        sets.add(d);
        
        BarData cd = new BarData(xList, sets);
        return cd;
    }

	private List<String> getXLabels( int type ){
		List<String> labels = new ArrayList<String>();
		switch(type){
		case XAXIS_LABEL_TYPE_DAY:{
			for(int i = 0; i < 24; i++){
				labels.add(String.valueOf(i));
			}
		}			
			break;
		case XAXIS_LABEL_TYPE_WEEK:
			for(int i = 0; i<7; i++){
				labels.add(WEEK[i]);
			}
			break;
		case XAXIS_LABEL_TYPE_MONTH:
			for(int i = 0; i < 31; i++){
				labels.add(String.valueOf(i));
			}
			break;
		}
		
		return labels;
	}
	

}
