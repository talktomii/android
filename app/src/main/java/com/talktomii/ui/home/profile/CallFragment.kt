package com.talktomii.ui.home.profile

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk.getApplicationContext
import com.google.gson.Gson
import com.talktomii.R
import com.talktomii.data.model.call.CallRequest
import com.talktomii.data.model.call.CallUser
import com.talktomii.data.network.ApisRespHandler
import com.talktomii.data.network.responseUtil.Status
import com.talktomii.databinding.FragmentCallBinding
import com.talktomii.ui.home.HomeViewModel
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.utlis.*
import com.talktomii.utlis.SocketManager.Companion.onAcceptCall
import com.talktomii.utlis.SocketManager.Companion.onRejectCall
import com.talktomii.utlis.dialogs.ProgressDialog
import dagger.android.support.DaggerFragment
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import javax.inject.Inject


class CallFragment : DaggerFragment(), SocketManager.OnMessageReceiver {

    private var channelName: String? = ""
    private lateinit var other: CallUser
    private lateinit var callRequest: CallRequest
    private var agoraToken: String = ""
    private val LOG_TAG: String = "abcd"
    private val PERMISSION_REQ_ID_RECORD_AUDIO = 22

    private var mRtcEngine // Tutorial Step 1
            : RtcEngine? = null
    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Tutorial Step 1
        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         * Leave the channel: When the user/host leaves the channel, the user/host sends a goodbye message. When this message is received, the SDK determines that the user/host leaves the channel.
         * Drop offline: When no data packet of the user or host is received for a certain period of time (20 seconds for the communication profile, and more for the live broadcast profile), the SDK assumes that the user/host drops offline. A poor network connection may lead to false detections, so we recommend using the Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who
         * leaves
         * the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         * USER_OFFLINE_QUIT(0): The user left the current channel.
         * USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         * USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        override fun onUserOffline(uid: Int, reason: Int) { // Tutorial Step 4
            requireActivity().runOnUiThread(Runnable { onRemoteUserLeft(uid, reason) })
        }

        /**
         * Occurs when a remote user stops/resumes sending the audio stream.
         * The SDK triggers this callback when the remote user stops or resumes sending the audio stream by calling the muteLocalAudioStream method.
         *
         * @param uid ID of the remote user.
         * @param muted Whether the remote user's audio stream is muted/unmuted:
         *
         * true: Muted.
         * false: Unmuted.
         */
        override fun onUserMuteAudio(uid: Int, muted: Boolean) { // Tutorial Step 6
//            requireActivity().runOnUiThread(Runnable { onRemoteUserVoiceMuted(uid, muted) })
        }
    }


    private lateinit var binding: FragmentCallBinding


    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var prefsManager: PrefsManager
    lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCallBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun setListener() {
        binding.ivCallAccept.setOnClickListener {
            var jsonObject = JSONObject()
            jsonObject.put("channelName", channelName)
            jsonObject.put("isForVideoCall", false)
            jsonObject.put("otherId", other._id)
            jsonObject.put("token", agoraToken)
            (requireActivity() as MainActivity).socketManager.acceptCall(jsonObject, this)
            initAgoraEngineAndJoinChannel()
            binding.constraintLayout6.visible()
            binding.clAcceptReject.gone()
            runTimer()
        }
        binding.ivCallEnd.setOnClickListener {
            var jsonObject = JSONObject()
            jsonObject.put("channelName", channelName)
            jsonObject.put("isForVideoCall", false)
            jsonObject.put("otherId", other._id)
            jsonObject.put("token", agoraToken)
            (requireActivity() as MainActivity).socketManager.rejectCall(jsonObject, this)
//            leaveChannel()
        }
        binding.ivSpeaker.setOnClickListener {
            onSwitchSpeakerphoneClicked(it)
        }
        binding.ivMute.setOnClickListener {
            onLocalAudioMuteClicked(it)
        }
        binding.ivEnd.setOnClickListener {
            var jsonObject = JSONObject()
            jsonObject.put("channelName", channelName)
            jsonObject.put("isForVideoCall", false)
            jsonObject.put("otherId", other._id)
            jsonObject.put("token", agoraToken)
            (requireActivity() as MainActivity).socketManager.rejectCall(jsonObject, this)
//            leaveChannel()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireActivity())

        bindObserver()
        if (requireArguments()["DATA"] != null) {
            other = Gson().fromJson(requireArguments()["DATA"].toString(), CallUser::class.java)
            viewModel.initCall(getUser(prefsManager)?.admin?._id ?: "")
            channelName = getUser(prefsManager)?.admin?._id
        }
        if (requireArguments()["CALL_REQUEST"] != null) {
            callRequest = Gson().fromJson(
                requireArguments()["CALL_REQUEST"].toString(),
                CallRequest::class.java
            )
            other = callRequest.loginUser
            channelName = other._id
            binding.constraintLayout6.gone()
            binding.clAcceptReject.visible()
            agoraToken = callRequest.token
        }
        setUi()
        setListener()
        (requireActivity() as MainActivity).socketManager.onAcceptCall(this)
        (requireActivity() as MainActivity).socketManager.onRejectCall(this)

    }

    private fun setUi() {
        Glide.with(requireContext()).load(other.profilePhoto).into(binding.ivUserProfile)
        binding.txtName.text = other.name
        binding.txtUserName.text = "@${other.userName}"
    }

    private fun bindObserver() {

        viewModel.agoraToken.observe(requireActivity(), Observer {
            it ?: return@Observer
            when (it.status) {
                Status.SUCCESS -> {
                    agoraToken = it.data?.token ?: ""
                    progressDialog.setLoading(false)
                    if (checkSelfPermission(
                            Manifest.permission.RECORD_AUDIO,
                            PERMISSION_REQ_ID_RECORD_AUDIO
                        )
                    ) {

                        initAgoraEngineAndJoinChannel()
                    }
                }

                Status.ERROR -> {
                    progressDialog.setLoading(false)
                    ApisRespHandler.handleError(it.error, requireActivity(), prefsManager)
                }
                Status.LOADING -> {
                    progressDialog.setLoading(true)
                }

            }
        })


    }

    fun initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine() // Tutorial Step 1
        joinChannel() // Tutorial Step 2
    }

    fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        Log.i(
            LOG_TAG,
            "checkSelfPermission $permission $requestCode"
        )
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            )
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission),
                requestCode
            )
            return false
        }
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(permission),
            requestCode
        )
        return true
    }

    fun showLongToast(msg: String?) {
        requireActivity().runOnUiThread(Runnable {
            Toast.makeText(
                getApplicationContext(),
                msg,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        leaveChannel()
        RtcEngine.destroy()
        mRtcEngine = null
    }


    // Tutorial Step 7
    fun onLocalAudioMuteClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)
        }

        // Stops/Resumes sending the local audio stream.
        mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
    }


    // Tutorial Step 5
    fun onSwitchSpeakerphoneClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
            //Normal Icon
        } else {
            //Muted Icon
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)
        }

        // Enables/Disables the audio playback route to the speakerphone.
        //
        // This method sets whether the audio is routed to the speakerphone or earpiece. After calling this method, the SDK returns the onAudioRouteChanged callback to indicate the changes.
        mRtcEngine!!.setEnableSpeakerphone(view.isSelected())
    }


    // Tutorial Step 3
    fun onEncCallClicked(view: View?) {
        requireActivity().onBackPressed()
    }


    // Tutorial Step 1
    private fun initializeAgoraEngine() {
        mRtcEngine = try {
            RtcEngine.create(requireContext(), getString(R.string.agora_app_id), mRtcEventHandler)
        } catch (e: Exception) {
            Log.e(
                LOG_TAG,
                Log.getStackTraceString(e)
            )
            throw RuntimeException(
                """
                     NEED TO check rtc sdk init fatal error
                     ${Log.getStackTraceString(e)}
                     """.trimIndent()
            )
        }
    }


    // Tutorial Step 2
    private fun joinChannel() {
        var accessToken: String? = agoraToken
//        if (TextUtils.equals(accessToken, "") || TextUtils.equals(
//                accessToken,
//                "#YOUR ACCESS TOKEN#"
//            )
//        ) {
//            accessToken = null // default, no token
//        }

        // Sets the channel profile of the Agora RtcEngine.
        // CHANNEL_PROFILE_COMMUNICATION(0): (Default) The Communication profile. Use this profile in one-on-one calls or group calls, where all users can talk freely.
        // CHANNEL_PROFILE_LIVE_BROADCASTING(1): The Live-Broadcast profile. Users in a live-broadcast channel have a role as either broadcaster or audience. A broadcaster can both send and receive streams; an audience can only receive streams.
        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)

        // Allows a user to join a channel.
        mRtcEngine!!.joinChannel(
            accessToken,
            channelName,
            "Extra Optional Data",
            0
        )
        if (requireArguments()["CALL_REQUEST"] == null) {
            var jsonObject = JSONObject()
            jsonObject.put("channelName", getUser(prefsManager)?.admin?._id)
            jsonObject.put("isForVideoCall", false)
            jsonObject.put("otherId", other._id)
            jsonObject.put("token", agoraToken)
            (requireActivity() as MainActivity).socketManager.connectCall(jsonObject, this)
        }
        // if you do not specify the uid, we will generate the uid for you
    }


    // Tutorial Step 3
    private fun leaveChannel() {
        mRtcEngine?.leaveChannel()
        lifecycleScope.launch {
            try {
                findNavController().popBackStack()
            } catch (e: Exception) {
                requireActivity().onBackPressed()
            }
        }
//        requireActivity().onBackPressed()
    }

    // Tutorial Step 4
    private fun onRemoteUserLeft(uid: Int, reason: Int) {
//        showLongToast(
//            String.format(
//                Locale.US, "user %d left %d",
//                uid and 0xFFFFFFFFL.toInt(), reason
//            )
//        )
//        val tipMsg: View = findViewById(R.id.quick_tips_when_use_agora_sdk) // optional UI
//        tipMsg.visibility = View.VISIBLE
        lifecycleScope.launch {
            requireActivity().onBackPressed()
        }
    }


    // Tutorial Step 6
    private fun onRemoteUserVoiceMuted(uid: Int, muted: Boolean) {
        showLongToast(
            String.format(
                Locale.US, "user %d muted or unmuted %b",
                uid and 0xFFFFFFFFL.toInt(), muted
            )
        )
    }

    override fun onMessageReceive(message: String, event: String) {
        when (event) {
            onAcceptCall -> {
                runTimer()

            }
            onRejectCall -> {
                leaveChannel()

            }
        }
    }

    private fun runTimer() {
        var seconds = 0
        var running = true
        // Get the text view.
        // Creates a new Handler
        val handler = Handler(Looper.getMainLooper())

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(object : Runnable {
            override fun run() {
                val hours: Int = seconds / 3600
                val minutes: Int = seconds % 3600 / 60
                val secs: Int = seconds % 60

                // Format the seconds into hours, minutes,
                // and seconds.
                val time = java.lang.String
                    .format(
                        Locale.getDefault(),
                        "%d:%02d:%02d", hours,
                        minutes, secs
                    )

                // Set the text view text.
                binding.txtCallDuration.text = time

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000)
            }
        })

    }

}
