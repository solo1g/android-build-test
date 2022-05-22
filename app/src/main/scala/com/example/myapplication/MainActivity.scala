package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import org.bitcoins.core.protocol.Bech32Address
import org.bitcoins.core.protocol.script.P2WPKHWitnessSPKV0
import org.bitcoins.crypto.ECPrivateKey
import org.bitcoins.core.config.TestNet3

class MainActivity extends AppCompatActivity {

    private var addressView: TextView = _

    override protected def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressView=findViewById(R.id.address_text)
    }

    def generateAddress(view: View): Unit = {
        val privateKey = ECPrivateKey()
        val publicKey = privateKey.publicKey
        val scriptPubKey = P2WPKHWitnessSPKV0(publicKey)
        val address = Bech32Address(scriptPubKey, TestNet3)
        Log.i("BTC", address.toString)
        addressView.setText(address.toString)
    }
}
