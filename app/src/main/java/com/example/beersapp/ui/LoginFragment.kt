package com.example.beersapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beersapp.R
import com.example.beersapp.databinding.FragmentLoginBinding
import com.example.beersapp.ui.viewmodel.MainViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginShareViewModel = mainViewModel

        setupObservers()
    }

    private fun setupObservers() {
        mainViewModel.eventLoginMade.observe(viewLifecycleOwner, {
            if (it) {
                goToWelcome()
                mainViewModel.goToMainScreenComplete()
            }
        })
    }

    private fun goToWelcome() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }
}