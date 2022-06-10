
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.nio.file.Paths;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.typesafe.config.Config;

import org.bitcoins.commons.util.ServerArgParser;
import org.bitcoins.node.Node;
import org.bitcoins.server.BitcoinSAppConfig;
import org.bitcoins.server.BitcoinSServerMain;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorSystem;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

public class MainActivity extends AppCompatActivity {

    Node node;
    ActorSystem system;
    TextView height,status;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        height=findViewById(R.id.height);
        button =findViewById(R.id.button);
        status=findViewById(R.id.status);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void run(View view) throws TimeoutException, InterruptedException{
        Duration dur=Duration.apply(1,TimeUnit.HOURS);
        if(node==null) {
            Log.i("BTC", "START");

            system = ActorSystem.create("test-system");

            org.flywaydb.core.api.android.ContextHolder.setContext(this);

            Path datadir;
            datadir = Paths.get(getFilesDir().getAbsolutePath() + File.separator + "datadir");

            Log.i("BTC", datadir.toString());

            Vector<String> v = Vector$.MODULE$.empty();
            Vector<Config> v2 = Vector$.MODULE$.empty();
            ServerArgParser argParser = new ServerArgParser(v);
            BitcoinSAppConfig config = new BitcoinSAppConfig(datadir, v2, system);

            BitcoinSServerMain s = new org.bitcoins.server.BitcoinSServerMain(argParser, system, config);

//            Await.result(s.start(),dur);
            Await.result(config.start(), dur);
            node = Await.result(s.startBitcoinSBackend(), dur);

            button.setText("Update height");
            status.setText("Node: Running");

            Log.i("BTC", "DONE");
        }

        int height = (int) Await.result(node.getBestHashBlockHeight(system.dispatcher()),dur);
        Log.i("BTC","HEIGHT: "+height);

        this.height.setText("Current height: "+height);
    }
}