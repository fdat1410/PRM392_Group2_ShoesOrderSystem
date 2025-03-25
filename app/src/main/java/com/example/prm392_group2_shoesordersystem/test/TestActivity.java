package com.example.prm392_group2_shoesordersystem.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.EmailSender;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnSendEmail = findViewById(R.id.btnOpen);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTestEmail();
            }
        });
    }

    private void sendTestEmail() {
        new SendEmailTask().execute();
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                EmailSender m = new EmailSender();
                m.sendEmail("nam", "NEW PASSWORD: ", "dsạbdạb");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {

            super.onPostExecute(success);
        }
    }
}
