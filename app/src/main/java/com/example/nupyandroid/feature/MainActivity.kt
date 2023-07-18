package com.example.nupyandroid.feature

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nupyandroid.R
import com.example.nupyandroid.Util
import com.example.nupyandroid.Util.BLOCTO_MAINNET_APP_ID
import com.example.nupyandroid.Util.BLOCTO_TESTNET_APP_ID
import com.example.nupyandroid.base.BaseActivity
import com.example.nupyandroid.databinding.ActivityMainBinding
import com.example.nupyandroid.feature.chatroom.ChatRoomCodeFragment
import com.example.nupyandroid.feature.chatroom.ChatRoomFragment
import com.example.nupyandroid.feature.login.LoginFragment
import com.example.nupyandroid.feature.nft.NftDetailFragment
import com.example.nupyandroid.feature.nft.NftListFragment
import com.example.nupyandroid.feature.progressroom.ProgressCompleteFragment
import com.example.nupyandroid.feature.progressroom.ProgressIngFragment
import com.portto.fcl.Fcl
import com.portto.fcl.config.AppDetail
import com.portto.fcl.config.Network
import com.portto.fcl.provider.blocto.Blocto
import com.portto.fcl.provider.dapper.Dapper
import com.portto.fcl.ui.discovery.showConnectWalletDialog

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(
            )
        )[MainViewModel::class.java]
    }

    private lateinit var locationManager: LocationManager

    private var inProgressFragment = ProgressIngFragment.newInstance()
    private var completionProgressFragment = ProgressCompleteFragment.newInstance()
    private var roomCodeFragment = ChatRoomCodeFragment.newInstance()
    private var roomChatFragment = ChatRoomFragment.newInstance()
    private var nftListFragment = NftListFragment.newInstance()
    private var nftDetailFragment = NftDetailFragment.newInstance()
    private var loginFragment = LoginFragment.newInstance()

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
        // 초기 화면 설정
        replaceOutFragment(loginFragment)
//        setInitialFragment(inProgressFragment)
        binding.btnInProgress.setOnClickListener {
            replaceFragment(inProgressFragment)
            binding.btnInProgress.setTextColor(this.resources.getColor(R.color.gray800))
            binding.btnComplete.setTextColor(this.resources.getColor(R.color.gray400))
        }
        binding.btnComplete.setOnClickListener {
            replaceFragment(completionProgressFragment)
            binding.btnComplete.setTextColor(this.resources.getColor(R.color.gray800))
            binding.btnInProgress.setTextColor(this.resources.getColor(R.color.gray400))
        }
        binding.btnJoinSecretRoom.setOnClickListener {
            viewModel.setFlow(MainViewModel.Flow.ChatCode)
        }
        binding.btnNftList.setOnClickListener {
            viewModel.setFlow(MainViewModel.Flow.NftList)
        }

    }


    private fun initFcl(isMainnet: Boolean) {
        Fcl.init(
            env = if (isMainnet) Network.MAINNET else Network.TESTNET,
            appDetail = AppDetail(),
            supportedWallets = getWalletList(isMainnet)
        )
        showConnectWalletDialog(this@MainActivity) {
            viewModel.connect(
                withAccountPoof = true
            )
        }
    }

    private fun getWalletList(isMainnet: Boolean) = listOf(
        Blocto.getInstance(if (isMainnet) BLOCTO_MAINNET_APP_ID else BLOCTO_TESTNET_APP_ID),
        Dapper
    )

    override fun initObserves() {
        viewModel.flow.observe(this) {
            when (it) {
                MainViewModel.Flow.ChatCode -> {
                    replaceOutFragment(roomCodeFragment)
                }

                MainViewModel.Flow.ChatRoom -> {
                    replaceOutFragment(roomChatFragment)
                }

                MainViewModel.Flow.NftList -> {
                    replaceOutFragment(nftListFragment)
                }

                MainViewModel.Flow.NftDetail -> {
                    replaceOutFragment(nftDetailFragment)
                }

                MainViewModel.Flow.Login -> {
                    initFcl(false)
                }

                MainViewModel.Flow.LoginComplete -> {
                    removeOutFragment(loginFragment)
                    setInitialFragment(inProgressFragment)
                }

                else -> {}
            }
        }
    }

    private fun setInitialFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun replaceOutFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_out_container, fragment)
            .commit()
    }

    private fun removeOutFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
    }


    private fun permissionRequest() {
        val permissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.e("permission", "location permission granted")
                }

                permissions.getOrDefault(Manifest.permission.CAMERA, false) -> {
                    Log.e("permission", "camera permission granted")
                }

                else -> {
                    Util.createToast(this, "permission denied")
                }
            }
        }

        permissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
            )
        )
    }


    private fun getMylocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
//        val loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        viewModel.setLoc(loc?.latitude, loc?.longitude)
    }
}