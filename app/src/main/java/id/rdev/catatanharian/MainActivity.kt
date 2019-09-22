package id.rdev.catatanharian

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_note.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()

        var  myRef : DatabaseReference? = database.getReference("notes")

        // Read Data
        myRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // looping ketika mengambil data
                // merah ? coba tambahkan ()
                val dataArray = ArrayList<Note>()
                for (i in dataSnapshot.children){
                    val data = i.getValue(Note::class.java)
                    data?.key = i.key
                    data?.let { dataArray.add(it) }
                }
                rvListNotes.adapter = NotesAdapter(dataArray, object : NotesAdapter.OnClick {
                    override fun edit(note: Note?) {
                        val intent = Intent(this@MainActivity, FormNoteActivity::class.java)
                        intent.putExtra("note", note)
                        startActivity(intent)
                    }

                    override fun delete(key: String?) {
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("Hapus ?")
                            setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                                myRef?.child(key.toString())?.removeValue()
//                                Toast.makeText(this@MainActivity, key, Toast.LENGTH_SHORT).show()
                            }
                            setNegativeButton("Tidak", { dialogInterface: DialogInterface, i: Int -> })
                        }.show()
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException())
            }
        })

        btAddNote.setOnClickListener {
            startActivity(Intent(this@MainActivity, FormNoteActivity::class.java))
        }
    }
}
