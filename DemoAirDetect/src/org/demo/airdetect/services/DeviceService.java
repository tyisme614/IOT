package org.demo.airdetect.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.demo.airdetect.models.Sensor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.graphics.Bitmap;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 
 * @author Stevens
 *	This service includes all methods used for interacting with remote server
 */
public class DeviceService extends Service {

	/*
	 * constants
	 */
	//url path
	private static final String LOG_TAG = "DeviceService";
	private static final String URL_LOGIN = "/sensorhub/rs/user/login?";
	private static final String URL_SIGNUP = "/sensorhub/rs/user/register?";
	private static final String URL_DEVICE_SIGNUP = "/sensorhub/rs/device/registerWithOutUserName?";
	private static final String URL_DEVICE_SIGNUP_USER = "/sensorhub/rs/device/registerWithUserName?";
	private static final String URL_COMMENT_POST = "/sensorhub/rs/article/postComment?";
	private static final String URL_COMMENT_CHECKOUT = "/sensorhub/rs/article/list?";
	private static final String URL_PULISH = "/sensorhub/rs/article/upload?";
	//protocols
	private static final String CMD_SCAN = "scan";
	//broadcasts
	public static final String ACTION_LOGIN_SUCC = "action.login.success";
	public static final String ACTION_LOGIN_FAIL = "action.login.fail";
	public static final String ACTION_REGISTER = "action.register";
	public static final String ACTION_COMMENT_POST = "action.post";
	public static final String ACTION_COMMENT_CHECK = "action.check";
	public static final String ACTION_COMMENT_PUBLISH  ="action.publish";
	//extras
	public static final String EXTRA_RESULT = "extra.result";
	public static final String EXTRA_PWD = "extra.password";
	public static final String EXTRA_CREDITS = "extra.credits";
	public static final String EXTRA_NICK = "extra.nick";
	public static final String EXTRA_ID = "extra.id";
	public static final String EXTRA_USERNAME = "extra.username";
	public static final String EXTRA_MESSAGE = "extra.message";
	
	//debug
	private static final String SERVER_IP = "119.254.103.80";
	private static final String SERVER_PORT = "9080";
	
	
	/*
	 * 
	 *Local members
	 */
	private static DeviceService singleton = null;
	private Thread loginThread = null;
	private Thread scanThread = null;
	private Thread udpListenerThread = null;
	private boolean udpStarted = false;
	private Thread tcpThread = null;
	private DatagramSocket udpSocketSend = null;
	private DatagramSocket udpSocketRev = null;
	private Socket tcpSocket = null;
	
	private LocalBroadcastManager lbMgr = null;
	
	//constructor
	public DeviceService(){
		super();
		lbMgr = LocalBroadcastManager.getInstance(this);
	}
	
	public static DeviceService getInstance(){
		if(singleton == null){
			singleton = new DeviceService();
		}
		
		return singleton;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Service Lifycycle
	 */
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		startListenUDP();
		startScan();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
	}
	
	/*
	 * local methods
	 */	
	
	/**
	 * startScan
	 */
	public void startScan(){
		scanThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int scanCount = 20;
				try {
					udpSocketSend = new DatagramSocket();
					udpSocketSend.setBroadcast(true);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//construct broadcast address
				WifiManager wifi = (WifiManager) DeviceService.this.getSystemService(Context.WIFI_SERVICE);
				DhcpInfo dhcp = wifi.getDhcpInfo();
				int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
				byte[] quads = new byte[4];
				for(int i =0; i<4; i++){
					quads[i] = (byte) ((broadcast>>i*8)&0xFF);
				}
				InetAddress address = null;
				try {
					address = InetAddress.getByAddress(quads);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DatagramPacket pkt = new DatagramPacket(CMD_SCAN.getBytes(), CMD_SCAN.getBytes().length, address, 2525);
				while( scanCount > 0) {
					
					try {
						udpSocketSend.send(pkt);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					scanCount--;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//end of while
			
			}});//end of thread
		scanThread.start();
	}//end of startScan
	
	public void startListenUDP(){
		udpStarted = true;
		udpListenerThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					udpSocketRev = new DatagramSocket(2526);
				
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] buffer = new byte[1024];
				while(udpStarted){
					DatagramPacket pkt = new DatagramPacket( buffer, buffer.length);
					try {
						udpSocketRev.receive(pkt);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					byte[] data = pkt.getData();
					String dataStr = new String(data);
					String[] subStr = dataStr.split(",");
					String deviceID = subStr[0].split(":")[1];
					String IP = subStr[1].split(":")[1];
					String SensorID = subStr[2].split(":")[1];
					
				}
			}});
		
		udpListenerThread.start();
	}
	
	/**
	 * Register
	 * @param user
	 * @param pwd
	 * @param nick
	 */
	public void Register(final String user, final String pwd, final String nick){
		//replaced by sharedPreferences in the future
		new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String server_ip = SERVER_IP;
				String server_port = SERVER_PORT;
				HttpClient httpclient = new DefaultHttpClient();
				StringBuffer sb_url = new StringBuffer();
				sb_url.append("http://")
				.append(server_ip)
				.append(":")
				.append(server_port)
				.append(URL_SIGNUP)
				.append("userName=" + user )
				.append("&")
				.append("password=" + pwd)
				.append("&")
				.append("nickName=" + nick);
				HttpPost post = new HttpPost();
				try {
					URI uri = new URI(sb_url.toString());
					post.setURI(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpResponse response;
				try {
					response = httpclient.execute(post);
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						String json = "";
						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject jsonObj = new JSONObject(json);
						boolean result =  jsonObj.getBoolean("success");
						String message= jsonObj.getString("message");
						if( lbMgr != null ){
							Intent i = new Intent( ACTION_REGISTER );
							i.putExtra(EXTRA_RESULT, result);
							i.putExtra(EXTRA_MESSAGE, message);
							lbMgr.sendBroadcast(i);
						}
					
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}).start();
			
	}//end of Register


	/**
	 * Login
	 * @param user
	 * @param pwd
	 */
	public void Login(final String user, final String pwd){
		//replaced by sharedPreferences in the future
		loginThread  = new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String server_ip = SERVER_IP;
				String server_port = SERVER_PORT;
				HttpClient httpclient = new DefaultHttpClient();
				StringBuffer sb_url = new StringBuffer();
				sb_url.append("http://")
				.append(server_ip)
				.append(":")
				.append(server_port)
				.append(URL_LOGIN)
				.append("userName=" + user )
				.append("&")
				.append("password=" + pwd);
				HttpGet get = new HttpGet();
				try {
					URI uri = new URI(sb_url.toString());
					get.setURI(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpResponse response;
				try {
					response = httpclient.execute(get);
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						String json = "";
						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject jsonObj = new JSONObject(json);
						if( jsonObj.getBoolean("success") ){
							JSONObject message = jsonObj.getJSONObject("message");
							if( message != null ){
								String pwd = message.getString("password");
								int credits = message.getInt("credits");
								String nick = message.getString("nickName");
								int id = message.getInt("id");
								String userName = message.getString("username");
								if( lbMgr != null ){
									Intent i = new Intent( ACTION_LOGIN_SUCC );
									i.putExtra(EXTRA_PWD, pwd);
									i.putExtra(EXTRA_CREDITS, credits);
									i.putExtra(EXTRA_NICK, nick);
									i.putExtra(EXTRA_ID, id);
									i.putExtra(EXTRA_USERNAME, userName);
									lbMgr.sendBroadcast(i);
								}
							}
							}else{
								JSONObject message = jsonObj.getJSONObject("message");
								if( message != null ){
									String msg = message.getString(EXTRA_MESSAGE);
									Intent i = new Intent( ACTION_LOGIN_FAIL );
									i.putExtra(EXTRA_MESSAGE, msg);
									lbMgr.sendBroadcast(i);
								}
						}
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}});
		
		loginThread.start();
		
	}//end of Login
	
	
	/**
	 * RegisterDevice
	 * @param withID
	 * @param userID
	 * @param dev_uuids
	 * @param dev_uuid
	 * @param typeCode
	 * @param sensors
	 */
	public void RegisterDevice(final boolean withID, final String userID, final List<String> dev_uuids, final String dev_uuid, final String typeCode, final List<Sensor> sensors){
		//replaced by sharedPreferences in the future
		new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String server_ip = SERVER_IP;
				String server_port = SERVER_PORT;
				HttpClient httpclient = new DefaultHttpClient();
				StringBuffer sb_url = new StringBuffer();
				if(withID){
					sb_url.append("http://")
					.append(server_ip)
					.append(":")
					.append(server_port)
					.append(URL_DEVICE_SIGNUP_USER)
					.append("userID=" + userID )
					.append("&");
					for(int i = 0; i<dev_uuids.size(); i++){
						String item = dev_uuids.get(i);
						sb_url.append(item);
						if( i != dev_uuids.size() -1 )
							sb_url.append(",");						
					}
				}else{
					sb_url.append("http://")
					.append(server_ip)
					.append(":")
					.append(server_port)
					.append(URL_DEVICE_SIGNUP)
					.append("dev_uuid=" + dev_uuid )
					.append("&")
					.append("typeCode=" + typeCode )
					.append("&");					
					for(int i = 0; i<sensors.size(); i++){
						Sensor item = sensors.get(i);
						sb_url.append(item.getType());
						sb_url.append(",");
						sb_url.append(item.getUuid());
						sb_url.append(";");
					}
				}
				
				HttpPost post = new HttpPost();
				try {
					URI uri = new URI(sb_url.toString());
					post.setURI(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpResponse response;
				try {
					response = httpclient.execute(post);
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						String json = "";
						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject jsonObj = new JSONObject(json);
						boolean result =  jsonObj.getBoolean("success");
						String message= jsonObj.getString("message");
						if( lbMgr != null ){
							Intent i = new Intent( ACTION_REGISTER );
							i.putExtra(EXTRA_RESULT, result);
							i.putExtra(EXTRA_MESSAGE, message);
							lbMgr.sendBroadcast(i);
						}
					
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}).start();
			
	}//end of RegisterDevice
	
	/**
	 * PostComment
	 * @param articleID
	 * @param msg
	 * @param userID
	 * @param img
	 * @param cID
	 */
	public void PostComment(final String articleID, final String msg, final String userID, final Bitmap img, final String cID){
		//replaced by sharedPreferences in the future
		new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String server_ip = SERVER_IP;
				String server_port = SERVER_PORT;
				HttpClient httpclient = new DefaultHttpClient();
				StringBuffer sb_url = new StringBuffer();
				sb_url.append("http://")
				.append(server_ip)
				.append(":")
				.append(server_port)
				.append(URL_COMMENT_POST)
				.append("articleId=" + articleID )
				.append("&")
				.append("message=" + msg)
				.append("&")
				.append("userId=" + userID)
				.append("&")
				.append("file=" + img.toString())
				.append("&")
				.append("cId=" + cID);
				HttpPost post = new HttpPost();
				try {
					URI uri = new URI(sb_url.toString());
					post.setURI(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpResponse response;
				try {
					response = httpclient.execute(post);
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						String json = "";
						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject jsonObj = new JSONObject(json);
						boolean result =  jsonObj.getBoolean("success");
						String message= jsonObj.getString("message");
						if( lbMgr != null ){
							Intent i = new Intent( ACTION_COMMENT_POST );
							i.putExtra(EXTRA_RESULT, result);
							i.putExtra(EXTRA_MESSAGE, message);
							lbMgr.sendBroadcast(i);
						}
					
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}).start();
			
	}//end of PostComment
	
	/**
	 * CheckComment
	 * @param articleID
	 * @param userID
	 * @param page
	 * @param rows
	 */
	public void CheckComment(final String articleID,final String userID, final String page, final String rows){
		//replaced by sharedPreferences in the future
		new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String server_ip = SERVER_IP;
				String server_port = SERVER_PORT;
				HttpClient httpclient = new DefaultHttpClient();
				StringBuffer sb_url = new StringBuffer();
				sb_url.append("http://")
				.append(server_ip)
				.append(":")
				.append(server_port)
				.append(URL_COMMENT_CHECKOUT)
				.append("articleId=" + articleID )
				.append("&")				
				.append("userId=" + userID)
				.append("&")
				.append("page=" +page)
				.append("&")
				.append("rows=" + rows);
				HttpGet get = new HttpGet();
				try {
					URI uri = new URI(sb_url.toString());
					get.setURI(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpResponse response;
				try {
					response = httpclient.execute(get);
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						String json = "";
						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject jsonObj = new JSONObject(json);
						boolean result =  jsonObj.getBoolean("success");
						String message= jsonObj.getString("message");
						if( lbMgr != null ){
							Intent i = new Intent( ACTION_COMMENT_CHECK );
							i.putExtra(EXTRA_RESULT, result);
							i.putExtra(EXTRA_MESSAGE, message);
							lbMgr.sendBroadcast(i);
						}
					
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}).start();
			
	}//end of CheckComment
	
	
	public void PublishComment(final String title,final String body, final String file, final String userID){
		//replaced by sharedPreferences in the future
		new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String server_ip = SERVER_IP;
				String server_port = SERVER_PORT;
				HttpClient httpclient = new DefaultHttpClient();
				StringBuffer sb_url = new StringBuffer();
				sb_url.append("http://")
				.append(server_ip)
				.append(":")
				.append(server_port)
				.append(URL_PULISH)
				.append("title=" + title )
				.append("&")				
				.append("body=" + body)
				.append("&")
				.append("file=" +file)
				.append("&")
				.append("userId=" + userID);
				HttpPost post = new HttpPost();
				try {
					URI uri = new URI(sb_url.toString());
					post.setURI(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpResponse response;
				try {
					response = httpclient.execute(post);
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						String json = "";
						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject jsonObj = new JSONObject(json);
						boolean result =  jsonObj.getBoolean("success");
						String message= jsonObj.getString("message");
						if( lbMgr != null ){
							Intent i = new Intent( ACTION_COMMENT_CHECK );
							i.putExtra(EXTRA_RESULT, result);
							i.putExtra(EXTRA_MESSAGE, message);
							lbMgr.sendBroadcast(i);
						}
					
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}).start();
			
	}//end of PublishComment
}
