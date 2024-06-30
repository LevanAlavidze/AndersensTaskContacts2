import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapptest.Contacts
import com.example.myapptest.R
import com.squareup.picasso.Picasso

class ContactListAdapter(
    private val context: Context,
    private var contactsList: List<Contacts>,
    private var onItemClickListener: ((Int, Contacts) -> Unit)? = null

) : RecyclerView.Adapter<ContactViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.nameTextView.text = contact.name
        holder.numberTextView.text = contact.number
        holder.surnameTextView.text = contact.surname
        Picasso.get().load(contact.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position, contact)
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes") { _, _ ->
                    contactsList = contactsList.toMutableList().apply { removeAt(position) }
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, contactsList.size)
                }
                .setNegativeButton("No", null)
                .show()
            true
        }
    }

    override fun getItemCount(): Int = contactsList.size

    fun updateList(newList: List<Contacts>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactsList, newList))
        contactsList = newList
        diffResult.dispatchUpdatesTo(this)
    }
    fun updateContact(updatedContact: Contacts) {
        val index = contactsList.indexOfFirst { it.id == updatedContact.id }
        if (index != -1) {
            contactsList = contactsList.toMutableList().apply {
                set(index, updatedContact)
            }
            notifyItemChanged(index)
        }
    }
}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
    val surnameTextView: TextView = itemView.findViewById(R.id.contact_surname)
    val numberTextView: TextView = itemView.findViewById(R.id.contact_number)
    val imageView: ImageView = itemView.findViewById(R.id.contact_image)
}


class ContactDiffCallback(
    private val oldList: List<Contacts>,
    private val newList: List<Contacts>
): DiffUtil.Callback(){
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].imageUrl == newList[newItemPosition].imageUrl
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
