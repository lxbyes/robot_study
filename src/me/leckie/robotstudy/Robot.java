package me.leckie.robotstudy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

/**
 * 可以帮你完成学习的机器人<br>
 * 
 * @author Leckie
 * @date 2014年10月25日
 */
public class Robot {

	String some =
	        ";timeout=\"http://login.chinahrt.com/portal/chongqingdaxuesheng/login.jsp\"; __auc=a97e354e14945314df803d030ad; _ga=GA1.2.569341933.1414205099";
	private String loginUrl = "http://passport.chinahrt.com/login.do";
	// 学习的地址
	private String studyUrl = "http://lms.chinahrt.com/pages/playerfiles/lms";

	private String cookie = null;

	private String data1 =
	        "cmi.core.student_id~r@l@ad~79300^r@l@ad^cmi.core.student_name~r@l@ad~612325199008092310^r@l@ad^cmi.core.lesson_location~r@l@ad~";

	private String data2 =
	        "^r@l@ad^cmi.core.credit~r@l@ad~credit^r@l@ad^cmi.core.lesson_status~r@l@ad~incomplete^r@l@ad^cmi.core.entry~r@l@ad~^r@l@ad^cmi.core.score.raw~r@l@ad~^r@l@ad^cmi.core.score.max~r@l@ad~^r@l@ad^cmi.core.score.min~r@l@ad~^r@l@ad^cmi.core.total_time~r@l@ad~00:00:06^r@l@ad^cmi.core.lesson_mode~r@l@ad~normal^r@l@ad^cmi.core.exit~r@l@ad~^r@l@ad^cmi.core.session_time~r@l@ad~00:05:01^r@l@ad^cmi.suspend_data~r@l@ad~";

	private String data3 =
	        "^r@l@ad^cmi.launch_data~r@l@ad~^r@l@ad^cmi.comments~r@l@ad~^r@l@ad^cmi.comments_from_lms~r@l@ad~^r@l@ad^cmi.objectives._count~r@l@ad~0^r@l@ad^cmi.student_data.mastery_score~r@l@ad~^r@l@ad^cmi.student_data.max_time_allowed~r@l@ad~^r@l@ad^cmi.student_data.time_limit_action~r@l@ad~^r@l@ad^cmi.student_preference.audio~r@l@ad~0^r@l@ad^cmi.student_preference.language~r@l@ad~^r@l@ad^cmi.student_preference.speed~r@l@ad~0^r@l@ad^cmi.student_preference.text~r@l@ad~0^r@l@ad^cmi.interactions._count~r@l@ad~0^r@l@ad^";

	List< String > chapterSelectUrls = new ArrayList< String >();

	List< List< String > > timeInfos = new ArrayList< List< String >>();

	/**
	 * 初始化<br>
	 */
	public Robot() {
		init();
	}

	/**
	 * 初始化操作<br>
	 */
	public void init() {
		initChapterInfo();
		initTimeInfo();
	}

	/**
	 * 初始化章url信息<br>
	 */
	public void initChapterInfo() {
		File file = null;
		FileInputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			file = new File("chapter.txt");
			is = new FileInputStream(file);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = "";
			while (( line = br.readLine() ) != null) {
				chapterSelectUrls.add(line);
			}
			System.out.println("--init chapterInfo completed--");
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化时间信息<br>
	 */
	public void initTimeInfo() {
		File file = null;
		FileInputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			file = new File("time.txt");
			is = new FileInputStream(file);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = "";
			while (( line = br.readLine() ) != null) {
				String[] times = line.split(",");
				List< String > timeList = new ArrayList< String >();
				for (int i = 0; i < times.length; i++) {
					timeList.add(convertTime(times[ i ].trim()));
				}
				timeInfos.add(timeList);
			}
			System.out.println("--init timeInfo completed--");
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转换时间<br>
	 * 00:10:23-->600.23
	 * 
	 * @param str
	 * @return
	 */
	public static String convertTime(String str) {
		if (str == null || "".equals(str.trim())) {
			return "0.0";
		}
		String[] strs = str.split(":");
		if (strs == null || strs.length != 3) {
			return "0.0";
		}
		double tmp = 0.0;
		tmp =
		        Double.parseDouble(strs[ 0 ]) * 60 * 60 + Double.parseDouble(strs[ 1 ]) * 60
		                + Double.parseDouble(strs[ 2 ]);// 在时间上可以适当优化
		return String.valueOf(tmp);
	}

	/**
	 * 进行学习
	 * 
	 * @param userName
	 * @param password
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void study(String userName, String password) throws ClientProtocolException, IOException {
		login(userName, password);
		for (int i = 0; i < chapterSelectUrls.size(); i++) {
			System.out.println("--start chapter:" + i + "--");
			boolean result = selectChapter(chapterSelectUrls.get(i));
			if (result) {
				System.out.println("--success select:" + i + "--");
			} else {
				System.err.println("--failed select:" + i + "--");
			}
			studyChapter(timeInfos.get(i));
		}
	}

	/**
	 * 选择章<br>
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public boolean selectChapter(String chapterUrl) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientFactory.getHttpClient();
		HttpGet get = new HttpGet(chapterUrl);
		get.setHeader("Cookie", cookie);// 设置Cookie通过认证
		HttpResponse response = client.execute(get);
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() == 200) {
			return true;
		}
		return false;
	}

	/**
	 * 学习章<br>
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void studyChapter(List< String > timeList) throws ClientProtocolException, IOException {
		// 循环学习每一小节
		for (int i = 0; i < timeList.size(); i++) {
			System.out.println("--start section:" + i + "--");
			int result = studySection(timeList.get(i), i);
			while (result != 200) {
				result = studySection(timeList.get(i), i);
			}
			System.out.println("--success:" + i + "--:" + result + "--");
		}
	}

	/**
	 * 学习一小节<br>
	 * 
	 * @param time
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public int studySection(String time, int item) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientFactory.getHttpClient();
		HttpPost post = new HttpPost(studyUrl);

		post.setHeader("Cookie", cookie);

		NameValuePair nextAction = new BasicNameValuePair("nextAction", "none");
		NameValuePair itemID = new BasicNameValuePair("itemID", String.valueOf(item));
		NameValuePair lmsAction = new BasicNameValuePair("lmsAction", "update");
		String dataStr = data1 + time + data2 + time + data3;
		NameValuePair data = new BasicNameValuePair("data", dataStr);

		List< NameValuePair > params = new ArrayList< NameValuePair >();
		params.add(nextAction);
		params.add(itemID);
		params.add(lmsAction);
		params.add(data);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		StatusLine statusLine = response.getStatusLine();
		System.out.println(statusLine.getReasonPhrase());
		return statusLine.getStatusCode();
	}

	/**
	 * 登录<br>
	 * 
	 * @param userName
	 * @param password
	 * @return 登录成功后被认证的sessionId
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public boolean login(String userName, String password) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientFactory.getHttpClient();
		HttpPost post = new HttpPost(loginUrl);

		NameValuePair loginName = new BasicNameValuePair("loginName", userName);
		NameValuePair loginPwd = new BasicNameValuePair("loginPwd", password);

		List< NameValuePair > params = new ArrayList< NameValuePair >();
		params.add(loginName);
		params.add(loginPwd);

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() == 200) {
			String setCookie = response.getFirstHeader("Set-Cookie").getValue();
			setCookie = setCookie.substring(0, setCookie.indexOf(';'));
			this.cookie = setCookie + some;
			System.out.println("--login success--");
			return true;
		}
		System.err.println("--login failed--");
		return false;
	}
}
