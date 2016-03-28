package xwh.piano.view;

import xwh.music.piano.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Preference_Num extends Preference {


	public static int num_key_white =10;
	public static final int NUM_DEFAULT = 10;	// 默认白键数目。
	
	private TextView txt_num;
	private ImageButton bt_add, bt_subtract;
	private Button bt_default;
	
	private Context context;

	public Preference_Num(Context context) {
		super(context);
		this.context = context;
	}

	public Preference_Num(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public Preference_Num(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}


	@Override
	protected void onBindView(View view) {
		super.onBindView(view);

		bt_add = (ImageButton) view.findViewById(R.id.bt_add);
		bt_subtract = (ImageButton) view.findViewById(R.id.bt_subtract);
		bt_default = (Button) view.findViewById(R.id.bt_default);
		txt_num = (TextView) view.findViewById(R.id.txt_num);
		
		txt_num.setText(""+num_key_white);
		
		bt_add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int num = Integer.parseInt(txt_num.getText().toString());
				num++;
				if(num>52){
					return;
				}
				txt_num.setText(""+num);
				saveToPreference(num);
			}
			
		});
		
		bt_subtract.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int num = Integer.parseInt(txt_num.getText().toString());
				num--;
				if(num<7){
					return;
				}
				txt_num.setText(""+num);
				saveToPreference(num);
			}
			
		});
		
		bt_default.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				txt_num.setText(""+NUM_DEFAULT);
				saveToPreference(NUM_DEFAULT);
			}
			
		});

	}
	
	
	private void saveToPreference(int num){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("setting_num_key_white", num); 		 
		editor.commit(); 
	}


}
