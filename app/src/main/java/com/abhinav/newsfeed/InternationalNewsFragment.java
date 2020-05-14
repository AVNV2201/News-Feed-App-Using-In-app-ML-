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

import java.util.ArrayList;

public class InternationalNewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_international_news, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.internationalrcView);
        recyclerView.setLayoutManager( new LinearLayoutManager(root.getContext()));

        ArrayList<News> internationalNewsList = new ArrayList<>();

        NewsCardAdapter adapter = new NewsCardAdapter(getContext(), internationalNewsList);
        recyclerView.setAdapter(adapter);

        NewsDownloadHelper downloadHelper = new NewsDownloadHelper(root.getContext());

        for( int i = 1; i < ResourceHelper.countryCodes.size()-1; i++ ) {
            downloadHelper.setNewsList(recyclerView, internationalNewsList, ResourceHelper.countryCodes.get(i), null);
        }

        return root;
    }
}
