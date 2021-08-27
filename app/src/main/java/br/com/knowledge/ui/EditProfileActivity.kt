package br.com.knowledge.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.knowledge.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    companion object {
        private val PERMISSION_CODE_IMAGE_PICK = 100
        private val IMAGE_PICK_CODE = 101
    }

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindingListeners()
    }

    private fun bindingListeners() {
        binding.tvAlterarPerfil.setOnClickListener {
            requestPermission(
                PERMISSION_CODE_IMAGE_PICK,
                Manifest.permission.READ_EXTERNAL_STORAGE) { pickImageFromGallery() }
        }
    }

    private fun requestPermission(codePermission: Int, requestPermission: String, params: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(requestPermission) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(requestPermission)
                requestPermissions(permission, codePermission)
            } else {
                params()
            }
        } else {
            params()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            PERMISSION_CODE_IMAGE_PICK -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    // Abri uma activity que esclarece por que esta
                    // permissão e necessario para o funcionamento do app
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("PICK", "PEGAR MENSAGEM")
        Log.e("Result", "${resultCode == Activity.RESULT_OK}-${requestCode == IMAGE_PICK_CODE}")

        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ivProfile.setImageURI(data?.data)
        }
    }
}