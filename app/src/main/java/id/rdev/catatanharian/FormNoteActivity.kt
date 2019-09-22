package id.rdev.catatanharian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form_note.*
import java.util.HashMap

class FormNoteActivity : AppCompatActivity() {
    var note : Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_note)

        val data = intent.getSerializableExtra("note")
        var edit = true

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("notes")

        if (data != null) {
            note = data as Note
            etNoteNameEdit.setText(note?.title)
            etNoteDescriptionEdit.setText(note?.description)

            btActForm.setText("Edit")
        } else {
            btActForm.setText("Tambah")
            edit = false
        }

        btActForm.setOnClickListener {
            if (edit) {
                val changeData = HashMap<String, Any>()
                changeData.put("title", etNoteNameEdit.text.toString())
                changeData.put("description", etNoteDescriptionEdit.text.toString())

                myRef.child(note?.key.toString()).updateChildren(changeData)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val key = myRef.push().key

                val newNote = Note()
                newNote.title = etNoteNameEdit.text.toString()
                newNote.description = etNoteDescriptionEdit.text.toString()

                myRef.child(key.toString()).setValue(newNote)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}
