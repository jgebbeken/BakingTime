package dragons.android.bakingtime.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import dragons.android.bakingtime.ListSorter;
import dragons.android.bakingtime.R;
import dragons.android.bakingtime.ViewModels.DetailViewModel;
import dragons.android.bakingtime.model.Step;

/* ExoPlayer code adapted from http://codelabs.developers.google.com/ in able to use the latest
 * version of ExoPlayer.  Udacity's tutorials had depreciated methods in the newer version.
 */


public class StepsDetailView extends Fragment {

    private PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;
    private Uri uri;

    private static final String NO_VIDEO_PLACEHOLDER = "https://images.pexels.com/photos/691114/pexels-photo-691114.jpeg";


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvStepsHeader) TextView tvStepsHeader;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tvStepDescription) TextView tvStepDescription;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.no_video_placeholder) ImageView ivNoVideo;

    public StepsDetailView(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.steps_detail_view, container, false);
        ButterKnife.bind(this,rootView);

        mPlayerView = rootView.findViewById(R.id.video_view);
        //mNoVideoPlaceholder = rootView.findViewById(R.id.no_video_placeholder);
        DetailViewModel model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DetailViewModel.class);
        ListSorter listSorter = new ListSorter();
        Step step;

        step = model.getStep();

        step = listSorter.fileTypeCheck(step);


        tvStepsHeader.setText(step.getShortDescription());
        tvStepDescription.setText(step.getDescription());



        if(!step.getVideoURL().isEmpty()) {

            uri = Uri.parse(step.getVideoURL());
            ivNoVideo.setVisibility(View.GONE);

        } else{
            mPlayerView.setVisibility(View.GONE);
            ivNoVideo.setVisibility(View.VISIBLE);
            Picasso.get().load(NO_VIDEO_PLACEHOLDER).into(ivNoVideo);



        }


        return rootView;
    }

    private void initExoPlayer(){

       mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
               new DefaultTrackSelector(), new DefaultLoadControl());
       mPlayerView.setPlayer(mSimpleExoPlayer);
       mSimpleExoPlayer.setPlayWhenReady(playWhenReady);
       mSimpleExoPlayer.seekTo(currentWindow,playbackPosition);

       if(uri != null) {
           MediaSource mediaSource = buildMediaSource(uri);
           mSimpleExoPlayer.prepare(mediaSource, true, false);
       }
    }

    private MediaSource buildMediaSource(Uri videoUri) {

        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("ExoPlayer"))
                .createMediaSource(videoUri);
    }


    // Not used but maybe later if I implement full screen mode.

// --Commented out by Inspection START (7/27/2018 11:28 PM):
//    private void hideSystemUI(){
//        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//        | View.SYSTEM_UI_FLAG_FULLSCREEN
//        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }
// --Commented out by Inspection STOP (7/27/2018 11:28 PM)

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            playbackPosition = mSimpleExoPlayer.getCurrentPosition();
            currentWindow = mSimpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        if(Util.SDK_INT > 23) {
            initExoPlayer();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        //hideSystemUI();
        if((Util.SDK_INT <= 23 || mSimpleExoPlayer == null)){
            initExoPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}