package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Login {
	private String username;
	private String password;

	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void login() throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = makeLoginPost();
		HttpResponse response = client.execute(post);

		HttpEntity entity = response.getEntity();
		printInputStreamContent(entity.getContent());
	}

	private HttpPost makeLoginPost() throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost("http://p.nju.edu.cn/portal_io/login");
		httpPost.setEntity(makePostEntiry());
		httpPost.addHeader("Referer", "http://p.nju.edu.cn/portal_io/login");

		return httpPost;
	}

	private HttpEntity makePostEntiry() throws UnsupportedEncodingException {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));

		return new UrlEncodedFormEntity(params, "UTF-8");
	}

	private void printInputStreamContent(InputStream istream) throws IOException {
		char[] buffer = new char[1024];

		Reader reader = new InputStreamReader(istream, "UTF-8");
		StringBuilder sb = new StringBuilder();

		int read;
		while ((read = reader.read(buffer, 0, buffer.length)) >= 0) {
			sb.append(buffer, 0, read);
		}

		System.out.println(sb.toString());
	}
}
