package IGT.Customer;

public enum EStatus {
    GOLD(500), SILVER(100), BRONZE(50), NONE(0);

    private int miles;

    EStatus(int miles){
        this.miles = miles;
    }

    public int getMiles(){
        return miles;
    }
}
