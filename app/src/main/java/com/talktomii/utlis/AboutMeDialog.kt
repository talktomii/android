package com.talktomii.utlis

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.databinding.DialogAboutMeBinding
import com.talktomii.ui.home.HomeScreenViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class AboutMeDialog(private var aboutYou: Any) : DaggerDialogFragment() {

    lateinit var binding: DialogAboutMeBinding

    @Inject
    lateinit var viewModel: HomeScreenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        playVideo()
    }

    fun playVideo() {
        if (aboutYou != null){
            binding.videoview.setVideoURI(Uri.parse(aboutYou as String))

        }else{
            binding.videoview.setVideoURI(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))
        }
        binding.videoview.setOnPreparedListener { mediaPlayer ->
            val videoRatio = mediaPlayer.videoWidth / mediaPlayer.videoHeight.toFloat()
            val screenRatio = binding.videoview.width / binding.videoview.height.toFloat()
            val scaleX = videoRatio / screenRatio
            if (scaleX >= 1f) {
                binding.videoview.scaleX = scaleX
            } else {
                binding.videoview.scaleY = 1f / scaleX
            }
        }
        binding.videoview.start()
    }

//    private fun initPlayer() {
//        var url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//
//        // Create a player instance.
//        mPlayer = SimpleExoPlayer.Builder(requireContext()).build()
//
//        // Bind the player to the view.
//        binding.playerView.player = mPlayer
//
//        //setting exoplayer when it is ready.
//        mPlayer!!.playWhenReady = true
//
//        // Set the media source to be played.
//        mPlayer!!.setMediaSource(buildMediaSource(url))
//
//        // Prepare the player.
//        mPlayer!!.prepare()
//
//    }

//    private fun buildMediaSource(videoURL: String): MediaSource {
//        // Create a data source factory.
//        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
//        // Create a progressive media source pointing to a stream uri.
//        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(videoURL))
//        return mediaSource
//    }

    private fun setListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

//    override fun onDismiss(dialog: DialogInterface) {
//        releasePlayer()
//    }

//    private fun releasePlayer() {
//        if (mPlayer == null) {
//            return
//        }
//        //release player when done
//        mPlayer!!.release()
//        mPlayer = null
//    }

    companion object {
        val TAG = "AboutMeDialog"
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}