package Model;

public class Type655_16 extends Instruction{
    private String sRs;
    private String sRt;
    private String sVariable;

    public Type655_16(String sOpCode, String sRs, String sRt, String sVariable) {
        this.sOpCode = sOpCode;
        this.sRs = sRs;
        this.sRt = sRt;
        this.sVariable = sVariable;
    }

    public String getsRs() {
        return sRs;
    }

    public void setsRs(String sRs) {
        this.sRs = sRs;
    }

    public String getsRt() {
        return sRt;
    }

    public void setsRt(String sRt) {
        this.sRt = sRt;
    }

    public String getsVariable() {
        return sVariable;
    }
}
