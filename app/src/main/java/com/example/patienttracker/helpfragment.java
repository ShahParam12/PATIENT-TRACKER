package com.example.patienttracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;

public class helpfragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_help,container,false);
        PDFView pdfView = (PDFView) v.findViewById(R.id.pdfView);

        pdfView.fromAsset("doctorworking.pdf").load();
                return v;
    }
}

