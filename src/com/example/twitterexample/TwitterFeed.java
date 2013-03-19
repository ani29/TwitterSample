package com.example.twitterexample;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Aniruddh
 * this class is wrapper class for Tweets Data
 *
 */
public class TwitterFeed {

	public String userName; //user name of Twitter user
	public String thumbURL; // profile Pic URL of user
	public String tweet;  // Tweet text
/**
 * 
 * @param data a String value to be parsed
 * @return ArrayList of Twitterfeed item, which we will use to show tweets
 */
	public static ArrayList<TwitterFeed> pasreJSON(String data) {
		ArrayList<TwitterFeed> twitterFeeds = new ArrayList<TwitterFeed>();
		JSONObject jObject;
		try {
			jObject = new JSONObject(data);
			JSONArray results = jObject.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				TwitterFeed feed = new TwitterFeed();
				JSONObject result = results.getJSONObject(i);
				feed.userName = result.getString("from_user");
				feed.thumbURL = result.getString("profile_image_url");
				feed.tweet = result.getString("text");
				twitterFeeds.add(feed);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return twitterFeeds;

	}
}
