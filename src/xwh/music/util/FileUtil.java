package xwh.music.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class FileUtil {

	private Context context;
	
	private static FileUtil instance;
	
	private FileUtil(Context context){
		this.context = context;
	}
	
	public static synchronized FileUtil getInstance(Context context){
		if(instance==null){
			instance = new FileUtil(context);
		}		
		return instance;
	}
	
	
	public String getNotes(){
		 AssetManager assetManager = context.getAssets();
		 InputStream inputStream = null;
		 BufferedReader bfr = null;
		 StringBuffer notes = new StringBuffer();
		  try {
			  inputStream = assetManager.open("music/songNotes.txt");
			  bfr = new BufferedReader(new InputStreamReader(inputStream, "GB2312"));
			  
			  String line = null;
			  while((line=bfr.readLine())!=null){
				  //Log.i("FileUtil",line);				  
				  notes.append(line+"\n");
			  }
			  
			  
		  } catch (IOException e) {
			  e.printStackTrace();
		  }finally{
			  
			  try {
				  //assetManager.close();
				  
				  if(bfr!=null){
					  bfr.close();
				  }
				  if(inputStream!=null){
					  inputStream.close();
				  }
			  } catch (IOException e) {
				e.printStackTrace();
			}
		  }

		  return notes.toString();
	}
}
