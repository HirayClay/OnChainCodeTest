package com.onchain.test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onchain.test.databinding.ActivityMainBinding
import com.onchain.test.ui.adapter.WalletBalanceAdapter
import com.onchain.test.vm.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val walletBalanceAdapter = WalletBalanceAdapter()
    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUi()
    }

    private fun initUi() {
        viewBinding.walletRecyclerView.adapter = walletBalanceAdapter

        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        //update balance list for all tokens
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.assetDataFlow.collect{
                    walletBalanceAdapter.update(it)
                }
            }
        }
    }

}