package com.example.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


 class TopFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_search).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_key).setOnClickListener(this)
    }

     override fun onClick(v: View) {

         val act =  requireActivity() as MainActivity

         when(v.id){
             R.id.btn_search->{
                 act.showSearch()
             }
             R.id.btn_key->{
                 act.showKey()
             }
         }
     }
}