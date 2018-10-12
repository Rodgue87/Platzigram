package com.platzi.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platzi.platzigram.R;
import com.platzi.platzigram.adapter.PictureAdapterRecyclerView;
import com.platzi.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView picturesRecycler = (RecyclerView) view.findViewById(R.id.searchRecyclerView);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

        picturesRecycler.setLayoutManager(gridLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buidPictures(),R.layout.cardview_picture,getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView );

        return view;
    }


    public ArrayList<Picture> buidPictures(){
        ArrayList<Picture> pictures =  new ArrayList<>();
        pictures.add(new Picture("https://www.novalandtours.com/images/guide/guilin.jpg", "Rodrigo Guerra", "4 días", "3 me Gusta"));
        pictures.add(new Picture("https://i.pinimg.com/originals/b0/60/49/b0604958c1603620bd7ca1b3a4e44f59.jpg", "Fely Aguirre", "3 días", "10 me Gusta"));
        pictures.add(new Picture("http://burgess-shale.rom.on.ca/images/zoomify/ogygopsis-gsc317h_img/TileGroup0/1-0-0.jpg", "David Guerra", "2 días", "9 me G usta"));
        return pictures;
    }

}
