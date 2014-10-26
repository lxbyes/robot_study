package me.leckie.robotstudy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author Leckie
 * @date 2014年10月25日
 */
@SuppressWarnings( "deprecation" )
public class HttpSend {

	/*
	 * @SuppressWarnings( "resource" ) public static void main(String[] args) {
	 * try { HttpClient client = new DefaultHttpClient(); String url = //
	 * "http://passport.chinahrt.com/studentLogin.jsp?redirect=http%3A%2F%2Flms.chinahrt.com%3A8180%2Fcourse%2FlmsHeart_json.do%3FuserId%3D79398%26JSESSIONID%3D8BC1759236A91F32C5392CB106CD4E43%26timeout%3Dhttp%3A%2F%2Flogin.chinahrt.com%2Fportal%2Fchongqingdaxuesheng%2Flogin.jsp%26__auc%3Da97e354e14945314df803d030ad%26_ga%3DGA1.2.569341933.1414205099"
	 * ; "http://lms.chinahrt.com/course/lmsHeart_json.do?userId=79398"; HttpGet
	 * get = new HttpGet(url); get.setHeader( "Cookie",
	 * "JSESSIONID=8BC1759236A91F32C5392CB106CD4E43; timeout=\"http://login.chinahrt.com/portal/chongqingdaxuesheng/login.jsp\"; __auc=a97e354e14945314df803d030ad; _ga=GA1.2.569341933.1414205099"
	 * ); for (int i = 0; i < 10000; i++) { HttpResponse response =
	 * client.execute(get); HttpEntity entity = response.getEntity();
	 * InputStream is = entity.getContent(); InputStreamReader isr = new
	 * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
	 * String line = ""; while (( line = br.readLine() ) != null) {
	 * System.out.println(line); } } } catch ( Exception e ) {
	 * e.printStackTrace(); } }
	 */

	@SuppressWarnings( "resource" )
	public static void main(String[] args) {
		selectChapter();
		try {
			HttpClient client = new DefaultHttpClient();
			String url = "http://lms.chinahrt.com/pages/playerfiles/lms";
			String cookie = "JSESSIONID=8BC1759236A91F32C5392CB106CD4E43";
			HttpPost post = new HttpPost(url);
			post.setHeader("Cookie", cookie);

			String dataStr =
			        "cmi.core.student_id~r@l@ad~79398^r@l@ad^cmi.core.student_name~r@l@ad~511523199009051730^r@l@ad^cmi.core.lesson_location~r@l@ad~"
			                + "120.26^r@l@ad^cmi.core.credit~r@l@ad~credit^r@l@ad^cmi.core.lesson_status~r@l@ad~incomplete^r@l@ad^cmi.core.entry~r@l@ad~^r@l@ad^cmi.core.score.raw~r@l@ad~^r@l@ad^cmi.core.score.max~r@l@ad~^r@l@ad^cmi.core.score.min~r@l@ad~^r@l@ad^cmi.core.total_time~r@l@ad~00:00:06^r@l@ad^cmi.core.lesson_mode~r@l@ad~normal^r@l@ad^cmi.core.exit~r@l@ad~^r@l@ad^cmi.core.session_time~r@l@ad~00:05:01^r@l@ad^cmi.suspend_data~r@l@ad~"
			                + "120.26^r@l@ad^cmi.launch_data~r@l@ad~^r@l@ad^cmi.comments~r@l@ad~^r@l@ad^cmi.comments_from_lms~r@l@ad~^r@l@ad^cmi.objectives._count~r@l@ad~0^r@l@ad^cmi.student_data.mastery_score~r@l@ad~^r@l@ad^cmi.student_data.max_time_allowed~r@l@ad~^r@l@ad^cmi.student_data.time_limit_action~r@l@ad~^r@l@ad^cmi.student_preference.audio~r@l@ad~0^r@l@ad^cmi.student_preference.language~r@l@ad~^r@l@ad^cmi.student_preference.speed~r@l@ad~0^r@l@ad^cmi.student_preference.text~r@l@ad~0^r@l@ad^cmi.interactions._count~r@l@ad~0^r@l@ad^";
			NameValuePair nextAction = new BasicNameValuePair("nextAction", "none");
			NameValuePair itemID = new BasicNameValuePair("itemID", "0");
			NameValuePair lmsAction = new BasicNameValuePair("lmsAction", "update");
			NameValuePair data = new BasicNameValuePair("data", dataStr);

			List< NameValuePair > params = new ArrayList< NameValuePair >();
			params.add(nextAction);
			params.add(itemID);
			params.add(lmsAction);
			params.add(data);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(entity);

			HttpResponse response = client.execute(post);

			HttpEntity resultEntity = response.getEntity();

			InputStream is = resultEntity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line = "";
			System.out.println("************************************************************************************");
			while (( line = br.readLine() ) != null) {
				System.out.println(line);
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void selectChapter() {
		try {
			HttpClient client = new DefaultHttpClient();
			String url =
			        "http://lms.chinahrt.com/Launch?scormPackageID=5b3c6ef4-ff4d-4b45-8c07-e6961b61e0e8&studyProgress=79.0&planType=1&exit=http://student.chinahrt.com/plan/plan_detail.do?planId=df018af7-d64b-4e70-a1db-18f3a8a3af86";
			String cookie = "JSESSIONID=8BC1759236A91F32C5392CB106CD4E43";
			HttpGet get = new HttpGet(url);
			get.setHeader("Cookie", cookie);

			HttpResponse response = client.execute(get);

			HttpEntity resultEntity = response.getEntity();

			InputStream is = resultEntity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line = "";
			System.out.println("************************************************************************************");
			while (( line = br.readLine() ) != null) {
				System.out.println(line);
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
