package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getguard.client.R;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Utils;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.suke.widget.SwitchButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewEventActivity extends AppCompatActivity {

    private int grayLight, yellow;

    private LinearLayout contentLayout, formOptionContainer, form1Container, form2Container, form3Container, publishLayout;
    private TextView formOptionText, form1Text, form2Text, form3Text;
    private View form1Line, form2Line, form3Line;
    private SwitchButton licenceSwitch, weaponSwitch, carSwitch;
    private LinearLayout weaponContainer, weapon1Container, weapon2Container, offerLayout, offerAmountLayout;
    private TextView weapon1Text, weapon2Text, selectedWeaponText, offerAmountText;
    private View weapon1Line, weapon2Line;
    private LinearLayout carContainer, car1Container, car2Container, car3Container, rankLayout;
    private TextView car1Text, car2Text, car3Text, selectedCarText, licenceText, responseText, viewText, publishText;
    private View car1Line, car2Line, car3Line;
    private AppCompatEditText startTimeInput, endTimeInput, addressInput, additionalInput;

    private ProgressBar progressBar;
    private LinearLayout errorContainer;
    private TextView errorText, emptyText;
    private Button errorBtn;
    private int dressCode = 0;
    private int weapon = -1, personalCar = -1;
    private boolean hasPrivateGuardLicence = false;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, EEEE HH:mm", new Locale("ru", "RU"));

    private User user;
    private String id;
    private EventResponse.Data data;
    private ProgressDialog progressDialog;
    private String startTime, endTime;
    private int eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        grayLight = getResources().getColor(R.color.colorGrayLight);
        yellow = getResources().getColor(R.color.colorYellow);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = AppDatabase.getInstance(this).getUserDAO().getUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Подождите...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Охрана жилья");

        eventType = getIntent().getIntExtra("eventType", 0);
        id = getIntent().getStringExtra("id");

        contentLayout = findViewById(R.id.content_layout);
        formOptionContainer = findViewById(R.id.form_option_container);
        form1Container = findViewById(R.id.form_1_container);
        form2Container = findViewById(R.id.form_2_container);
        form3Container = findViewById(R.id.form_3_container);
        formOptionText = findViewById(R.id.form_option_text);
        form1Text = findViewById(R.id.form_1_text);
        form2Text = findViewById(R.id.form_2_text);
        form3Text = findViewById(R.id.form_3_text);
        form1Line = findViewById(R.id.form_1_line);
        form2Line = findViewById(R.id.form_2_line);
        form3Line = findViewById(R.id.form_3_line);

        licenceSwitch = findViewById(R.id.licence_switch);
        weaponSwitch = findViewById(R.id.weapon_switch);
        carSwitch = findViewById(R.id.car_switch);

        weaponContainer = findViewById(R.id.weapon_container);
        weapon1Container = findViewById(R.id.weapon_1_container);
        weapon2Container = findViewById(R.id.weapon_2_container);
        weapon1Text = findViewById(R.id.weapon_1_text);
        weapon2Text = findViewById(R.id.weapon_2_text);
        weapon1Line = findViewById(R.id.weapon_1_line);
        weapon2Line = findViewById(R.id.weapon_2_line);
        selectedWeaponText = findViewById(R.id.selected_weapon_text);

        carContainer = findViewById(R.id.car_container);
        car1Container = findViewById(R.id.car_1_container);
        car2Container = findViewById(R.id.car_2_container);
        car3Container = findViewById(R.id.car_3_container);
        car1Text = findViewById(R.id.car_1_text);
        car2Text = findViewById(R.id.car_2_text);
        car3Text = findViewById(R.id.car_3_text);
        car1Line = findViewById(R.id.car_1_line);
        car2Line = findViewById(R.id.car_2_line);
        car3Line = findViewById(R.id.car_3_line);
        selectedCarText = findViewById(R.id.selected_car_text);
        licenceText = findViewById(R.id.licence_text);

        offerLayout = findViewById(R.id.offer_layout);
        offerAmountLayout = findViewById(R.id.offer_amount_layout);
        offerAmountText = findViewById(R.id.offer_amount_text);

        startTimeInput = findViewById(R.id.start_time_input);
        endTimeInput = findViewById(R.id.end_time_input);
        addressInput = findViewById(R.id.address_input);
        additionalInput = findViewById(R.id.additional_input);
        responseText = findViewById(R.id.response_text);
        viewText = findViewById(R.id.view_text);
        publishLayout = findViewById(R.id.publish_layout);
        rankLayout = findViewById(R.id.rank_layout);

        progressBar = findViewById(R.id.progress_bar);
        errorContainer = findViewById(R.id.error_container);
        errorText = findViewById(R.id.error_text);
        errorBtn = findViewById(R.id.error_btn);
        publishText = findViewById(R.id.publish_text);

        if (id == null && data == null) {
            showContent();
            formOptionText.setOnClickListener(v -> {
                formOptionContainer.setVisibility(View.VISIBLE);
            });

            form1Container.setOnClickListener(v -> setFormOption(1));
            form2Container.setOnClickListener(v -> setFormOption(2));
            form3Container.setOnClickListener(v -> setFormOption(3));

            licenceSwitch.setOnCheckedChangeListener((view, isChecked) -> {
                hasPrivateGuardLicence = isChecked;
            });

            weaponSwitch.setOnCheckedChangeListener((view, isChecked) -> {
                weaponContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (!isChecked) {
                    selectedWeaponText.setVisibility(View.INVISIBLE);
                }
            });

            selectedWeaponText.setOnClickListener(v -> {
                weaponContainer.setVisibility(View.VISIBLE);
            });
            weapon1Container.setOnClickListener(v -> setWeaponOption(1));
            weapon2Container.setOnClickListener(v -> setWeaponOption(2));

            carSwitch.setOnCheckedChangeListener((view, isChecked) -> {
                carContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (!isChecked) {
                    selectedCarText.setVisibility(View.INVISIBLE);
                }
            });

            selectedCarText.setOnClickListener(v -> {
                carContainer.setVisibility(View.VISIBLE);
            });
            car1Container.setOnClickListener(v -> setCarOption(1));
            car2Container.setOnClickListener(v -> setCarOption(2));
            car3Container.setOnClickListener(v -> setCarOption(3));

            startTimeInput.setOnClickListener(v -> {
                new SingleDateAndTimePickerDialog.Builder(NewEventActivity.this)
                        .bottomSheet()
                        .curved()
                        .mainColor(Color.BLACK)
                        .customLocale(new Locale("ru", "RU"))
                        .minDateRange(new Date())
                        .title("Начало смены")
                        .listener(date -> {
                            startTimeInput.setText("Начало смены - " + dateFormat.format(date));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                            startTime = sdf.format(new Date());
                        })
                        .display();
            });

            endTimeInput.setOnClickListener(v -> {
                new SingleDateAndTimePickerDialog.Builder(NewEventActivity.this)
                        .bottomSheet()
                        .curved()
                        .mainColor(Color.BLACK)
                        .customLocale(new Locale("ru", "RU"))
                        .minDateRange(new Date())
                        .title("Конец смены")
                        .listener(date -> {
                            endTimeInput.setText("Конец смены - " + dateFormat.format(date));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                            endTime = sdf.format(new Date());
                        })
                        .display();
            });

            offerLayout.setOnClickListener(v -> {
                startActivityForResult(new Intent(NewEventActivity.this, OfferActivity.class), 1111);
            });

            publishLayout.setOnClickListener(v -> {
                if (isValid()) {
                    createEvent();
                }
            });
        } else {
            licenceSwitch.setVisibility(View.GONE);
            carSwitch.setVisibility(View.GONE);
            weaponSwitch.setVisibility(View.GONE);
            licenceText.setVisibility(View.VISIBLE);
            selectedCarText.setVisibility(View.VISIBLE);
            selectedWeaponText.setVisibility(View.VISIBLE);
            offerAmountLayout.setVisibility(View.VISIBLE);

            rankLayout.setVisibility(View.VISIBLE);
            publishLayout.setVisibility(View.GONE);
            addressInput.setEnabled(false);
            startTimeInput.setEnabled(false);
            endTimeInput.setEnabled(false);
            additionalInput.setEnabled(false);


            if (user.getRoleType() != 1) {
                publishLayout.setVisibility(View.VISIBLE);
                publishText.setText("Откликнуться");
                publishLayout.setOnClickListener(v -> {
                    respond();
                });
            }
//            formOptionText.setText("Камуфляж");
//            licenceText.setText("Нет");
//            selectedWeaponText.setText("Травматическое");
//            selectedCarText.setText("Нет");
//
//            addressInput.setText("Московская область, д. Жуковка, д.50");
//            startTimeInput.setText("Начало смены 9 сентября, среда 10:00");
//            endTimeInput.setText("Конец смены 12 сентября, среда 10:00");
//            responseText.setText("6 откликов");
//            viewText.setText("46");
//
//            offerAmountText.setText("250");
        }

        errorBtn.setOnClickListener(v -> {
            getEvent();
        });

        if (id != null) {
            getEvent();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1111 && resultCode == RESULT_OK) {
            offerAmountText.setText(data.getStringExtra("amount"));
            offerAmountLayout.setVisibility(View.VISIBLE);
        }

    }

    private void setFormOption(int option) {

        dressCode = option - 1;
        form1Text.setTextColor(option == 1 ? yellow : grayLight);
        form2Text.setTextColor(option == 2 ? yellow : grayLight);
        form3Text.setTextColor(option == 3 ? yellow : grayLight);
        form1Line.setVisibility(option == 1 ? View.VISIBLE : View.GONE);
        form2Line.setVisibility(option == 2 ? View.VISIBLE : View.GONE);
        form3Line.setVisibility(option == 3 ? View.VISIBLE : View.GONE);
        switch (option) {
            case 1:
                formOptionText.setText(form1Text.getText());
                break;
            case 2:
                formOptionText.setText(form2Text.getText());
                break;
            case 3:
                formOptionText.setText(form3Text.getText());
                break;
        }

        Observable.timer(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        formOptionContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setWeaponOption(int option) {

        weapon = option - 1;
        weapon1Text.setTextColor(option == 1 ? yellow : grayLight);
        weapon2Text.setTextColor(option == 2 ? yellow : grayLight);
        weapon1Line.setVisibility(option == 1 ? View.VISIBLE : View.GONE);
        weapon2Line.setVisibility(option == 2 ? View.VISIBLE : View.GONE);
        switch (option) {
            case 1:
                selectedWeaponText.setText(weapon1Text.getText());
                break;
            case 2:
                selectedWeaponText.setText(weapon2Text.getText());
                break;
        }
        selectedWeaponText.setVisibility(View.VISIBLE);

        Observable.timer(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        weaponContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setCarOption(int option) {

        personalCar = option - 1;
        car1Text.setTextColor(option == 1 ? yellow : grayLight);
        car2Text.setTextColor(option == 2 ? yellow : grayLight);
        car3Text.setTextColor(option == 3 ? yellow : grayLight);
        car1Line.setVisibility(option == 1 ? View.VISIBLE : View.GONE);
        car2Line.setVisibility(option == 2 ? View.VISIBLE : View.GONE);
        car3Line.setVisibility(option == 3 ? View.VISIBLE : View.GONE);
        switch (option) {
            case 1:
                selectedCarText.setText(car1Text.getText());
                break;
            case 2:
                selectedCarText.setText(car2Text.getText());
                break;
            case 3:
                selectedCarText.setText(car3Text.getText());
                break;
        }
        selectedCarText.setVisibility(View.VISIBLE);

        Observable.timer(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        carContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }

    private void showProgress() {
        contentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }

    private void showError(String message) {
        contentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        if (message != null) {
            errorText.setText(message);
        }
    }

    private void setDataToUI() {

        switch (data.getDressCode()) {
            case 0:
                formOptionText.setText(form1Text.getText());
            case 1:
                formOptionText.setText(form2Text.getText());
            case 2:
                formOptionText.setText(form3Text.getText());
        }
        licenceText.setText(data.getHasPrivateGuardLicense() ? "Да" : "Нет");
        switch (data.getWeapon()) {
            case 0:
                selectedWeaponText.setText(weapon1Text.getText());
            case 1:
                selectedWeaponText.setText(weapon2Text.getText());
            default:
                selectedWeaponText.setText("Нет");
        }
        switch (data.getPersonalCar()) {
            case 0:
                selectedCarText.setText(car1Text.getText());
            case 1:
                selectedCarText.setText(car2Text.getText());
            case 2:
                selectedCarText.setText(car3Text.getText());
            default:
                selectedCarText.setText("Нет");
        }

        addressInput.setText(data.getAddress());
        startTimeInput.setText("Начало смены " + formatDate(data.getJobStartTime()));
        endTimeInput.setText("Конец смены " + formatDate(data.getGuardJobEndTime()));

        int responses = data.getRespondedUsers() != null ? data.getRespondedUsers().size() : 0;
        responseText.setText(responses + " откликов");
        viewText.setText("46");

        offerAmountText.setText(String.valueOf(data.getRatePrice()));

    }

    private void getEvent() {
        showProgress();
        NetworkManager.getInstance(this).getEvent(user.getToken(), id, (errorMessage, data) -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {
                this.data = data;
                setDataToUI();
                showContent();

            }
        });
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", new Locale("ru"));
    SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMMM, EEEEE HH:mm", new Locale("ru"));

    private String formatDate(String dateStr) {
        if (dateStr == null) return "";
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void createEvent() {
        progressDialog.show();
        int ratePrice = 0;
        try {
            ratePrice = Integer.parseInt(offerAmountText.getText().toString());
        } catch (NumberFormatException e) {

        }
        NetworkManager.getInstance(this).createEvent(user.getToken(),
                addressInput.getText().toString(),
                addressInput.getText().toString(),
                startTime,
                endTime,
                ratePrice,
                dressCode,
                hasPrivateGuardLicence,
                weapon,
                null,
                personalCar,
                additionalInput.getText().toString(),
                eventType,
                (errorMessage, data) -> {
            progressDialog.dismiss();

            if (errorMessage != null) {
                Toast.makeText(NewEventActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            if (data != null) {
                finish();
            }
        });
    }

    private boolean isValid() {
        if (weapon == -1) {
            Toast.makeText(this, "Выберите оружию пожалуйста", Toast.LENGTH_LONG).show();
            return false;
        }
        if (personalCar == -1) {
            Toast.makeText(this, "Выберите автомобиль пожалуйста", Toast.LENGTH_LONG).show();
            return false;
        }
        if (addressInput.getText().length() < 1) {
            Toast.makeText(this, "Введите адрес пожалуйста", Toast.LENGTH_LONG).show();
            return false;
        }
        if (startTime == null) {
            Toast.makeText(this, "Введите начало смены пожалуйста", Toast.LENGTH_LONG).show();
            return false;
        }
        if (endTime == null) {
            Toast.makeText(this, "Введите конец смены пожалуйста", Toast.LENGTH_LONG).show();
            return false;
        }
        if (offerAmountText.getText().length() < 1) {
            Toast.makeText(this, "Введите предложение пожалуйста", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void respond() {
        progressDialog.show();

        NetworkManager.getInstance(this).respond(user.getToken(), id, (errorMessage, data) -> {
                    progressDialog.dismiss();

                    if (errorMessage != null) {
                        Toast.makeText(NewEventActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                    if (data != null) {
                        finish();
                    }
                });
    }

}
