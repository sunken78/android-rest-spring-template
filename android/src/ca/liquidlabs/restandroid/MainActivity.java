package ca.liquidlabs.restandroid;

import ca.liquidlabs.restandroid.model.RestCallback;
import ca.liquidlabs.restandroid.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A Demo Activity showing how to handle REST requests
 * in a clean/organized way.
 * 
 * @author Abdullah Rubiyath
 */

public class MainActivity extends Activity implements RestCallback {
	
	// holds of the editText (which contains username)
	private static EditText editText;
	
	// a progress bar - displayed when tweets are retrieved
	private static ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) super.findViewById(R.id.input_twitter_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * Event Handler for Button Clicks in the Activity
	 * 
	 * @param v  The View from where button was tapped
	 * 
	 * @return always returns false
	 */
	public boolean onActionBtnClick(View v) {
		
		switch (v.getId()) {
		
			case R.id.btn_tweet:
				// handle tweets

				// get the twitter username
				String username = editText.getText().toString();
				
				/* get the user's instance and then retrieve the Tweets */
				User user = User.getInstance();
				user.getTweets(username, this);
				
				break;
			
			case R.id.btn_upload:
				// upload picture
				break;
		}		
		
		return false;		
	}

	@Override
	public void preExecute() {
		// show the progress bar
		this.showProgressDialog("Retreiving Your Tweets");
	}

	@Override
	public void postExecute(String response) {
		// hide the progress bar and then show a Toast
		this.hideProgressDialog();
		Toast.makeText(this, response, Toast.LENGTH_LONG).show();		
	}

	@Override
	public String inExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelExecute() {
		// Cancel The Task
		(User.getInstance()).cancelTask();		
	}
	
	
	/**
	 * Shows a Progress Dialog with a Cancel Button
	 *  
	 * @param msg
	 */
	public void showProgressDialog(String msg) 
	{
		final RestCallback callback = this;
		
		// check for existing progressDialog
		if (progressDialog == null) {
			// create a progress Dialog
			progressDialog = new ProgressDialog(this);

			// remove the ability to hide it by tapping back button
			progressDialog.setIndeterminate(true);
			
			progressDialog.setCancelable(false);
			
			progressDialog.setMessage(msg);
			
			if (callback != null) {
				progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", 
					new Dialog.OnClickListener() {					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Cancel the Task when user hits Cancel Button
							callback.cancelExecute();
						}
				});
			}
		}

		// now display it.
		progressDialog.show();		
	}	
	
	
	/**
	 * Hides the Progress Dialog
	 */
	public void hideProgressDialog() {
		
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		
		progressDialog = null;
	}	
	

}
