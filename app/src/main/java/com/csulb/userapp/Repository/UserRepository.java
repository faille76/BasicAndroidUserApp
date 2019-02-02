package com.csulb.userapp.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.csulb.userapp.Entity.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class UserRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoginApp";
    private static final int DATABASE_VERSION = 2;

    /**
     * User table
     */
    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_LAST_NAME = "last_name";
    private static final String KEY_USER_FIRST_NAME = "first_name";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_AGE = "age";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_SESSION_TOKEN = "session_token";

    private static UserRepository sInstance;

    /**
     * Singleton Pattern for create / get instance.
     *
     * @param context the context will be used only if it is the first instance.
     * @return the instance.
     */
    public static synchronized UserRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UserRepository(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Create the database helper.
     *
     * @param context the context.
     */
    private UserRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Mini helper to convert user to content value.
     *
     * @param user the user.
     * @return content value.
     */
    private ContentValues userToContent(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.username);
        values.put(KEY_USER_FIRST_NAME, user.firstName);
        values.put(KEY_USER_LAST_NAME, user.lastName);
        values.put(KEY_USER_AGE, user.age);
        values.put(KEY_USER_EMAIL, user.email);
        values.put(KEY_USER_PASSWORD, user.password);
        values.put(KEY_USER_SESSION_TOKEN, user.sessionToken);
        return values;
    }

    /**
     * Mini helper to convert a cursor (got from database) to user.
     *
     * @param cursor the cursor.
     * @return the user.
     */
    private User cursorToUser(Cursor cursor) {
        User newUser = new User();
        newUser.id = cursor.getLong(cursor.getColumnIndex(KEY_USER_ID));
        newUser.username = cursor.getString(cursor.getColumnIndex(KEY_USER_USERNAME));
        newUser.firstName = cursor.getString(cursor.getColumnIndex(KEY_USER_FIRST_NAME));
        newUser.lastName = cursor.getString(cursor.getColumnIndex(KEY_USER_LAST_NAME));
        newUser.age = cursor.getInt(cursor.getColumnIndex(KEY_USER_AGE));
        newUser.email = cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL));
        newUser.password = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD));
        newUser.sessionToken = cursor.getString(cursor.getColumnIndex(KEY_USER_SESSION_TOKEN));
        return newUser;
    }

    /**
     * Add an user.
     *
     * @param user the user object.
     * @return the user id.
     */
    public long addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = user.id;
        db.beginTransaction();
        try {
            if (userId == -1) {
                userId = db.insertOrThrow(TABLE_USERS, null, userToContent(user));
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        user.id = userId;
        return userId;
    }

    /**
     * Update an user who already exists in the database.
     *
     * @param user the user.
     * @return the user id.
     */
    public long updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = user.id;
        db.beginTransaction();
        try {
            if (userId == -1) {
                userId = db.update(TABLE_USERS, userToContent(user),
                        "id=?", new String[]{String.valueOf(user.id)});
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    /**
     * Get the list of the users.
     *
     * @return the users list.
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        String USERS_SELECT_QUERY = String.format("SELECT * FROM %s;", TABLE_USERS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    users.add(cursorToUser(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get users from database.");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return users;
    }

    /**
     * General method to find an user by field.
     *
     * @param field the field key name.
     * @param value the value associated.
     * @return the user or null.
     */
    private User getUserByField(String field, String value) {
        String USERS_SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = ?;",
                TABLE_USERS, field);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, new String[]{String.valueOf(value)});
        try {
            if (cursor.moveToFirst()) {
                return cursorToUser(cursor);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get user from database.");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Get an user by email in the database.
     *
     * @param email the user email.
     * @return the User if exists or null.
     */
    public User getUserByEmail(String email) {
        return getUserByField(KEY_USER_EMAIL, email);
    }

    /**
     * Get an user by session in the database.
     *
     * @param sessionToken the session token.
     * @return the User if exists or null.
     */
    public User getUserBySession(String sessionToken) {
        return getUserByField(KEY_USER_SESSION_TOKEN, sessionToken);
    }

    /**
     * Get an user by username in the database.
     *
     * @param username the username.
     * @return the User if exists or null.
     */
    public User getUserByUsername(String username) {
        return getUserByField(KEY_USER_USERNAME, username);
    }

    /**
     * Delete an user in the database.
     *
     * @param user the user.
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_USERS,"id=?", new String[]{String.valueOf(user.id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Create the tables.
     *
     * @param db the database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_USERNAME + " VARCHAR(255) UNIQUE," +
                KEY_USER_LAST_NAME + " VARCHAR(255)," +
                KEY_USER_FIRST_NAME + " VARCHAR(255)," +
                KEY_USER_EMAIL + " VARCHAR(255) UNIQUE," +
                KEY_USER_SESSION_TOKEN + " VARCHAR(255) UNIQUE," +
                KEY_USER_AGE + " INTEGER," +
                KEY_USER_PASSWORD + " VARCHAR(255)" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    /**
     * Upgrade if the database is not initialized or version update.
     *
     * @param db the database.
     * @param oldVersion the old version.
     * @param newVersion the new version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
