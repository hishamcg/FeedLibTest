package com.strata.firstmyle_lib.signin_otp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class LibSmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle extras = intent.getExtras();
		if (extras == null)
			return;

		// To display a Toast whenever there is an SMS.
		// Toast.makeText(context,"Recieved",Toast.LENGTH_SHORT).show();

		Object[] pdus = (Object[]) extras.get("pdus");
		for (Object pdu : pdus) {
			SmsMessage SMessage = SmsMessage.createFromPdu((byte[]) pdu);
			String body = SMessage.getMessageBody();
			Intent in = new Intent("SmsMessage.intent.MAIN").putExtra("get_msg", body);
			context.sendBroadcast(in);
		}
	}
}
