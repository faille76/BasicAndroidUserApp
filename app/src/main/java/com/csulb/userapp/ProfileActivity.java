package com.csulb.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csulb.userapp.Entity.User;
import com.csulb.userapp.Repository.SessionHelper;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final SessionHelper sessionHelper = SessionHelper.getInstance(getApplicationContext());
        if (sessionHelper.getUser() == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        User user = sessionHelper.getUser();

        TextView nameValueTextView = findViewById(R.id.profile_name_value_text_view);
        nameValueTextView.setText(user.firstName + " " + user.lastName);

        TextView emailValueTextView = findViewById(R.id.profile_email_value_text_view);
        emailValueTextView.setText(user.email);

        TextView ageValueTextView = findViewById(R.id.profile_age_value_text_view);
        ageValueTextView.setText(Integer.toString(user.age));

        Button logoutBtn = findViewById(R.id.profile_logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sessionHelper.destroySession();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button routeBtn = findViewById(R.id.profile_route_button);
        routeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RouteActivity.class);
                startActivity(intent);
            }
        });
    }
}
