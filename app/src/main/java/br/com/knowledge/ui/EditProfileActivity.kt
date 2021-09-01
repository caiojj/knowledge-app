package br.com.knowledge.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.knowledge.R
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.core.extensions.loadingImage
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.databinding.ActivityEditProfileBinding
import br.com.knowledge.presentation.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<EditProfileViewModel>()
    private val dialog by lazy {  createProgressDialog() }
    private val activeUser: ActiveUser by lazy { initUser() }
    private lateinit var image: Uri

    companion object {
        private val PERMISSION_CODE_IMAGE_PICK = 100
        private val IMAGE_PICK_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindingListeners()
        bindingObserver()
        initComponent()
    }

    private fun initUser() : ActiveUser {
        return intent.getSerializableExtra("activeUser") as ActiveUser
    }

    private fun initComponent() {
        binding.tieName.setText(activeUser.name)
        binding.tieUser.setText(activeUser.nameUser)
        loadingImage(binding.root.context, activeUser.imageUrl, binding.ivProfile)
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {

                EditProfileViewModel.State.SavedUrl -> dialog.dismiss()
                EditProfileViewModel.State.Loading -> {
                    dialog.show()
                }
                is EditProfileViewModel.State.Uploaded -> {
                    if(it.res.isSuccessful)  {
                        binding.ivProfile.setImageURI(image)
                        it.res.body()?.let { res ->
                            viewModel.updateImage(res.imageUrl, activeUser.id)
                        }
                    } else {
                        dialog.dismiss()
                        createDialog {
                            setMessage("${it.res.message()}")
                        }
                    }
                }
                is EditProfileViewModel.State.Error -> {
                    dialog.dismiss()
                    Log.e("Uploaded Error", "${it.error}")
                }
            }
        }
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
                // Implementação de uma activity que da mais
                // detahes para o usuário do porque a permissão e necessária
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.let {
                viewModel.uploadImage(activeUser.token, activeUser.id, it)
            }
            data?.data?.let {
                image = it
            }
        }
    }
}