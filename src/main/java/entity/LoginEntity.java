package entity;

public class LoginEntity {
    private int retVal;
    
    public LoginEntity() {
    }

    public LoginEntity(int retVal) {
        this.retVal = retVal;
    }

    public int getRetVal() {
        return retVal;
    }

    public void setRetVal(int retVal) {
        this.retVal = retVal;
    }
}
