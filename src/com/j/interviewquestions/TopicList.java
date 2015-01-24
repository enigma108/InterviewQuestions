package com.j.interviewquestions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TopicList extends ListFragment {

	private static final String TAG = "TOPICLIST";
	private static final boolean SHOW_LOG = true;
	private static final String TOPIC2 = "topic";
	ArrayList<String> topics = null;
	JSONObject myData = null;

	private boolean isLog() {

		return SHOW_LOG;
	}

	public void Log(String msg) {

		if (isLog()) {
			Log.d(TAG, msg);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		Log("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log("onCreate");

		topics = new ArrayList<String>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log("onCreateView");

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		Log("onActivityCreated");

		topics = new ArrayList<String>();
		new DownloadJSONTask().execute();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Log("onStart");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Log("onResume");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		Log("onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		Log("onStop");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

		Log("onDestroyView");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		Log("onDestroy");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();

		Log("onDetach");
	}

	private void handleDataResponse() {

		Log("handleDataResponse");

		String topic = null;

		try {
			JSONArray data = myData.getJSONArray("topics");
			for (int i = 0; i < data.length(); i++) {

				JSONObject jobj = data.getJSONObject(i);
				topic = jobj.getString(TOPIC2);
				topics.add(topic);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class DownloadJSONTask extends AsyncTask<Void, Void, JSONObject> {

		private static final String URL = "http://enigma108.github.io/enigma.github.io/interviewquestions.json";

		@Override
		protected JSONObject doInBackground(Void... arg0) {

			Log("doInBackground");

			JSONObject jsonResponse = null;

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpGet httpGet = new HttpGet(URL);

				HttpResponse response = httpclient.execute(httpGet,
						localContext);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				String line = "";
				StringBuffer sb = new StringBuffer();

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				String result = sb.toString();

				jsonResponse = new JSONObject(result);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);

			Log("onPostExecute");

			myData = result;
			handleDataResponse();

			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, topics));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(getActivity(), topics.get(position), Toast.LENGTH_LONG).show();
	}
}
