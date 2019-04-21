package com.siregar.ira.clientserver

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.lauwba.project.desalauwba.ui.berita.BeritaModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var list: MutableList<BeritaModel>? = null
    private var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = mutableListOf()
        get_data_berita().execute()

        cari.setOnClickListener {
            if (cari_berita.text.toString().isEmpty()) {
                get_data_berita().execute()
            } else {
                CariBerita().execute()
            }
        }
    }

    inner class CariBerita : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog.show(this@MainActivity, "", "Lagi nyari nih guys")
        }

        override fun doInBackground(vararg params: String?): String {
            val req = RequestHandler()
            return req.sendGetRequest(Config.url_cari_berita + cari_berita.text.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd?.dismiss()
            try {
                val objek = JSONObject(result)
                if (objek.getInt("status") == 1) {
                    Toast.makeText(this@MainActivity, "Tidak ada data!", Toast.LENGTH_SHORT).show()
                } else {
                    val array = objek.getJSONArray("data")
                    list?.removeAll(list!!)
                    for (i in 0 until array.length()) {
                        val data = array.getJSONObject(i)
                        val model = BeritaModel()
                        model.id_berita = data.getString("id_berita")
                        model.judul = data.getString("judul")
                        model.kategori = data.getString("kategori")
                        model.isi = data.getString("isi")
                        model.gambar = data.getString("gambar")
                        model.tanggal = data.getString("tanggal")
                        list?.add(model)
                        val adapter = list?.let { BeritaAdapter(it) }
                        rc.layoutManager = LinearLayoutManager(this@MainActivity)
                        rc.adapter = adapter
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }

    inner class get_data_berita : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog.show(this@MainActivity, "", "Wait", true, true)
        }

        override fun doInBackground(vararg params: String?): String {

            val handler = RequestHandler()
            val result = handler.sendGetRequest(Config.url_berita)
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                pd?.dismiss()
                val objek = JSONObject(result)
                if (objek.getInt("status") == 1) {
                    Toast.makeText(this@MainActivity, "Tidak ada data!", Toast.LENGTH_SHORT).show()
                } else {
                    list?.removeAll(list!!)
                    val array = objek.getJSONArray("data")
                    for (i in 0 until array.length()) {
                        val data = array.getJSONObject(i)
                        val model = BeritaModel()
                        model.id_berita = data.getString("id_berita")
                        model.judul = data.getString("judul")
                        model.kategori = data.getString("kategori")
                        model.isi = data.getString("isi")
                        model.gambar = data.getString("gambar")
                        model.tanggal = data.getString("tanggal")
                        list?.add(model)
                        val adapter = list?.let { BeritaAdapter(it) }
                        rc.layoutManager = LinearLayoutManager(this@MainActivity)
                        rc.adapter = adapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
