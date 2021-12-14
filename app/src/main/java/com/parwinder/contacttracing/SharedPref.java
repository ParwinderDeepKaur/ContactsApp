package com.parwinder.contacttracing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPref {

    private static SharedPreferences mSharedPref;

    /*empty constructor*/
    private SharedPref() {
    }

    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    /*get array list of type ContactsData from Shared Preference
     * @key: get array list with this tag
     * @return the array list value */
    public static ArrayList<ContactsData> read(String key) {
        Gson gson = new Gson();
        String json = mSharedPref.getString(key, null);
        Type type = new TypeToken<ArrayList<ContactsData>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /*save array list of type ContactsData in Shared Preference
     * @list: list to be saved
     * @key: get array list with this key*/
    public static void write(ArrayList<ContactsData> list, String key) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    /* read string from sharef preference
     * @key string key
     * @defValue the actual value
     * @return the string value*/
    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    /*
     * save @value with this @key in shared preference
     */
    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    /*
     * get @defValue of the @key from shared preference
     * @return the boolean value
     */
    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    /*
     * save boolean data in shared preference
     * the boolean @value to be saved with @key
     */
    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    /*
     * read integer @defValue with the @key
     * @return the integer value
     */
    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    /*
     * save integer data in shared preference
     * the integer @value to be saved with @key
     */
    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }

    /*
     * clear the value saved in shared preference
     * @contacts_list is the key to be removed from shared preferences
     */
    public static void clear(String contacts_list) {
        mSharedPref.edit().remove(contacts_list).apply();
    }
}