package sample;

public class Data {
    private String Address;
    private String Representation;

    public Data(String Address, String Representation){
        this.Address = Address;
        this.Representation = Representation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRepresentation() {
        return Representation;
    }

    public void setRepresentation(String representation) {
        Representation = representation;
    }
}
