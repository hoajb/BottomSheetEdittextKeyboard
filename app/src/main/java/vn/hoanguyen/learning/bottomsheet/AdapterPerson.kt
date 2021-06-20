package vn.hoanguyen.learning.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by Hoa Nguyen on Jun 15 2021.
 */
class AdapterPerson(private val allList: List<Person> = emptyList()) :
    RecyclerView.Adapter<ViewHolderPerson>(), Filterable {

    private val filteredList: MutableList<Person> by lazy {
        print("allList size = ${allList.size}")
        allList.toMutableList()
    }

    private fun submitList(filteredList: List<Person>) {
        this.filteredList.clear()
        this.filteredList.addAll(filteredList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPerson {
        return ViewHolderPerson(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_person, parent, false)
        )
    }

    private fun getItemAt(position: Int) = filteredList[position]

    override fun onBindViewHolder(holder: ViewHolderPerson, position: Int) {
        holder.bindingItem(getItemAt(position))
    }

    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Person>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(allList)
            } else {
                for (item in allList) {
                    if (item.name.lowercase(Locale.getDefault()).startsWith(
                            constraint.toString()
                                .lowercase(Locale.getDefault())
                        )
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as List<Person>)
        }

    }
}

class ViewHolderPerson(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val txtName: TextView = itemView.findViewById(R.id.txtName)
    private val txtNumber: TextView = itemView.findViewById(R.id.txtNumber)

    fun bindingItem(item: Person) {
        txtNumber.text = adapterPosition.toString()
        txtName.text = item.name

        itemView.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                if (layoutPosition % 2 == 0) android.R.color.white else android.R.color.darker_gray
            )
        )
    }
}



