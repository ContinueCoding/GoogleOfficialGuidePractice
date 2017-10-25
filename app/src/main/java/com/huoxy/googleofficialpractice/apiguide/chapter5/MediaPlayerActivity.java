package com.huoxy.googleofficialpractice.apiguide.chapter5;

import android.content.ComponentName;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.huoxy.googleofficialpractice.R;

/**
 * Created by huoxy on 2017/10/25.
 * 媒体浏览器客户端 - 媒体播放器
 */
public class MediaPlayerActivity extends AppCompatActivity {

    private MediaBrowserCompat mediaBrowser;
    private MyConnectionCallback connectionCallback;
    private MyControllerCallback controllerCallback;

    private ImageView mPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        //1-1
        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, MediaPlaybackService.class),
                connectionCallback,
                null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //1-2
        mediaBrowser.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //1-3
        if (MediaControllerCompat.getMediaController(MediaPlayerActivity.this) != null) {
            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).unregisterCallback(controllerCallback);
        }
        mediaBrowser.disconnect();
    }

    //3-1
    void buildTransportControls() {
        // Grab the view for the play/pause button
        mPlayPause = (ImageView) findViewById(R.id.play_pause);

        // Attach a listener to the button - 暂停or继续播放
        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly
                int pbState = MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getPlaybackState().getState();
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
                } else {
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().play();
                }
            }
        });

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(MediaPlayerActivity.this);

        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback);
    }


    //TODO Customize ConnectionCallback
    private class MyConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        @Override
        public void onConnected() {
            //2-1
            // Get the token for the MediaSession
            MediaSessionCompat.Token token = mediaBrowser.getSessionToken();

            // Create a MediaControllerCompat
            MediaControllerCompat mediaController = null;
            try {
                mediaController = new MediaControllerCompat(MediaPlayerActivity.this, token);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // Save the controller
            MediaControllerCompat.setMediaController(MediaPlayerActivity.this, mediaController);

            //TODO  Finish building the UI
            buildTransportControls();
        }

        @Override
        public void onConnectionFailed() {
            // The Service has refused our connection
        }

        @Override
        public void onConnectionSuspended() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
        }
    }

    //TODO Customize ControllerCallback
    //4-1
    private class MyControllerCallback extends MediaControllerCompat.Callback{
        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
        }
    }
}
