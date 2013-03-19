package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 
 * @author Aniruddh
 * this class having static utility method that will be used in application
 *
 */
public class Util {

	private static NetworkInfo networkInfo;
/**
 * 
 * @param is InputStream
 * @param os Output Stream
 */
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * Is there internet connection
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		networkInfo = connectivity.getActiveNetworkInfo();
		// NetworkInfo info

		if (networkInfo == null || !networkInfo.isConnected()) {
			return false;
		}
		return true;

	}
/**
 * this method will download image from server
 * @param fileUrl URL of the server
 * @return inputstrean
 * @throws IOException
 */
	public static InputStream downloadPhoto(String fileUrl) throws IOException {
		URL myFileUrl = null;

		myFileUrl = new URL(fileUrl);
		System.out.println(myFileUrl);
		HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
		conn.setDoInput(true);

		InputStream is = conn.getInputStream();
		return is;

	}
}
