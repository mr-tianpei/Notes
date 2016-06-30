package com.mr.notes;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectedAct extends Activity implements OnClickListener {
	private Button s_delet, s_back;
	private TextView s_tv;
	private ImageView s_img;
	private VideoView s_video;
	private NoteDB noteDB;
	private SQLiteDatabase dbWrite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		// String s = ""+getIntent().getIntExtra(NoteDB.ID, 0);
		// System.out.println(s);
		s_delet = (Button) findViewById(R.id.s_delete);
		s_back = (Button) findViewById(R.id.s_back);
		s_tv = (TextView) findViewById(R.id.s_text);
		s_img = (ImageView) findViewById(R.id.s_img);
		s_video = (VideoView) findViewById(R.id.s_video);
		s_delet.setOnClickListener(this);
		s_back.setOnClickListener(this);
		noteDB = new NoteDB(this);
		dbWrite = noteDB.getWritableDatabase();
		
		if(getIntent().getStringExtra(NoteDB.PATH).equals("null")){
			s_img.setVisibility(View.GONE);
		}else{
			s_img.setVisibility(View.VISIBLE);
		}
		if(getIntent().getStringExtra(NoteDB.VIDEO).equals("null")){
			s_video.setVisibility(View.GONE);
		}else{
			s_video.setVisibility(View.VISIBLE);
		}
		s_tv.setText(getIntent().getStringExtra(NoteDB.CONTENT));
		Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(NoteDB.PATH));
		s_img.setImageBitmap(bitmap);
		s_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO)));
		s_video.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.s_delete:
			deletDate();
			finish();
			break;

		case R.id.s_back:
			finish();
			break;
		}
			
		}
	public void deletDate(){
		dbWrite.delete(NoteDB.TABLE_NAME, "_id="+getIntent().getIntExtra(NoteDB.ID, 0), null);
//		delete from notes where id=?;
	}

}
