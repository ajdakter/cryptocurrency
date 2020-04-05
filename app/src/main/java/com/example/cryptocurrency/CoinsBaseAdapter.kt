package com.example.cryptocurrency

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.custom_coins_row.view.*
import java.lang.Exception
import java.text.DecimalFormat

class CoinsBaseAdapter(var context: Context, var coinsArray: Array<CoinObject>) : BaseAdapter() {
    val df = DecimalFormat("0.00")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = LayoutInflater.from(context)
        var custom_row = inflater.inflate(R.layout.custom_coins_row, parent, false)
        val c_iconUri: Uri = Uri.parse(coinsArray[position].c_iconUrl)

        try {
            GlideToVectorYou.justLoadImage(context as Activity?, c_iconUri, custom_row.imgCoin)
            custom_row.tvCoinsSymbol.setTextColor(Color.parseColor(coinsArray[position].c_color))
            custom_row.tvCoinsPrice.setTextColor(Color.parseColor(coinsArray[position].c_color))
            custom_row.tvCoinsSymbol.setText(coinsArray[position].c_symbol)
            custom_row.tvCoinsPrice.setText(df.format(coinsArray[position].c_price))


        } catch (error: Exception) {
            Log.e("Error : ", "CoinsBaseAdapter : " + error.toString())
            GlideToVectorYou.justLoadImage(context as Activity?, c_iconUri, custom_row.imgCoin)
            coinsArray[position].c_color = "#075c53"
            custom_row.tvCoinsSymbol.setTextColor(Color.parseColor(coinsArray[position].c_color))
            custom_row.tvCoinsPrice.setTextColor(Color.parseColor(coinsArray[position].c_color))
            custom_row.tvCoinsSymbol.setText(coinsArray[position].c_symbol)
            custom_row.tvCoinsPrice.setText(df.format(coinsArray[position].c_price))

        }


        return custom_row
    }

    override fun getItem(position: Int): Any {
        return coinsArray.get(position)

    }

    override fun getItemId(position: Int): Long {
        return 0;

    }

    override fun getCount(): Int {
        return coinsArray.size

    }
}