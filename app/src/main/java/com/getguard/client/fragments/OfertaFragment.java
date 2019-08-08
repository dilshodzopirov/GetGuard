package com.getguard.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getguard.client.R;
import com.github.barteksc.pdfviewer.PDFView;

public class OfertaFragment extends Fragment {

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

        PDFView pdfView = view.findViewById(R.id.pdf_view);
        pdfView.fromAsset("oferta.pdf")
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .spacing(10)
                .load();

        return view;
    }

}
