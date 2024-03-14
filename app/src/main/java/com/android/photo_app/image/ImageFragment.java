package com.android.photo_app.image;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.photo_app.R;

import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment {
    private List<Image> imageList;
    private ImageAdapter imageAdapter;
    private static final String TAG = "ImageFragment";

    public ImageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("ImageFragment", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.imageFragmentRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        imageList = getAllImages();
        imageAdapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(imageAdapter);

        return view;
    }

    private List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();

        // Use the ContentResolver from the Fragment's Context
        ContentResolver contentResolver = requireContext().getContentResolver();

        // Use MediaStore to retrieve image file paths
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            while (cursor.moveToNext()) {
                String imagePath = cursor.getString(columnIndex);
                Log.e(TAG,""+ imagePath);
                images.add(new Image(imagePath));
            }

            cursor.close();
        }

        return images;
    }


}
