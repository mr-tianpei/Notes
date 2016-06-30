package com.mr.notes;

import com.mr.notes.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Cursor cursor;
	private Context context;
	private LinearLayout layout;

	public MyAdapter(Context context, Cursor cursor) {
		this.cursor = cursor;
		this.context = context;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {

		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View coverView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout) inflater.inflate(R.layout.cell, null);
		TextView contenttv = (TextView) layout.findViewById(R.id.list_content);
		TextView timetv = (TextView) layout.findViewById(R.id.list_time);
		ImageView imageViewtv = (ImageView) layout
				.findViewById(R.id.list_image);
		ImageView videotv = (ImageView) layout.findViewById(R.id.list_video);
		cursor.moveToPosition(position);
		String content = cursor
				.getString(cursor.getColumnIndex(NoteDB.CONTENT));
		String time = cursor.getString(cursor.getColumnIndex(NoteDB.TIME));
		contenttv.setText(content);
		timetv.setText(time);
		return layout;
	}

}
