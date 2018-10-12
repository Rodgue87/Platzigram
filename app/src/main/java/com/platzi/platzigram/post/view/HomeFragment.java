package com.platzi.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.platzi.platzigram.R;
import com.platzi.platzigram.adapter.PictureAdapterRecyclerView;
import com.platzi.platzigram.model.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_Home), false, view);
        RecyclerView picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);
        fabCamera = (FloatingActionButton) view.findViewById(R.id.fabCamera);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);



        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buidPictures(),R.layout.cardview_picture,getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView );

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        return view;
    }

    private void takePicture() {
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                e.printStackTrace();//crear archivo antes de abrir la camara
            }
            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(),"com.platzi.platzigram", photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);
            }

        }
    }

    private File createImageFile() throws IOException {//molde o contenedor de archivo.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());//definir el nombre. con timestamp será nombre único.
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storegeDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);//ruta default para almacener fotos en android.
        File photo = File.createTempFile(imageFileName, ".jpg", storegeDir);//archivo temporal cuyo nombre sera imagefilename con extension jpg y ubicar en.

        photoPathTemp = "file:" + photo.getAbsolutePath();//para tener acceso a la imagen

        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("HomeFragment", "Camera ok !!! :)");
            Intent i = new Intent(getActivity(), NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP", photoPathTemp);
            startActivity(i);
        }
    }

    public ArrayList<Picture> buidPictures(){
        ArrayList<Picture> pictures =  new ArrayList<>();
        pictures.add(new Picture("https://www.novalandtours.com/images/guide/guilin.jpg", "Rodrigo Guerra", "4 días", "3 me Gusta"));
        pictures.add(new Picture("https://i.pinimg.com/originals/b0/60/49/b0604958c1603620bd7ca1b3a4e44f59.jpg", "Fely Aguirre", "3 días", "10 me Gusta"));
        pictures.add(new Picture("http://burgess-shale.rom.on.ca/images/zoomify/ogygopsis-gsc317h_img/TileGroup0/1-0-0.jpg", "David Guerra", "2 días", "9 me G usta"));
        return pictures;
    }

    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }



}
