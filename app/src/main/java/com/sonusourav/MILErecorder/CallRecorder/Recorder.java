package com.sonusourav.MILErecorder.CallRecorder;


import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public class Recorder {

    private MediaRecorder mediaRecorder;
    private String fileName;

    public Recorder(String fileName) {
        this.fileName = fileName;
        initiateMediaRecorder();
    }

    private void initiateMediaRecorder(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(setFileName());
        Log.d("Filename","Reached");
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
            public void onError(MediaRecorder arg0, int arg1, int arg2) {
                Log.e("RecorderError", "OnErrorListener " + arg1 + "," + arg2);
            }
        };
        mediaRecorder.setOnErrorListener(errorListener);

        MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
            public void onInfo(MediaRecorder arg0, int arg1, int arg2) {
                Log.e("RecorderInfoListener", "OnInfoListener " + arg1 + "," + arg2);
            }
        };
        mediaRecorder.setOnInfoListener(infoListener);
    }

    private String setFileName(){
        File folder=Environment.getExternalStoragePublicDirectory("MILE Recorder");
        if(!folder.exists())
            folder.mkdir();
        return folder.getAbsolutePath()+"/"+fileName+".mp3";
    }

    public void setRecord(boolean isPlay){
        if(isPlay){
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();
        }
        else {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }
    }
}
