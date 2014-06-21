package com.skylion.request.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.skylion.parse.settings.ParseConstants;

public class Respond {
	
	private ParseObject respondObj;
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
	private String objectId;
	private int fragmentType;
	private Integer candidateStatus;
		
	public Respond toObject(ParseObject obj) {
		if(obj == null)
			return new Respond();
		ParseUser ust = null;
		ParseObject requestt = null;
		ust = obj.getParseUser(ParseConstants.RESPONDS_USER);
		requestt = obj.getParseObject(ParseConstants.RESPONDS_REQUEST);
		bdate = obj.getString(ParseConstants.RESPONDS_BDATE);
		name = obj.getString(ParseConstants.RESPONDS_NAME);
		email = obj.getString(ParseConstants.RESPONDS_EMAIL);
		lastJob = obj.getString(ParseConstants.RESPONDS_LAST_JOB);
		lastPost = obj.getString(ParseConstants.RESPONDS_LAST_POST);
		photo = (ParseFile)obj.get(ParseConstants.RESPONDS_PHOTO);
		comment = obj.getString(ParseConstants.RESPONDS_COMMENT);
		createdAt = obj.getCreatedAt();
		updatedAt = obj.getUpdatedAt();
		experience = obj.getString(ParseConstants.RESPONDS_EXPERIENCE);		
		candidateStatus = (Integer) obj.getNumber(ParseConstants.RESPONDS_TYPE);
		objectId = obj.getObjectId();
		respondObj = obj;
				
		try { user = ust.fetch();} 
		catch (ParseException e) { e.printStackTrace(); }
		
		try { request = requestt.fetch();} 
		catch (ParseException e) { e.printStackTrace(); }
			
		return this;
	}	
	
	public Integer getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(Integer candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public String getObjectId() {
		return objectId;
	}	

	public int getFragmentType() {
		return fragmentType;
	}

	public void setFragmentType(int fragmentType) {
		this.fragmentType = fragmentType;
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
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("UA"));
			return df.format(date.getTime()) + " GMT";
		}
		return null;
	}		
}
