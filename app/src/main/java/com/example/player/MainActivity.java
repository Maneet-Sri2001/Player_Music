package com.example.player;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<SongModel> songList;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdaptor adaptor;

    Context context = this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reqPerm();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
        adaptor = new PagerAdaptor(getSupportFragmentManager());

        adaptor.addFrag(new listSong(), "");
        adaptor.addFrag(new Search(), "");
        adaptor.addFrag(new Play(), "");

        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_library_music);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_list_music);
    }

    public void reqPerm() {
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        songList = displaySong();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<SongModel> displaySong() {

        final ArrayList<SongModel> list = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.AudioColumns.GENRE, MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.YEAR};
        Cursor c = getApplicationContext().getContentResolver()
                .query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);
                String dur = c.getString(3);
                String img = c.getString(4);
                String genre = c.getString(5);
                String date = c.getString(7);
                String type = c.getString(6);
                String name = path.substring(path.lastIndexOf("/") + 1);

                if (path.contains(".mp3") || path.contains(".wav")) {
                    Log.e(" Name :" + name, " Album :" + album);
                    Log.e(" Path :" + path, " Artist :" + artist);
                    list.add(new SongModel(name, artist, album, path, genre, img, dur, type, date));
                }
            }
            c.close();
        }
        return list;
    }
}