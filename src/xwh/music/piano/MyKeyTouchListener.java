package xwh.music.piano;

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyKeyTouchListener implements OnTouchListener{

	public static boolean scrollable = true;		// 键盘是否可滚动
	private MyKeyboard keyboard;

	
	// 用Android自己优化的SparseArray代替HashMap
	private SparseArray<MyKey> keys_down;
	

	public MyKeyTouchListener(MyKeyboard keyboard) {
		this.keyboard = keyboard;
		this.keys_down = new SparseArray<MyKey>();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		// Mark: 貌似最多只能响应两个手指，第三个手指的事件监测不到
		// Mark，上面是手机的问题
		// Log.i("MyKey","PointerCount:"+event.getPointerCount());
		//Log.i("MyKey","actionIndex:"+event.getAction()+"PointerCount:"+event.getPointerCount());
		
		float x = 0;
		float y = 0;
		
		int action = event.getAction() & MotionEvent.ACTION_MASK; 
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT; 
        int pointerId = event.getPointerId(pointerIndex); 
        switch (action) { 
        case MotionEvent.ACTION_DOWN: 
        case MotionEvent.ACTION_POINTER_DOWN:
        	try{
        		x = event.getX(pointerId);
        		y = event.getY(pointerId);
        	}catch (IllegalArgumentException e) {	// 多点触控出现的错误
        		//e.printStackTrace();
        		return false;
        	}
        	
        	if(y<0){
				return false;
			}
        	MyKey key_down = keyboard.getKeybyPosition(x, y);
        	keys_down.put(pointerId, key_down);
        	key_down.keyDown();
        	//Log.i("MyKey","key"+pointerId+"  Down:"+key_down.getKey());
            break; 
        case MotionEvent.ACTION_UP:           
        case MotionEvent.ACTION_POINTER_UP: 
        case MotionEvent.ACTION_CANCEL: 
        	MyKey key_up = keys_down.get(pointerId);
        	if(key_up!=null){
        		key_up.keyUp();
        		keys_down.remove(pointerId);
        		//Log.i("MyKey","key"+pointerId+"  Up:"+key_up.getKey());
        	}       	
            break; 
        case MotionEvent.ACTION_MOVE: 
            /*int pointerCount = event.getPointerCount(); 
            for (int i = 0; i < pointerCount; i++) { 
                pointerIndex = i; 
                pointerId = event.getPointerId(pointerIndex); 
                x[pointerId] = (int)event.getX(pointerIndex); 
                y[pointerId] = (int)event.getY(pointerIndex); 
            } */
            break; 
        } 

		
		return !scrollable;		// 是否可以滚动。 原理：如果这里消耗掉事件，那么scrollView就收不到Touch事件了。
	}
	
	

}
