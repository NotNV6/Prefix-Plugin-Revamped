package me.nv6.prefixes.prefix;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.Setter;
import me.nv6.prefixes.profile.Profile;
import me.nv6.prefixes.util.Database;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Getter
public class Prefix {

    @Getter private static List<Prefix> prefixes = new ArrayList<>();

    @Setter
    private String name, description, prefix;

    public Prefix(String name, String description, String prefix) {
        this.name = name;
        this.description = description;
        this.prefix = prefix;

        prefixes.add(this);
    }

    public static void loadPrefixes() {
        MongoCollection collection = Database.getPrefixes();
        collection.find().forEach(new Block() {
            @Override
            public void apply(Object object) {
                Document document = (Document) object;
                String name = document.getString("name");
                String description = document.getString("description");
                String prefix = document.getString("prefix");

                new Prefix(name, description, prefix);
            }
        });
    }

    public void save() {
        Document document = new Document();
        document.put("name", name);
        document.put("description", description);
        document.put("prefix", prefix);

        Database.getPrefixes().replaceOne(eq("name", this.name), document, new UpdateOptions().upsert(true));

    }

    public static Prefix getByName(String name) { return prefixes.stream().filter(prefix2 -> prefix2.getName().equals(name)).findFirst().orElse(null); }

    public void delete() {
        prefixes.remove(this);

        Profile.profiles.forEach(profile ->  {
            if(profile.getPrefixes().contains(prefix)) profile.getPrefixes().remove(prefix);
        });

        MongoCollection collection = Database.getPrefixes();
        Document document = (Document) collection.find(eq("name", name)).first();

        if(document == null) return;
        collection.deleteOne(document);
    }

}
