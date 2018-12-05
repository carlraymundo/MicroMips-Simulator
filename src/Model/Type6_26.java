package Model;

public class Type6_26 extends Instruction{
    private String sVariable;

    public Type6_26(String sOpCode, String sVariable) {
        this.sOpCode = sOpCode;
        this.sVariable = sVariable;
    }

    public String getsVariable() {
        return sVariable;
    }

    public void setsVariable(String sVariable) {
        this.sVariable = sVariable;
    }
}
