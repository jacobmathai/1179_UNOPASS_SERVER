package com.mtech.server;



import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.widget.EditText;
import android.widget.Toast;

public class ServerActivity extends Activity {
    /** Called when the activity is first created. */
	static EditText txtmob;
	static EditText txtmsg;
	@Override
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txtmob=(EditText)findViewById(R.id.editText1);
        txtmsg=(EditText)findViewById(R.id.editText2);
     // new ServerActivity().sendSMS("4334343","ere");
       
          //---display the new SMS message---
    
        
	}

}