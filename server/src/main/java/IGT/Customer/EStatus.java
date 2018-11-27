package IGT.Customer;

public enum EStatus {
    GOLD(10000), SILVER(5000), BRONZE(2500), NONE(0);

    private int miles;

    EStatus(int miles){
        this.miles = miles;
    }

    public int getMiles(){
        return miles;
    }
}
