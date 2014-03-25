package com.skylion.request.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ExpandableViewHelper {
	public static void slideIntoDirection(Context ctx, View v, int type) {

		Animation a = AnimationUtils.loadAnimation(ctx, type);
		if (a != null) {
			a.reset();
			if (v != null) {
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
}
