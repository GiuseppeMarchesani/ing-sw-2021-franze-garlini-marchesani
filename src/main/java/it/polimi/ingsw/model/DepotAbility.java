public class DepotAbility extends Ability {
    public DepotAbility(ResourceType r){
        super(r);
    }
    public void activateAbility(Player P){
        P.getWarehouse().addDepot(resourceAbility);
    }


}
