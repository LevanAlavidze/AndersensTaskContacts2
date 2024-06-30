package com.example.myapptest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import com.squareup.picasso.Picasso
import androidx.fragment.app.activityViewModels

class ContactDetails : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext().applicationContext)
    }
    private var contact: Contacts? = null

    private lateinit var contactNameEditText: EditText
    private lateinit var contactSurnameEditText: EditText
    private lateinit var contactNumberEditText: EditText
    private lateinit var contactImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var editButton: Button

    private var isEditingMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_details, container, false)

        // Initialize views
        contactNameEditText = view.findViewById(R.id.contact_name_details)
        contactSurnameEditText = view.findViewById(R.id.contact_surname_details)
        contactNumberEditText = view.findViewById(R.id.contact_number_details)
        contactImageView = view.findViewById(R.id.contact_image_details)
        saveButton = view.findViewById(R.id.save_button)
        editButton = view.findViewById(R.id.edit_button)

        // Observe selected contact from SharedViewModel
        sharedViewModel.selectedContact.observe(viewLifecycleOwner) { selectedContact ->
            contact = selectedContact
            updateUI(contact)
        }

        editButton.setOnClickListener {
            toggleEditMode()
        }

        saveButton.setOnClickListener {
            contact?.let {
                it.name = contactNameEditText.text.toString()
                it.surname = contactSurnameEditText.text.toString()
                it.number = contactNumberEditText.text.toString()
                sharedViewModel.updateContact(it)
                toggleEditMode() // Exit editing mode

                // Notify adapter to update the specific contact
                val contactListFragment = parentFragmentManager.findFragmentById(R.id.fragment_container) as? ContactList
                contactListFragment?.updateContactInAdapter(it)

                if (!isTwoPane()) {
                    parentFragmentManager.popBackStack()
                }
                
            }
        }

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEditingMode) {
                    toggleEditMode() // Exit editing mode before navigating back
                } else {
                    parentFragmentManager.popBackStack()
                }
            }
        })

        return view
    }

    private fun updateUI(contact: Contacts?) {
        contact?.let {
            contactNameEditText.setText(it.name)
            contactSurnameEditText.setText(it.surname)
            contactNumberEditText.setText(it.number)
            Picasso.get().load(it.imageUrl).into(contactImageView)
        }
    }

    private fun toggleEditMode() {
        isEditingMode = !isEditingMode
        // Enable/disable EditTexts based on editing mode
        contactNameEditText.isEnabled = isEditingMode
        contactSurnameEditText.isEnabled = isEditingMode
        contactNumberEditText.isEnabled = isEditingMode

        // Change button text based on editing mode
        if (isEditingMode) {
            editButton.visibility = View.GONE // Hide edit button in edit mode if needed
            saveButton.visibility = View.VISIBLE
        } else {
            editButton.visibility = View.VISIBLE
            saveButton.visibility = View.GONE
        }
    }

    private fun isTwoPane(): Boolean {
        return activity?.findViewById<View?>(R.id.details_fragment_container) != null
    }
}