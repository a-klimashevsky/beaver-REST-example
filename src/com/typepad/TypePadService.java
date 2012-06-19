package com.typepad;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TypePadService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
