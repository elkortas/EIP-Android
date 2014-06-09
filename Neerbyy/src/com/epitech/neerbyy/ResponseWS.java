package com.epitech.neerbyy;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
//import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * ResponseWs represent a generic object type that return the WebService for each request.
 *	
 * @author Seb
 *
 */
public class ResponseWS {
/**
 * responseCode is the error code number. 0 for no error, or 1.	 
 */
	@SerializedName("responseCode")
	public int responseCode;
	/**
	 * responseMessage is the string message error than the WebService return. It set to null on no error. 
	 */
	@SerializedName("responseMessage")
	public String responseMessage;
	/**
	 * result is a generic type class who depend the type of the client request.
	 * It allow the Gson library to instantiate the needed class.
	 */
	@SerializedName("result")
	//public Object result;
	public Result result;
	
	public class Result {
		
		@SerializedName("user")
		public User user;
		
		@SerializedName("places")
		public Place.PlaceInfo[] places;
		
		@SerializedName("publications")
		public Post.PostInfos[] postes;
	
		@SerializedName("comments")
		public Commentary.CommInfos[] comms;
		
		@SerializedName("votes")
		public Votes[] votes;
		
		//@SerializedName("error")
		//public User user;
	}
	
	/**
	 * Return the typed instanced object
	 * @param obj
	 * The type of Class need to be instanced
	 * @param mode
	 * mode is use to a temporary debugging test
	 * @return
	 * the typed instanced object
	 */
	public <T> T getValue(Class<T> obj)
	{
		try {
			//Gson gson = new Gson();	
					if (responseCode != 1)
							{
								if (obj == User.class) {
									Log.w("DETECT", "DETECT USER");
									return (T) result.user;
								}
								else if (obj == Place.class) {
									Log.w("DETECT", "DETECT PLACE");
									Place place = new Place();
									place.list = result.places;
									//return (T) result.places;
									return (T) place;
								}
								else if (obj == Post.class) {
									Log.w("DETECT", "DETECT POST_UPDATE");
									Post post = new Post();
									post.list = result.postes;
									//return (T) result.places;
									return (T) post;
								}
								else if (obj == Commentary.class) {
									Log.w("DETECT", "DETECT COMM_UPDATE");								
									Commentary comm = new Commentary();
									comm.list = result.comms;
									return (T) comm;
								}
								else if (obj == Votes.class) {
									Log.w("DETECT", "DETECT GET_VOTES");								
									//Votes[] votes = new Votes[]();
									//comm.list = result.comms;
									return (T) result.votes;
								}
							//return gson.fromJson(gg, obj);						
							}
					else
					{
						//return gson.fromJson(gson.toJson(result), obj);
						return null;
					}
				}
		catch(JsonParseException e)
		{
			System.out.println("Exception nb 2 in check_exitrestrepWSResponse::"+e.toString());
			return null;
		}	
		return null;
	}
}

