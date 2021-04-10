public class Ability {
    protected ResourceType resourceAbility;
    public Ability(ResourceType r){
        resourceAbility=r;
    }
    public ResourceType getResourceAbility() {
        return resourceAbility;
    }
    public void activateAbility(Player p){
        System.out.println("No Ability");
    }
}
