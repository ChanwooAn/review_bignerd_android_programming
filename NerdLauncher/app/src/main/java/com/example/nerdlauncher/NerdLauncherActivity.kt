package com.example.nerdlauncher

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG="NerdLauncherActivityMain"


class NerdLauncherActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nerd_launcher)

        recyclerView=findViewById(R.id.app_recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)

        setAdapter()

    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun setAdapter(){
        val startIntent=Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities=packageManager.queryIntentActivities(startIntent,0)
        activities.sortWith(Comparator{a,b->
            String.CASE_INSENSITIVE_ORDER.compare(
                a.loadLabel(packageManager).toString(),
                b.loadLabel(packageManager).toString()
            )
        })//알파벳 순으로 정렬


        recyclerView.adapter=ActivityAdapter(activities)
        Log.d(TAG,"${activities.size} activities")

    }
    private class ActivityHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        private val nameTextView=itemView as TextView
        private lateinit var resolveInfo:ResolveInfo

        init {
            nameTextView.setOnClickListener(this)
        }
        fun bindActivity(resolveInfo: ResolveInfo){
            this.resolveInfo=resolveInfo
            val packageManager=itemView.context.packageManager
            val appName=resolveInfo.loadLabel(packageManager).toString()
            nameTextView.text=appName
        }

        override fun onClick(v: View) {
            Log.d(TAG,"click")
            val activitiyInfo=resolveInfo.activityInfo

            val intent=Intent(Intent.ACTION_MAIN).apply{
                setClassName(activitiyInfo.applicationInfo.packageName,activitiyInfo.name)
            }//application을 시작하는 명시적 intent

            val context=v.context
            context.startActivity(intent)
        }
    }
    private class ActivityAdapter(val activities:List<ResolveInfo>):RecyclerView.Adapter<ActivityHolder>(){
        override fun getItemCount(): Int {
            return activities.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val layoutInflater=LayoutInflater.from(parent.context)
            val view=layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false)

            return ActivityHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            val resolveInfo=activities[position]
            holder.bindActivity(resolveInfo)

        }

    }

}