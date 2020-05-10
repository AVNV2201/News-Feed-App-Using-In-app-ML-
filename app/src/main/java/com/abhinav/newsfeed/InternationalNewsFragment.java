package com.abhinav.newsfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InternationalNewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_international_news, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.internationalrcView);
        recyclerView.setLayoutManager( new LinearLayoutManager(root.getContext()));

        NewsDownloadHelper downloadHelper = new NewsDownloadHelper(root.getContext());
        downloadHelper.getNewsList(recyclerView, ResourceHelper.Country.US, null);

        return root;
    }
}
