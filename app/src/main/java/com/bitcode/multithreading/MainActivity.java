package com.bitcode.multithreading;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new BtnDownloadClickListener());
    }

    private class BtnDownloadClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String [] fileUrls = {
                    "https://bitcode.in/android-course-details.pdf",
                    "https://bitcode.in/c-course-details.pdf",
                    "https://bitcode.in/ios-course-details.pdf",
            };

            new DownloadThread().execute(fileUrls);
        }
    }

    private class DownloadThread extends AsyncTask<String,Integer,Float>{
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Bitcode");
            progressDialog.setMessage("Fetch Data");
           // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String... fileUrls) {
            for (String fileUrl : fileUrls){
                progressDialog.setMessage("Downloading"+fileUrl);
                for(int i = 0;i <= 100;i++) {
                    Log.e("tag", fileUrl + "" + i + " % ");

                    Integer[] progress = new Integer[1];
                    progress[0] = i;
                    publishProgress(progress);
                    progressDialog.setProgress(i);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return 12.21F;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnDownload.setText("progress" + values[0]);
        }

        @Override
        protected void onPostExecute(Float result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            btnDownload.setText("Result" + result);
        }
    }
}
//study - what is a process, thread,why multithreading, Thread class, Runnable interface?