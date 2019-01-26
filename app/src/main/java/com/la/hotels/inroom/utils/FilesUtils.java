package com.la.hotels.inroom.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.StatFs;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FilesUtils 
{
	public static final String SDCARD_PATH = "/storage/sdcard1/";



	//delete file based on path 
	public static void deleteFile(String path)
	{
		try 
		{
			File file = new File(path);
			if(file.exists())
				file.delete();
		} catch (Exception e) {
		   e.printStackTrace();
		}
	}
   
    //Get filename from completePath
	public static String getFileName(String compltePath)
	{
		String fileName = null;
		try 
		{
			fileName = compltePath.substring(compltePath.lastIndexOf(File.separator) + 1);
			
		} catch (Exception e)
		{
		  e.printStackTrace();
		}
		return fileName;
    }

	//Create Normal File based String data and path
	@SuppressWarnings("finally")
	public static boolean createNormalFile(String data, String filePath)
	{
		boolean isAnyError=false;
		try 
		{
			LogUtils.debug("createFile", "NormalFileCreating...");
			File dataFile = new File(filePath);
			dataFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(dataFile,true);
			if (fos != null)
			{
				data += "\n";
				fos.write(data.getBytes());
			}
			fos.close();
			isAnyError=false;
		} 
		catch (Exception e)
		{
		  e.printStackTrace();
		  isAnyError= true;
		  LogUtils.debug("createFile", "error while creating file");
		}finally{
			return isAnyError;		
		}
	}
	
	//compress single file based on path 
	public static boolean getZipFileForSingle(String normalFilePath, String zipFilePath)
    {
        boolean isErrorWhileConvertingToZip=false;
		FileOutputStream fos;
        FileInputStream fis;
		try 
		{
			File file = new File(normalFilePath);
			LogUtils.debug("getZipFile", "ZipFileCreating...");
			fos = new FileOutputStream(file);
			ZipOutputStream zos = new ZipOutputStream(fos);
		    ZipEntry ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);
		    fis = new FileInputStream(file);
		    byte[] buffer = new byte[1024];
	        int len;
	        while ((len = fis.read(buffer)) > 0) 
	        {
	            zos.write(buffer, 0, len);
	        }
	        zos.closeEntry();
	        zos.close();
	        fis.close();
	        fos.close();
	        LogUtils.debug("getZipFile", "ZipFileCreated");
		} 
		catch (IOException e) {
			e.printStackTrace();
			isErrorWhileConvertingToZip=true;
			LogUtils.debug("getZipFile", "error while creating zip file");
		}
		catch (Exception e) {
			e.printStackTrace();
			isErrorWhileConvertingToZip=true;
			LogUtils.debug("getZipFile", "error while creating zip file");
		}
		return isErrorWhileConvertingToZip;
    }
	
	//compress multiple files based filePaths
	public static boolean getZipFileForMultiple(String[]filePaths, String zipFilepath)
	{
		
		try  
		 {    LogUtils.debug("getZipFile", "ZipFileCreating...");
			  int BUFFER = 2048;
		      BufferedInputStream bufferInStream = null;
		      FileOutputStream fileOutStream = new FileOutputStream(zipFilepath);
		      ZipOutputStream zipOutStram = new ZipOutputStream(new BufferedOutputStream(fileOutStream));
		      byte data[] = new byte[BUFFER]; 
		      for(int i=0; i < filePaths.length; i++) 
		      { 
		    	 LogUtils.debug("getZipFile", "Adding: " + filePaths[i]); 
		         FileInputStream fileInStream = new FileInputStream(filePaths[i]);
		         bufferInStream = new BufferedInputStream(fileInStream, BUFFER);
		         ZipEntry entry = new ZipEntry(getFileName(filePaths[i]));
		         zipOutStram.putNextEntry(entry); 
		         int count; 
		         while ((count = bufferInStream.read(data, 0, BUFFER)) != -1) 
		         { 
		        	 zipOutStram.write(data, 0, count); 
		         } 
		         bufferInStream.close(); 
		      } 
		 
		      zipOutStram.close();
		      LogUtils.debug("getZipFile", "ZipFileCreated");
		    } catch(Exception e) {
		     LogUtils.debug("getZipFile", "error while creating zip file");	
		      e.printStackTrace(); 
		    } 
		
		return false;
		
	}
	
	//Extract normalFile from zipfile
	public static void unZipForSingle(String zipFilePath, String normalFilePath)
	  { 
		
		  try 
		  {
			  FileInputStream fis = new FileInputStream(zipFilePath);
			  ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			  ZipEntry entry;
	
			  while ((entry = zis.getNextEntry()) != null) 
			  {
			    int size;
			    byte[] buffer = new byte[2048];
	
			    FileOutputStream fos = new FileOutputStream(normalFilePath);
			    BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
	
			    while ((size = zis.read(buffer, 0, buffer.length)) != -1) 
			    {
			    	LogUtils.debug("unZip","Unziping the file ...");
			    	bos.write(buffer, 0, size);
			    }
			    bos.flush();
			    bos.close();
			  }
			  LogUtils.debug("unZip","Unziping the file completed");
			  zis.close();
			  fis.close();
			  
	   } 
	  catch (Exception e)
	  {
		e.printStackTrace();
		LogUtils.error("unZip", "error in unzipping the file");
	  }
	 
	  } 
	
	  //Extract normalFiles from zipfile
	  public static void unZipForMultiple(String zipFilePath, String unzipFileFolderPath)
	  {
		     byte[] buffer = new byte[1024];
		     try
		     {
		    	//create output directory is not exists
		    	File folder = new File(unzipFileFolderPath);
		    	if(!folder.exists())
		    	{
		    		folder.mkdir();
		    	}
		 
		    	//get the zip file content
		    	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));
		    	//get the zipped file list entry
		    	ZipEntry ze = zis.getNextEntry();
		    	while(ze!=null)
		    	{
		    	   String fileName = ze.getName();
		           File newFile = new File(unzipFileFolderPath + File.separator + fileName);
		           LogUtils.debug("unzip", "extracted file: "+newFile.getAbsolutePath()); 
		            //create all non exists folders
		            //else you will hit FileNotFoundException for compressed folder
		            new File(newFile.getParent()).mkdirs();
		            FileOutputStream fos = new FileOutputStream(newFile);
		            int len;
		            while ((len = zis.read(buffer)) > 0) 
		            {
		       		  fos.write(buffer, 0, len);
		            }
		 
		            fos.close();   
		            ze = zis.getNextEntry();
		    	}
		 
		        zis.closeEntry();
		    	zis.close();
		 
		    }
		     catch(IOException ex)
		     {
		       ex.printStackTrace(); 
		    }
		   }    
	

		
		//Method to convert Stream to String 
		public static String convertStreamToString(InputStream inputStream) throws IOException
		{
			//Initialize variables
			String responce = "";
			
			if (inputStream != null)
			{
				Writer writer = new StringWriter();
			    char[] buffer = new char[1024];
			    try
			    {
			       //Reader
			       Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			       int n;
			       while ((n = reader.read(buffer)) != -1)
			       {
			    	   //writing
			            writer.write(buffer, 0, n);
			       }
			       responce =  writer.toString();
			       writer.close();
			    }
			    finally 
			    {
			    	//closing InputStream
			    	inputStream.close();
			    }
			    
			    return responce;
			}
			else 
			{       
				return "";
			}
	    }

    public static long getExternalStorageUsedSpace() {
        StatFs stat = new StatFs(SDCARD_PATH);
//        long bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        long bytesAvailable = stat.getTotalBytes() - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong());
//        long megAvailable = bytesAvailable / 1048576;

        return bytesAvailable;
    }


    //Method to copy file from one location to another
		public void copyFile(String sourceFilePath, String destFilePath) throws IOException
	    {
		 	File sourceFile = new File(sourceFilePath);
		 	File destFile 	= new File(destFilePath);
		 	
			if (!sourceFile.exists())
			{
			    return;
			}
			if (!destFile.exists()) 
			{
			    destFile.createNewFile();
			}
			FileChannel source = null;
			FileChannel destination = null;
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			if (destination != null && source != null) 
			{
			    destination.transferFrom(source, 0, source.size());
			}
			if (source != null)
			{
			    source.close();
			}
			if (destination != null)
			{
			    destination.close();
			}

	      }

		  //Method to copy Streams
		 public static void CopyStream(InputStream is, OutputStream os)
		 {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              if(Thread.currentThread().isInterrupted()) return;
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
		 }
		 
	 //Method to convert Image/Bitmap to ByteArrayOutputStram	 
	 private byte[] convertImageToByteArrayOutputStream(ImageView ivExample)
	 {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BitmapDrawable drawable = (BitmapDrawable) ivExample.getDrawable();
			Bitmap bitmap = drawable.getBitmap();
			bitmap.compress(CompressFormat.PNG, 100, baos);
			return baos.toByteArray();
			
	}
	 
	 //Method to convert ByteArrayOutputStream to Bitmap
	private Bitmap convertByteArrayOutputStreamToBitmap(byte[] photo)
	{
		
		ByteArrayInputStream bais = new ByteArrayInputStream(photo);
		Bitmap bitmap = BitmapFactory.decodeStream(bais);
		return bitmap;
	}

}
