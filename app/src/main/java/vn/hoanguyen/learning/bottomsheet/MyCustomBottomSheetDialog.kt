package vn.hoanguyen.learning.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by Hoa Nguyen on Jun 15 2021.
 */
class MyCustomBottomSheetDialog : BottomSheetDialogFragment() {
    companion object {
        const val KEY_LIST = "key_list"
        fun newInstance(listPerson: List<Person>): MyCustomBottomSheetDialog {
            return MyCustomBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_LIST, ArrayList(listPerson))
                }
            }
        }
    }

    private val listPerson: List<Person> by lazy {
        arguments?.getParcelableArrayList(KEY_LIST) ?: emptyList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_dialog_bottom_sheet_custom, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val edittext = view.findViewById<EditText>(R.id.edtSearch)
        val recyclerViewList = view.findViewById<RecyclerView>(R.id.recyclerViewList)

        val adapterList = AdapterPerson(listPerson)
        with(recyclerViewList) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = adapterList
        }

        edittext.doAfterTextChanged { input ->
            adapterList.filter.filter(input)
        }
    }
}