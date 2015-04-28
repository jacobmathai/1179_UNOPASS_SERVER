package com.mtech.server;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;
import android.app.Activity;
import android.app.PendingIntent;

public class SmsReceiver extends BroadcastReceiver {
	static String reply = "";
	static String s[];
	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {

		this.context = context;
		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		String str1 = "";
		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += msgs[i].getOriginatingAddress();
				str += ",";
				str += msgs[i].getMessageBody().toString();
				str1 += msgs[i].getMessageBody();
				str += "\n";
			}

			System.out.println(str);
			s = str.split(",");
			ServerActivity.txtmob.setText(s[0]);
			System.out.println(s[0]);
			ServerActivity.txtmsg.setText(s[1]);
			System.out.println(s[1]);

			s[1] = str = new AESEncryptor().decrypt(s[1], "AbCdEfGhIjKlMnOp");
			str = s[0] + "," + s[1];
			s = str.split(",");
			String s1 = s[2].trim();
			System.out.println(s1);
			try {
				if (s1.equals("register")) {
					String url = "http://" + ServerConfig.ip
							+ ":8084/bank/opassRegister.jsp?mob="
							+ SmsReceiver.s[0].trim() + "&rand="
							+ SmsReceiver.s[1].trim();

					// Toast.makeText(context,url, Toast.LENGTH_SHORT).show();
					reply = WebClient.getData(url).trim();
					if (reply.trim().equals("success"))
						sendSMS(s[0],
								"u have successfully registered through opass");
					else if (reply.trim().equals("exist")) {
						sendSMS(s[0], "Existing username");
					} else {
						sendSMS(s[0], "Registration failed");
					}
					Toast.makeText(context, reply.trim(), Toast.LENGTH_SHORT)
							.show();
				}
				if (s1.equals("recovery")) {
					String url = "http://" + ServerConfig.ip
							+ ":8084/bank/opassRecovery.jsp?mob="
							+ SmsReceiver.s[0].trim() + "&rand="
							+ SmsReceiver.s[1].trim();
					System.out
							.println(url
									+ ">>>>>>>>>>>>>>>>>recovery>>>>>>>>>>>>>>>>>>>>>>>>");
					// Toast.makeText(context,url, Toast.LENGTH_SHORT).show();
					reply = WebClient.getData(url).trim();
					if (reply.trim().equals("success")) {
						sendSMS(s[0],
								"u have successfully recovered through opass");
					} else {
						sendSMS(s[0], "Recovery failed");
					}
					Toast.makeText(context, reply.trim(), Toast.LENGTH_SHORT)
							.show();
				}
				if (s1.equals("login")) {
					String url = "http://" + ServerConfig.ip
							+ ":8084/bank/loginCheck.jsp?mob=" + s[0].trim()
							+ "&pass=" + s[1].trim();
					reply = WebClient.getData(url).trim();
					Toast.makeText(context, "login", Toast.LENGTH_SHORT).show();
					// sendSMS(s[0], reply);
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		}

		// Toast.makeText(context,str1, Toast.LENGTH_LONG).show();
	}

	public void sendSMS(String phoneNumber, String message) {
		PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(
				context, ServerActivity.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, pi, null);

	}
}
