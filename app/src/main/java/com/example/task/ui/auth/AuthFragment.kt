package com.example.task.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class AuthFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentAuthBinding
    private lateinit var signInRequest: BeginSignInRequest
    private val REQ_ONE_TAP = 2
    private lateinit var oneTapClient: SignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        binding.btnGoogle.setOnClickListener {
            signInWithGoogle()
        }

    }

    private fun signInWithGoogle() {
        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener {
            startIntentSenderForResult(
                it.pendingIntent.intentSender, REQ_ONE_TAP,
                null, 0, 0, 0, null
            )
        }.addOnFailureListener {
            Log.e("ololo", "singInGoogle: " + it.message)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_ONE_TAP -> try {
                val credential: SignInCredential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    mAuth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(
                            requireActivity()
                        ) { task ->
                            if (task.isSuccessful) {
                                findNavController().navigateUp()
                            } else {
                                Log.e("aaa", "onActivityResult: " + task.exception?.message)
                            }
                        }
                }
            } catch (_: ApiException) {
            }
        }
    }
}