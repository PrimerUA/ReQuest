package com.skylion.request.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

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

	private ParseObject parseObject;

	public Vacancy() {
	}

	public Vacancy toObject(ParseObject obj) {
		if (obj == null)
			return new Vacancy();

		image = (ParseFile) obj.get("image");
		title = obj.getString("title");
		demands = obj.getString("demands");
		terms = obj.getString("terms");
		company = obj.getString("company");
		createdAt = obj.getCreatedAt();
		expire = (Date) obj.get("expire");
		reward = obj.getInt("reward");
		salary = obj.getString("salary");
		city = obj.getString("city");
		companyDescription = obj.getString("companyDescription");
		companyAddress = obj.getString("companyAddress");
		author = obj.getParseUser("user");

		return this;
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
		String res = null;
		if (expire != null) {
			SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("UA"));
			res = df.format(expire.getTime()) + " GMT";
		}
		return res;
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

}
