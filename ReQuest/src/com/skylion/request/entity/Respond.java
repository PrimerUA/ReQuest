package com.skylion.request.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Respond {
	
	private ParseObject respondObj;
	
	private Integer type;
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
		ParseUser ust = null;
		ParseObject requestt = null;
		type = obj.getInt("type");		
		ust = obj.getParseUser("user");
		requestt = obj.getParseObject("request");
		bdate = obj.getString("birthDate");
		name = obj.getString("name");
		email = obj.getString("email");
		lastJob = obj.getString("lastJob");
		lastPost = obj.getString("lastPosition");
		photo = (ParseFile)obj.get("photo");
		comment = obj.getString("comment");
		createdAt = obj.getCreatedAt();
		updatedAt = obj.getUpdatedAt();
		experience = obj.getString("experience");
		respondObj = obj;
				
		try {
			user = ust.fetch();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			request = requestt.fetch();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return this;
	}

	public Integer getType() {
		return type;
	}

	
	public ParseObject getRespondObj() {
		return respondObj;
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

	public String getCreatedAt() {
		return getDateString(createdAt);
	}

	public String getUpdatedAt() {				
		return getDateString(updatedAt);
	}
	
	private String getDateString(Date date) {
		String res = null;
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("UA"));
			res = df.format(date.getTime()) + " GMT";
		}
		return res;
	}
	
	
}
