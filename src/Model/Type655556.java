package Model;

public class Type655556 extends Instruction{
    private String sRs;
    private String sRt;
    private String sRd;
    private String sSa;
    private String sFunc;


    public Type655556(String sOpCode, String sRs, String sRt, String sRd, String sSa, String sFunc) {
        this.sOpCode = sOpCode;
        this.sRs = sRs;
        this.sRt = sRt;
        this.sRd = sRd;
        this.sSa = sSa;
        this.sFunc = sFunc;
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

    public String getsRd() {
        return sRd;
    }

    public void setsRd(String sRd) {
        this.sRd = sRd;
    }

    public String getsSa() {
        return sSa;
    }

    public String getsFunc() {
        return sFunc;
    }
}
