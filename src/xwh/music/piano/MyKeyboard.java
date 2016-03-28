package xwh.music.piano;

import xwh.piano.view.Preference_Num;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MyKeyboard extends HorizontalScrollView{

	private LinearLayout container_keys;
	private MyKey[] myKeys;
	
	public MyKeyboard(Context context) {
		super(context);
		inital();		
	}	
	public MyKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		inital();
	}
	
	
	public void inital(){
		init_keys();
		
		this.setOnTouchListener(new MyKeyTouchListener(this));
		
		this.postDelayed((new Runnable() {
			@Override
			public void run() {
				MyKeyboard.this.smoothScrollTo(MyKey.key_width_white*20, 0);	// 滑到第20个白键（中间位置）
			}
		}),50);
	}


	// 根据屏幕计算宽度
	public void setKeyWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		((PianoActivity)this.getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width_white = dm.widthPixels/Preference_Num.num_key_white;
		int height_white = width_white*23/6;	// 按照图片比例来算
		int width_black = width_white*4/5;
		int height_black = width_white*14/6;
		
		Log.i("setKeyWidth",width_white+"");
		
		MyKey.setKeySize(width_white, height_white, width_black, height_black);
	}
	
	
	/**
	 * 生成琴键数组，用数组来统一管理所有的按键，标准钢琴，黑白一共88键
	 */
	public void init_keys(){
		
		if(myKeys==null){
			myKeys = new MyKey[88];
		}		
		
		if(container_keys == null){
			container_keys = new LinearLayout(this.getContext());
			//container_keys.setOrientation(Horizontal);
			this.addView(container_keys);
		}else{
			container_keys.removeAllViews();
		}
		
		
		setKeyWidth();
		
		// 黑键的左间距
		int margin1 = MyKey.key_width_white-MyKey.key_width_black/2;
		int margin2 = MyKey.key_width_white*2-MyKey.key_width_black;
		int margin3 = MyKey.key_width_white-MyKey.key_width_black;

		
		LayoutInflater inflater = ((PianoActivity)this.getContext()).getLayoutInflater();
		
		// 最左端3个键
		View v_left = inflater.inflate(R.layout.piano_keys_left,null);
		MyKey key_white_a_left = (MyKey) v_left.findViewById(R.id.key_white_a);
		MyKey key_black_a_left = (MyKey) v_left.findViewById(R.id.key_black_a);
		MyKey key_white_b_left = (MyKey) v_left.findViewById(R.id.key_white_b);
		key_white_a_left.setKey(21,MyKey.WHITE,0);
		key_black_a_left.setKey(22,MyKey.BLACK,margin1);
		key_white_b_left.setKey(23,MyKey.WHITE,0);		

		container_keys.addView(v_left);
		
		
		myKeys[0] = key_white_a_left;
		myKeys[1] = key_black_a_left;
		myKeys[2] = key_white_b_left;
		
		// 中间7个整8度
		for(int i=0;i<7;i++){
			View v = inflater.inflate(R.layout.piano_keys,null);
			MyKey key_white_c = (MyKey) v.findViewById(R.id.key_white_c);
			MyKey key_white_d = (MyKey) v.findViewById(R.id.key_white_d);
			MyKey key_white_e = (MyKey) v.findViewById(R.id.key_white_e);
			MyKey key_white_f = (MyKey) v.findViewById(R.id.key_white_f);
			MyKey key_white_g = (MyKey) v.findViewById(R.id.key_white_g);
			MyKey key_white_a = (MyKey) v.findViewById(R.id.key_white_a);
			MyKey key_white_b = (MyKey) v.findViewById(R.id.key_white_b);
			
			
			MyKey key_black_c = (MyKey) v.findViewById(R.id.key_black_c);
			MyKey key_black_d = (MyKey) v.findViewById(R.id.key_black_d);
			MyKey key_black_f = (MyKey) v.findViewById(R.id.key_black_f);
			MyKey key_black_g = (MyKey) v.findViewById(R.id.key_black_g);
			MyKey key_black_a = (MyKey) v.findViewById(R.id.key_black_a);
			
			key_white_c.setKey(24+12*i,MyKey.WHITE,0);
			key_black_c.setKey(25+12*i,MyKey.BLACK,margin1);
			key_white_d.setKey(26+12*i,MyKey.WHITE,0);
			key_black_d.setKey(27+12*i,MyKey.BLACK,margin3);
			key_white_e.setKey(28+12*i,MyKey.WHITE,0);
			key_white_f.setKey(29+12*i,MyKey.WHITE,0);
			key_black_f.setKey(30+12*i,MyKey.BLACK,margin2);
			key_white_g.setKey(31+12*i,MyKey.WHITE,0);
			key_black_g.setKey(32+12*i,MyKey.BLACK,margin3);
			key_white_a.setKey(33+12*i,MyKey.WHITE,0);
			key_black_a.setKey(34+12*i,MyKey.BLACK,margin3);
			key_white_b.setKey(35+12*i,MyKey.WHITE,0);
				
			container_keys.addView(v);
			
			
			myKeys[3+i*12] = key_white_c;
			myKeys[4+i*12] = key_black_c;
			myKeys[5+i*12] = key_white_d;
			myKeys[6+i*12] = key_black_d;
			myKeys[7+i*12] = key_white_e;
			myKeys[8+i*12] = key_white_f;
			myKeys[9+i*12] = key_black_f;
			myKeys[10+i*12] = key_white_g;
			myKeys[11+i*12] = key_black_g;
			myKeys[12+i*12] = key_white_a;
			myKeys[13+i*12] = key_black_a;
			myKeys[14+i*12] = key_white_b;			
			
		}
		
		
		
		// 最右边一个键。
		final MyKey v_right = new MyKey(this.getContext());
		v_right.setBackgroundResource(R.drawable.key_white_up);
		v_right.setKey(108, MyKey.WHITE,0);
		
		container_keys.addView(v_right);

		myKeys[87] = v_right;

		// 设置大小和间隔
		for(MyKey key: myKeys){
			key.setLayoutParams();
		}
		
	}
	
	public MyKey getKeybyPosition(float x, float y){
		
		int offset = this.getScrollX();	// 滚动位置
		
		float p_x = offset+x;

		int index = 0;
		
		int index_x = (int) (p_x/MyKey.key_width_white);		// 当前白键在x方向的位置
		
		int index_white = getIndex(index_x);	// 当前白键在数组中的位置
		
		if(y<MyKey.key_height_black){		// 可能是黑键
			
			//Log.i("onTouch MotionEvent","p_x:"+p_x+", key:"+(MyKey.key_width_white*(index_x+1)-MyKey.key_width_black/2));
			
			if(index_white>0&&myKeys[index_white-1].getType()==MyKey.BLACK
					&&p_x<(MyKey.key_width_white*index_x+MyKey.key_width_black/2)){
				index = index_white-1;
			}else if(index_white<51&&myKeys[index_white+1].getType()==MyKey.BLACK
					&&p_x>(MyKey.key_width_white*(index_x+1)-MyKey.key_width_black/2)){
				index = index_white+1;
			}else{
				index = index_white;
			}
			
		}else{		// 白键
			index = index_white;
		}
		
		
		
		//Log.i("onTouch MotionEvent","offset:"+offset+", index:"+index_x);
		
		return myKeys[index];
	}
	
	private int getIndex(int index_white){
		
		int index = 0;
		if(index_white<0){
			return 0;
		}
		if(index_white>=51){
			return 87;
		}
	
		int t = index_white / 7;
		
		switch(index_white % 7){
			case 0: index = 0;	break;
			case 1: index = 2;	break;
			case 2: index = 3;	break;
			case 3: index = 5;	break;
			case 4: index = 7;	break;
			case 5: index = 8;	break;
			case 6: index = 10;	break;
		}
		
		return index+t*12 ;
	}

	
	
}
