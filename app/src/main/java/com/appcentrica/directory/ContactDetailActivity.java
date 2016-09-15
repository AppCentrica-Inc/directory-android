package com.appcentrica.directory;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * An activity representing a single Contact detail screen.
 */
public class ContactDetailActivity extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "item_id";

    private Contact mContact; // holds the current contact

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Contacts.loadData(this);

        // Get the selected contact ID from intent arguments
        int contactPosition = getIntent().getIntExtra(ARG_ITEM_ID, 0);
        mContact = Contacts.LIST.get(contactPosition);

        // Set up collapsing toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set contact name and image in toolbar
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final ImageView appBarImage = (ImageView) findViewById(R.id.detail_toolbar_image);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mContact.getName());
            Picasso.with(this)
                    .load(mContact.getPictureUrl())
                    .placeholder(R.drawable.default_contact)
                    .error(R.drawable.default_contact)
                    .into(appBarImage);
        }

        // Get contact detail views
        TextView email = ((TextView) findViewById(R.id.contact_detail_email));
        TextView number = ((TextView) findViewById(R.id.contact_detail_number));
        TextView title = ((TextView) findViewById(R.id.contact_detail_title));
        TextView linkedIn = ((TextView) findViewById(R.id.contact_detail_linkedin));

        // Set contact details inside appropriate views
        email.setText(mContact.getEmail());
        number.setText(mContact.getNumber());
        title.setText(mContact.getTitle());
        linkedIn.setText("LinkedIn");

        // Set onClick listeners to make the app more useful
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mContact.getEmail()));
                startActivity(intent);
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mContact.getNumber()));
                startActivity(intent);
            }
        });
        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mContact.getLinkedInUrl()));
                startActivity(intent);
            }
        });

        // Set up floating action button, add to contacts when clicked
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mContact.getEmail());
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, mContact.getNumber());
                intent.putExtra(ContactsContract.Intents.Insert.NAME, mContact.getName());
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, "AppCentrica");
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, mContact.getTitle());

                // Contact picture is a bit different since we have to pass the bitmap as a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap contactPicture = ((BitmapDrawable) appBarImage.getDrawable()).getBitmap();
                contactPicture.compress(Bitmap.CompressFormat.PNG, 100, stream);
                ArrayList<ContentValues> data = new ArrayList<>();
                ContentValues row = new ContentValues();
                row.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
                row.put(ContactsContract.CommonDataKinds.Photo.PHOTO, stream.toByteArray());
                data.add(row);
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Behaviour when pressing the back button, go back to Contact List
            supportFinishAfterTransition();
            NavUtils.navigateUpTo(this, new Intent(this, ContactListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
