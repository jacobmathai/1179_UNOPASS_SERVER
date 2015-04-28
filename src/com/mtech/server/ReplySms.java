package com.mtech.server;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
  
public class ReplySms extends Activity{
public void sendSMS(String phoneNumber, String message)
    {  PendingIntent pi = PendingIntent.getActivity(this, 0,
            new Intent(this, ServerActivity.class), 0);                
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(phoneNumber, null, message, pi, null);  

}
  
}
