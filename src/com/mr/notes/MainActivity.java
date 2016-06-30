package com.mr.notes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	private Button textButton, imgButton, videoButton;
	private ListView lv;
	private Intent intent;
	private NoteDB noteDB;
	private SQLiteDatabase dbReader;
	private MyAdapter adapter;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	// ��ʼ��
	public void initView() {
		// ��ȡ���
		textButton = (Button) findViewById(R.id.text);
		imgButton = (Button) findViewById(R.id.image);
		videoButton = (Button) findViewById(R.id.video);
		lv = (ListView) findViewById(R.id.lv);

		textButton.setOnClickListener(this);
		imgButton.setOnClickListener(this);
		videoButton.setOnClickListener(this);
		noteDB = new NoteDB(this);
		dbReader = noteDB.getReadableDatabase();
		// listviewItem�������
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor.moveToPosition(position);
				// ��ת��selectact,����ҳ��
				Intent i = new Intent(MainActivity.this, SelectedAct.class);
				// ��������
				i.putExtra(NoteDB.ID,
						cursor.getInt(cursor.getColumnIndex(NoteDB.ID)));
				i.putExtra(NoteDB.CONTENT,
						cursor.getString(cursor.getColumnIndex(NoteDB.CONTENT)));
				i.putExtra(NoteDB.PATH,
						cursor.getString(cursor.getColumnIndex(NoteDB.PATH)));
				i.putExtra(NoteDB.VIDEO,
						cursor.getString(cursor.getColumnIndex(NoteDB.VIDEO)));
				i.putExtra(NoteDB.TIME,
						cursor.getString(cursor.getColumnIndex(NoteDB.TIME)));
				startActivity(i);
			}
		});
	}

	@Override
	public void onClick(View v) {
		intent = new Intent(this, AddContent.class);
		switch (v.getId()) {
		// �������
		case R.id.text:
			intent.putExtra("flag", "1");
			startActivity(intent);
			break;
		// ���ͼƬ
		case R.id.image:
			intent.putExtra("flag", "2");
			startActivity(intent);
			break;
		// �����Ƶ
		case R.id.video:
			intent.putExtra("flag", "3");
			startActivity(intent);
			break;
		}
	}

	// �鿴���ݿⷽ��
	public void selectDb() {
		cursor = dbReader.query(NoteDB.TABLE_NAME, null, null, null, null,
				null, null);
		adapter = new MyAdapter(this, cursor);
		lv.setAdapter(adapter);
	}

	// ��onresume�����е��ò鿴��ҳ�淽��
	@Override
	protected void onResume() {
		super.onResume();
		selectDb();
	}
}
