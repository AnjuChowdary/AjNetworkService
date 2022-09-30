package com.ae.ajnetworkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ae.ajnetworkdemo.databinding.ActivityMainBinding
import com.ae.ajnetworkservice.repository.AjNetworkingService
import com.ae.ajnetworkservice.utils.NetworkResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val mTAG = MainActivity::class.java.simpleName
    private lateinit var mViewBinding: ActivityMainBinding

    //regionAjNetworkService Initialization
    private val mNetworkingService: AjNetworkingService
        get() {
            return AjNetworkingService(baseUrl = "https://reqres.in")
        }
    //endregion

    var mNetworkResponse: MutableStateFlow<NetworkResponse<UserResponse?>> = MutableStateFlow(NetworkResponse.Idle())
    var mCreateUserNetworkResponse: MutableStateFlow<NetworkResponse<NewUserResponse?>> = MutableStateFlow(NetworkResponse.Idle())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //region Enable ViewBinding
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        //endregion

        observeResponse()

        //region Get Call Sample
        mViewBinding.checkButton.setOnClickListener{
            launch {
                mNetworkResponse.value = mNetworkingService.getCall(endpoint = "/api/users?page=2")
            }
        }
        //endregion

        //region Post Call Sample
        mViewBinding.postButton.setOnClickListener {
            launch {
                mCreateUserNetworkResponse.value = mNetworkingService.postCall(endpoint = "/api/users", request = NewUser(name = "Anju", job = "SWE"))
            }
        }
        //endregion
    }

    private fun observeResponse() {
        launch {
            mNetworkResponse.collectLatest {
                when(it) {
                    is NetworkResponse.Loading -> {
                        Log.i(mTAG, "Loading ===============")
                    }
                    is NetworkResponse.Success -> {
                        Log.i(mTAG, "Success: "+it.response)
                    }
                    is NetworkResponse.Error -> {
                        Log.i(mTAG, "Error: "+it.errorMessage)
                    }
                    is NetworkResponse.Idle -> {
                        Log.i(mTAG, "Idle ..........")
                    }
                }
            }

            mCreateUserNetworkResponse.collectLatest {
                when(it) {
                    is NetworkResponse.Loading -> {
                        Log.i(mTAG, "Loading ===============")
                    }
                    is NetworkResponse.Success -> {
                        Log.i(mTAG, "Creaet User Success: "+it.response)
                    }
                    is NetworkResponse.Error -> {
                        Log.i(mTAG, "Error: "+it.errorMessage)
                    }
                    is NetworkResponse.Idle -> {
                        Log.i(mTAG, "Idle ..........")
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}