package com.example.myapptest

import ContactListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactList : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext().applicationContext)
    }
    private lateinit var contactListAdapter: ContactListAdapter
    private lateinit var searchView: SearchView
    private var isTwoPane: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        isTwoPane = view.findViewById<View?>(R.id.details_fragment_container) != null

        // Initialize RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.contact_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        // Initialize the adapter
        contactListAdapter = ContactListAdapter(requireContext(), emptyList()) { contactPosition: Int, contact: Contacts ->
            sharedViewModel.selectContact(contact)
            openContactDetails()
        }
        recyclerView.adapter = contactListAdapter

        // Observe contacts list from ViewModel
        sharedViewModel.contactsList.observe(viewLifecycleOwner) { contacts ->
            contactListAdapter = ContactListAdapter(requireContext(), contacts) { contactPosition: Int, contact: Contacts ->
                sharedViewModel.selectContact(contact)
                if (isTwoPane) {
                    openContactDetailsInTwoPane()
                } else {
                    openContactDetails()
                }
            }
            recyclerView.adapter = contactListAdapter
        }

        // Initialize and set SearchView listener
        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.orEmpty())
                return false
            }
        })

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        return view
    }

    private fun openContactDetails() {
        val fragment = ContactDetails()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun openContactDetailsInTwoPane() {
        val fragment = ContactDetails()
        parentFragmentManager.beginTransaction()
            .replace(R.id.details_fragment_container, fragment)
            .commit()
    }

    private fun filter(text: String) {
        val filteredList = sharedViewModel.contactsList.value?.filter {
            it.name.contains(text, ignoreCase = true) || it.surname.contains(text, ignoreCase = true)
        }
        contactListAdapter.updateList(filteredList ?: emptyList())
    }
}