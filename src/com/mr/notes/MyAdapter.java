package com.mr.notes;

import com.mr.notes.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
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
		String url = cursor.getString(cursor.getColumnIndex(NoteDB.PATH));
		String videoUri = cursor.getString(cursor.getColumnIndex(NoteDB.VIDEO));
		imageViewtv.setImageBitmap(getImageThumbnail(url, 200, 200));
		videotv.setImageBitmap(getVideoBitmapThumbnail(videoUri, 200, 200, MediaStore.Images.Thumbnails.MICRO_KIND));
		contenttv.setText(content);
		timetv.setText(time);
		return layout;
	}
//ªÒ»°Õº∆¨Àı¬‘Õº
	public Bitmap getImageThumbnail(String uri, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		int beHeight = options.outHeight / height;
		int beWidth = options.outWidth / width;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
	//ªÒ»° ”∆µÀı¬‘Õº
	public Bitmap getVideoBitmapThumbnail(String uri,int width,int height,int kind){
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		
		return bitmap;
		
	}

}
