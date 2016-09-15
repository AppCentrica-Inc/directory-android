package com.appcentrica.directory;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Contacts {
    private static final String CONTACT_DATA_FILE_NAME = "contact_data.json";

    public static ArrayList<Contact> LIST = new ArrayList<>();

    /**
     * Initialize contact data from a json file
     * @return
     */
    public static boolean loadData(Activity activity){
        if (LIST.isEmpty()){
            String data = loadJSONFromAsset(activity);
            if (data == null){
                return false;
            }

            try{
                loadContactList(data);
            } catch (JSONException e){
                Log.e("Contacts", "Error parsing contacts JSON data: " + e.getMessage());
                return false;
            }
        }

        return true;
    }

    /**
     * Get a list of contacts given the JSON data as a String
     * @param contactData
     * @throws JSONException
     */
    private static void loadContactList(String contactData) throws JSONException{
        JSONObject contactJSON = new JSONObject(contactData);
        JSONArray contactArray = contactJSON.optJSONArray("contacts");
        if (contactArray != null){
            for (int i = 0; i < contactArray.length(); i++) {
                Contact newContact = parseSingleContact(contactArray.getJSONObject(i));
                LIST.add(newContact);
            }
        }
    }

    /**
     * Parse a single Contact item given a contact JSONObject
     * @param contactJSON
     * @return
     * @throws JSONException
     */
    private static Contact parseSingleContact(JSONObject contactJSON) throws JSONException{
        Contact contact = new Contact();
        contact.setName(contactJSON.optString("name"));
        contact.setTitle(contactJSON.optString("title"));
        contact.setNumber(contactJSON.optString("number"));
        contact.setLinkedInUrl(contactJSON.optString("linkedIn"));
        contact.setPictureUrl(contactJSON.optString("pictureUrl"));
        contact.setEmail(contactJSON.optString("email"));
        return contact;
    }

    /**
     * Loads a json file from the assets directory and reads it as a String
     * @return
     */
    private static String loadJSONFromAsset(Activity activity) {
        String json;
        try {
            InputStream is = activity.getAssets().open(CONTACT_DATA_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
