package com.skylion.request.utils;

import android.content.Context;
import android.widget.Toast;

public class DialogsViewer {
	
	public static void showErrorDialog(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
