package com.example.cryptocurrency

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.custom_coins_row.view.*
import kotlinx.android.synthetic.main.activity_coins_detail.*

class CoinsArrayAdapter(
    context: Context,
    resource: Int,
    textViewResourceId: Int,
    var symbolCoinArray: Array<String>,
    var priceCoinArray: Array<String>,
    var iconUrlCoinsArray: Array<String>,
    var colorCoinsArray: Array<String>
) : ArrayAdapter<String>(context, resource, textViewResourceId, symbolCoinArray) {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var inflater = LayoutInflater.from(context)
        var n_inflater=LayoutInflater.from(context)
        var custom_coins_row_view = inflater.inflate(R.layout.custom_coins_row, parent, false)


        var coinsIcon = custom_coins_row_view.findViewById(R.id.imgCoin) as ImageView

        var coinsSymbol = custom_coins_row_view.tvCoinsSymbol

        var coinsPrice = custom_coins_row_view.tvCoinsPrice

        var coinsImgUri: Uri = Uri.parse(iconUrlCoinsArray[position])

        try {

            if (colorCoinsArray[position] == "null") {

                colorCoinsArray[position] = "#075c53"
            }
            if (symbolCoinArray[position] == "null") {

                symbolCoinArray[position] = "C"
            }
            if (priceCoinArray[position] == "null") {

                priceCoinArray[position] = "0.00"
            }
            if (iconUrlCoinsArray[position] == "null") {

                iconUrlCoinsArray[position] == ""
            }

            GlideToVectorYou.justLoadImage(
                context as Activity?,
                coinsImgUri,
                coinsIcon
            )

            coinsSymbol.setTextColor(Color.parseColor(colorCoinsArray[position]))
            coinsPrice.setTextColor(Color.parseColor(colorCoinsArray[position]));
            coinsPrice.setText(priceCoinArray[position])
            coinsSymbol.setText(symbolCoinArray[position])
            Log.e("TEST", iconUrlCoinsArray[position])
        }
        catch (hata: Exception) {
            Log.e("TEST", "" + hata.toString())
            colorCoinsArray[position] = "#075c53"
            coinsSymbol.setTextColor(Color.parseColor(colorCoinsArray[position]))
            coinsPrice.setTextColor(Color.parseColor(colorCoinsArray[position]));
            coinsPrice.setText(priceCoinArray[position])
            coinsSymbol.setText(symbolCoinArray[position])


        }

        return custom_coins_row_view

    }





}