package xwh.music.piano;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

public class PianoApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
		
	
	private boolean keyNumChanged = false;	// 屏幕显示白键数目是否发生变化
	
	public void setKeyNumChanged(boolean keyNumChanged){
		this.keyNumChanged = keyNumChanged;
	}
	public boolean isKeyNumChanged(){
		return keyNumChanged;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	
	// 注意：init并没在每次启动时执行。
/*	private void init(){
		
		// 获取配置文件信息
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean scroll = prefs.getBoolean("setting_scroll", true);		
		MyKeyTouchListener.scrollable = scroll;
		String delay = prefs.getString("setting_delay", "1000");
		MyKey.delayLength = Integer.parseInt(delay);
		
		int num_key_white = prefs.getInt("setting_num_key_white", 10);
		Preference_Num.num_key_white = num_key_white;
		
		MySoundPlayer.getInstance(this).setOffset(0);
		
		String setting_major = prefs.getString("setting_major", "0");
		
		Log.i("setting_major",setting_major);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("setting_major", "0");		// 每次启动恢复C调 		 
		editor.commit(); 
		
	}*/
	
	@Override
	public void onCreate() {

		Log.i("application","onCreate");
		
		super.onCreate();
		//init();
	}
	
	
	// 退出， 释放所有Activity。。。
	public void exit(){
		for(Activity activity:activityList){
			activity.finish();
		}
		activityList.clear();
	}
	
	
	
}
