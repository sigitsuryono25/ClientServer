package com.siregar.ira.clientserver

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_berita.*
import org.json.JSONObject

class DetailBerita : AppCompatActivity() {
    private var id: String? = null
    private var pd: ProgressDialog? = null
    private var from: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)
        id = intent.getStringExtra(Config.id)
        from = intent.getStringExtra("from")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        getdetailberita().execute()


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class getdetailberita : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val request = RequestHandler()
            return request.sendGetRequest(Config.url_detail_berita + id)

        }

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog.show(this@DetailBerita, "", "Wait...", false, true)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd?.dismiss()
            val objek = JSONObject(result)
            val array = objek.getJSONArray("data")
            for (i in 0 until array.length()) {
                val data = array.getJSONObject(i)
//                judul.text =
                supportActionBar?.title = data.getString("judul")
                isi.text = data.getString("isi")
                tanggal.text = data.getString("tanggal")
                Glide.with(this@DetailBerita)
                    .load(Config.url_gambar + data.getString("gambar")).into(gambarberita)
            }
        }
    }
}
