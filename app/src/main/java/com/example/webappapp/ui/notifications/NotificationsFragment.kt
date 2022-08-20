package com.example.webappapp.ui.notifications

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.webappapp.MainActivity
import com.example.webappapp.databinding.FragmentNotificationsBinding
import us.zoom.sdk.*

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity

    private var zoomID = "9999706950"
    private var zoompw = "3Qjzz6"

    private fun initializeSdk(context: Context) {
        val sdk = ZoomSDK.getInstance()

        val params = ZoomSDKInitParams().apply {
            appKey = "aI8eBOpQKSoulvEpydKIPNb8bsMWCWhuBaTG"
            appSecret = "Zir0MaweEJlEUcK5Fzry74oO5oEPoCjG9IlZ"
            domain = "zoom.us"
            enableLog = true // Optional: enable logging for debugging
        }

        val listener = object: ZoomSDKInitializeListener {
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
                Log.d(ContentValues.TAG, "onZoomSDKInitializeResult: $errorCode / $internalErrorCode")
            }

            override fun onZoomAuthIdentityExpired() {
                Log.d(ContentValues.TAG, "onZoomAuthIdentityExpired: ")
            }
        }

        sdk.initialize(context, listener, params)
    }

    private fun joinMeeting(context: Context, meetingNumber: String, meetingPassword: String) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val options = JoinMeetingOptions()
        val params = JoinMeetingParams().apply {
            displayName = "Android"
            meetingNo = meetingNumber
            password = meetingPassword
        }

        meetingService.joinMeetingWithParams(context, params, options)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSdk(mainActivity)
        binding.joinBtn.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                joinMeeting(mainActivity, zoomID, zoompw)
            }, 4000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}