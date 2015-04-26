package org.demo.airdetect.main;

import org.demo.airdetect.views.ProgressCircleView;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IndexFragment extends Fragment {

	private static final String LOG_TAG = "IndexFragment";
	
	public static final String KEY_CURRENT_INDEX = "key_current_index";
	private static final int MSG_UPDATE_STATE = 2001;
	private static final int STATE_EXCELLENT = 1001;
	private static final int STATE_NORMAL = 1002;
	private static final int STATE_BAD = 1003;
	private static final int STATE_DANGER = 1004;
	
	private static final int TOTAL_INDEX = 100;	
	private static IndexFragment singleton = null;
	
	private int mTotalIndex;
	private int mCurrentIndex;
	//widgets
	private ProgressCircleView pcv_index;
	private TextView tv_state;
	private boolean started = false;
	private int curr_state;
	private Thread update;
	public static IndexFragment getInstance(int curr){
		if(singleton == null){
			singleton = new IndexFragment();
		}
//		Bundle b = new Bundle();
//		b.putInt(KEY_CURRENT_INDEX, curr);
//		singleton.setArguments(b);
		
		return singleton;
	}
	
	public IndexFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_index, container, false);
		pcv_index = (ProgressCircleView) rootView.findViewById(R.id.pcv_index);
		tv_state = (TextView) rootView.findViewById(R.id.tv_state);
		
		return rootView;
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
//		mCurrentIndex = getArguments().getInt(KEY_CURRENT_INDEX);
		pcv_index.clearAnimation();
		pcv_index.clearFocus();
		pcv_index.destroyDrawingCache();
		
		tv_state.setText("Excellent!");
		tv_state.setTextColor(getResources().getColor(R.color.green));
		curr_state = STATE_EXCELLENT;
		pcv_index.setColor(getResources().getColor(R.color.green), getResources().getColor(R.color.green));
		mCurrentIndex = (int) (Math.random()*100.0);
		if(update != null ){
			started = false;
			update.interrupt();
			update = null;
		}
		start();
		super.onResume();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	//	start();
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * Handlers
	 */
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch( msg.arg1 ){
			case STATE_EXCELLENT:
				tv_state.setText("Excellent!");
				tv_state.setTextColor(getResources().getColor(R.color.green));
				pcv_index.setColor(getResources().getColor(R.color.green), getResources().getColor(R.color.green));
				break;
			case STATE_NORMAL:
				tv_state.setText("Normal");
				tv_state.setTextColor(getResources().getColor(R.color.limegreen));
				pcv_index.setColor(getResources().getColor(R.color.limegreen), getResources().getColor(R.color.limegreen));
				break;
			case STATE_BAD:
			tv_state.setText("Bad");
			tv_state.setTextColor(getResources().getColor(R.color.orange));
			pcv_index.setColor(getResources().getColor(R.color.orange), getResources().getColor(R.color.orange));
			break;
			case STATE_DANGER:
				tv_state.setText("Danger!!!");
				tv_state.setTextColor(getResources().getColor(R.color.red));
				pcv_index.setColor(getResources().getColor(R.color.red), getResources().getColor(R.color.red));
				break;
			}
//			pcv_index.setProgress(msg.arg2);
		}
		
		
	};
	/**
	 * Local methods
	 * 
	 */
	
	public void start(){
		
		update = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int index = 0;
				started = true;
				int offset = 1;
				while( started && index <= mCurrentIndex ){
					
					index+=offset;
					if( index > mCurrentIndex ){
						index = mCurrentIndex;
					}else{
//						offset ++;
					}					
					int state = STATE_EXCELLENT;
					if(index < 25){
						state = STATE_EXCELLENT;						
					}else if( index >=25 && index < 50 ){						
						state = STATE_NORMAL;
					}else if( index >=50 && index <75 ){
						state = STATE_BAD;
					}else if( index >=75 ){
						state = STATE_DANGER;
					}
					if( curr_state != state ){
						curr_state = state;
						Message msg = mHandler.obtainMessage(MSG_UPDATE_STATE, curr_state, index);
						msg.sendToTarget();
					}
					pcv_index.setProgress(index);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//end of while
//				pcv_index.clearAnimation();
				started  = false;
			}});
		update.start();
	}
}
