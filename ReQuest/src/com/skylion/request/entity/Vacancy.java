package com.skylion.request.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.skylion.parse.settings.ParseConstants;

public class Vacancy {

	private ParseFile image;
	private String title;
	private String demands;
	private String terms;
	private String company;
	private Date createdAt;
	private Date expire;
	private int reward;
	private String salary;
	private String city;
	private String companyDescription;
	private String companyAddress;
	private ParseUser author;
	private String objectId;	
	private Integer respondsCount = 0;
	private int fragmentType;
	private ParseObject vacancyObj;	

	private ParseObject parseObject;	

	public Vacancy() {		
	}

	public Vacancy toObject(ParseObject obj) {
		if (obj == null)
			return new Vacancy();
		ParseUser tauthor = null;
		image = (ParseFile) obj.get(ParseConstants.REQUESTS_IMAGE);
		title = obj.getString(ParseConstants.REQUESTS_TITLE);
		demands = obj.getString(ParseConstants.REQUESTS_DEMANDS);
		terms = obj.getString(ParseConstants.REQUESTS_TERMS);
		company = obj.getString(ParseConstants.REQUESTS_COMPANY);
		createdAt = obj.getCreatedAt();
		expire = (Date) obj.get(ParseConstants.REQUESTS_EXPIRE);
		reward = obj.getInt(ParseConstants.REQUESTS_REWARD);
		salary = obj.getString(ParseConstants.REQUESTS_SALARY);
		city = obj.getString(ParseConstants.REQUESTS_CITY);
		companyDescription = obj.getString(ParseConstants.REQUESTS_COMPANY_DESCRIPTION);
		companyAddress = obj.getString(ParseConstants.REQUESTS_COMPANY_ADDRESS);
		tauthor = obj.getParseUser(ParseConstants.REQUESTS_USER);
		objectId = obj.getObjectId();
		
		try { author = tauthor.fetch(); } 
		catch (ParseException e) { e.printStackTrace();}
		
		this.vacancyObj = obj;
		return this;
	}
	
	public void setRespondsCount(int count) {
		this.respondsCount = count;
	}	
	
	public Integer getRespondsCount() {
		return respondsCount;
	}
	
	public ParseFile getImage() {
		return image;
	}

	public void setImage(ParseFile image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCreatedAtToString() {
		String res = null;
		if (expire != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("UA"));
			res = df.format(createdAt.getTime()) + " GMT";
		}
		return res;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getReward() {
		return reward;
	}
	
	public String getRewardText() {
		return "$" + ((Integer)reward).toString();
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public ParseObject getParseObject() {
		return parseObject;
	}

	public void setParseObject(ParseObject parseObject) {
		this.parseObject = parseObject;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getDemands() {
		return demands;
	}

	public void setDemands(String demands) {
		this.demands = demands;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getExpireToString() {		
		if (expire != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("UA"));			
			return df.format(expire.getTime()) + " GMT";
		}
		return null;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public ParseUser getAuthor() {
		return author;
	}

	public void setAuthor(ParseUser author) {
		this.author = author;
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
	
	public ParseObject getvacancyObj() {
		return vacancyObj;
	}	
		
	@Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        Vacancy other = (Vacancy) obj;
        return this.objectId.equals(other.getObjectId());
    }
	
	@Override
	public int hashCode() {
	    return objectId.hashCode();
	}
}
