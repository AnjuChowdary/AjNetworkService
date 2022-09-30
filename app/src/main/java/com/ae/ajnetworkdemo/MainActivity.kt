package com.ae.ajnetworkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ae.ajnetworkdemo.databinding.ActivityMainBinding
import com.ae.ajnetworkservice.repository.AjNetworkingService
import com.ae.ajnetworkservice.utils.NetworkResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
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

        //region Get Call Sample
        mViewBinding.checkButton.setOnClickListener{
            Log.i(mTAG, "check Button Clicked********")
            runBlocking {
                launch {
                    mNetworkResponse.value = mNetworkingService.getCall(endpoint = "/api/users?page=2")
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
                }
            }
        }
        //endregion

        //region Post Call Sample
        mViewBinding.postButton.setOnClickListener {
            Log.i(mTAG, "Post Button Clicked")
            runBlocking {
                launch {
                    mCreateUserNetworkResponse.value = mNetworkingService.postCall(endpoint = "/api/users", request = NewUser(name = "Anju", job = "SWE"))
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
        }
        //endregion
    }
}