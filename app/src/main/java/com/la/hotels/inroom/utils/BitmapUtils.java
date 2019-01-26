package com.la.hotels.inroom.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

public class BitmapUtils 
{
	//Method to get resized bitmap
	public static Bitmap resizeBitmap(Bitmap bitmapOrg, int newWidth, int newHeight)
	{
		Bitmap resizedBitmap = null;
		try
		{
        
		int width=bitmapOrg.getWidth();
        int height=bitmapOrg.getHeight();
        
        // calculate the scale - in this case = 0.4f 
        float scaleWidth = ((float) newWidth) / width; 
        float scaleHeight = ((float) newHeight) / height;
        
        // createa matrix for the manipulation 
        Matrix matrix = new Matrix();
        // resize the bit map 
        matrix.postScale(scaleWidth, scaleHeight); 
        // rotate the Bitmap 
        //matrix.postRotate(45); 
        resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
        bitmapOrg.recycle();
		
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
        
        return resizedBitmap;
	}
	
	  //Method to getRoundedCorner Bitmap
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPxRadius)
		{
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	            bitmap.getHeight(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	     
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	        final RectF rectF = new RectF(rect);
	        final float roundPx =roundPxRadius;
	     
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	     
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);
	     
	        return output;
		}

	 
	 public static Bitmap getBitmap(String path)
	 {
		 Bitmap bmp = null;
		 try
		 {
			 bmp = BitmapFactory.decodeFile(path);
		 }
		 catch(OutOfMemoryError e)
		 {
			 e.printStackTrace();
			 System.gc();
		 }
		 catch (Exception e)
		 {
			e.printStackTrace();
		 }
		 return bmp;
	 }

	/**
	 * This Method returns StateList Drawable (Dynamica Selector)
	 */


	public static StateListDrawable getDrawableFromBitmaps(Context context ,Bitmap normal ,Bitmap hover)
	{
		StateListDrawable state = new StateListDrawable();
		state = new StateListDrawable();
		state.addState(new int[] {android.R.attr.state_pressed},new BitmapDrawable(context.getResources(),hover));
		state.addState(new int[] {},new BitmapDrawable(context.getResources(),normal));
		return state;
	}

	public static StateListDrawable getSelectorFromDrawables(Context context,int normal,int hover)
	{
		StateListDrawable state = new StateListDrawable();
		state = new StateListDrawable();
		state.addState(new int[] {android.R.attr.state_pressed},context.getResources().getDrawable(hover));
		state.addState(new int[] { },context.getResources().getDrawable(normal));
		return state;
	}

	/**
	 * This method will converts ColorDrawable into StateListDrawable
	 * @param context
	 * @param normal : UnSelected Drawable
	 * @param hover: Selected Drawable
	 * @return
	 */
	public static StateListDrawable getColorDrawable(Context context, ColorDrawable normal, ColorDrawable hover)
	{
		StateListDrawable state = new StateListDrawable();
		state = new StateListDrawable();
		state.addState(new int[] {android.R.attr.state_pressed},hover);
		state.addState(new int[] { },normal);
		return state;
	}

	/**
	 * This method will converts image colors into ColorStateList
	 * @param context
	 * @param normal  : UnSelected Color
	 * @param hover   : Selected Color
	 * @return ColorStateList
	 */
	public static ColorStateList getColorStateListDrawable(Context context,int normal,int hover)
	{

		int[][] states = new int[][]
				{
						new int[] { android.R.attr.state_pressed}, // enabled
						new int[] {} // disabled
				};

		int[] colors = new int[]
				{
						hover,
						normal
				};
		ColorStateList myList = new ColorStateList(states, colors);

		return myList;
	}
	
	
}
