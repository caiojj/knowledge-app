package br.com.knowledge.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.knowledge.R
import br.com.knowledge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment(ArticleFragment())
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.view_articles -> {
                    setFragment(ArticleFragment())
                    true
                }
                R.id.view_edit_article -> {

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