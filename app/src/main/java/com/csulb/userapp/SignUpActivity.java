package com.csulb.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csulb.userapp.Entity.User;
import com.csulb.userapp.Repository.SessionHelper;
import com.csulb.userapp.Repository.UserHelper;
import com.csulb.userapp.Repository.UserFileRepository;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    /**
     * All resources input.
     */
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordRepeatEditText;
    private EditText ageEditText;

    private UserFileRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userRepository = UserFileRepository.getInstance(getApplicationContext());

        usernameEditText = findViewById(R.id.sign_up_username_edit_text);
        firstNameEditText = findViewById(R.id.sign_up_first_name_edit_text);
        lastNameEditText = findViewById(R.id.sign_up_last_name_edit_text);
        emailEditText = findViewById(R.id.sign_up_email_edit_text);
        passwordEditText = findViewById(R.id.sign_up_password_edit_text);
        passwordRepeatEditText = findViewById(R.id.sign_up_password_repeat_edit_text);
        ageEditText = findViewById(R.id.sign_up_age_edit_text);

        Button submitBtn = findViewById(R.id.sign_up_submit_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> errorList = new ArrayList<>();
                if (usernameEditText.getText().toString().isEmpty()) {
                    errorList.add("Username is empty.");
                } else if (userRepository.getUserByUsername(usernameEditText.getText().toString()) != null) {
                    errorList.add("Username is already taken.");
                }

                if (firstNameEditText.getText().toString().isEmpty()) {
                    errorList.add("First name is empty.");
                }

                if (lastNameEditText.getText().toString().isEmpty()) {
                    errorList.add("Last name is empty.");
                }

                if (emailEditText.getText().toString().isEmpty()) {
                    errorList.add("Email is empty.");
                } else if (userRepository.getUserByEmail(emailEditText.getText().toString()) != null) {
                    errorList.add("Email is already taken.");
                }

                if (passwordEditText.getText().toString().isEmpty()) {
                    errorList.add("Password is empty.");
                } else if (!passwordEditText.getText().toString().equals(passwordRepeatEditText.getText().toString())) {
                    errorList.add("Passwords are not the same.");
                }

                if (ageEditText.getText().toString().isEmpty()) {
                    errorList.add("Age is empty.");
                } else if (Integer.parseInt(ageEditText.getText().toString()) < 1 || Integer.parseInt(ageEditText.getText().toString()) > 99) {
                    errorList.add("Your age must be between 1 to 99.");
                }

                if (errorList.size() > 0) {
                    StringBuilder errorMessage = new StringBuilder("Error(s):");
                    for (String str : errorList) {
                        errorMessage.append("\n").append(str);
                    }
                    Toast.makeText(getApplicationContext(), errorMessage.toString(), Toast.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    user.username = usernameEditText.getText().toString();
                    user.email = emailEditText.getText().toString();
                    user.firstName = firstNameEditText.getText().toString();
                    user.lastName = lastNameEditText.getText().toString();
                    user.age = Integer.valueOf(ageEditText.getText().toString());
                    user.password = UserHelper.getHashedPassword(passwordEditText.getText().toString());

                    if (userRepository.addUser(user) == -1) {
                        Toast.makeText(getApplicationContext(), "Error occurred, please ask the developer.", Toast.LENGTH_LONG).show();
                    } else {
                        SessionHelper.getInstance(getApplicationContext(), user);
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        Button cancelBtn = findViewById(R.id.sign_up_cancel_button);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
