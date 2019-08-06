package com.getguard.client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getguard.client.R;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private MaskedEditText phoneInput, codeInput;
    private AppCompatButton continueBtn;
    private LinearLayout customerContainer, guardContainer, formContainer, ofertaContainer, acceptContainer;
    private TextView customerText, guardText, sendSmsText, editNumberText;
    private View customerLine, guardLine;
    private FrameLayout typeContainer;

    private int white, yellow;
    private ViewState viewState = ViewState.phone;
    private CountDownTimer timer;

    private enum ViewState {
        phone, sms, oferta
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        white = getResources().getColor(R.color.colorWhite);
        yellow = getResources().getColor(R.color.colorYellow);

        phoneInput = findViewById(R.id.phone_input);
        continueBtn = findViewById(R.id.continue_btn);
        customerContainer = findViewById(R.id.customer_container);
        guardContainer = findViewById(R.id.guard_container);
        customerText = findViewById(R.id.customer_text);
        guardText = findViewById(R.id.guard_text);
        customerLine = findViewById(R.id.customer_line);
        guardLine = findViewById(R.id.guard_line);
        codeInput = findViewById(R.id.code_input);
        sendSmsText = findViewById(R.id.send_sms_text);
        editNumberText = findViewById(R.id.edit_number_text);
        typeContainer = findViewById(R.id.type_container);
        formContainer = findViewById(R.id.form_container);
        ofertaContainer = findViewById(R.id.oferta_container);
        acceptContainer = findViewById(R.id.accept_container);

        continueBtn.setOnClickListener(v -> {
            Log.d("test", phoneInput.getText().toString() + " : " + phoneInput.getRawText());
            closeKeyboard();
            updateViewState(ViewState.sms);
        });

        customerContainer.setOnClickListener(v -> switchType(0));
        guardContainer.setOnClickListener(v -> switchType(1));

        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                continueBtn.setVisibility(editable.length() > 14 ? View.VISIBLE : View.GONE);
                Log.d(TAG, "afterTextChanged: " + editable.length());
            }
        });

        codeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 4) {
                    closeKeyboard();
                    updateViewState(ViewState.oferta);
                }
                Log.d(TAG, "afterTextChanged: " + editable.length());
            }
        });

        editNumberText.setOnClickListener(v -> {
            updateViewState(ViewState.phone);
        });

        acceptContainer.setOnClickListener(v -> {
            User user = new User();
            user.setFirstName("Dilshod");
            user.setLastName("Zopirov");
            user.setEmail("dilshdzopirov@gmail.com");
            AppDatabase.getInstance(LoginActivity.this).getUserDAO().insert(user);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });

    }

    private void switchType(int type) {
        customerText.setTextColor(type == 0 ? yellow : white);
        guardText.setTextColor(type == 0 ? white : yellow);
        customerLine.setVisibility(type == 0 ? View.VISIBLE : View.GONE);
        guardLine.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
    }

    private void updateViewState(ViewState state) {
        viewState = state;
        switch (state) {
            case phone:
                phoneInput.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.VISIBLE);
                typeContainer.setVisibility(View.VISIBLE);
                codeInput.setVisibility(View.GONE);
                sendSmsText.setVisibility(View.GONE);
                editNumberText.setVisibility(View.GONE);
                break;
            case sms:
                phoneInput.setVisibility(View.GONE);
                continueBtn.setVisibility(View.GONE);
                typeContainer.setVisibility(View.GONE);
                codeInput.setVisibility(View.VISIBLE);
                sendSmsText.setVisibility(View.VISIBLE);
                editNumberText.setVisibility(View.VISIBLE);

                timer = new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        sendSmsText.setText(String.format("Повторная отправка СМС через %d с", millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        sendSmsText.setText("Отправить СМС еще раз");
                    }
                }.start();
                break;
            case oferta:
                if (timer != null) timer.cancel();
                formContainer.setVisibility(View.GONE);
                ofertaContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
