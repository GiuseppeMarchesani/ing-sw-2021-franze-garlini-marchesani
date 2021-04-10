package it.polimi.ingsw.model;

public class MarbleAbility extends Ability {
        public MarbleAbility(ResourceType r) {
            super(r);
        }

        public void activateAbility(Player P) {
            if (!(P.getMarbleConversion().contains(resourceAbility))) {
                P.getMarbleConversion().add(resourceAbility);
        }
    }
}