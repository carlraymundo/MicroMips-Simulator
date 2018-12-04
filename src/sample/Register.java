package sample;

public class Register {

    private String RegisterNum;
    private String Value;

    public Register(String regnum,String val){
        RegisterNum = regnum;
        Value = val;
    }

    public String getRegisterNum() {
        return RegisterNum;
    }

    public void setRegisterNum(String registerNum) {
        RegisterNum = registerNum;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
