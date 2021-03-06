package com.huoxy.googleofficialpractice.apiguide.chapter5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huoxy on 2017/10/24.
 *  媒体浏览器服务端
 */
public class MediaPlaybackService extends MediaBrowserServiceCompat {
    private static final String TAG = "MediaPlaybackService";

    private static final String MY_MEDIA_ROOT_ID = "my_media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "my_empty_media_root_id";

    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a MediaSessionCompat
        mMediaSession = new MediaSessionCompat(this, TAG);

        // Enable callbacks from MediaButtons and TransportControls
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MyMediaSessionCallback() has methods that handle callbacks from a media controller
        mMediaSession.setCallback(new MyMediaSessionCallback());

        // Set the session's token so that client activities can communicate with it
        setSessionToken(mMediaSession.getSessionToken());
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        if (allowBrowsing(clientPackageName, clientUid)) {
            // Returns a root ID that clients can use with onLoadChildren() to retrieve
            // the content hierarchy.
            return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
        } else {
            // Clients can connect, but this BrowserRoot is an empty hierachy
            // so onLoadChildren returns nothing. This disables the ability to browse for content.
            return new BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null);
        }
    }

    //Test
    private int myPid = 0;
    private boolean allowBrowsing(String clientPackageName, int clientUid){
        return clientPackageName.equals(getPackageName()) || clientUid == myPid;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

        //  Browsing not allowed
        if (TextUtils.equals(MY_EMPTY_MEDIA_ROOT_ID, parentId)) {
            result.sendResult(null);
            return;
        }

        //TODO Assume for example that the music catalog is already loaded/cached.

        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID.equals(parentId)) {
            // TODO Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // TODO Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems);

    }

    private class MyMediaSessionCallback extends MediaSessionCompat.Callback{

        @Override
        public void onPrepare() {
            super.onPrepare();
            Log.i(TAG, "onPrepare()=====");
        }

        @Override
        public void onPlay() {
            super.onPlay();
            Log.i(TAG, "onPlay()=====");
            //TODO The best place to show Foreground Service is inside the onPlay() method


        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i(TAG, "onPause()=====");
        }
    }
}
