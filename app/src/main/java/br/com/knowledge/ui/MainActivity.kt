package br.com.knowledge.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.knowledge.R
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.databinding.ActivityMainBinding
import br.com.knowledge.presentation.MainViewModel
import br.com.knowledge.presentation.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var userActive: ActiveUser
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel  by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindingObserver()
        setFragment(ArticleFragment())
        viewModel.getActiveUser()
        initBottomNavigation()
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                is State.ObtainedUser -> {
                    userActive = it.activeUser.first()
                }
                else -> {}
            }
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.view_articles -> {
                    setFragment(ArticleFragment())
                    true
                }
                R.id.view_edit_article -> {
                    it.isCheckable = false
                    val intent = Intent(this, WriteArticleActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.view_profile -> {
                    setFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fr: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.main_frame_layout, fr)
        beginTransaction.commit()
    }
}