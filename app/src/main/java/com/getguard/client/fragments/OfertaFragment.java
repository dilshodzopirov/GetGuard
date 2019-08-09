package com.getguard.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getguard.client.R;
import com.getguard.client.adapters.OfertaAdapter;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfertaFragment extends Fragment {

    private HtmlTextView htmlTextView;
    private OfertaAdapter adapter;

    public OfertaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OfertaAdapter();
        recyclerView.setAdapter(adapter);

        InputStream is = null;
        try {
            is = getActivity().getAssets().open("oferta.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);
            final String arr[] = str.split("§");
            List<String> items = Arrays.asList(arr);
            adapter.setItems(items);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

}
