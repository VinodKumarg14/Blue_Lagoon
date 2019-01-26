package com.la.hotels.inroom.db.dataaccess;

/**
 * Created by PCCS-0007 on 11/7/2016.
 */
public class NewsDL extends BaseDL
{
    private static final String TABLE_NEWS  			    =  "news";

    private static final String COL_TITLE      			    =  "title";
    private static final String COL_CONTENT       			=  "content";
    private static final String COL_SOURCE_NAME       		=  "source_name";
    private static final String COL_CATEGORIES       		=  "categories";
    private static final String COL_IMAGEURL       			=  "imageurl";
    private static final String COL_COUNTRY_CODE       		=  "countrycode";
    private static final String COL_SOURCE_URL       		=  "sorce_url";


    /*CREATE TABLE "news" ("title" VARCHAR, "content" VARCHAR, "source_name" VARCHAR,
        "categories" VARCHAR, "imageurl" VARCHAR, "countrycode" VARCHAR, "sorce_url" VARCHAR)*/

    public NewsDL()
    {
        super(TABLE_NEWS);
    }

//    public void insertNews(NewsDO newsDO)
//    {
//        synchronized (SofitelInroomApplication.MyLock)
//        {
//            SQLiteStatement stmtInsert = null,stmtUpdate=null,stmtRecCount = null;
//            if(newsDO != null)
//            {
//                try
//                {
//                    openTransaction();
//
//                    String insertQuery 	= "INSERT INTO " + TABLE_NEWS + "("+COL_TITLE+" , "+COL_CONTENT+" , "+COL_SOURCE_NAME+" , "+COL_CATEGORIES+" , "+COL_IMAGEURL+" , "+COL_COUNTRY_CODE+" , "+COL_SOURCE_URL+")" + " VALUES (?,?,?,?,?,?,?)";
//                    String updateQuery 	= "UPDATE "+TABLE_NEWS+" set "+COL_TITLE+"=? , "+COL_CONTENT+"=? , "+COL_SOURCE_NAME+"=? , "+COL_CATEGORIES+"=? ,"+COL_IMAGEURL+"=? , "+COL_COUNTRY_CODE+"=? , "+COL_SOURCE_URL+"=? "+ " WHERE "+COL_TITLE+"=? ";
//
//                    stmtRecCount 		= getSqlStatement("select count(*) from "+TABLE_NEWS+" WHERE "+COL_TITLE+"=?");
//
//                    stmtInsert 			= getSqlStatement(insertQuery);
//                    stmtUpdate 			= getSqlStatement(updateQuery);
//
//                    stmtRecCount.bindString(1, newsDO.title);
//
//                    long count = stmtRecCount.simpleQueryForLong();
//
//                    if(count > 0)//Update Query
//                    {
//                        stmtUpdate.bindString(1, newsDO.title);
//                        stmtUpdate.bindString(2, newsDO.content);
//                        stmtUpdate.bindString(3, newsDO.sourceName);
//                        stmtUpdate.bindString(4, newsDO.categories);
//                        stmtUpdate.bindString(5, newsDO.imageUrl);
//                        stmtUpdate.bindString(6, newsDO.countryCode);
//                        stmtUpdate.bindString(7, newsDO.sourceUrl);
//                        stmtUpdate.bindString(8, newsDO.title);
//
//                        stmtUpdate.execute();
//                    }
//                    else //Insert Query
//                    {
//                        stmtInsert.bindString(1, newsDO.title);
//                        stmtInsert.bindString(2, newsDO.content);
//                        stmtInsert.bindString(3, newsDO.sourceName);
//                        stmtInsert.bindString(4, newsDO.categories);
//                        stmtInsert.bindString(5, newsDO.imageUrl);
//                        stmtInsert.bindString(6, newsDO.countryCode);
//                        stmtInsert.bindString(7, newsDO.sourceUrl);
//
//                        stmtInsert.executeInsert();
//                    }
//                    setTransaction();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                finally
//                {
//                    closeTransaction();
//                    if(stmtInsert != null)
//                        stmtInsert.close();
//                }
//            }
//        }
//    }
//
//    public Vector<NewsDO> getAlNews()
//    {
//        Vector<NewsDO> vecNewsDos = new Vector<NewsDO>();
//        NewsDO  newsDO = null;
//        synchronized (SofitelInroomApplication.MyLock)
//        {
//            Cursor cursor = null;
//            try
//            {
//                openDataBase();
//
//                String query ="SELECT * FROM "+TABLE_NEWS;
//                cursor = getCursor(query, null);
//                if(cursor.moveToFirst())
//                {
//                    do
//                    {
//                        newsDO      					= 	new NewsDO();
//                        newsDO.title    		        = 	cursor.getString(cursor.getColumnIndex(COL_TITLE));
//                        newsDO.content    	            = 	cursor.getString(cursor.getColumnIndex(COL_CONTENT));
//                        newsDO.sourceName               = 	cursor.getString(cursor.getColumnIndex(COL_SOURCE_NAME));
//                        newsDO.categories    		    = 	cursor.getString(cursor.getColumnIndex(COL_CATEGORIES));
//                        newsDO.imageUrl    			    = 	cursor.getString(cursor.getColumnIndex(COL_IMAGEURL));
//                        newsDO.countryCode    			= 	cursor.getString(cursor.getColumnIndex(COL_COUNTRY_CODE));
//                        newsDO.sourceUrl    			= 	cursor.getString(cursor.getColumnIndex(COL_SOURCE_URL));
//
//                        vecNewsDos.add(newsDO);
//
//                    } while (cursor.moveToNext());
//                }
//
//            }
//            catch (Exception e)
//            {
//                e.getLocalizedMessage();
//            }
//            finally
//            {
//                if(cursor != null && !cursor.isClosed())
//                    cursor.close();
//                closeDatabase();
//            }
//        }
//        return vecNewsDos;
//    }
//
//    public void deleteAllNews()
//    {
//        synchronized (SofitelInroomApplication.MyLock)
//        {
//            try
//            {
//                openDataBase();
//                String query = "DELETE FROM "+TABLE_NEWS;
//                excuteQuery(query);
//            }
//            catch (Exception e)
//            {
//                e.getLocalizedMessage();
//            }
//            finally
//            {
//                closeDatabase();
//            }
//        }
//    }
//
//    public void deleteNews(String title)
//    {
//        synchronized (SofitelInroomApplication.MyLock)
//        {
//            try
//            {
//                openDataBase();
//                String query = "DELETE FROM "+TABLE_NEWS+ " WHERE "  + COL_TITLE+" = '"+ title+"'";
//                excuteQuery(query);
//            }
//            catch (Exception e)
//            {
//                e.getLocalizedMessage();
//            }
//            finally
//            {
//                closeDatabase();
//            }
//        }
//    }
}
