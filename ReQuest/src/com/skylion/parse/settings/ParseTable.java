package com.skylion.parse.settings;

import com.skylion.parse.settings.ParseConstants;
import com.skylion.request.RequestConstants;

public class ParseTable {
	public static String REQUESTS_TABLE_NAME;
	public static String RESPONDS_TABLE_NAME;
	public static String WALLET_TABLE_NAME;
	public static String TRANSACTIONS_TABLE_NAME;
	
	public ParseTable() {
		if(RequestConstants.DEV_FLUG == true) {
			REQUESTS_TABLE_NAME = ParseConstants.REQUESTS_TABLE_DEV;
			RESPONDS_TABLE_NAME = ParseConstants.RESPONDS_TABLE_DEV;
			WALLET_TABLE_NAME = ParseConstants.WALLET_TABLE_DEV;
			TRANSACTIONS_TABLE_NAME = ParseConstants.TRANSACTION_TABLE_DEV;
		}
		else {
			REQUESTS_TABLE_NAME = ParseConstants.REQUESTS_TABLE_PROD;
			RESPONDS_TABLE_NAME = ParseConstants.RESPONDS_TABLE_PROD;
			WALLET_TABLE_NAME = ParseConstants.WALLET_TABLE_PROD;
			TRANSACTIONS_TABLE_NAME = ParseConstants.TRANSACTION_TABLE_PROD;
		}
	}
}
