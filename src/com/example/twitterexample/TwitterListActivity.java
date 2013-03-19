package com.example.twitterexample;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import com.util.Util;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author Aniruddh
 *
 */
public class TwitterListActivity extends Activity {

	String[] param = new String[1];
	ListView mListView;
	TwitterListAdapter mListAdapter;
	Context mContext;
	ArrayList<TwitterFeed> twitterFeeds;
	protected ServerResult result = null;
	ProgressDialog pd;

	public ServerResult getResult() {
		return result;
	}

	public static enum ServerResult {
		SERVER_CONNECTION_ERROR, SUCCESS, NO_NETWORK_CONNECTION, INVALID_ENTRY, NO_RESULT_FOUND
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_twitter_list);
		mListView = (ListView) findViewById(R.id.twitterlist);
		TwitterSyncTask twitterTask = new TwitterSyncTask();
		param[0] = getIntent().getStringExtra("SEARCH_STRING");
		// Invoking Async task to download JSON from Twitter
		twitterTask.execute(param);

	}
/**
 * 
 * @author Aniruddh
 */
	class TwitterSyncTask extends
			AsyncTask<String, Void, ArrayList<TwitterFeed>> {

		final String MAINSEARCHURL = "http://search.twitter.com/search.json?q=";
		final String EXTRAURL = "&include_entities=true&result_type=mixed&count=5";

		String getUrl(String key) {
			StringBuilder Urlbuilder = new StringBuilder();
			Urlbuilder.append(MAINSEARCHURL).append(key).append(EXTRAURL);
			return Urlbuilder.toString();
		}

		public TwitterSyncTask() {
			super();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(mContext);
			pd.setMessage("Please Wait");
			pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			pd.show();
		}

		@Override
		protected ArrayList<TwitterFeed> doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (!Util.isConnected(mContext)) {
				result = ServerResult.NO_NETWORK_CONNECTION;
				return null;
			}
			int len = params.length;
			if (len == 0) {
				result = ServerResult.INVALID_ENTRY;
				return null;
			}

			ArrayList<TwitterFeed> data = null;
			try {
				// getting response from server
				String response = HttpManager
						.getDataFromServer(getUrl(params[0]));
				// parsing JSON, received from twitter server
				data = TwitterFeed.pasreJSON(response);
				if (data.size() > 0) {
					result = ServerResult.SUCCESS;
				} else {
					result = ServerResult.NO_RESULT_FOUND;
				}
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
		}

		@Override
		protected void onPostExecute(ArrayList<TwitterFeed> result) {
			// TODO Auto-generated method stub
			twitterFeeds = result;
			switch (getResult()) {
			case SUCCESS: {
				// Toast.makeText(mContext, "success",
				// Toast.LENGTH_LONG).show();
				mListAdapter = new TwitterListAdapter(mContext, twitterFeeds);
				mListView.setAdapter(mListAdapter);
			}
				break;

			case NO_RESULT_FOUND: {
				Toast.makeText(mContext, "no Result Found", Toast.LENGTH_LONG)
						.show();
			}
				break;

			case SERVER_CONNECTION_ERROR: {
				Toast.makeText(mContext, "Server Error Occured",
						Toast.LENGTH_LONG).show();
			}
				break;

			case NO_NETWORK_CONNECTION: {
				Toast.makeText(mContext, "No network available",
						Toast.LENGTH_LONG).show();
			}
				break;
			default:
				break;
			}
			pd.dismiss();
			super.onPostExecute(result);
		}
	}

		
}
