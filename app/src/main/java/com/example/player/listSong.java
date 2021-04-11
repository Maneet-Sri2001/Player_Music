
package com.example.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listSong extends Fragment {

    private RecyclerView listSong;
    private ArrayList<SongModel> songModels;

    public MainActivity get;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_song, container, false);

        listSong = view.findViewById(R.id.song_list);
        listSong.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        listSong.setLayoutManager(layoutManager);
        get = (MainActivity) getActivity();
        if (get.songList != null)
            songModels = get.songList;

        songListAdaptor adaptor = new songListAdaptor(getContext(), get.songList);
        listSong.setAdapter(adaptor);
        return view;
    }


}