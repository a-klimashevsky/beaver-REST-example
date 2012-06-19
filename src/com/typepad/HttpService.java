package com.typepad;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class for communication with services
 * 
 * @author Alex Klimashevsky
 * 
 */
public class HttpService {
	
	public enum HttpMethod{GET,POST,PUT,DELETE}

	/**
	 * logger
	 */
	private static final Logger mLogger = new Logger(HttpService.class);

	/**
	 * return json from service method
	 * 
	 * @param url
	 *            url of method
	 * @param params
	 *            post params if method is POST, else null
	 * @return string represent when json
	 */
	public static String sendRequest(String host, String path,HttpMethod method, Map<String,Object> params)
			{
		HttpClient client = new DefaultHttpClient();
		HttpRequestBase request = getRequestMethod(method,host,path);
		if (params != null) {
			try {
				JSONObject jsonObject = new JSONObject();
				for (int i = 0; i < params.size(); i++) {
					NameValuePair nameValuePair = params.get(i);
					try {
						jsonObject.put(nameValuePair.getName(),
								nameValuePair.getValue());
					} catch (JSONException e) {
						mLogger.error(e);
					}
				}
				String jsonString = jsonObject.toString();
				StringEntity stringEntity = new StringEntity(jsonString,
						HTTP.UTF_8);
				stringEntity.setContentType("json");
				((HttpPost) request).addHeader("Content-Type",
						"application/json;charset=UTF-8");
				((HttpPost) request).setEntity(stringEntity);
			} catch (UnsupportedEncodingException e) {
				mLogger.error(e);
			}
		}
		BasicHttpResponse response = null;
		try {
			response = (BasicHttpResponse) client.execute(request);
		} catch (ClientProtocolException e) {
			mLogger.error(e);
		} catch (IOException e) {
			mLogger.error(e);
		}
		int statusCode = response.getStatusLine().getStatusCode();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			response.getEntity().writeTo(os);
		} catch (IOException e) {
			mLogger.error(e);
		}

		String result = os.toString();
		
		return result;
	}

	/**
	 * return actual http method wrapper
	 * 
	 * @param isPost
	 *            indicate than we need a POST wrapper
	 * @param url
	 *            url of method
	 * @return HTTP POST or HTTP GET wrapper
	 */
	private static HttpRequestBase getRequestMethod(HttpMethod method, String host,String path) {
		if (!path.startsWith("/")) {
			path = "/" + path;
        }
		String url = host + path;
		switch (method) {
		case GET:
			return new HttpGet(url);
		case POST:
			return new HttpPost(url);
		case DELETE:
			return new HttpDelete(url);
		case PUT:
			return new HttpPut(url);
		default:
			break;
		}
		return new HttpGet(url);
	}

	
}
