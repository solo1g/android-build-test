package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.bitcoins.core.protocol.Bech32Address;
import org.bitcoins.core.protocol.script.P2WPKHWitnessSPKV0;
import org.bitcoins.core.protocol.script.WitnessScriptPubKey;
import org.bitcoins.crypto.*;
import org.bitcoins.core.config.TestNet3;

class _TestNet3 extends TestNet3{}

public class MainActivity extends AppCompatActivity {

    private TextView addressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressView = (TextView) findViewById(R.id.address_text);
    }

    public void generateAddress(View view) {
        ECPrivateKey privateKey=ECPrivateKey.apply();
        ECPublicKey publicKey=privateKey.publicKey();
        P2WPKHWitnessSPKV0 scriptPubKey=P2WPKHWitnessSPKV0.apply(publicKey);
        Bech32Address address=Bech32Address.apply((WitnessScriptPubKey) scriptPubKey,new _TestNet3());
        Log.i("BTC",address.toString());
        addressView.setText(address.toString());
    }
}