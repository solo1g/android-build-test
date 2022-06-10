package com.example.myapplication;

import android.util.Log;

import com.typesafe.config.Config;

import org.bitcoins.commons.util.ServerArgParser;
import org.bitcoins.server.BitcoinSAppConfig;
import org.bitcoins.server.BitcoinSServerMain;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorSystem;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws TimeoutException, InterruptedException {

        ActorSystem system = ActorSystem.create("test-system");
//        org.flywaydb.core.api.android.ContextHolder.setContext(this);

        //checking dao test
//        (new org.bitcoins.node.NeutrinoNodeTest()).execute(null, ConfigMap$.MODULE$.empty(),false,false,false,false,false);

//        (new org.bitcoins.db.models.MasterXPubDAOTest()).execute(null, ConfigMap$.MODULE$.empty(),false,false,false,false,false);

//        Flyway flyway =Flyway.configure().locations("classpath:db/migration").dataSource("jdbc:sqlite:/data/user/0/com.example.myapplication/cache/bitcoin-s-7075446740576150616/regtest/testdb.sqlite","","").load();
//        flyway.migrate();

        Path datadir;
        try {
            datadir = Files
                    .createTempDirectory("node-example");
        } catch (IOException e){
            throw new RuntimeException("cannot create temp dir");
        }




        Vector<String> v = Vector$.MODULE$.empty();
        Vector<Config> v2 = Vector$.MODULE$.empty();
        ServerArgParser argParser=new ServerArgParser(v);
        BitcoinSAppConfig config=new BitcoinSAppConfig(datadir,v2,system);

        BitcoinSServerMain s=new  org.bitcoins.server.BitcoinSServerMain(argParser,system,config);

        Await.result(s.start(), Duration.apply(1, TimeUnit.DAYS));


        assertEquals(4, 2 + 2);
    }
}