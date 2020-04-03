package com.example.cryptocurrency

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.activity_coins_detail.*
import java.lang.Exception

class CoinsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coins_detail)

        var list_position = intent.extras?.get("position") as Int
        val coinsPrice = intent.extras?.get("price") as Array<*>
        val coinsSymbol = intent.extras?.get("symbol") as Array<*>
        val coinsName = intent.extras?.get("name") as Array<*>
        val coinsIconUrl = intent.extras?.get("iconUrl") as Array<*>
        val coinsDescription = intent.extras?.get("description") as Array<*>

        supportActionBar?.setTitle(coinsSymbol[list_position].toString())
        tvCoinsDetails.setText(coinsDescription[list_position].toString()+coinsDescription[list_position].toString()+coinsDescription[list_position].toString())
        tvCoinsName.setText(coinsName[list_position].toString())
        tvCoinsDPrice.setText(coinsPrice[list_position].toString())
        var imageUri: Uri = Uri.parse(coinsIconUrl[list_position].toString())

        try {
            My_Glide(imageUri,imgCoinsDetail,this)

        } catch (hata: Exception) {

            Log.e("HATAAAA", "" + hata.toString())
        }
    }
}

fun My_Glide(imageUri:Uri,imgCoin:ImageView,context: Context){

    GlideToVectorYou.justLoadImage(
        context as Activity?,
        imageUri,
        imgCoin
    )

}
