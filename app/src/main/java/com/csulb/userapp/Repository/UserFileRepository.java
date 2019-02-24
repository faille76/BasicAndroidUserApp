package com.csulb.userapp.Repository;

import android.content.Context;
import android.util.Log;

import com.csulb.userapp.Entity.User;
import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserFileRepository {

    private static final String USER_FILE_NAME = "LoginApp.csv";

    /**
     * User table
     */
    private static final Integer KEY_USER_USERNAME = 0;
    private static final Integer KEY_USER_FIRST_NAME = 1;
    private static final Integer KEY_USER_LAST_NAME = 2;
    private static final Integer KEY_USER_EMAIL = 3;
    private static final Integer KEY_USER_AGE = 4;
    private static final Integer KEY_USER_PASSWORD = 5;

    private Context context;
    private static UserFileRepository sInstance;

    /**
     * Singleton Pattern for create / get instance.
     *
     * @param context the context will be used only if it is the first instance.
     * @return the instance.
     */
    public static synchronized UserFileRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UserFileRepository(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Create the database helper.
     *
     * @param context the context.
     */
    private UserFileRepository(Context context) {
        this.context = context;
    }

    /**
     * Mini helper to convert user to content value.
     *
     * @param user the user.
     * @return content value.
     */
    private String[] userToContent(User user) {
        String str = user.username + "#" + user.firstName + "#" + user.lastName + "#" + user.email + "#" + user.age + "#" + user.password;
        return str.split("#");
    }

    /**
     * Mini helper to convert a cursor (got from database) to user.
     *
     * @param userArray the cursor.
     * @return the user.
     */
    private User arrayToUser(String[] userArray) {
        User newUser = new User();
        newUser.username = userArray[KEY_USER_USERNAME];
        newUser.firstName = userArray[KEY_USER_FIRST_NAME];
        newUser.lastName = userArray[KEY_USER_LAST_NAME];
        newUser.email = userArray[KEY_USER_EMAIL];
        newUser.age = Integer.parseInt(userArray[KEY_USER_AGE]);
        newUser.password = userArray[KEY_USER_PASSWORD];
        return newUser;
    }

    /**
     * Add an user.
     *
     * @param user the user object.
     * @return the user id.
     */
    public long addUser(User user) {
        try {
            FileOutputStream fos = this.context.openFileOutput(USER_FILE_NAME, Context.MODE_PRIVATE | Context.MODE_APPEND);
            fos.write((user.username + "," + user.firstName + "," + user.lastName + "," + user.email + "," + user.age + "," + user.password + "\n").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * General method to find an user by field.
     *
     * @param field the field key name.
     * @param value the value associated.
     * @return the user or null.
     */
    private User getUserByField(Integer field, String value) {
        try {
            FileInputStream fos = this.context.openFileInput(USER_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fos);
            CSVReader reader = new CSVReader(isr);
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Log.i("user", nextLine[field]);
                if (nextLine[field].equals(value)) {
                    reader.close();
                    return arrayToUser(nextLine);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get an user by email in the csv.
     *
     * @param email the user email.
     * @return the User if exists or null.
     */
    public User getUserByEmail(String email) {
        return getUserByField(KEY_USER_EMAIL, email);
    }

    /**
     * Get an user by username in the csv.
     *
     * @param username the username.
     * @return the User if exists or null.
     */
    public User getUserByUsername(String username) {
        return getUserByField(KEY_USER_USERNAME, username);
    }
}
