package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Board.FaithTrack;
import it.polimi.ingsw.model.Board.FaithZone;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class FaithTrackTest {
    private FaithTrack faithTrack;

    @Before
    public void setUp() {
        //Faith Zone generation
        String faithZonesJson = "";
        ArrayList<FaithZone> faithZones = null;

        try {
            faithZonesJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\faith-track.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<FaithZone>>(){}.getType();
        faithZones = new Gson().fromJson(faithZonesJson, foundListType);


        //VPspaces generation
        String VPspacesJson = "";
        LinkedHashMap<Integer, Integer> VPspaces = null;

        try {
            VPspacesJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\vp-spaces.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<LinkedHashMap<Integer, Integer>>(){}.getType();
        VPspaces = new Gson().fromJson(VPspacesJson, foundHashMapType);

        faithTrack = new FaithTrack(faithZones, VPspaces);

    }

    @Test
    public void testIsOnFinalPosition() {
        assertTrue(faithTrack.isOnFinalPosition(24));
        assertTrue(faithTrack.isOnFinalPosition(25));
        assertTrue(faithTrack.isOnFinalPosition(26));
        assertFalse(faithTrack.isOnFinalPosition(23));
        assertFalse(faithTrack.isOnFinalPosition(0));
        assertFalse(faithTrack.isOnFinalPosition(14));
    }

    @Test
    public void testIsInFaithZone() {
            assertFalse(faithTrack.isInFaithZone(2, 0));
            assertFalse(faithTrack.isInFaithZone(2, 1));
            assertTrue(faithTrack.isInFaithZone(5, 0));
            assertTrue(faithTrack.isInFaithZone(8, 0));
            assertFalse(faithTrack.isInFaithZone(11, 0));
            assertFalse(faithTrack.isInFaithZone(11, 1));
            assertTrue(faithTrack.isInFaithZone(16, 1));
            assertFalse(faithTrack.isInFaithZone(25, 2));
    }

    @Test
    public void testIsOnPopeSpace() {
        assertEquals(faithTrack.isOnPopeSpace(4), -1);
        assertEquals(faithTrack.isOnPopeSpace(8), 0);
        assertEquals(faithTrack.isOnPopeSpace(8), -1);
        assertEquals(faithTrack.isOnPopeSpace(8), -1);
        assertEquals(faithTrack.isOnPopeSpace(9), -1);
        assertEquals(faithTrack.isOnPopeSpace(17), 1);
        assertEquals(faithTrack.isOnPopeSpace(16), -1);
        assertEquals(faithTrack.isOnPopeSpace(8), -1);
        assertEquals(faithTrack.isOnPopeSpace(23), -1);
        assertEquals(faithTrack.isOnPopeSpace(24), 2);
    }

    @Test
    public void testGetAssociatedVP() {
        assertEquals(faithTrack.getAssociatedVP(2),0);
        assertEquals(faithTrack.getAssociatedVP(3),1);
        assertEquals(faithTrack.getAssociatedVP(5),1);
        assertEquals(faithTrack.getAssociatedVP(10),4);
        assertEquals(faithTrack.getAssociatedVP(11),4);
        assertEquals(faithTrack.getAssociatedVP(13),6);
        assertEquals(faithTrack.getAssociatedVP(17),9);
        assertEquals(faithTrack.getAssociatedVP(20),12);
        assertEquals(faithTrack.getAssociatedVP(21),16);
        assertEquals(faithTrack.getAssociatedVP(23),16);
        assertEquals(faithTrack.getAssociatedVP(24),20);
        assertEquals(faithTrack.getAssociatedVP(27),20);
        System.out.println("\n"+
                "    __  __           _                     __   _____      _                                          " + "\n" +
                "	|  \\/  |         | |                   / _| |  __ \\    (_)                                         " + "\n" +
                "	| \\  / | __ _ ___| |_ ___ _ __    ___ | |_  | |__) |___ _ _ __   __ _ ___ ___  __ _ _ __   ___ ___ " + "\n" +
                "	| |\\/| |/ _` / __| __/ _ \\ '__|  / _ \\|  _| |  _  // _ \\ | '_ \\ / _` / __/ __|/ _` | '_ \\ / __/ _ \\ "+ "\n" +
                "	| |  | | (_| \\__ \\ ||  __/ |    | (_) | |   | | \\ \\  __/ | | | | (_| \\__ \\__ \\ (_| | | | | (_|  __/ "+ "\n" +
                "	|_|  |_|\\__,_|___/\\__\\___|_|     \\___/|_|   |_|  \\_\\___|_|_| |_|\\__,_|___/___/\\__,_|_| |_|\\___\\___| "+ "\n");
        System.out.println("Welcome to Santorini Board Game!");
    }

}