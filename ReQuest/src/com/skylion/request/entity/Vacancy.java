package com.skylion.request.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.parse.ParseFile;
import com.parse.ParseObject;

public class Vacancy {

	private ParseFile image;
	private String title;
	private String description;
	private String company;
	private Date createdAt;
	private int reward;

	private ParseObject parseObject;

	public Vacancy() {
	}

	public Vacancy toObject(ParseObject obj) {
		if (obj == null)
			return new Vacancy();

		// setParseObject(obj);

		image = (ParseFile) obj.get("image");
		title = obj.getString("title");
		description = obj.getString("description");
		company = obj.getString("company");
		createdAt = obj.getCreatedAt();
		reward = obj.getInt("reward");

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCreatedAtToString() {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", new Locale("UA"));
		String res = df.format(createdAt.getTime()) + " GMT";
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

}
