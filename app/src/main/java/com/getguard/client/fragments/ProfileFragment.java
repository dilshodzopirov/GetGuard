package com.getguard.client.fragments;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getguard.client.R;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private TextView ratingText;
    private CircleImageView image;
    private AppCompatRatingBar ratingBar;
    private EditText firstNameInput, lastNameInput, fathersNameInput, phoneNumberInput;

    private User user;
    private ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        user = AppDatabase.getInstance(getActivity()).getUserDAO().getUser();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Подождите...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ratingText = v.findViewById(R.id.rating_text);
        ratingBar = v.findViewById(R.id.rating_bar);
        image = v.findViewById(R.id.image);
        firstNameInput = v.findViewById(R.id.first_name_input);
        lastNameInput = v.findViewById(R.id.last_name_input);
        fathersNameInput = v.findViewById(R.id.fathers_name_input);
        phoneNumberInput = v.findViewById(R.id.phone_number_input);

        Glide.with(this)
                .load(Config.BASE_URL + "api/Upload/" + user.getPhotoId())
                .apply(new RequestOptions().centerCrop())
                .into(image);

        String[] name = user.getUserName().split("\\s+");
        String firstName = name.length > 0 ? name[0] : "";
        String lastName = name.length > 1 ? name[1] : "";
        String fathersName = name.length > 2 ? name[2] : "";

        firstNameInput.setText(firstName);
        lastNameInput.setText(lastName);
        fathersNameInput.setText(fathersName);

        getInfo();

        return v;
    }

    private void getInfo() {
        NetworkManager.getInstance(getActivity()).getUserById(user.getToken(), user.getId(), (errorMessage, data) -> {
            if (data != null) {
                ratingBar.setRating(data.getRating());
                ratingText.setText(data.getRating() + ", " + data.getUserRatingCount() + " Оценки");
                phoneNumberInput.setText(data.getPhoneNumber());
            }
        });
    }

}
