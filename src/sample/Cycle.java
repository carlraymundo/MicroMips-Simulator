package sample;

public class Cycle {

    private String IR;
    private String NPC;
    private String A;
    private String B;
    private String IMM;
    private String ALUOUTPUT;
    private String COND;
    private String PC;
    private String LMD;
    private String RANGE;
    private String RN;

    public Cycle(String IR, String NPC, String A, String B, String IMM, String ALUOUTPUT, String COND, String PC, String LMD, String RANGE, String RN){
        this.IR = IR;
        this.NPC = NPC;
        this.A = A;
        this.B = B;
        this.IMM = IMM;
        this.ALUOUTPUT = ALUOUTPUT;
        this.COND = COND;
        this.PC = PC;
        this.LMD = LMD;
        this.RANGE = RANGE;
        this.RN = RN;
    }

    public String getIR() {
        return IR;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }

    public String getNPC() {
        return NPC;
    }

    public void setNPC(String NPC) {
        this.NPC = NPC;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getIMM() {
        return IMM;
    }

    public void setIMM(String IMM) {
        this.IMM = IMM;
    }

    public String getALUOUTPUT() {
        return ALUOUTPUT;
    }

    public void setALUOUTPUT(String ALUOUTPUT) {
        this.ALUOUTPUT = ALUOUTPUT;
    }

    public String getCOND() {
        return COND;
    }

    public void setCOND(String COND) {
        this.COND = COND;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getLMD() {
        return LMD;
    }

    public void setLMD(String LMD) {
        this.LMD = LMD;
    }

    public String getRANGE() {
        return RANGE;
    }

    public void setRANGE(String RANGE) {
        this.RANGE = RANGE;
    }

    public String getRN() {
        return RN;
    }

    public void setRN(String RN) {
        this.RN = RN;
    }

}
