package com.login.beachstop.android.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.login.beachstop.android.model.ServerResponse;

public class HttpUtil {

	private int TIMEOUT = 30000;

	@SuppressWarnings("null")
	public ServerResponse getJSONFromURLPost(String url, List<NameValuePair> nameValuePairs) {
		try {

			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
			HttpProtocolParams.setContentCharset(httpParameters, HTTP.ISO_8859_1);
			HttpProtocolParams.setHttpElementCharset(httpParameters, HTTP.ISO_8859_1);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			HttpPost httppost = new HttpPost(url);			

			if (nameValuePairs != null || nameValuePairs.size() != 0) {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.ISO_8859_1));
			}

			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() != ServerResponse.SC_OK) {
				if (response.getStatusLine().getStatusCode() == ServerResponse.SC_PRECONDITION_FAILED) {
					HttpEntity entity = response.getEntity();
					ServerResponse serverResponse = new ServerResponse(response.getStatusLine().getStatusCode(), null);
					serverResponse.setMsgPreConditionFail(EntityUtils.toString(entity, HTTP.UTF_8));
					return serverResponse;
				}
				return new ServerResponse(response.getStatusLine().getStatusCode(), null);
			} else {
				return new ServerResponse(response.getStatusLine().getStatusCode(), read(response));
			}

		} catch (Exception e) {
			return new ServerResponse(0, null);
		}

	}

	@SuppressWarnings("null")
	public ServerResponse getJSONFromURLPut(String url, List<NameValuePair> nameValuePairs) {
		try {

			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
			HttpProtocolParams.setContentCharset(httpParameters, HTTP.ISO_8859_1);
			HttpProtocolParams.setHttpElementCharset(httpParameters, HTTP.ISO_8859_1);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			HttpPut httpput = new HttpPut(url);

			if (nameValuePairs != null || nameValuePairs.size() != 0) {
				httpput.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.ISO_8859_1));
			}

			HttpResponse response = httpclient.execute(httpput);

			if (response.getStatusLine().getStatusCode() != ServerResponse.SC_OK) {
				if (response.getStatusLine().getStatusCode() == ServerResponse.SC_PRECONDITION_FAILED) {
					HttpEntity entity = response.getEntity();
					ServerResponse serverResponse = new ServerResponse(response.getStatusLine().getStatusCode(), null);
					serverResponse.setMsgPreConditionFail(EntityUtils.toString(entity, HTTP.UTF_8));
					return serverResponse;
				}
				return new ServerResponse(response.getStatusLine().getStatusCode(), null);
			} else {
				return new ServerResponse(response.getStatusLine().getStatusCode(), read(response));
			}

		} catch (Exception e) {
			return new ServerResponse(0, null);
		}

	}

	public ServerResponse getJSONFromURL(String url) {
		try {
			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			HttpGet requisicao = new HttpGet(url);

			HttpResponse response;

			response = httpclient.execute(requisicao);

			if (response.getStatusLine().getStatusCode() != ServerResponse.SC_OK) {
				if (response.getStatusLine().getStatusCode() == ServerResponse.SC_PRECONDITION_FAILED) {
					HttpEntity entity1 = response.getEntity();
					ServerResponse serverResponse = new ServerResponse(response.getStatusLine().getStatusCode(), null);
					serverResponse.setMsgPreConditionFail(EntityUtils.toString(entity1, HTTP.UTF_8));
					return serverResponse;
				}
				return new ServerResponse(response.getStatusLine().getStatusCode(), null);
			} else {
				return new ServerResponse(response.getStatusLine().getStatusCode(), read(response));
			}

		} catch (Exception e) {
			return new ServerResponse(0, null);
		}
	}

	private JSONObject read(HttpResponse response) {

		JSONObject jsonObject = null;

		try {

			StringBuilder builder = new StringBuilder();
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			jsonObject = readJson(builder);

			if (jsonObject == null) {
				return readJsonByArray(builder);
			}
			return jsonObject;

		} catch (Exception ex) {
			return null;
		}
	}

	private JSONObject readJson(StringBuilder builder) {
		try {
			return new JSONObject(builder.toString());
		} catch (Exception ex) {
			return null;
		}
	}

	private JSONObject readJsonByArray(StringBuilder builder) {
		JSONObject jsonObject = new JSONObject();
		try {
			return jsonObject.put("", new JSONArray(builder.toString()));

		} catch (Exception ex) {
			return null;
		}
	}
}
