package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    lateinit var manager: FragmentManager


    companion object {
        val TAG_S = "查找"
        val TAG_K = "重点"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        manager = supportFragmentManager
    }



    fun showSearch() {
        showFragment(TAG_S)
    }

    fun showKey() {
        showFragment(TAG_K)
    }

    private fun showFragment(tag: String) {
        val frag =manager.findFragmentByTag(tag)?:
        when (tag){
            TAG_S-> SearchFragment()
            else-> KeyFragment()
        }
        val trans = manager.beginTransaction()

        manager.fragments.forEach {
            if(it != frag && it !is TopFragment){
                trans.hide(it)
            }
        }
        if(frag.isAdded){
            trans.show(frag)
        }else{
            trans.add(R.id.down, frag,tag)
        }
        trans.commit()
    }
}