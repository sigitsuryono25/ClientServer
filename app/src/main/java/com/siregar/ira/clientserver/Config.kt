package com.siregar.ira.clientserver

object Config {

//    private const val Host="http://192.168.43.93/desalauwba/" //yang bakal berganti

    private const val Host ="http://192.168.169.2/webservice/"
    const val url_gambar= Host+"assets/upload/"
    const val id="id"
    val url_detail_berita = Host+"index.php/Berita/select_by_get/"
    val url_berita = Host+"index.php/Berita/select/"
    val url_cari_berita = Host + "index.php/Berita/select_name?judul="
}