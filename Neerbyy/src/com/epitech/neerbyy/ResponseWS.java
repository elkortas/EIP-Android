package com.epitech.neerbyy;

import java.util.List;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class ResponseWS {
	@SerializedName("responseCode")
	public int responseCode;
	@SerializedName("responseMessage")
	public String responseMessage;
	@SerializedName("result")
	public Object result;
	
	public <T> T getValue(Class<T> obj, int mode)   // 1 = user create account
	{
		try {  //  mettre un champ user dans class response .......
			Gson gson = new Gson();	
					if (mode == 1 && responseCode != 1) // for create user
							{
								String gg = "";
								gg = gson.toJson(result);
								gg = gg.substring(8,gg.length() - 1);
								Log.w("TRANSFORM", "NEW USER =  " + gg);
								Log.w("RECUS OBJ", "receiveOBJ vec code = " + this.responseCode + " nameClass " + obj.getName() + "::::::::::::::::::::::::: " + gson.toJson(result));
								return gson.fromJson(gg, obj);
							}
					else
						return gson.fromJson(gson.toJson(result), obj);
			}
		catch(JsonParseException e)
		{
			System.out.println("Exception in check_exitrestrepWSResponse::"+e.toString());
		}	
		return null;
	}
	
	public <T> List<T> getTabValue(Class<T> obj)
	{
		try {
			Gson gson = new Gson();
			if (responseCode != 1 )
				{
					Log.w("RECUS OBJ", "receiveOBJ =  ::::::::::::::::::::::::: " + gson.toJson(result));
				
					//Type collectionType = new TypeToken<List<T>>(){}.getType();
					//List<T> lcs = (List<T>)	new Gson().fromJson(gson.toJson(result) , collectionType);
					//return lcs;
			
					
			//		T[] mcArray = gson.fromJson(gson.toJson(result), T[].class);
			//		return mcArray;
					
					//return gson.fromJson(gson.toJson(result), collectionType);
				}
			}
		catch(JsonParseException e)
		{
			System.out.println("Exception in synthaxe Json :: "+e.toString());
		}		
		return null;
	}
}

