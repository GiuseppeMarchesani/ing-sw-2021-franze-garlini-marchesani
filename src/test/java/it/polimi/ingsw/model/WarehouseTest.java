package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.Depot;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WarehouseTest {
    Warehouse testWarehouse;

    @Before
    public void setUp() throws Exception {
        testWarehouse= new Warehouse();
    }

    @Test
    public void addDepot() {

        testWarehouse.addDepot(ResourceType.STONE);
        assertEquals(4, testWarehouse.getDepotList().size());
        assertEquals(ResourceType.STONE.getVal(), testWarehouse.getDepotList().get(3).getResourceType().getVal());
        assertEquals(0, testWarehouse.getDepotList().get(3).getRearrangeble());

        testWarehouse.addDepot(ResourceType.SHIELD);
        assertEquals(5, testWarehouse.getDepotList().size());
        assertEquals(ResourceType.SHIELD.getVal(), testWarehouse.getDepotList().get(4).getResourceType().getVal());
        assertEquals(0, testWarehouse.getDepotList().get(4).getRearrangeble());




    }

    @Test
    public void rearrange() {
        HashMap<ResourceType, Integer> testDiscardingResources = new HashMap<>();

        Depot depot1= testWarehouse.getDepotList().get(1);
        Depot depot0=testWarehouse.getDepotList().get(0);
        Depot depot2 =testWarehouse.getDepotList().get(2);
        depot1.setResourceType(ResourceType.COIN);
        depot1.setResourceQuantity(1);
        depot0.setResourceType(ResourceType.STONE);
        depot0.setResourceQuantity(2);


        testDiscardingResources = testWarehouse.rearrange(1, 0);

        assertTrue(testDiscardingResources.isEmpty());
        assertEquals("COIN", testWarehouse.getDepotList().get(0).getResourceType().toString());
        assertEquals(1, testWarehouse.getDepotList().get(0).getResourceQuantity());
        assertEquals("STONE", testWarehouse.getDepotList().get(1).getResourceType().toString());
        assertEquals(2, testWarehouse.getDepotList().get(1).getResourceQuantity());

        depot1.setResourceType(ResourceType.SERVANT);
        depot1.setResourceQuantity(1);
        depot0.setResourceType(ResourceType.SHIELD);
        depot0.setResourceQuantity(3);

        testDiscardingResources = testWarehouse.rearrange(1, 0);

        assertEquals(1, (int) testDiscardingResources.get(ResourceType.SHIELD));
        assertEquals("SERVANT", testWarehouse.getDepotList().get(0).getResourceType().toString());
        assertEquals("SHIELD", testWarehouse.getDepotList().get(1).getResourceType().toString());
        assertEquals(2, testWarehouse.getDepotList().get(1).getResourceQuantity());
    }

    @Test
    public void place() {
        testWarehouse.getDepotList().get(0).setResourceQuantity(3);
        testWarehouse.getDepotList().get(0).setResourceType(ResourceType.SERVANT);
        testWarehouse.getDepotList().get(1).setResourceType(ResourceType.COIN);
        testWarehouse.getDepotList().get(1).setResourceQuantity(1);
        testWarehouse.getDepotList().get(2).setResourceQuantity(0);
        testWarehouse.getDepotList().get(2).setResourceType(ResourceType.SHIELD);

        assertEquals(3,(int) testWarehouse.place(ResourceType.SERVANT, 3, 0));
        assertEquals(0, (int) testWarehouse.place(ResourceType.COIN, 1,1));
        assertEquals(3, (int) testWarehouse.place(ResourceType.SHIELD, 4, 2));
    }

    @Test
    public void hasResource() {
        testWarehouse.getDepotList().get(0).setResourceQuantity(1);
        testWarehouse.getDepotList().get(0).setResourceType(ResourceType.SERVANT);
        testWarehouse.getDepotList().get(1).setResourceType(ResourceType.COIN);
        testWarehouse.getDepotList().get(1).setResourceQuantity(1);
        testWarehouse.getDepotList().get(2).setResourceType(ResourceType.SHIELD);
        testWarehouse.getDepotList().get(2).setResourceQuantity(1);

        assertEquals(0, testWarehouse.hasResource(ResourceType.SERVANT));
        assertEquals(-1, testWarehouse.hasResource(ResourceType.STONE));
        assertEquals(1, testWarehouse.hasResource(ResourceType.COIN));
        assertEquals(2, testWarehouse.hasResource(ResourceType.SHIELD));
    }

    @Test
    public void isEmpty() {
        testWarehouse.getDepotList().get(0).setResourceQuantity(0);
        testWarehouse.getDepotList().get(1).setResourceQuantity(0);
        testWarehouse.getDepotList().get(2).setResourceQuantity(0);
        testWarehouse.getDepotList().get(0).setResourceType(ResourceType.ANY);
        testWarehouse.getDepotList().get(1).setResourceType(ResourceType.ANY);
        testWarehouse.getDepotList().get(2).setResourceType(ResourceType.ANY);

        assertTrue(testWarehouse.isEmpty());

        testWarehouse.getDepotList().get(2).setResourceType(ResourceType.COIN);
        testWarehouse.getDepotList().get(2).setResourceQuantity(1);

        assertFalse(testWarehouse.isEmpty());
    }

    @Test
    public void getSpace() {
        ArrayList<Integer> testSpace = new ArrayList<>();

        testWarehouse.getDepotList().get(0).setResourceQuantity(2);
        testWarehouse.getDepotList().get(1).setResourceQuantity(0);
        testWarehouse.getDepotList().get(2).setResourceQuantity(1);
        testWarehouse.addDepot(ResourceType.SERVANT);
        testWarehouse.getDepotList().get(3).setResourceQuantity(0);
        testSpace=testWarehouse.getSpace();
        assertEquals(1,(int) testSpace.get(0));
        assertEquals(2,(int) testSpace.get(1));
        assertEquals(0,(int) testSpace.get(2));
        assertEquals(2,(int) testSpace.get(3));




    }
}