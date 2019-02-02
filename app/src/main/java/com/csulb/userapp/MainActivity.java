package com.csulb.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csulb.userapp.Entity.User;
import com.csulb.userapp.Repository.UserHelper;
import com.csulb.userapp.Repository.UserRepository;
import com.csulb.userapp.Repository.SessionHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionHelper sessionHelper = SessionHelper.getInstance(getApplicationContext());
        if (sessionHelper.getUser() != null) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }

        Button submitBtn = findViewById(R.id.login_submit_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText userOrEmailEditText = findViewById(R.id.login_user_or_email_edit_text);
                EditText passwordEditText = findViewById(R.id.login_password_edit_text);


                UserRepository userRepository = UserRepository.getInstance(getApplicationContext());
                User user = userRepository.getUserByEmail(userOrEmailEditText.getText().toString());
                if (user == null) {
                    user = userRepository.getUserByUsername(userOrEmailEditText.getText().toString());
                }
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Email was not found.", Toast.LENGTH_LONG).show();
                } else {
                    if (UserHelper.getHashedPassword(passwordEditText.getText().toString()).equals(user.password)) {
                        user.sessionToken = UserHelper.getRandomToken();
                        userRepository.updateUser(user);
                        SessionHelper.getInstance(getApplicationContext(), user);
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is not good.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Button signUpBtn = findViewById(R.id.login_sign_up_button);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
