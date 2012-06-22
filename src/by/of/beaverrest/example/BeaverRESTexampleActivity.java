package by.of.beaverrest.example;

import com.typepad.TypePadServiceHelper;

import by.of.beaverrest.ServiceCallbackListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BeaverRESTexampleActivity extends Activity implements ServiceCallbackListener, OnClickListener{
	private TypePadServiceHelper serviceHelper;

	@Override
	protected void onResume() {
		super.onResume();
		serviceHelper.addListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		serviceHelper.removeListener(this);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceHelper = TypePadServiceHelper.getInstance(getApplication());
        setContentView(R.layout.main);
        findViewById(R.id.test).setOnClickListener(this);
    }

	public void onServiceCallback(int requestId, Intent requestIntent,
			int resultCode, Bundle data) {
		
	}

	public void onClick(View arg0) {
		serviceHelper.getApplication("6p012877b14c76970c");
	}
}