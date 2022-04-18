package com.talktomii.utlis

import android.app.Activity
import android.util.Log
import com.talktomii.data.model.UserData
import com.google.gson.Gson
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URI

class SocketManager {
    private lateinit var userData: UserData
    var socket: Socket? = null

    var activity: Activity?= null

    companion object {

        // Listen Events
        const val LISTEN_LOCATION = "sendLocationResp"
        const val JOIN = "join"
        const val connectCall = "connectCall"

        //Emit Events
        const val SEND_LOCATION = "send-location"

        private var INSTANCE: SocketManager? = null

        fun getInstance() = INSTANCE
            ?: synchronized(SocketManager::class.java) {
                INSTANCE
                    ?: SocketManager()
                        .also { INSTANCE = it }
            }

        /**
         * Disconnects from current instance and also releases references to it
         * so that a new instance will be created next time.
         * */
        fun destroy() {
            Log.e("Socket", "Destroying socket instance")
            INSTANCE?.disconnect()
            INSTANCE = null
        }
    }

    fun connect(activity:Activity,prefsManager: PrefsManager) {
        this.activity = activity
        val options = IO.Options()
        options.forceNew = false
        options.reconnection = true
        var user= getUser(prefsManager)
        socket = IO.socket(URI.create("https://api.talktomii.com"),
            options
        )
        socket?.connect()
        socket?.on(Socket.EVENT_CONNECT) {
            Log.e("Socket", "Socket Connect ${socket?.id()}")
        }
        socket?.on(Socket.EVENT_DISCONNECT) {
            Log.e("Socket", "Socket Disconnect")
        }
//        socket?.on(Socket.TIM) {
//            Log.e("Socket", "Socket timeout")
//        }
//        socket?.on(Socket.EVENT_ERROR) {
//            Log.e("Socket", "Socket error")
//        }
        socket?.on(Socket.EVENT_CONNECT_ERROR) {
            Log.e("Socket", "Socket error second${Gson().toJson(it[0])}")
        }
    }
    fun disconnect() {
        socketOff()
        socket?.off()
        socket?.disconnect()
        //  Timber.d("Disconnect")
    }

    fun on(event: String, listener: Emitter.Listener) {
        socket?.on(event, listener)
    }

    fun off(event: String, listener: Emitter.Listener) {
        socket?.off(event, listener)
    }

    fun emit(event: String, args: Any, acknowledge: Ack) {
        socket?.emit(event, args, acknowledge)
    }

    fun emit(event: String, args: Any) {
        socket?.emit(event, args)
    }

    fun sendLocation(arg: JSONObject, msgAck: OnMessageReceiver) {
        socket?.emit(
            SEND_LOCATION, arg,
            Ack { args ->
                println("------------------" + args[0])
                msgAck.onMessageReceive(
                    args[0].toString(),
                    SEND_LOCATION
                )
            })
    }
    fun joinApp(arg: JSONObject, msgAck: OnMessageReceiver) {
        socket?.emit(
            JOIN, arg,
            Ack { args ->
                println("------------------" + args[0])
                msgAck.onMessageReceive(
                    args[0].toString(),
                    JOIN
                )
            })
    }
    fun connectCall(arg: JSONObject, msgAck: OnMessageReceiver) {
        socket?.emit(
            connectCall, arg,
            Ack { args ->
                println("------------------" + args[0])
                msgAck.onMessageReceive(
                    args[0].toString(),
                    connectCall
                )
            })
    }
    // Listen Events
    fun onLocation(msgAck: OnMessageReceiver) {
        socket?.on(LISTEN_LOCATION) { args ->
            msgAck.onMessageReceive(
                args[0].toString(),
                LISTEN_LOCATION
            )
        }
    }
    fun onCallConnect(msgAck: OnMessageReceiver) {
        socket?.on(connectCall) { args ->
            msgAck.onMessageReceive(
                args[0].toString(),
                connectCall
            )
        }
    }
    private fun socketOff() {
        socket?.off(LISTEN_LOCATION)
    }

    interface OnMessageReceiver {
        fun onMessageReceive(message: String, event: String)
    }
}