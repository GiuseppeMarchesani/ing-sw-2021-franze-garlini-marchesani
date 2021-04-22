package it.polimi.ingsw.model;

public class ProductionAbility extends Ability {
    public ProductionAbility(ResourceType r) {
        super(r);
    }

    public void activateAbility(Player P) {
            P.getDevCardSlot().getSlotLeader().add(new DevCard(resourceAbility));

    }
}
