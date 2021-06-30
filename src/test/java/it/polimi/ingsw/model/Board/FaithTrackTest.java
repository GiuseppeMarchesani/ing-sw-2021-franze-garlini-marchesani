package it.polimi.ingsw.model.Board;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
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
            InputStream is = Game.class.getResourceAsStream("/faith-track.JSON");
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = is.read()) != -1; ) {
                sb.append((char) ch);
            }
            faithZonesJson = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<FaithZone>>(){}.getType();
        faithZones = new Gson().fromJson(faithZonesJson, foundListType);


        //VPspaces generation
        String VPspacesJson = "";
        LinkedHashMap<Integer, Integer> VPspaces = null;

        try {
            InputStream is = Game.class.getResourceAsStream("/vp-spaces.JSON");
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = is.read()) != -1; ) {
                sb.append((char) ch);
            }
            VPspacesJson = sb.toString();
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
    }

    @Test
    public void testNextFaithZone(){
        assertEquals(5, faithTrack.getNextFaithZone().getStart());
        faithTrack.getFaithZones().get(0).setActivated();
        assertEquals(12, faithTrack.getNextFaithZone().getStart());
        faithTrack.getFaithZones().get(1).setActivated();
        faithTrack.getFaithZones().get(2).setActivated();
        assertEquals(100, faithTrack.getNextFaithZone().getStart());
    }

    @Test
    public void testIndexOfNextFaithZone() {
        assertEquals(0, faithTrack.indexOfNextFaithZone());
        faithTrack.getFaithZones().get(0).setActivated();
        assertEquals(1, faithTrack.indexOfNextFaithZone());
        faithTrack.getFaithZones().get(1).setActivated();
        assertEquals(2, faithTrack.indexOfNextFaithZone());
    }
}