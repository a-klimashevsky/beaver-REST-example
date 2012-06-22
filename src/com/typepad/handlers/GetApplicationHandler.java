package com.typepad.handlers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import by.of.beaverrest.BaseIntentHandler;

import com.typepad.TypePadServiceHelper;

public class GetApplicationHandler extends BaseIntentHandler{
	private static final String TAG = "TestActionHandler";

	public static String ACTION = TypePadServiceHelper.PACKAGE
			.concat(".ACTION_GET_APPLICATION");

	public static String EXTRA_ID = ACTION
			.concat(".EXTRA_ID");


	@Override
	public void doExecute(Intent intent, Context context,
			ResultReceiver callback) {
		String id = intent.getStringExtra(EXTRA_ID);

		Bundle data = new Bundle();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Log.wtf(TAG, "WTF");
		}
	}
}
