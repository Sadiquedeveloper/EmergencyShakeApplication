package com.sushant.EmergencyShake;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SelectContacts extends Fragment implements View.OnClickListener{


    @BindView(R.id.buttonContacts)
    Button buttonContacts;

    String phone;

    static final int PICK_CONTACT = 1;

    View view;

    public SelectContacts() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.select_contacts_fragemt, container, false);
        ButterKnife.bind(this, view);
        buttonContacts = view.findViewById(R.id.buttonContacts);
        buttonContacts.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonContacts) {
            Intent contacts = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
            contacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(contacts, PICK_CONTACT);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT) {
        }
        if (resultCode == Activity.RESULT_OK){
            Uri contactUri = data.getData();
            String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getActivity().getApplicationContext().getContentResolver()
                    .query(contactUri, projection, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Util.phone = cursor.getString(column);

        }

    }

}