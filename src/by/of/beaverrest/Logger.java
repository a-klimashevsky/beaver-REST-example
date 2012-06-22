package by.of.beaverrest;

import android.util.Log;

public class Logger {
	private static final boolean useLogger = true;
	private static final String mApplicationTag = "XL_";
	private String mTag;

	public Logger(Class<?> targetClass) {
		mTag = mApplicationTag + targetClass.getName();
	}

	public void debug(String message) {
		if (useLogger)
			Log.d(mTag, message);
	}

	public void info(String message) {
		if (useLogger)
			Log.i(mTag, message);
		;
	}

	public void error(String message) {
		if (useLogger)
			Log.e(mTag, message);
	}

	public void error(Exception e) {
		if (useLogger)
			printFullErrorInfo(e);
	}

	private void printFullErrorInfo(Exception ex) {
		StringBuilder message = new StringBuilder();

		message.append(ex.getMessage() + "\n");
		message.append(Log.getStackTraceString(ex));

		Log.e(mTag, message.toString());
	}
}
