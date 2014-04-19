package com.skylion.request;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.PushService;

import android.app.Application;

public class RequestApplication extends Application {

	public void onCreate() {
		Parse.initialize(this, "V10TgoAKTJ7B8H8YjJhgucaXdGiDeROgxACn6aA2", "fx842YzbYh82BqORRq0LWTHOT2oSoSsJvveu0Y3y");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		
		ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);
	}
}
