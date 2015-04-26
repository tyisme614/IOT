package org.demo.airdetect.main;

import android.app.Fragment;

public class OtherFragment extends Fragment {

	
	private static OtherFragment singleton = null;
	
	public static OtherFragment getInstance(){
		if(singleton == null){
			singleton = new OtherFragment();
		}
		
		return singleton;
	}
	
	public OtherFragment(){
		
	}
}
