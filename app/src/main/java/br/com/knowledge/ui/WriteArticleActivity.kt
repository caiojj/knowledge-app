package br.com.knowledge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.knowledge.R
import br.com.knowledge.databinding.ActivityWriteArticleBinding

class WriteArticleActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWriteArticleBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}