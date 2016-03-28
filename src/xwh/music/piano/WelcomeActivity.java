package xwh.music.piano;

import xwh.music.util.MySoundPlayer;
import xwh.piano.view.Preference_Num;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class WelcomeActivity extends Activity {

	protected static final int WELCOME_WAITING = 1;

	private static Handler handler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 在配置文件里面设置
        setContentView(R.layout.welcome);
        
        this.init();	// 初始化
  

        
        handler = new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		if (msg.what == WELCOME_WAITING) {
        			Intent intent = new Intent();
    		        intent.setClass(WelcomeActivity.this, PianoActivity.class);   		        
    		        WelcomeActivity.this.startActivity(intent);
    		        
    		        WelcomeActivity.this.finish();
        		}


        	}
        };
        
        handler.sendEmptyMessageDelayed(WELCOME_WAITING, 2000);		// 一段时间后跳转
        
    }
    
    
    /*
     *  系统初始化
     *  注： 这种赋值的初始化不要放在Application里面，Application是单例了，而且退出后再次启动时，onCreate不执行。
     */
	private void init(){
		
		
		Log.i("init","delayLength:"+MyKey.delayLength+",   num_key_white"+Preference_Num.num_key_white);
		
		// 获取配置文件信息
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean scroll = prefs.getBoolean("setting_scroll", true);		
		MyKeyTouchListener.scrollable = scroll;
		
		String delay = prefs.getString("setting_delay", "1000");
		MyKey.delayLength = Integer.parseInt(delay);
		
		int num_key_white = prefs.getInt("setting_num_key_white", 10);
		Preference_Num.num_key_white = num_key_white;
		
		//MySoundPlayer.getInstance(this).setOffset(0);	// 每次启动恢复C调 
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("setting_major", "0");		// 每次启动恢复C调 		 
		editor.commit(); 
		
		
		
        MySoundPlayer.getInstance(this).init();		// 加载音频 
        
           
        
    }

    
    
    
    
}
