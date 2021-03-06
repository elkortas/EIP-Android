package com.epitech.neerbyy;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/** This class represent the list of commentary send by the WebService.
 * Each of this commentary are associate to the same memory.
 * @see ViewMemory
 * @author Seb
 */
public class Commentary implements Serializable {
	private static final long serialVersionUID = -7494458459431032438L;

	@SerializedName("comments") 
    public CommInfos[] list;
    
	/** This class represent the data of one commentary 
	 * @author Seb
	 */
	public class CommInfos implements Serializable {
    	@SerializedName("id")
    	public int id;
    	@SerializedName("content")
    	public String content;
    	@SerializedName("publication_id")
    	public int publicationId; 
    	@SerializedName("created_at")
    	public String created_at;
    	@SerializedName("updated_at")
    	public String updated_at;
    	@SerializedName("user")
    	public User user;      // or InfoUserCommentary
    	
    	/** This class represent the data of the user associate of the comentary
    	 * @author Seb
    	 */
    	private class InfoUserCommentary {
    		@SerializedName("id")
        	public int id;
        	@SerializedName("username")
        	public String username;
        	@SerializedName("avatar")
        	public String avatar;
        	@SerializedName("avatar_thumb")
        	public String avatar_thumb;
    	}
    }
}
