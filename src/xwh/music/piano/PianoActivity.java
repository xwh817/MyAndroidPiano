package xwh.music.piano;

import xwh.music.util.FileUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PianoActivity extends Activity {

	private TextView txt_notes;
	
	private MyKeyboard keyboard_1;
	//private MyKeyboard keyboard_2;
		
	private PianoApplication application;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.piano_panel);

		
		application = (PianoApplication) this.getApplication();
		application.addActivity(this);
	
		txt_notes = (TextView) this.findViewById(R.id.txt_notes);
		
		keyboard_1 = (MyKeyboard) this.findViewById(R.id.keyboard_1);
		//keyboard_2 = (MyKeyboard) this.findViewById(R.id.keyboard_2);
				
		setSongNotes();

	}


	// 显示歌谱
	private void setSongNotes(){
		FileUtil util = FileUtil.getInstance(this);

		String notes = util.getNotes();
		
		txt_notes.setText(notes);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_piano, menu);             
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.menu_settings:{
				Intent intent = new Intent(PianoActivity.this,MyPreference.class);
				PianoActivity.this.startActivity(intent);
				break;
			}
			case R.id.menu_exit:{
				application.exit();
				break;
			}
		}
		return true;
	}

	@Override
	protected void onResume() {
		
		// 按键设置改变的时候
		if(application.isKeyNumChanged()){
			keyboard_1.init_keys();
			//keyboard_2.init_keys();
			application.setKeyNumChanged(false);
		}
		
		super.onResume();
		
	}
	
	@Override
    public void onBackPressed() {
    	confirmQuite();
    }
	
	private AlertDialog quitDlg;
	private void confirmQuite() {
		if (null != quitDlg) {
			quitDlg.cancel();
		}
		quitDlg = null;
		quitDlg = new AlertDialog.Builder(this)
				.setTitle("退出").setMessage("您确定要退出么？")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						application.exit();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						quitDlg.cancel();
					}
				}).create();
		quitDlg.show();
	}
	
}
