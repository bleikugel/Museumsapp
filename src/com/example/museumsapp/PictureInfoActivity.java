package com.example.museumsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class PictureInfoActivity extends Activity{
	
	private Picture currentPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_picture_info);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		
        
		Intent intent = getIntent();
		
	   if(intent.getSerializableExtra("Picture") != null)
		{
			Object obj =  (Object) intent.getSerializableExtra("Picture");
			if(obj instanceof Picture)
			{
				 currentPicture = (Picture) obj;
				 showInfosFromPicture(currentPicture);
			}
		}
		
	}
	
	public void showInfosFromPicture(Picture p)
	{
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);	
		Options opts = new Options();
		opts.inScaled = true;
		opts.inSampleSize = 3;
		Bitmap bm = BitmapFactory.decodeResource(getResources(), p.getPictureID(), opts);
		
		imageView.setImageBitmap(bm);
		imageView.setScaleType(ScaleType.FIT_XY);
		
		TextView txt1 = (TextView) findViewById(R.id.textView1);	
		txt1.setText(p.getName());
		
		TextView txt2 = (TextView) findViewById(R.id.textView2);	
		txt2.setText(p.getCreator());
		
		TextView txt3 = (TextView) findViewById(R.id.textView3);	
		txt3.setText(p.getDescription());
		
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}

}
