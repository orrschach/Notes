package com.example.notes

import android.content.ContentValues
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net.HttpURLConnection
import java.net.URL

class SearchFragment : Fragment() {
    val TAG = "@@MAIN"
    private var ch_word : String?=null
    private var w_class : String?=null
    private var en_word : String?=null
    private var toUpdate: Word? =null
    lateinit var helper:DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        val search:Button?=view.findViewById(R.id.search_btn)
        val save:Button?=view.findViewById(R.id.save_btn)
        val s_en:EditText?=view.findViewById(R.id.search_text)
        val en:TextView?=view.findViewById(R.id.en_word)
        val ch:TextView?=view.findViewById(R.id.ch_word)
        val cl:TextView?=view.findViewById(R.id.word_class)
        search?.setOnClickListener{ v:View->search(s_en.toString(),en,ch,cl)}
        save?.setOnClickListener{v:View->save()}
        return view
    }

    private fun search(s_en:String,en:TextView?,ch:TextView?,cl:TextView?){
        en_word=s_en
        val web: String= "https://www.iciba.com/word?w=$s_en"
        val doc: Document = Jsoup.connect(web).get()
        val element: Elements = doc.select("div[calss=Mean_part__UI9M6]")
        ch_word = element.select("li > i").toString()
        w_class = element.select("div > span").toString()
        en?.setText(s_en)
        ch?.setText(ch_word)
        cl?.setText(w_class)
    }


    private fun save() {
        val db = helper.writableDatabase
        val item = Word(en_word,ch_word,w_class)
        val values = ContentValues().apply {
            put(Word.COL_ch,item.ch)
            put(Word.COL_en,item.en)
            put(Word.COL_w_class,item.w_class)
        }
        var rs = -1
        if(toUpdate != null){
            item.id = toUpdate?.id
            Log.d(TAG,"update row id = $rs")

            rs = db.insert(Word.TABLE,null,values).toInt()
            if(rs != -1 ){
                item.id = rs
        }

    }

}}