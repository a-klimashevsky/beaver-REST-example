package com.typepad;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TypePadService extends Service {

	public static final String EXTRA_STATUS_RECEIVER = TypePadServiceHelper.PACKAGE.concat("STATUS_RECEIVER");

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
