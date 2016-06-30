package com.mr.notes;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener {
	private String val;
	private Button saveBtn,cancelBtn;
	private TextView c_text;
	private ImageView c_image;
	private VideoView c_video;
	private NoteDB noteDB;
	private SQLiteDatabase dbWriter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		val = getIntent().getStringExtra("flag");
		saveBtn = (Button) findViewById(R.id.save);
		cancelBtn = (Button) findViewById(R.id.cancel);
		c_text = (TextView) findViewById(R.id.c_text);
		c_image = (ImageView) findViewById(R.id.c_image);
		c_video = (VideoView) findViewById(R.id.c_video);
		
		saveBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		noteDB = new NoteDB(this);
		dbWriter = noteDB.getWritableDatabase();
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			addContent();
			finish();
			break;

		case R.id.cancel:
			finish();
			break;
		}
	}
	private void addContent(){
		ContentValues values = new ContentValues();
		values.put(NoteDB.CONTENT, c_text.getText().toString());
		values.put(NoteDB.TIME, getTime());
		dbWriter.insert(NoteDB.TABLE_NAME, null, values);
	}
	public String getTime(){
		return (new SimpleDateFormat("yyyyƒÍMM‘¬ss»’ HH:mm:ss").format(new Date()));
	}
}
