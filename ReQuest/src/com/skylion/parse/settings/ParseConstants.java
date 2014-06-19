package com.skylion.parse.settings;

public class ParseConstants {
	

	public static final String RESPONDS_TABLE_PROD = "ProdResponds";
	public static final String WALLET_TABLE_PROD = "ProdWallet";
	public static final String REQUESTS_TABLE_PROD = "ProdRequests";
	public static final String TRANSACTION_TABLE_PROD = "ProdTransaction";

	public static final String RESPONDS_TABLE_DEV = "Responds";
	public static final String WALLET_TABLE_DEV = "Wallet";
	public static final String REQUESTS_TABLE_DEV = "Requests";
	public static final String TRANSACTION_TABLE_DEV = "Transaction";
	
	
	// query constants
	public static final String QUERY_EQUAL_REQUEST = "request";
	public static final String QUERY_EQUAL_TYPE = "type";
	public static final String QUERY_EQUAL_USER = "user";
	
	// Wallet table
	public static final String WALLET = "wallet";
	public static final String WALLET_TOTAL = "total";
	
	// Requests table
	public static final String REQUESTS_TITLE = "title";
	public static final String REQUESTS_REWARD = "reward";
	public static final String REQUESTS_USER = "user";
	public static final String REQUESTS_COMPANY = "company";
	public static final String REQUESTS_SALARY = "salary";
	public static final String REQUESTS_CITY = "city";
	public static final String REQUESTS_DEMANDS = "demands";
	public static final String REQUESTS_TERMS = "terms";
	public static final String REQUESTS_COMPANY_DESCRIPTION = "company_description";
	public static final String REQUESTS_COMPANY_ADDRESS = "company_address";
	public static final String REQUESTS_EXPIRE = "expire";
	public static final String REQUESTS_IMAGE = "image";
	public static final String REQUESTS_TYPE = "type";
	
	//Responds table
	public static final String RESPONDS_BDATE = "birthDate";
	public static final String RESPONDS_COMMENT = "comment";
	public static final String RESPONDS_EMAIL = "email";
	public static final String RESPONDS_EXPERIENCE = "experience";
	public static final String RESPONDS_LAST_JOB = "lastJob";
	public static final String RESPONDS_LAST_POST = "lastPosition";
	public static final String RESPONDS_NAME = "name";
	public static final String RESPONDS_PHOTO = "photo";
	public static final String RESPONDS_PROOF = "";
	public static final String RESPONDS_REQUEST = "request";
	public static final String RESPONDS_STATUS = "type";		// << -- remove this col
	public static final String RESPONDS_TYPE = "type";
	public static final String RESPONDS_USER = "user";		
	
	// User table
	public static final String USER_NAME = "username";
	public static final String USER_PASSWORD = "password";
	public static final String USER_AUTH_DATA = "authData";
	public static final String USER_EMAIL_VERIFIED = "emailVerified";
	public static final String USER_AVATAR = "avatar";
	public static final String USER_EMAIL = "email";
	public static final String USER_PARENT = "parent";
	public static final String USER_WALLET = "wallet";
	
	// Transaction table
	public static final String TRANSACTION_AMOUNT = "amount";
	public static final String TRANSACTION_DESTINATION = "destination";
	public static final String TRANSACTION_TAX = "tax";	
	
}
