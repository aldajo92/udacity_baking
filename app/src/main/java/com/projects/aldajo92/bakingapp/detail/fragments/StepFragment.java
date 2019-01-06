package com.projects.aldajo92.bakingapp.detail.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.aldajo92.bakingapp.ImageUtils;
import com.projects.aldajo92.bakingapp.R;
import com.projects.aldajo92.bakingapp.models.ui.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.projects.aldajo92.bakingapp.Constants.EXTRA_EXO_PLAYER_POSITION;
import static com.projects.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.projects.aldajo92.bakingapp.Constants.EXTRA_STEP;
import static com.projects.aldajo92.bakingapp.Constants.PLAY_READY_KEY;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class StepFragment extends Fragment implements Player.EventListener {

    @Nullable
    @BindView(R.id.textView_step_count)
    AppCompatTextView stepCount;

    @Nullable
    @BindView(R.id.textView_short_description)
    AppCompatTextView shortDescription;

    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView playerView;

    @BindView(R.id.image_no_video)
    AppCompatImageView noVideoImage;

    @Nullable
    @BindView(R.id.textView_long_description)
    AppCompatTextView longDescription;

    private int listIndex;
    private Step step;

    private Unbinder unbinder;
    private TrackSelector trackSelector;
    private SimpleExoPlayer exoPlayer;

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    private boolean playIsReady;
    private long position;
    private String videoUrl;

    public static StepFragment newInstance(Step step) {
        StepFragment stepFragment = new StepFragment();
        stepFragment.step = step;
        return stepFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LIST_INDEX, listIndex);
        outState.putParcelable(EXTRA_STEP, step);
        outState.putLong(EXTRA_EXO_PLAYER_POSITION, position);
        outState.putBoolean(PLAY_READY_KEY, playIsReady);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container,false);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null){
            playIsReady = savedInstanceState.getBoolean(PLAY_READY_KEY);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        videoUrl = step.getVideoURL();

        if (view.findViewById(R.id.textView_step_count) != null) {
            stepCount.setText(getString(R.string.step_count_title, listIndex));
            shortDescription.setText(step.getShortDescription());
            longDescription.setText(step.getDescription());
        }

        if (!TextUtils.isEmpty(videoUrl)) {
            noVideoImage.setVisibility(View.GONE);
            initializePlayer(Uri.parse(videoUrl));
        } else {
            playerView.setVisibility(View.GONE);
            ImageUtils.loadImageFromUrl(step.getThumbnailURL(), noVideoImage, R.drawable.video_unavailable);
            noVideoImage.setVisibility(View.VISIBLE);
        }

        int orientation = getResources().getConfiguration().orientation;


        if (videoUrl != null && !videoUrl.isEmpty()) {

            noVideoImage.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);

            if (savedInstanceState != null) {
                position = savedInstanceState.getLong(EXTRA_EXO_PLAYER_POSITION);
            }

            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));

            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.isTablet)) {
                expandView(playerView);
                hideSystemUI();
            }

        } else {
            ImageUtils.loadImageFromUrl(step.getThumbnailURL(), noVideoImage, R.drawable.video_unavailable);
            playerView.setVisibility(View.GONE);
            noVideoImage.setVisibility(View.VISIBLE);

            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.isTablet)) {
                expandView(noVideoImage);
                hideSystemUI();
            }
        }
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), "StepSinglePageFragment");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                exoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                super.onPause();
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                exoPlayer.seekTo(0);
            }
        });
        mediaSession.setActive(true);
    }

    private void expandView(View view) {
        view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private void hideSystemUI() {
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listIndex = savedInstanceState.getInt(EXTRA_LIST_INDEX);
            step = savedInstanceState.getParcelable(EXTRA_STEP);
            position = savedInstanceState.getLong(EXTRA_EXO_PLAYER_POSITION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(playIsReady);
            exoPlayer.seekTo(position);
        } else if (videoUrl != null && !videoUrl.isEmpty()){
            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));
            exoPlayer.setPlayWhenReady(playIsReady);
            exoPlayer.seekTo(position);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            position = exoPlayer.getCurrentPosition();
            playIsReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }

        unbinder.unbind();
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

            exoPlayer.seekTo(position);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    public void stopPlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
