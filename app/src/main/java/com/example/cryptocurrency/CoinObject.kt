package com.example.cryptocurrency

class CoinObject(
    name: String = "Coin",
    symbol: String = "C",
    description: String = "A flat disc or piece of metal with an official stamp, used as money.",
    price: Double = 0.00,
    color: String = "#15d46f",
    iconUrl: String = "https://f0.pngfuel.com/png/880/89/gold-coin-computer-icons-lakshmi-gold-coin-png-clip-art-thumbnail.png"

) {
    var c_name: String
    var c_symbol: String
    var c_description: String
    var c_price: Double
    var c_color: String
    var c_iconUrl: String

    init {
        this.c_name = name;
        this.c_symbol = symbol;
        this.c_description = description;
        this.c_price = price;
        this.c_color = color;
        this.c_iconUrl = iconUrl;

    }

}


