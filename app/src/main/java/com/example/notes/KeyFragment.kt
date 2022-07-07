package com.example.notes

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class KeyFragment : Fragment() {

    lateinit var adapter: WordAdapter
    lateinit var helper:DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        helper = DBHelper(getContext(),"note.db",3)

        adapter = WordAdapter()

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(getContext())
        recycler.adapter = adapter
        readInDb()
        return view
    }

    @SuppressLint("Range")
    private fun readInDb() {
        val db = helper.readableDatabase
        val cursor = db.query(Word.TABLE,null,null,null,null,null,
            "${Word.COL_ID} desc ")
        val arr = arrayListOf<Word>()
        if(cursor.moveToFirst()){
            do{
                arr.add(
                    Word(
                        cursor.getString(cursor.getColumnIndex(Word.COL_en)),
                        cursor.getString(cursor.getColumnIndex(Word.COL_ch)),
                        cursor.getString(cursor.getColumnIndex(Word.COL_w_class)),
                    ).apply {
                        id = cursor.getInt(cursor.getColumnIndex(Word.COL_ID))
                    }
                )
            }while (cursor.moveToNext())
        }

        adapter.setData(arr)

        cursor.close()
    }

    inner class WordAdapter : RecyclerView.Adapter<WordViewHolder>() {

        val data  = arrayListOf<Word>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word,parent,false)
            return WordViewHolder(view).apply {
                en = view.findViewById(R.id.en_w)
                ch = view.findViewById(R.id.ch_w)
                w_class = view.findViewById(R.id.wd_class)
            }
        }

        override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
            holder.render(data[position])
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim1)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setData(arr: ArrayList<Word>) {
            data.addAll(arr)
            notifyDataSetChanged()
        }

        fun addItem(item: Word) {
            data.add(0,item)
            notifyItemInserted(0)
        }

        fun replaceItem(id: Int?, item: Word) {
            val idx = findIdx(id)
            if(idx >= 0) {
                data.set(idx, item)
                notifyItemChanged(idx)
            }
        }

        private fun findIdx(id: Int?): Int {
            var idx = -1
            data.forEachIndexed { index, todo ->
                if(todo.id == id){
                    idx = index
                }
            }
            return idx
        }

        fun itemDeleted(id: Int?) {
            val idx = findIdx(id)
            if(idx >= 0){
                data.removeAt(idx)
                notifyItemRemoved(idx)
            }
        }

    }

    inner class WordViewHolder(view: View): RecyclerView.ViewHolder(view){

        var id:TextView? = null
        var en:TextView? = null
        var ch:TextView? = null
        var w_class:TextView? = null

        fun render(word: Word) {
            id?.text = word.id.toString()
            en?.text = word.en.toString()
            ch?.text = word.ch.toString()
            w_class?.text = word.w_class.toString()
            /*
            btnDelete?.setOnClickListener {
                Log.d(TAG,"to delete id = ${word.id} ")
                val db = helper.writableDatabase
                db.delete(Word.TABLE,"id = ?", arrayOf(word.id.toString()))
                adapter.itemDeleted(word.id)
            }
            btnUpdate?.setOnClickListener {
                Log.d(TAG,"to update id = ${word.id} ")
                toUpdate = word
                setInputText(word.content)
            }*/
        }
    }

}