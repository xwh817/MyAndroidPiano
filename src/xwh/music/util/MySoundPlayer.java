package xwh.music.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;

public class MySoundPlayer {

	private SoundPool pool;
	
	private int[] soundIDs;

	private static MySoundPlayer instance;
	private Context context;
	
	private Map<Integer,LinkedList<Integer>> streamIDs;
	
	
	private int offset=0;	// 调值偏移量，默认C调，偏移0

	// 单例模式
	private MySoundPlayer(Context context) {
		this.context = context;
	}

	public static synchronized MySoundPlayer getInstance(Context context) {

		if (instance == null) {
			instance = new MySoundPlayer(context);
		}
		return instance;
	}

	public void setOffset(int offset){
		this.offset = offset;
	}
	public int getOffset(){
		return offset;
	}
	
	public void init() {
				
		pool = new SoundPool(20, AudioManager.STREAM_MUSIC, 100);

		soundIDs = new int[88];
		streamIDs = new HashMap<Integer,LinkedList<Integer>>();		
		
		LoadSoundsTask loadTask = new LoadSoundsTask();
		loadTask.execute(0);
		
	}
	
	/**
	 * 加载音频，使用异步处理，不阻塞主线程。
	 * @author xwh
	 */
	class LoadSoundsTask extends AsyncTask<Integer, Integer, String>{


		@Override
		protected String doInBackground(Integer... params) {
			
			try {
				// 使用Java反射机制获取R中的静态变量。
				Class<?> raw = Class.forName("xwh.music.piano.R$raw");		// 注意内部类的写法
				for (int i = 0; i < 88; i++) {
					Field simp = raw.getField("key_" + (i+21));		// 反射获取属性
					int id = (Integer) simp.get(null);		// 静态变量与具体对象无关，传null
					soundIDs[i] = pool.load(context, id, 1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} 
		
		
	}
	
		
	public void play(int key) {

		int index = key+offset-21;		// key的范围为 21~108 
		if(index>87||index<0){
			return;		// 如果超出范围，不发音
		}
		int soundID = soundIDs[index];
		int streamID =  pool.play(soundID, 1, 1, 1, 0, 1);	// 返回streamID

		Log.i("streamID",""+streamID);
		
		if(streamID!=0){		// 同一soundID，每次将播放后的streamID都不一样。。
			LinkedList<Integer> list = null;
			if(!streamIDs.containsKey(soundID)){
				list = new LinkedList<Integer>();
				streamIDs.put(soundID,list);
			}else{
				list = streamIDs.get(soundID);
			}
			list.add(streamID);
			//streamIDs.put(soundID,soundID);
		}
	}
	
	
	public void stop(int key){
		int index = key+offset-21;		// key的范围为 21~108 
		if(index>87||index<0){
			return;		// 如果超出范围
		}
		
		int soundID = soundIDs[index];

		LinkedList<Integer> list = streamIDs.get(soundID);
		if(list!=null){
			Iterator<Integer> it = list.iterator();
			while(it.hasNext()){
				Integer streamID = it.next();
				pool.stop(streamID);
			}
			list.clear();
		}
	
	}

}
