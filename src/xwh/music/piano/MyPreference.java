package xwh.music.piano;

import xwh.music.util.MySoundPlayer;
import xwh.piano.view.Preference_Num;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyPreference extends PreferenceActivity{


	private PianoApplication application;

	private ListPreference setting_delay;
	private ListPreference setting_major;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.layout.preferences);
		
		application = (PianoApplication) this.getApplication();
		application.addActivity(this);
		
		setting_delay = (ListPreference) this.findPreference("setting_delay");
		setting_delay.setTitle("设置余音："+setting_delay.getEntry());
		
		setting_major = (ListPreference) this.findPreference("setting_major");
		setting_major.setTitle("调值："+setting_major.getEntry());
		
		setting_delay.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				
				/*int index = getValueIndex(setting_delay,newValue.toString());	
				CharSequence option = "";
				if(index!=-1){
					CharSequence[] major_delays = setting_delay.getEntries();
					option = major_delays[index];
				}*/	
				String option = getOpitionByValue(setting_delay,newValue.toString());
				setting_delay.setTitle("设置余音："+option);
				return true;
			}
			
		});
		
		setting_major.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				

				Log.i("setting_major","newValue:"+newValue.toString());
				
				/*int index = getValueIndex(setting_major,newValue.toString());		
				CharSequence option = "";
				if(index!=-1){
					CharSequence[] major_options = setting_major.getEntries();
					option = major_options[index];
				}*/
				String option = getOpitionByValue(setting_major,newValue.toString());
				
				setting_major.setTitle("调值："+option);	
				MySoundPlayer.getInstance(MyPreference.this).setOffset(Integer.parseInt(newValue.toString()));
				
				return true;	// 若返回false，则不会保存，界面不会自动更新。
			}
			
		});
		
	}
	

	// 返回ListPreference的某个值对应的option
	private String getOpitionByValue(ListPreference preference, String value){
		
		int index = -1;
		CharSequence[] values = preference.getEntryValues();
		for(int i=0;i<values.length;i++){
			if(values[i].toString().equals(value)){
				index =  i;
				break;
			}
		}
		
		if(index!=-1){
			CharSequence[] options = preference.getEntries();
			CharSequence option = options[index];
			return option.toString();
		}else{
			return null;
		}
		
		
		
	}
	
	
	
	/*@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		
		if("setting_scroll".equals(preference.getKey())){
			boolean scroll = prefs.getBoolean("setting_scroll", false);		
    		MyKeyTouchListener.scrollable = scroll;
		}else if("setting_delay".equals(preference.getKey())){
			String delay = prefs.getString("setting_delay", "2000");
    		MyKey.delayLength = Integer.parseInt(delay);
		}
		
		
		return false;
	}*/
	
	// 刷新设置
	private void setting(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean scroll = prefs.getBoolean("setting_scroll", true);		
		MyKeyTouchListener.scrollable = scroll;
		String delay = prefs.getString("setting_delay", "1500");
		MyKey.delayLength = Integer.parseInt(delay);
		
		int num_key_white = prefs.getInt("setting_num_key_white", 10);
		if(Preference_Num.num_key_white != num_key_white){
			Preference_Num.num_key_white = num_key_white;
			application.setKeyNumChanged(true);
		}
		
		
		//String setting_major = prefs.getString("setting_major", "0");
		
		
	}
	
	@Override
	public void onBackPressed() {
		setting();
		super.onBackPressed();
	}
	
	
}
