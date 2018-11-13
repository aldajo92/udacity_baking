package com.example.aldajo92.bakingapp.detail.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldajo92.bakingapp.ImageUtils;
import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.aldajo92.bakingapp.Constants.EXTRA_EXO_PLAYER_POSITION;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_LIST_INDEX;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_NEXT_ENABLED;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_PREV_ENABLED;
import static com.example.aldajo92.bakingapp.Constants.EXTRA_STEP;

public class RecipeStepFragment extends Fragment implements ExoPlayer.EventListener {

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
    private boolean isPrevEnabled;
    private boolean isNextEnabled;
    private long position;

    private Unbinder unbinder;
    private TrackSelector trackSelector;
    private SimpleExoPlayer exoPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listIndex = savedInstanceState.getInt(EXTRA_LIST_INDEX);
            step = savedInstanceState.getParcelable(EXTRA_STEP);
            isPrevEnabled = savedInstanceState.getBoolean(EXTRA_PREV_ENABLED);
            isNextEnabled = savedInstanceState.getBoolean(EXTRA_NEXT_ENABLED);
            position = savedInstanceState.getLong(EXTRA_EXO_PLAYER_POSITION);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LIST_INDEX, listIndex);
        outState.putParcelable(EXTRA_STEP, step);
        outState.putBoolean(EXTRA_PREV_ENABLED, isPrevEnabled);
        outState.putBoolean(EXTRA_NEXT_ENABLED, isNextEnabled);
        outState.putLong(EXTRA_EXO_PLAYER_POSITION, position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (view.findViewById(R.id.textView_step_count) != null) {
            stepCount.setText(getString(R.string.step_count_title, listIndex));
            shortDescription.setText(step.getShortDescription());
            longDescription.setText(step.getDescription());
        }

        if (!TextUtils.isEmpty(step.getVideoURL())) {
            noVideoImage.setVisibility(View.GONE);
            initializePlayer(Uri.parse(step.getVideoURL()));
        } else {
            playerView.setVisibility(View.GONE);
            ImageUtils.loadImageFromUrl(step.getThumbnailURL(), noVideoImage, R.drawable.video_unavailable);
            noVideoImage.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        position = exoPlayer != null ? exoPlayer.getCurrentPosition() : 0;
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
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

    public void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
            trackSelector = null;
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

    public void setStep(Step step) {
        this.step = step;
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

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
