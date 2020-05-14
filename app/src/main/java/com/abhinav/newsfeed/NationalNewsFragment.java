package com.abhinav.newsfeed;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.telephony.euicc.DownloadableSubscription;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.cache.MemoryCache;

import java.util.ArrayList;
import java.util.HashMap;

public class NationalNewsFragment extends Fragment {

    ArrayList<News> nationalNewsList ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_national_news, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.nationalrcView);
        recyclerView.setLayoutManager( new LinearLayoutManager(root.getContext()));

        nationalNewsList = new ArrayList<>();

        NationalNewsCardAdapter adapter = new NationalNewsCardAdapter(getContext(), nationalNewsList);
        recyclerView.setAdapter(adapter);

        MLBasedNewsDownloadHelper downloadHelper = new MLBasedNewsDownloadHelper(root.getContext());

        for( int i = 0; i < ResourceHelper.categoryCodes.size(); i++ ) {
            downloadHelper.setNewsList(recyclerView,
                    nationalNewsList,
                    ResourceHelper.Country.INDIA,
                    ResourceHelper.categoryCodes.get(i),
                    MainActivity.selections.get(i));
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        for( int i = 0; i < MainActivity.clicked.size(); i++ ){
            if( MainActivity.clicked.get(i) ){

                int index = -1 ;
                String s = nationalNewsList.get(i).getCategory();

                if( s != null ){
                    if( s.equals("health") ) index = 0;
                    else if( s.equals("business") ) index = 1;
                    else if( s.equals("technology") ) index = 2;
                    else if( s.equals("entertainment") ) index = 3;
                    else if( s.equals("science") ) index = 4;
                    else if( s.equals("sports") ) index = 5;
                }

                if(  index != -1 ){
                    MainActivity.no_of_rewards.set(index, MainActivity.no_of_rewards.get(index)+1 );
                }
            }
        }

        MainActivity.sharedPreferences.edit().putInt(MLHelper.storageNameForNoOfRounds,MainActivity.no_of_rounds).apply();

        ArrayList<String> tmp1 = new ArrayList<>();
        ArrayList<String> tmp2 = new ArrayList<>();
        for( int i = 0; i < MainActivity.no_of_rewards.size(); i++ ){
            tmp1.add(String.valueOf(MainActivity.no_of_rewards.get(i)));
            tmp2.add(String.valueOf(MainActivity.no_of_selections.get(i)));
        }

        try {
            MainActivity.sharedPreferences.edit().putString(MLHelper.storageNameForNoOfRewards, ObjectSerializer.serialize(tmp1)).apply();
            MainActivity.sharedPreferences.edit().putString(MLHelper.storageNameForNoOfSelections, ObjectSerializer.serialize(tmp2)).apply();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
