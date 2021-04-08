package it.polimi.ingsw.model;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This Class represents the Faith Track
 */
public class FaithTrack {
    private List<FaithZone> faithZones = new ArrayList<>();

    /**
     * Class constructor.
     * @throws IOException
     */
    public FaithTrack() throws IOException {
        String faithZonesJson = new String(Files.readAllBytes(Paths.get("ing-sw-2021-franze-garlini-marchesani/src/main/resources/faith-track.JSON")));
        Type foundListType = new TypeToken<ArrayList<FaithZone>>(){}.getType();
        faithZones = new Gson().fromJson(faithZonesJson, foundListType);
    }
}
