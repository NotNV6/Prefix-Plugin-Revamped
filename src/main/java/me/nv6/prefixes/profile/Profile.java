package me.nv6.prefixes.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.Setter;
import me.nv6.prefixes.prefix.Prefix;
import me.nv6.prefixes.util.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
public class Profile {

    private static List<Profile> profiles = new ArrayList<>();


    private UUID uuid;
    private List<Prefix> prefixes;
    private Prefix currentPrefix;
    private Player player;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.prefixes = new ArrayList<>();
        player = Bukkit.getPlayer(uuid);

        load();
    }


    public static Profile getProfile(Player player) { return profiles.stream().filter(profile ->  profile.getPlayer().equals(player)).findFirst().orElse(null); }
    public static Collection<Profile> getProfiles() { return profiles; }

    public void load() {
        profiles.add(this);
        
        Document document = (Document) collection.find(eq("uuid", uuid.toString())).first();

        if(document == null) return;

        if(document.containsKey("prefix")) this.currentPrefix = Prefix.getByName(document.getString("prefix"));
        if(document.containsKey("prefixes")) for(String string : (List<String>) document.get("prefixes")) this.prefixes.add(Prefix.getByName(string));
    }


    public void save() {
        Document document = new Document();

        document.put("uuid", player.getUniqueId().toString());

        if(currentPrefix != null && !currentPrefix.getName().isEmpty()) document.put("prefix", currentPrefix.getName());

        if(!prefixes.isEmpty()) {
            List<String> prefixes2 = new ArrayList<>();
            prefixes.forEach(prefix -> prefixes2.add(prefix.getName()); // not sure wether this works, but it should, not home to test it. (made outside of an IDE)
            document.put("prefixes", prefixes2);
        }

        Database.getProfiles().replaceOne(eq("uuid", this.uuid.toString()), document, new UpdateOptions().upsert(true));
    }

    public void destroy() {
        this.save();

        profiles.remove(this);
    }

}
