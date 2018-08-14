package com.sushant.EmergencyShake;

import android.content.Context;
import android.content.Intent;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent in26tent) {
        Intent smsIntent = new Intent (context,ActivateFragment.class);
        context.startService(smsIntent);
    }
}













//picasso