package dk.dtu.arsfest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.model.LocationList;

public class FileCache {
	
	public static void createCacheFile(Context context, String filename,
			String content) throws IOException {
		File cacheFile = new File(context.getCacheDir() + File.separator + filename);
		cacheFile.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(cacheFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		PrintWriter pw = new PrintWriter(osw);
		pw.println(content);
		pw.flush();
		pw.close();
	}
	
	public static ArrayList<Location> readCacheFile(Context context, String filename) throws IOException {
		
		Gson gson = new GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create();
		
		File cacheFile = new File(context.getCacheDir() + File.separator + filename);
		FileInputStream fis = new FileInputStream(cacheFile);
		
		LocationList list = gson.fromJson(new InputStreamReader(fis, "UTF-8"), LocationList.class);
		return list.getLocations();
	}

}
