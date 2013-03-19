package com.example.twitterexample;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;


/**
 * General HTTP handler class which will used to to HTTP transaction
 * @author Aniruddh
 *
 */
public final class HttpManager {
	private HttpManager() {}
	private final static String TAG = "HttpManager";

	

	//private final static String POST_MIME_TYPE_FILE = "multipart/form-data";

	public static String getDataFromServer (String Url) throws ClientProtocolException,
	SocketTimeoutException,ConnectTimeoutException,UnknownHostException, IOException
	{

		HttpClient httpClient;

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,15000);

		// Set the default socket timeout
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters,25000);

		httpClient = new DefaultHttpClient(httpParameters);
		StringBuilder uriBuilder = new StringBuilder(Url);

		StringBuffer sbResponse = new StringBuffer ();
		HttpResponse response;

		
		HttpGet request = new HttpGet(uriBuilder.toString());
		try
		{
			response = httpClient.execute(request);
		}catch (UnknownHostException e){
			throw e;
		}catch (SocketTimeoutException e)
		{
			throw e;
		}
		catch (ConnectTimeoutException e)
		{
			Log.e(TAG, "HTTPMANAGER CONNECTION TIMEOUT");
			throw e;
		}


		int status = response.getStatusLine().getStatusCode();

		// we assume that the response body contains the error message
		if (status == HttpStatus.SC_OK) {
			InputStream content = response.getEntity().getContent();
			// <consume response>			
			BufferedReader reader = new BufferedReader (new InputStreamReader(content));
			String line;			
			while ((line = reader.readLine())!=null)
			{
				sbResponse.append(line);
			}			
			content.close(); // this will also close the connection		
			return sbResponse.toString();
		} else {
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			response.getEntity().writeTo(ostream);
			Log.e("HTTP CLIENT", ostream.toString());			
			return null;
		}
	}
}
