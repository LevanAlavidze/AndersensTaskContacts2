package com.example.myapptest

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel(context: Context) : ViewModel() {

    private val _selectedContact = MutableLiveData<Contacts>()
    val selectedContact: LiveData<Contacts>
        get() = _selectedContact

    private val _contactsList = MutableLiveData<List<Contacts>>()
    val contactsList: LiveData<List<Contacts>>
        get() = _contactsList

    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    init {
        _contactsList.value = loadContacts() ?: generatePredefinedContacts().also { saveContacts(it) }
    }

    fun selectContact(contact: Contacts) {
        _selectedContact.value = contact
    }

    fun updateContact(updatedContact: Contacts) {
        val currentList = _contactsList.value.orEmpty().toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedContact.id }
        if (index != -1) {
            currentList[index] = updatedContact
            _contactsList.value = currentList
            saveContacts(currentList)
        }
    }

    private fun generatePredefinedContacts(): List<Contacts> {
        return listOf(
            Contacts(1, "Alice", "Smith", "+995555555555", "https://picsum.photos/200?random=1"),
            Contacts(2, "Bob", "Johnson", "+9555545557585", "https://picsum.photos/200?random=2"),
            Contacts(3, "Charlie", "Williams", "+998888888888", "https://picsum.photos/200?random=3"),
            Contacts(4, "Olivia", "Brown", "+996666666666", "https://picsum.photos/200?random=4"),
            Contacts(5, "Emma", "Jones", "+997777777777", "https://picsum.photos/200?random=5"),
            Contacts(6, "Sophia", "Miller", "+9977777123123", "https://picsum.photos/200?random=6"),
            Contacts(7, "Noah", "Davis", "+9977777747585", "https://picsum.photos/200?random=7"),
            Contacts(8, "Isabella", "Garcia", "+997777777127", "https://picsum.photos/200?random=8"),
            Contacts(9, "Michael", "Rodriguez", "+995555555555", "https://picsum.photos/200?random=9"),
            Contacts(10, "Emily", "Wilson", "+9555545557585", "https://picsum.photos/200?random=10"),
            Contacts(11, "Ava", "Brown", "+998888888888", "https://picsum.photos/200?random=11"),
            Contacts(12, "Mia", "Jones", "+996666666666", "https://picsum.photos/200?random=12"),
            Contacts(13, "Avery", "Miller", "+997777777777", "https://picsum.photos/200?random=13"),
            Contacts(14, "Ella", "Davis", "+9977777123123", "https://picsum.photos/200?random=14"),
            Contacts(15, "Zoe", "Garcia", "+9977777747585", "https://picsum.photos/200?random=15"),
            Contacts(16, "Avery", "Rodriguez", "+997777777127", "https://picsum.photos/200?random=16"),
            Contacts(17, "Ella", "Wilson", "+995555555555", "https://picsum.photos/200?random=17"),
            Contacts(18, "Zoe", "Brown", "+9555545557585", "https://picsum.photos/200?random=18"),
            Contacts(19, "Avery", "Jones", "+998888888888", "https://picsum.photos/200?random=19"),
            Contacts(20, "Ella", "Miller", "+996666666666", "https://picsum.photos/200?random=20"),
            Contacts(21, "Zoe", "Davis", "+997777777777", "https://picsum.photos/200?random=21"),
            Contacts(22, "Avery", "Garcia", "+9977777123123", "https://picsum.photos/200?random=22"),
            Contacts(23, "Ella", "Rodriguez", "+9977777747585", "https://picsum.photos/200?random=23"),
            Contacts(24, "Zoe", "Wilson", "+997777777127", "https://picsum.photos/200?random=24"),
            Contacts(25, "Avery", "Brown", "+995555555555", "https://picsum.photos/200?random=25"),
            Contacts(26, "Ella", "Jones", "+9555545557585", "https://picsum.photos/200?random=26"),
            Contacts(27, "Zoe", "Miller", "+998888888888", "https://picsum.photos/200?random=27"),
            Contacts(28, "Avery", "Davis", "+996666666666", "https://picsum.photos/200?random=28"),
            Contacts(29, "Ella", "Garcia", "+997777777777", "https://picsum.photos/200?random=29"),
            Contacts(30, "Zoe", "Rodriguez", "+9977777123123", "https://picsum.photos/200?random=30"),
            Contacts(31, "Alice", "Smith", "+995555555555", "https://picsum.photos/200?random=1"),
            Contacts(32, "Bob", "Johnson", "+9555545557585", "https://picsum.photos/200?random=2"),
            Contacts(33, "Charlie", "Williams", "+998888888888", "https://picsum.photos/200?random=3"),
            Contacts(34, "Olivia", "Brown", "+996666666666", "https://picsum.photos/200?random=4"),
            Contacts(35, "Emma", "Jones", "+997777777777", "https://picsum.photos/200?random=5"),
            Contacts(36, "Sophia", "Miller", "+9977777123123", "https://picsum.photos/200?random=6"),
            Contacts(37, "Noah", "Davis", "+9977777747585", "https://picsum.photos/200?random=7"),
            Contacts(38, "Isabella", "Garcia", "+997777777127", "https://picsum.photos/200?random=8"),
            Contacts(39, "Michael", "Rodriguez", "+995555555555", "https://picsum.photos/200?random=9"),
            Contacts(40, "Emily", "Wilson", "+9555545557585", "https://picsum.photos/200?random=10"),
            Contacts(41, "Ava", "Brown", "+998888888888", "https://picsum.photos/200?random=11"),
            Contacts(42, "Mia", "Jones", "+996666666666", "https://picsum.photos/200?random=12"),
            Contacts(43, "Avery", "Miller", "+997777777777", "https://picsum.photos/200?random=13"),
            Contacts(44, "Ella", "Davis", "+9977777123123", "https://picsum.photos/200?random=14"),
            Contacts(45, "Zoe", "Garcia", "+9977777747585", "https://picsum.photos/200?random=15"),
            Contacts(46, "Avery", "Rodriguez", "+997777777127", "https://picsum.photos/200?random=16"),
            Contacts(47, "Ella", "Wilson", "+995555555555", "https://picsum.photos/200?random=17"),
            Contacts(48, "Zoe", "Brown", "+9555545557585", "https://picsum.photos/200?random=18"),
            Contacts(49, "Avery", "Jones", "+998888888888", "https://picsum.photos/200?random=19"),
            Contacts(40, "Ella", "Miller", "+996666666666", "https://picsum.photos/200?random=20"),
            Contacts(51, "Zoe", "Davis", "+997777777777", "https://picsum.photos/200?random=21"),
            Contacts(52, "Avery", "Garcia", "+9977777123123", "https://picsum.photos/200?random=22"),
            Contacts(53, "Ella", "Rodriguez", "+9977777747585", "https://picsum.photos/200?random=23"),
            Contacts(54, "Zoe", "Wilson", "+997777777127", "https://picsum.photos/200?random=24"),
            Contacts(55, "Avery", "Brown", "+995555555555", "https://picsum.photos/200?random=25"),
            Contacts(56, "Ella", "Jones", "+9555545557585", "https://picsum.photos/200?random=26"),
            Contacts(57, "Zoe", "Miller", "+998888888888", "https://picsum.photos/200?random=27"),
            Contacts(58, "Avery", "Davis", "+996666666666", "https://picsum.photos/200?random=28"),
            Contacts(59, "Ella", "Garcia", "+997777777777", "https://picsum.photos/200?random=29"),
            Contacts(60, "Zoe", "Rodriguez", "+9977777123123", "https://picsum.photos/200?random=30"),
            Contacts(61, "Alice", "Smith", "+995555555555", "https://picsum.photos/200?random=1"),
            Contacts(62, "Bob", "Johnson", "+9555545557585", "https://picsum.photos/200?random=2"),
            Contacts(63, "Charlie", "Williams", "+998888888888", "https://picsum.photos/200?random=3"),
            Contacts(64, "Olivia", "Brown", "+996666666666", "https://picsum.photos/200?random=4"),
            Contacts(65, "Emma", "Jones", "+997777777777", "https://picsum.photos/200?random=5"),
            Contacts(66, "Sophia", "Miller", "+9977777123123", "https://picsum.photos/200?random=6"),
            Contacts(67, "Noah", "Davis", "+9977777747585", "https://picsum.photos/200?random=7"),
            Contacts(68, "Isabella", "Garcia", "+997777777127", "https://picsum.photos/200?random=8"),
            Contacts(69, "Michael", "Rodriguez", "+995555555555", "https://picsum.photos/200?random=9"),
            Contacts(70, "Emily", "Wilson", "+9555545557585", "https://picsum.photos/200?random=10"),
            Contacts(71, "Ava", "Brown", "+998888888888", "https://picsum.photos/200?random=11"),
            Contacts(72, "Mia", "Jones", "+996666666666", "https://picsum.photos/200?random=12"),
            Contacts(73, "Avery", "Miller", "+997777777777", "https://picsum.photos/200?random=13"),
            Contacts(74, "Ella", "Davis", "+9977777123123", "https://picsum.photos/200?random=14"),
            Contacts(75, "Zoe", "Garcia", "+9977777747585", "https://picsum.photos/200?random=15"),
            Contacts(76, "Avery", "Rodriguez", "+997777777127", "https://picsum.photos/200?random=16"),
            Contacts(77, "Ella", "Wilson", "+995555555555", "https://picsum.photos/200?random=17"),
            Contacts(78, "Zoe", "Brown", "+9555545557585", "https://picsum.photos/200?random=18"),
            Contacts(79, "Avery", "Jones", "+998888888888", "https://picsum.photos/200?random=19"),
            Contacts(80, "Ella", "Miller", "+996666666666", "https://picsum.photos/200?random=20"),
            Contacts(81, "Zoe", "Davis", "+997777777777", "https://picsum.photos/200?random=21"),
            Contacts(82, "Avery", "Garcia", "+9977777123123", "https://picsum.photos/200?random=22"),
            Contacts(83, "Ella", "Rodriguez", "+9977777747585", "https://picsum.photos/200?random=23"),
            Contacts(84, "Zoe", "Wilson", "+997777777127", "https://picsum.photos/200?random=24"),
            Contacts(85, "Avery", "Brown", "+995555555555", "https://picsum.photos/200?random=25"),
            Contacts(86, "Ella", "Jones", "+9555545557585", "https://picsum.photos/200?random=26"),
            Contacts(87, "Zoe", "Miller", "+998888888888", "https://picsum.photos/200?random=27"),
            Contacts(88, "Avery", "Davis", "+996666666666", "https://picsum.photos/200?random=28"),
            Contacts(89, "Ella", "Garcia", "+997777777777", "https://picsum.photos/200?random=29"),
            Contacts(90, "Zoe", "Rodriguez", "+9977777123123", "https://picsum.photos/200?random=30"),
            Contacts(91, "Alice", "Smith", "+995555555555", "https://picsum.photos/200?random=1"),
            Contacts(92, "Bob", "Johnson", "+9555545557585", "https://picsum.photos/200?random=2"),
            Contacts(93, "Charlie", "Williams", "+998888888888", "https://picsum.photos/200?random=3"),
            Contacts(94, "Olivia", "Brown", "+996666666666", "https://picsum.photos/200?random=4"),
            Contacts(95, "Emma", "Jones", "+997777777777", "https://picsum.photos/200?random=5"),
            Contacts(96, "Sophia", "Miller", "+9977777123123", "https://picsum.photos/200?random=6"),
            Contacts(97, "Noah", "Davis", "+9977777747585", "https://picsum.photos/200?random=7"),
            Contacts(98, "Isabella", "Garcia", "+997777777127", "https://picsum.photos/200?random=8"),
            Contacts(99, "Michael", "Rodriguez", "+995555555555", "https://picsum.photos/200?random=9"),
            Contacts(100, "Emily", "Wilson", "+9555545557585", "https://picsum.photos/200?random=10")
        )
    }

    private fun saveContacts(contacts: List<Contacts>) {
        val editor = sharedPreferences.edit()
        editor.clear()
        contacts.forEach { contact ->
            val contactString = "${contact.name}|${contact.surname}|${contact.number}|${contact.imageUrl}"
            editor.putString(contact.id.toString(), contactString)
        }
        editor.apply()
    }

    private fun loadContacts(): List<Contacts>? {
        val contacts = mutableListOf<Contacts>()
        sharedPreferences.all.forEach { (key, value) ->
            if (value is String) {
                val parts = value.split("|")
                if (parts.size == 4) {
                    contacts.add(Contacts(key.toInt(), parts[0], parts[1], parts[2], parts[3]))
                }
            }
        }
        return if (contacts.isEmpty()) null else contacts
    }
}