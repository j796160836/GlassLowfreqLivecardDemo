package com.example.GlassLowfreqLivecardDemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Activity showing the options menu.
 */
public class MenuActivity extends Activity {

	/** Request code for setting the timer, visible for testing. */

	private final Handler mHandler = new Handler();

	private boolean mAttachedToWindow;
	private boolean mOptionsMenuOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		mAttachedToWindow = true;
		openOptionsMenu();
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mAttachedToWindow = false;
	}

	@Override
	public void openOptionsMenu() {
		if (!mOptionsMenuOpen && mAttachedToWindow) {
			mOptionsMenuOpen = true;
			super.openOptionsMenu();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		setOptionsMenuGroupState(menu, R.id.stop, true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection.
		switch (item.getItemId()) {
		case R.id.stop:
			// Stop the service at the end of the message queue for proper
			// options menu
			// animation. This is only needed when starting a new Activity or
			// stopping a Service
			// that published a LiveCard.
			post(new Runnable() {

				@Override
				public void run() {
					stopService(new Intent(MenuActivity.this,
							LiveCardService.class));
				}
			});
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		mOptionsMenuOpen = false;
		finish();
	}

	/**
	 * Posts a {@link Runnable} at the end of the message loop, overridable for
	 * testing.
	 */
	protected void post(Runnable runnable) {
		mHandler.post(runnable);
	}

	/**
	 * Sets all menu items visible and enabled state that are in the given
	 * group.
	 */
	private static void setOptionsMenuGroupState(Menu menu, int groupId,
			boolean enabled) {
		menu.setGroupVisible(groupId, enabled);
		menu.setGroupEnabled(groupId, enabled);
	}
}
