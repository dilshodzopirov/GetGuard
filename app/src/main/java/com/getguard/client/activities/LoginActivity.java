package com.getguard.client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getguard.client.R;
import com.getguard.client.adapters.OfertaAdapter;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.models.network.Register;
import com.getguard.client.models.network.SmsPhoneVerify;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.BiConsumer;
import com.getguard.client.utils.UIUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private MaskedEditText phoneInput, codeInput;
    private AppCompatButton continueBtn, formContinueBtn;
    private LinearLayout customerContainer, guardContainer, phoneLayout, ofertaLayout, codeLayout, acceptContainer, formLayout;
    private TextView customerText, guardText, sendSmsText, editNumberText, smsInfoText;
    private View customerLine, guardLine;

    private int white, yellow;
    private ViewState viewState = ViewState.phone;
    private CountDownTimer timer;
    private UserType userType = UserType.client;
    private ProgressDialog progressDialog;
    private Register.RegisterData data;
    private boolean isLoading = false;
    private String smsPhoneVerifyToken = "";

    private enum ViewState {
        phone, sms, form, oferta
    }

    private enum UserType {
        client, guard
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Подождите...");

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
        phoneLayout = findViewById(R.id.layout_phone);
        ofertaLayout = findViewById(R.id.layout_oferta);
        codeLayout = findViewById(R.id.layout_code);
        acceptContainer = findViewById(R.id.accept_container);
        smsInfoText = findViewById(R.id.sms_info_text);
        formLayout = findViewById(R.id.layout_form);
        formContinueBtn = findViewById(R.id.form_continue_btn);

        continueBtn.setOnClickListener(v -> {
            Log.d("test", phoneInput.getText().toString() + " : " + phoneInput.getRawText());
            closeKeyboard();
            smsPhoneVerify();
        });

        formContinueBtn.setOnClickListener(v -> {
            Log.d("test", phoneInput.getText().toString() + " : " + phoneInput.getRawText());
            closeKeyboard();
            updateViewState(ViewState.oferta);
        });

        customerContainer.setOnClickListener(v -> switchType(UserType.client));
        guardContainer.setOnClickListener(v -> switchType(UserType.guard));

        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                continueBtn.setVisibility(editable.length() > 15 ? View.VISIBLE : View.GONE);
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
                if (editable.length() >= 4 && !isLoading) {
                    closeKeyboard();
                    register();
                }
                Log.d(TAG, "afterTextChanged: " + editable.length());
            }
        });

        sendSmsText.setOnClickListener(v -> {
            codeInput.setText("");
            smsPhoneVerify();
        });

        editNumberText.setOnClickListener(v -> {
            updateViewState(ViewState.phone);
        });

        acceptContainer.setOnClickListener(v -> {
            User user = new User();
            user.setToken("Bearer " + data.getToken());
            user.setFirstName(data.getUser().getUserName());
            user.setEmail(data.getUser().getEmail());
            AppDatabase.getInstance(LoginActivity.this).getUserDAO().insert(user);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OfertaAdapter adapter = new OfertaAdapter();
        recyclerView.setAdapter(adapter);

        InputStream is = null;
        try {
            is = getAssets().open("oferta.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);
            final String arr[] = str.split("§");
            List<String> paragraphs = Arrays.asList(arr);
            adapter.setItems(paragraphs);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void switchType(UserType type) {
        userType = type;
        customerText.setTextColor(type == UserType.client ? yellow : white);
        guardText.setTextColor(type == UserType.client ? white : yellow);
        customerLine.setVisibility(type == UserType.client ? View.VISIBLE : View.GONE);
        guardLine.setVisibility(type == UserType.client ? View.GONE : View.VISIBLE);
    }

    private void updateViewState(ViewState state) {
        viewState = state;
        switch (state) {
            case phone:
                phoneLayout.setVisibility(View.VISIBLE);
                codeLayout.setVisibility(View.GONE);
                ofertaLayout.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);
                break;
            case sms:
                phoneLayout.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                ofertaLayout.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);

                smsInfoText.setText(String.format("На номер %s отправлено СМС с кодом из 4-цифр", phoneInput.getText().toString()));

                sendSmsText.setTextColor(white);
                sendSmsText.setTypeface(Typeface.create("@font/museo_sans_cyrl_700", Typeface.NORMAL));
                timer = new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        sendSmsText.setText(String.format("Повторная отправка СМС через %d с", millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        sendSmsText.setTextColor(yellow);
                        sendSmsText.setTypeface(Typeface.create("@font/museo_sans_cyrl_300", Typeface.NORMAL));
                        sendSmsText.setText("Отправить СМС еще раз");
                    }
                }.start();
                break;
            case form:
                if (timer != null) timer.cancel();
                phoneLayout.setVisibility(View.GONE);
                codeLayout.setVisibility(View.GONE);
                ofertaLayout.setVisibility(View.GONE);
                formLayout.setVisibility(View.VISIBLE);
                break;
            case oferta:
                if (timer != null) timer.cancel();
                phoneLayout.setVisibility(View.GONE);
                codeLayout.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);
                ofertaLayout.setVisibility(View.VISIBLE);
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

    private void smsPhoneVerify() {
        progressDialog.show();
        String phone = phoneInput.getText().toString();
        phone = phone.replaceAll(" ", "");
        phone = phone.replaceAll("-", "");
        int role = userType == UserType.client ? 1 : 2;
        NetworkManager.getInstance(this).smsPhoneVerify(phone, role, (error, smsPhoneVerify) -> {
            progressDialog.dismiss();
            if (error != null) {
                UIUtils.showError(this, null, error);
            }

            if (smsPhoneVerify != null) {
                if (smsPhoneVerify.getErrorMessage() != null) {
                    UIUtils.showError(this, null, smsPhoneVerify.getErrorMessage()[0]);
                } else {
                    smsPhoneVerifyToken = smsPhoneVerify.getData().getToken();
                    updateViewState(ViewState.sms);
                }
            }
        });
    }

    private void register() {
        isLoading = true;
        progressDialog.show();
        String code = codeInput.getText().toString();
        NetworkManager.getInstance(this).register(code, smsPhoneVerifyToken, (error, registerResponse) -> {
            isLoading = false;
            progressDialog.dismiss();
            if (error != null) {
                UIUtils.showError(this, null, error);
            }

            if (registerResponse != null) {
                if (registerResponse.getErrorMessage() != null) {
                    UIUtils.showError(this, null, registerResponse.getErrorMessage()[0]);
                } else {
                    data = registerResponse.getData();
                    updateViewState(userType == UserType.client ? ViewState.form : ViewState.oferta);
                }
            }
        });
    }

}
