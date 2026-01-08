package ru.otus.basicarchitecture

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddressFragment : Fragment() {

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressTextView =
            view.findViewById<AppCompatAutoCompleteTextView>(R.id.address_text_view)

        lifecycleScope.launch {
            viewModel.hints.collect { hintsList ->
                val suggestions = hintsList.map { it.value }
                val autoCompleteAdapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        suggestions
                    )
                addressTextView.setAdapter(autoCompleteAdapter)
            }
        }


        addressTextView.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val input = s.toString()
                    viewModel.getHints(input)
                }

                override fun afterTextChanged(s: Editable?) {}

            }
        )
    }

}