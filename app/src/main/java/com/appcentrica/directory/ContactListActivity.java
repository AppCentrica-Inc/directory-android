package com.appcentrica.directory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * An activity representing a list of Contacts.
 */
public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Set up toolbar at the top
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Load contacts from file
        Contacts.loadData(this);

        // Set up recycler view and fill it with all the contacts
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contact_list);
        recyclerView.setAdapter(new ContactListAdapter(this, Contacts.LIST));

    }
}
