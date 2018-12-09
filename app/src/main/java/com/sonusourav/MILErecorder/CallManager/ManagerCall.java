package com.sonusourav.MILErecorder.CallManager;


import android.content.Context;

import com.sonusourav.MILErecorder.CallRecorder.Recorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManagerCall extends CallReceiver {

    public static Recorder recorder;

    @Override
    public void onIncomingCallStarted(Context context, String number, Date startTime) {
        String date1 = new SimpleDateFormat("dd MMM yy h:mm a", Locale.US).format(startTime.getTime());
        recorder = new Recorder(number + "_" +  date1);
        recorder.setRecord(true);
    }

    @Override
    public void onOutgoingCallStarted(Context context, String number, Date startTime) {
        String date2 = new SimpleDateFormat("dd MMM yy h:mm a", Locale.US).format(startTime.getTime());
        recorder = new Recorder(number + "_" + date2);
        recorder.setRecord(true);
    }

    @Override
    public void onIncomingCallEnded(Context context, String number, Date startTime, Date endTime) {
        recorder.setRecord(false);
    }

    @Override
    public void onOutgoingCallEnded(Context context, String number, Date startTime, Date endTime) {
         recorder.setRecord(false);
    }

    @Override
    public void onMissedCall(Context context, String number, Date startTime) {

    }
}
