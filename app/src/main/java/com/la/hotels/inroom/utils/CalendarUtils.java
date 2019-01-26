package com.la.hotels.inroom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

////////////////////////////////////////////////////////////////////////////////////////////////////////////
// DATE FORMAT:                                       | TIME FORMAT:                       | SEPARATOR:   //
// d -- 1   dd -- 01                                  | h - 9 hh - 09 H - 9&21 HH - 09&21  | .  -- dot    //
// M -- 1   MM -- 01     MMM -- jan MMMM -- january   | m - 9 mm - 09                      | -  -- hypen  //
// yy - 14  yyyy - 2014                               | s - 9 ss - 09                      | "" -- space  //
//                                                    | a - am/pm                          |  : -- colon  //
//                                                    |                                    |  / -- slash  //
// 1.Calendar calendar = Calendar.getInstance();                                                          //   
// 2.SimpleDateFormat dateFormat = new SimpleDateFormat("pattern");                                       //
// 3.String date = dateFormat.format(calendar.getTime());                                                 //
////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class CalendarUtils 
{
	private static final String DATE_PATTERN1 		= "yyyy-MM-dd'T'HH:mm:ss";
	private static final String DATE_PATTERN2      	= "dd/MM/yyyy hh:mm:ss";
	 
    public static final String DATE_STD_PATTERN  = "yyyy-MM-dd HH:mm a";
	public static final String DATE_STD_PATTERN2 = "dd-MM-yyyy HH:mm ";
	public static final String DATE_STD_PATTERN3 = "EEE, MMM d";
	public static final String DATE_STD_PATTERN4 = "hh:mm";
	public static final String DATE_PATTERN5 ="yyyy-dd-MM";

	public static final String DATE_STD_PATTERN6 = "EEEE, MMMM d, yyyy";
	public static  final String DATE_FORMAT_PATTERN7 ="yyyy-MM-dd HH:mm:ss.SSS";


	//Get specific formatted Date(Date) from milliseconds(long) 
	public static Date getFormattedDate(long dateInMilliseconds)
	{
		Date resultDate;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN1);
	    resultDate = new Date(dateInMilliseconds);
		Date date = null;
		try {
			date = sdf.parse(sdf.format(resultDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//Get specific formatted Date(Date) from milliseconds 
	public static Date getDate(long milliSeconds)
	{
		 Date date = null;
		 SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN2);
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     
	     try {
			date = formatter.parse(formatter.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     return date;
	}
	
	//Get milliseconds(long) from Date(Date)
		public static long getTimeStamp(Date date)
		{
			try {
				return date.getTime()/1000;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		
	//Get total duration in  hh:mm:ss format from milliseconds(long)
	public static String getDuration(long milliseconds)
	{
		int seconds = (int) (milliseconds / 1000) % 60;
		int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
		int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
		if (hours < 1)
			return minutes + ":" + seconds;
		return hours + ":" + minutes + ":" + seconds;
	}

	public static String getCurrentDate(String datePattern)
	{
		String sdf = new SimpleDateFormat(datePattern).format(System.currentTimeMillis());
		return sdf;
	}
	public static String getCurrentDate()
	{
		String sdf = new SimpleDateFormat(DATE_STD_PATTERN).format(System.currentTimeMillis());
		return sdf;
	}
	
	public static String getCurrentDateForLogs()
	{
		String sdf = new SimpleDateFormat(DATE_STD_PATTERN2).format(System.currentTimeMillis());
		return sdf;
	}
	public static String getMonthAndDate()
	{
		String sdf = new SimpleDateFormat(DATE_STD_PATTERN3).format(System.currentTimeMillis());
		return sdf;
	}
	
	public static String getTimeInStringFormat()
	{
		String sdf = new SimpleDateFormat(DATE_STD_PATTERN4).format(System.currentTimeMillis());
		return sdf;
	}
	public static String getDayDateYear()
	{
		String sdf = new SimpleDateFormat(DATE_STD_PATTERN6).format(System.currentTimeMillis());
		return sdf;
	}
	public static String getAMOrPM()
	{
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN2);
		Calendar calendar = Calendar.getInstance();

		int hourOfDay =calendar.get(Calendar.HOUR_OF_DAY);
		if(hourOfDay<=12)
			return "AM";
		else
			return "PM";
	}

	public  static long getMillisecondsFromDateFormat(String DateFormat , String dateStr)
	{
		try
		{
			SimpleDateFormat dt = new SimpleDateFormat(DateFormat);
			Date date = dt.parse(dateStr);

			return date.getTime();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return System.currentTimeMillis();

	}

	/**
	 * Convert a millisecond duration to a string format
	 *
	 * @param millis A duration to convert to a string form
	 * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
	 */
	public static String getDurationBreakdown(long millis)
	{
		if(millis < 0)
		{
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}

		String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1));

		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long mins = TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1);

		StringBuilder stringBuilder = new StringBuilder();
		if(hours>0)
		{
			stringBuilder.append(String.format("%02d", TimeUnit.MILLISECONDS.toHours(millis)));
			stringBuilder.append(":");
		}

		if(mins>0)
		{
			stringBuilder.append(String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1)));
			stringBuilder.append(" ");
		}

		if(hours>0)
		{
			stringBuilder.append("hours ago");
		}
		else if(mins>0)
		{
			stringBuilder.append("mins ago");
		}
		else
		{
			stringBuilder.append("Just now ");
		}

		return(stringBuilder.toString());
	}
	
}



