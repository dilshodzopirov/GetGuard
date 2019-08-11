package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getguard.client.R;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.suke.widget.SwitchButton;

import java.text.SimpleDateFormat;
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

    private LinearLayout formOptionContainer, form1Container, form2Container, form3Container;
    private TextView formOptionText, form1Text, form2Text, form3Text;
    private View form1Line, form2Line, form3Line;
    private SwitchButton licenceSwitch, weaponSwitch, carSwitch;
    private LinearLayout weaponContainer, weapon1Container, weapon2Container, offerLayout, offerAmountLayout;
    private TextView weapon1Text, weapon2Text, selectedWeaponText, offerAmountText;
    private View weapon1Line, weapon2Line;
    private LinearLayout carContainer, car1Container, car2Container, car3Container;
    private TextView car1Text, car2Text, car3Text, selectedCarText;
    private View car1Line, car2Line, car3Line;
    private AppCompatEditText startTimeInput, endTimeInput;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, EEEE HH:mm", new Locale("ru", "RU"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        grayLight = getResources().getColor(R.color.colorGrayLight);
        yellow = getResources().getColor(R.color.colorYellow);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Охрана жилья");

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

        offerLayout = findViewById(R.id.offer_layout);
        offerAmountLayout = findViewById(R.id.offer_amount_layout);
        offerAmountText = findViewById(R.id.offer_amount_text);

        startTimeInput = findViewById(R.id.start_time_input);
        endTimeInput = findViewById(R.id.end_time_input);

        formOptionText.setOnClickListener(v -> {
            formOptionContainer.setVisibility(View.VISIBLE);
        });

        form1Container.setOnClickListener(v -> setFormOption(1));
        form2Container.setOnClickListener(v -> setFormOption(2));
        form3Container.setOnClickListener(v -> setFormOption(3));

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
                    })
                    .display();
        });

        offerLayout.setOnClickListener(v -> {
            startActivityForResult(new Intent(NewEventActivity.this, OfferActivity.class), 1111);
        });

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

}
