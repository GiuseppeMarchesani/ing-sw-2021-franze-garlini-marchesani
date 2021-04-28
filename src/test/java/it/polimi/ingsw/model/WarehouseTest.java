package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WarehouseTest {
    Warehouse testWarehouse;

    @Before
    public void setUp() throws Exception {
        testWarehouse=new Warehouse();
    }

    @Test
    public void addDepot() {
        testWarehouse.addDepot(new Ability(new ResourceType(4)).getResourceAbility());
        assertEquals(4, testWarehouse.getDepotList().size());
        assertEquals(new ResourceType(4).getResource(), testWarehouse.getDepotList().get(3).getResourceType().getResource());
        assertTrue(testWarehouse.getDepotList().get(3).getRearrangeble()==0);
    }

    @Test
    public void rearrange() {
        Depot depot1= testWarehouse.getDepotList().get(1);
        Depot depot2=testWarehouse.getDepotList().get(2);
        depot1.setResourceType(new ResourceType(3));
        depot1.setResourceQuantity(1);
        depot2.setResourceType(new ResourceType(5));
        depot2.setResourceQuantity(2);

        testWarehouse.rearrange(1, 2);
        assertEquals(3, testWarehouse.getDepotList().get(2).getResourceType().getResource());
        assertEquals(1, testWarehouse.getDepotList().get(2).getResourceQuantity());
        assertEquals(5, testWarehouse.getDepotList().get(1).getResourceType().getResource());
        assertEquals(2, testWarehouse.getDepotList().get(1).getResourceQuantity());
    }

    @Test
    public void place() {
    }

    @Test
    public void hasResource() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void getSpace() {
    }
}