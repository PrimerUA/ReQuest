package com.skylion.request.entity;

import java.util.Date;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Respond {
	
	private ParseObject respondObj;
	
	private Integer type;
	private ParseFile proof;
	private ParseUser user;
	private ParseObject request;
	private String bdate;
	private String name;
	private String email;
	private String experience;
	private String lastJob;
	private String lastPost;
	private ParseFile photo;
	private String comment;
	private Date createdAt;
	private Date updatedAt;
	
	
	public Respond toObject(ParseObject obj) {
		if(obj == null)
			return new Respond();
		
		type = obj.getInt("type");
		proof = obj.getParseFile("proof");
		user = obj.getParseUser("user");
		request = obj.getParseObject("request");
		bdate = obj.getString("birthDate");
		name = obj.getString("name");
		email = obj.getString("email");
		lastJob = obj.getString("lastJob");
		lastPost = obj.getString("lastPosition");
		photo = obj.getParseFile("photo");
		comment = obj.getString("comment");
		createdAt = (Date)obj.get("createdAt");
		updatedAt = (Date)obj.get("updatedAt");
		respondObj = obj;
			
		return this;
	}

	public Integer getType() {
		return type;
	}

	
	public ParseObject getRespondObj() {
		return respondObj;
	}


	public ParseFile getProof() {
		return proof;
	}


	public ParseUser getUser() {
		return user;
	}


	public ParseObject getRequest() {
		return request;
	}


	public String getBdate() {
		return bdate;
	}


	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}


	public String getExperience() {
		return experience;
	}


	public String getLastJob() {
		return lastJob;
	}


	public String getLastPost() {
		return lastPost;
	}


	public ParseFile getPhoto() {
		return photo;
	}


	public String getComment() {
		return comment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	
}
