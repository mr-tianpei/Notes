package com.mr.notes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener {
	private String val;
	private Button saveBtn, cancelBtn;
	private TextView c_text;
	private ImageView c_image;
	private VideoView c_video;
	private NoteDB noteDB;
	private SQLiteDatabase dbWriter;
	private File imgPath, videoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		// 得到上一个activity的Intent参数
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
		init();
	}

	// 初始化操作
	public void init() {
		// 添加文字
		if (val.equals("1")) {
			c_image.setVisibility(View.GONE);
			c_video.setVisibility(View.GONE);
		}
		// 添加图片
		if (val.equals("2")) {
			c_image.setVisibility(View.VISIBLE);
			c_video.setVisibility(View.GONE);
			Intent iimage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 这里有问题，Android改版后，不允许在外置储存卡直接获取图片，视频
			imgPath = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/DCIM/" + getTime() + ".jpg");
			iimage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgPath));
			// forresult的request参数是int类型
			startActivityForResult(iimage, 1);
		}
		// 添加视频
		if (val.equals("3")) {
			c_image.setVisibility(View.GONE);
			c_video.setVisibility(View.VISIBLE);
			Intent ivideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			videoPath = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/DCIM/" + getTime() + ".mp4");
			ivideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoPath));
			startActivityForResult(ivideo, 2);
		}
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

	// 添加内容到数据库
	private void addContent() {
		ContentValues values = new ContentValues();
		values.put(NoteDB.CONTENT, c_text.getText().toString());
		values.put(NoteDB.TIME, getTime());
		values.put(NoteDB.PATH, imgPath + "");
		values.put(NoteDB.VIDEO, videoPath + "");
		dbWriter.insert(NoteDB.TABLE_NAME, null, values);
	}

	// 获取当前系统时间的方法
	public String getTime() {
		return (new SimpleDateFormat("yyyy年MM月ss日 HH:mm:ss").format(new Date()));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			Bitmap bitmap = BitmapFactory.decodeFile(imgPath.getAbsolutePath());
			c_image.setImageBitmap(bitmap);
		}
		if (requestCode == 2) {
			c_video.setVideoURI(Uri.fromFile(videoPath));
			c_video.start();
		}
	}
}
