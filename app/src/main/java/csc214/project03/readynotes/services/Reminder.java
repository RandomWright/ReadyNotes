package csc214.project03.readynotes.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import csc214.project03.readynotes.MainActivity;
import csc214.project03.readynotes.R;

/**
 * Created by cwrig21 on 5/3/2017.
 */

public class Reminder extends IntentService {
    private static final String EXTRA_NOTE = "EXTRA_NOTE";
    private static final String TAG = "REMINDER SERVICE THING";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param TAG Used to name the worker thread, important only for debugging.
     */
    public Reminder() {
        super(TAG);
    }

    public static Intent newIntent(Context c, String note) {
        Intent i = new Intent(c, Reminder.class);
        i.putExtra(EXTRA_NOTE, note);
        return i;
    }

    public static void newReminder(Context c, String note) {
        Intent i = newIntent(c, note);
        PendingIntent pi = PendingIntent.getService(
                c,            // context
                123,  // request code (similar to startActivityForResult)
                i,            // the intent
                0);           // flags

        int ms = 60000;
        long timeToGoOff = SystemClock.elapsedRealtime() + ms;

        Log.i(TAG, "Reminder: " + note);
        AlarmManager manager = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        manager.setExact(
                AlarmManager.ELAPSED_REALTIME, // elapsed realtime (time since boot)
                timeToGoOff,                   // time in ms
                pi                             // the pending intent to use
        );
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "received = " + intent);
        if (intent != null) {
            String message = intent.getStringExtra(EXTRA_NOTE);

            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pend = PendingIntent.getActivity(
                    this,
                    0,
                    main,
                    0
            );

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(message)
                    .setContentTitle(getString(R.string.reminder))
                    .setSmallIcon(R.drawable.ic_reminder)
                    .setContentText(message)
                    .setContentIntent(pend)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(0, notification);
        }
    }
}
