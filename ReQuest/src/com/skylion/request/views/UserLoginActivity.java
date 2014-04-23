package com.skylion.request.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.skylion.request.R;

public class UserLoginActivity extends ActionBarActivity implements GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private ParseUser user;
	private ParseObject wallet;
	
	private SignInButton loginButton;

	public final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private PlusClient plusClient;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		progressDialog = new ProgressDialog(this);
		getSupportActionBar().setTitle(R.string.title_activity_auth);

		plusClient = new PlusClient.Builder(this, this, this).setVisibleActivities("http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();
		plusClient.connect();

		screenInit();
	}

	private void screenInit() {
		loginButton = (SignInButton) findViewById(R.id.LoginScreen_googleSignInButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGooglePlus();
			}
		});
	}

	protected void startGooglePlus() {
		if (!plusClient.isConnected()) {
			plusClient.connect();
			progressDialog = ProgressDialog.show(this, getString(R.string.connection), getString(R.string.connecting_auth));
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		plusClient.disconnect();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_RESOLVE_ERR && resultCode == RESULT_OK) {
			plusClient.connect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
			} catch (IntentSender.SendIntentException e) {
				plusClient.connect();
			}
		} else {
			progressDialog.dismiss();
			Toast.makeText(this, "G+ error code: " + result.getErrorCode(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		if (plusClient != null) {
			user = new ParseUser();
			user.setUsername(plusClient.getCurrentPerson().getDisplayName());
			user.setPassword("my-pass");
			user.setEmail(plusClient.getAccountName());
			user.put("avatar", plusClient.getCurrentPerson().getImage().getUrl());
			doAuth(user);
		} else {
			progressDialog.dismiss();
			Toast.makeText(UserLoginActivity.this, "G+ is null", Toast.LENGTH_SHORT).show();
		}
	}

	protected void doAuth(ParseUser user) {
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					doWallet(); // the user is new, create wallet for him
				} else {
					signIn(); // user already registered, sign in
				}
			}
		});
	}

	protected void doWallet() {
		wallet = new ParseObject("Wallet");
		wallet.put("total", 0);
		wallet.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				user.put("wallet", wallet);
				user.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException arg0) {
						Toast.makeText(UserLoginActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
						progressDialog.dismiss();
						finish();
					}
				});
			}
		});
	}

	protected void signIn() {
		ParseUser.logInInBackground(plusClient.getCurrentPerson().getDisplayName(), "my-pass", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					progressDialog.dismiss();
					Toast.makeText(UserLoginActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
					finish();
				} else {
					progressDialog.dismiss();
					Toast.makeText(UserLoginActivity.this, "Error code: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onDisconnected() {
		// Toast.makeText(this, getString(R.string.google_disconnected),
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}

}
