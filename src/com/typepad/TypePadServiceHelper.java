package com.typepad;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.typepad.handlers.GetApplicationHandler;

import by.of.beaverrest.ServiceCallbackListener;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.SparseArray;

public class TypePadServiceHelper {

	public static final String PACKAGE = "com.typepad";

	private ArrayList<ServiceCallbackListener> currentListeners = new ArrayList<ServiceCallbackListener>();

	private AtomicInteger idCounter = new AtomicInteger();

	private SparseArray<Intent> pendingActivities = new SparseArray<Intent>();

	private Application application;

	private static TypePadServiceHelper instance;

	public static TypePadServiceHelper getInstance(Application application) {
		if (instance == null) {
			instance = new TypePadServiceHelper(application);
		}
		return instance;
	}

	private TypePadServiceHelper(Application app) {
		this.application = app;
	}

	public void addListener(ServiceCallbackListener currentListener) {
		currentListeners.add(currentListener);
	}

	public void removeListener(ServiceCallbackListener currentListener) {
		currentListeners.remove(currentListener);
	}

	public int getApplication(String id) {
		final int requestId = createId();

		Intent i = createIntent(application, GetApplicationHandler.ACTION,
				requestId);
		i.putExtra(GetApplicationHandler.EXTRA_ID, id);

		return runRequest(requestId, null);
	}

	public boolean isPending(int requestId) {
		return pendingActivities.get(requestId) != null;
	}

	private int createId() {
		return idCounter.getAndIncrement();
	}

	private int runRequest(final int requestId, Intent i) {
		pendingActivities.append(requestId, i);
		application.startService(i);
		return requestId;
	}

	private Intent createIntent(final Context context, String actionLogin,
			final int requestId) {
		Intent i = new Intent(context, TypePadService.class);
		i.setAction(actionLogin);

		i.putExtra(TypePadService.EXTRA_STATUS_RECEIVER, new ResultReceiver(
				new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				Intent originalIntent = pendingActivities.get(requestId);
				if (isPending(requestId)) {
					pendingActivities.remove(requestId);

					for (ServiceCallbackListener currentListener : currentListeners) {
						if (currentListener != null) {
							currentListener.onServiceCallback(requestId,
									originalIntent, resultCode, resultData);
						}
					}
				}
			}
		});

		return i;
	}

}
