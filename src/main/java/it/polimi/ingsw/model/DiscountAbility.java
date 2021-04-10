public class DiscountAbility extends Ability{
    public DiscountAbility(ResourceType r){
        super(r);
    }
    public void activateAbility(Player P){
        if(P.getResourceDiscount().containsKey(resourceAbility)) {
            P.getResourceDiscount().put(resourceAbility, P.getResourceDiscount().get(resourceAbility)+2);
        }
        else{
            P.getResourceDiscount().put(resourceAbility, 2);
        };
    }
}
