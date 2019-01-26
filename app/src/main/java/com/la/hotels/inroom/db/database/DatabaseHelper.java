package com.la.hotels.inroom.db.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.la.hotels.inroom.constants.AppConstants;
import com.la.hotels.inroom.ui.activities.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  Description of class : This class having Database creation using SQLiteOpenHelper. 
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

	public static SQLiteDatabase _database;

	private final Context myContext;
	public static String sep = ",";
	public static final int VERSION_NO = 1;

	public DatabaseHelper(Context context)
	{
		super(context, AppConstants.DATABASE_NAME, null, VERSION_NO);
		AppConstants.DATABASE_PATH = BaseActivity.mContext.getFilesDir().toString() + "/";
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase() throws Exception
	{
		boolean dbExist = checkDataBase();

		if(!dbExist)
		{
			//By calling this method an empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			try
			{
				copyDataBase();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
	}


	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase()
	{
		SQLiteDatabase checkDB = null;
		try
		{
			AppConstants.DATABASE_PATH = myContext.getFilesDir().toString() + "/";
			String myPath = AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

			if(checkDB != null)
			{
				if(checkDB.getVersion() < VERSION_NO)
				{
					AppConstants.DB_HASUpdate = true;
					onUpgrade(checkDB, checkDB.getVersion(), VERSION_NO);
				}
				else
				{
					AppConstants.IS_APP_FIRST_INSTALL = true;
				}
			}
			else
			{
				AppConstants.IS_APP_FIRST_INSTALL = true;
			}

		}
		catch(SQLiteException e)
		{
			e.printStackTrace();
			//database does't exist yet.
		}
		if(checkDB != null)
		{
			checkDB.close();
		}

		return checkDB != null;
	}

	/**
	 * To Copy the database
	 * @throws IOException
	 */
	public void copyDataBase() throws IOException
	{
		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(AppConstants.DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[2048];
		int length;
		while ((length = myInput.read(buffer))>0)
		{
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}


	//To open the database
	public static SQLiteDatabase openDataBase() throws SQLException
	{

		try
		{
			if(AppConstants.DATABASE_PATH==null || AppConstants.DATABASE_PATH.equalsIgnoreCase(""))
				AppConstants.DATABASE_PATH = BaseActivity.mContext.getFilesDir().toString() + "/";
			Log.e("DATABASE_PATH",""+AppConstants.DATABASE_PATH);

			if(_database == null)
			{
				_database = SQLiteDatabase.openDatabase(AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
			}
			else if(!_database.isOpen())
			{
				_database = SQLiteDatabase.openDatabase(AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.CREATE_IF_NECESSARY);
			}
			return _database;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return _database;
		}
	}
	//To close database
	public static void closedatabase()
	{
		try
		{
			if(_database != null && _database.isOpen())
				_database.close();
		}
		catch (Exception e)
		{
//    		ErrorReporter.getInstance().handleException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.setVersion(newVersion);
	}

	public static synchronized DictionaryEntry[][] get(String query_str)
	{
		DictionaryEntry dir = null;
		String[] columns;
		int index;
		int rowIndex = 0;
		DictionaryEntry[] row_obj = null; //An array of columns and their values
		DictionaryEntry[][] data_arr = null;
		Cursor c = null;

		openDataBase();

		if(_database != null)
		{
			try
			{
				c = _database.rawQuery(query_str, null);
				if(c.moveToFirst())
				{
					rowIndex = 0;
					data_arr = new DictionaryEntry[c.getCount()][];
					do
					{
						columns = c.getColumnNames();
						row_obj = new DictionaryEntry[columns.length]; //(columns.length);
						for(int i=0; i<columns.length; i++)
						{
							dir = new DictionaryEntry();
							dir.key = columns[i];
							index = c.getColumnIndex(dir.key);
							if(dir.key.equals("barcode") ||dir.key.equals("ImageLarge"))
							{
								Log.i("dir.key",""+dir.key);
								dir.value = c.getBlob(index);
							}
							else
								dir.value = c.getString(index);
							row_obj[i] = dir;
						}
						data_arr[rowIndex] = row_obj;
						rowIndex++;
					}
					while(c.moveToNext());
				}
				if(c != null && !c.isClosed())
					c.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(c != null && !c.isClosed())
					c.close();
				closedatabase();
			}
		}
		return data_arr;
	}


	public static boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i=0; i<children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}
		// The directory is now empty so delete it  
		return dir.delete();
	}

}
