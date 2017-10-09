package com.huoxy.googleofficialpractice.apiguide.chapter3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.huoxy.googleofficialpractice.R;
import com.huoxy.googleofficialpractice.apiguide.ApiGuideActivity;

import static android.os.Build.ID;

public class UserInterfaceActivity extends AppCompatActivity {

    EditText et_input;
    AutoCompleteTextView autoCompleteTextView;
    InputMethodManager manager;

    CheckBox checkBox;

    RadioGroup bestPlayersRadioGroup;
    RadioButton ronaldo, kaka, rooney;

    ToggleButton toggleButton;

    Button timePicker, datePicker;

    NotificationManager notificationManager;
    Button simpleNotification, progressNotification, progressNotification2,
            fullScreenNotification;

    Button customToast;

    Button searchDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_interface);

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        et_input = (EditText) findViewById(R.id.et_input);
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:

                        return true;
                    case EditorInfo.IME_ACTION_SEND:

                        return true;
                    case EditorInfo.IME_ACTION_GO:

                        return true;
                }

                return false;
            }
        });


        checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do sth
                checkBox.setChecked(true);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //do sth based on isChecked
                if (isChecked) {
                    Toast.makeText(UserInterfaceActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInterfaceActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bestPlayersRadioGroup = (RadioGroup) findViewById(R.id.best_players);
        bestPlayersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.ronaldo:
                        Toast.makeText(UserInterfaceActivity.this, "Ronaldo", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.kaka:
                        Toast.makeText(UserInterfaceActivity.this, "Kaka", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rooney:
                        Toast.makeText(UserInterfaceActivity.this, "Rooney", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        toggleButton = (ToggleButton) findViewById(R.id.toggle_button);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(UserInterfaceActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInterfaceActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        timePicker = (Button) findViewById(R.id.time_picker);
        datePicker = (Button) findViewById(R.id.date_picker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment pickerFragment = new TimePickerFragment();
                pickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment pickerFragment = new DatePickerFragment();
                pickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        simpleNotification = (Button) findViewById(R.id.simple_notification);
        simpleNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSimpleNotification();
            }
        });


        progressNotification = (Button) findViewById(R.id.progress_notification);
        progressNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProgressNotification();
            }
        });

        progressNotification2 = (Button) findViewById(R.id.progress_notification2);
        progressNotification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProgressNotification2();
            }
        });

        fullScreenNotification = (Button) findViewById(R.id.full_screen_notification);
        fullScreenNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFullScreenN();
            }
        });


        customToast = (Button) findViewById(R.id.custom_toast);
        customToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_layout,
                        (ViewGroup) findViewById(R.id.custom_toast_container));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("This is a custom toast");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });


        searchDemo = (Button) findViewById(R.id.search_demo);
        searchDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInterfaceActivity.this, SearchActivity.class));
            }
        });
    }

    private void createSimpleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText("Notification Content Text......");

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        style.setBigContentTitle("Event tracker detail:");
        for (int i = 0; i < events.length; i++) {
            style.addLine("---" + i);
        }
        builder.setStyle(style);

        Intent resultIntent = new Intent(this, ApiGuideActivity.class);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ApiGuideActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);//点击时关闭通知

        notificationManager.notify(0, builder.build());
    }

    private void createProgressNotification(){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Progress Notification")
                .setContentText("Notification in progress......");

        // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            builder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            notificationManager.notify(0, builder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // When the loop is finished, updates the notification
                        builder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        notificationManager.notify(0, builder.build());
                    }
                }
        // Starts the thread by calling the run() method in its Runnable
        ).start();

    }

    private void createProgressNotification2(){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Progress Notification")
                .setContentText("Notification in progress......");

        // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            builder.setProgress(0, 0, true);
                            // Displays the progress bar for the first time.
                            notificationManager.notify(0, builder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // When the loop is finished, updates the notification
                        builder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        notificationManager.notify(0, builder.build());
                    }
                }
                // Starts the thread by calling the run() method in its Runnable
        ).start();

    }

    private void createFullScreenN(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("浮动通知")
                .setContentText("Notification Content Text......");

        // 设置通知的优先级
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // 设置通知的提示音
        builder.setSound(alarmSound);

        notificationManager.notify(1, builder.build());
    }
}
