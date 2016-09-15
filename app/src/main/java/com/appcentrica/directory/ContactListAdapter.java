package com.appcentrica.directory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter class for Contact List. Basically binds the contact
 * data to the views of each contact item in the list (ex:
 * profile picture and name).
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final WeakReference<Activity> activity;

    public ContactListAdapter(Activity activity, List<Contact> items) {
        mValues = items;
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate contact list view when view holder is created
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Called when view holder is ready to be displayed at a position.
        // Here you can set the data fields for each view in the view holder

        holder.mItem = mValues.get(position);
        holder.mContactName.setText(mValues.get(position).getName());

        // Load contact picture from the saved contact picture URL
        Context context = holder.mView.getContext();
        Picasso.with(context)
                .load(holder.mItem.getPictureUrl())
                .placeholder(R.drawable.default_contact)
                .error(R.drawable.default_contact)
                .into(holder.mContactPicture);

        // Go to the contact detail page when a contact is clicked
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra(ContactDetailActivity.ARG_ITEM_ID, position);

                // Show a profile picture transition when going from list to details
                if (activity.get() != null) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(activity.get(), holder.mContactPicture, "contactPictureTransition");
                    context.startActivity(intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    // ViewHolder class that holds all the inner views for each contact list item
    // Ex: contact picture and contact name
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircleImageView mContactPicture;
        public final TextView mContactName;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContactPicture = (CircleImageView) view.findViewById(R.id.contact_list_image);
            mContactName = (TextView) view.findViewById(R.id.contact_list_name);
        }

    }
}