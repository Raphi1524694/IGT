package IGT.Customer;

public enum EStatus {
    WHITE_GOLD(10000000), SPECIAL_PLATINUM(1000000), PLATINUM(100000), GOLD(10000), SILVER(5000), BRONZE(2500), NONE(0);

    private int miles;

    EStatus(int miles){
        this.miles = miles;
    }

    public int getMiles(){
        return miles;
    }
}
