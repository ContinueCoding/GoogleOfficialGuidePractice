package com.huoxy.googleofficialpractice.apiguide.chapter3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
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
import com.huoxy.googleofficialpractice.training.chapter4.DatePickerFragment;
import com.huoxy.googleofficialpractice.training.chapter4.TimePickerFragment;

public class UserInterfaceActivity extends AppCompatActivity {

    EditText et_input;
    AutoCompleteTextView autoCompleteTextView;
    InputMethodManager manager;

    CheckBox checkBox;

    RadioGroup bestPlayersRadioGroup;
    RadioButton ronaldo, kaka, rooney;

    ToggleButton toggleButton;

    Button timePicker, datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

                } else {

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
    }

}
