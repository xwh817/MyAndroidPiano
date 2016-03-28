package xwh.music.piano;

import xwh.music.util.MySoundPlayer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class MyKey extends ImageView{

	public static final int WHITE=0,BLACK=1;	// 黑/白键type常数
	
	
	
	//public static float rate=0.8f;		// 显示大小
	public static int key_width_white=60, key_height_white=230,
					  key_width_black=48, key_height_black=140;
	
	private int margin_left;	// 黑键距离上一个键的间隔，由键的宽度自动计算出。
	
	private int key;
	private int type;		// type 黑/白键
	private Context context;


	private int count = 0;		// 记录按下次数
	public static long delayLength;		// 余音长度 0~2000（改名叫余音，别叫延音了）
	
	private static int testCount = 0;
	
	private boolean down = false;
	
	public boolean isDown(){
		return down;
	}
	

	public void setKey(int key,int type,int margin_left) {
		this.key = key;
		this.type = type;
		this.margin_left = margin_left;
	}
	public int getKey() {
		return key;
	}

	public int getType() {
		return type;
	}
	
	public MyKey(Context context) {
		super(context);
		this.context = context;
	}
	
	public MyKey(Context context,AttributeSet attrs){
		super(context,attrs);
		this.context = context;	
	}
	
	public MyKey(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;	
	}
	
	
	public static void setKeySize(int width_white,int height_white,int width_black,int height_black){
		key_width_white = width_white;
		key_height_white = height_white;
		key_width_black = width_black;
		key_height_black = height_black;
	}
	
	
	public void setLayoutParams(){		
		int width = this.type==WHITE?key_width_white:key_width_black;
		int height = this.type==WHITE?key_height_white:key_height_black;
		LayoutParams params = new LayoutParams(width, height);
		
		params.setMargins(margin_left, 0, 0, 0);
		
		this.setLayoutParams(params);
	}
	

	
	public void keyDown(){
		
		down = true;
		
		testCount++;
		
		MySoundPlayer.getInstance(context).play(key);
		
		//Log.i("MyKey","keyDown:"+key);
				
		if(this.getType()==MyKey.WHITE){
			this.setBackgroundResource(R.drawable.key_white_down);
		}else{
			this.setBackgroundResource(R.drawable.key_black_down);
		}
		
		synchronized(this){
			count++;
		}
	}
	
	public void keyUp(){
		
		down = false;
		
		testCount--;
		
		//Log.i("MyKey","keyUp:"+key);
		
		if(this.getType()==MyKey.WHITE){
			this.setBackgroundResource(R.drawable.key_white_up);
		}else{
			this.setBackgroundResource(R.drawable.key_black_up);
		}
		

		/*if(delayLength<100){		// 太小了直接忽略
			count--;
			MySoundPlayer.getInstance(context).stop(key);
		}else{		// 超过2000都当成2000，因为音频播放时间才2秒
			*/
			if(delayLength>2000){
				delayLength = 2000;
			}
			
			this.postDelayed(new Runnable(){
				public void run() {

					count--;

					Log.i("key count:",""+count+", test:"+testCount);
					
					if(count<=0){	// 最后一次抬起才停止
						count = 0;
						MySoundPlayer.getInstance(context).stop(key);
					}
				};
			}, delayLength);
		

		
	}

	
}
