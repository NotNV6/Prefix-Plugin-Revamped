package me.nv6.prefixes.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static MongoClient client;
    private static MongoDatabase database;
    @Getter private static MongoCollection prefixes, profiles;

    public Database(String user, String password, String base, boolean auth, String host, int port) {
        if(auth) {
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(MongoCredential.createCredential(user, base, password.toCharArray()));
            client = new MongoClient(new ServerAddress(host, port), credentials);
        } else {
            client = new MongoClient(new ServerAddress(host, port));
        }

        database = client.getDatabase(base);

        profiles = database.getCollection("profiles");
        prefixes =  database.getCollection("prefixes");
    }


}
