package sample;

public class Opcode {
    private String instruction;
    private String bit31;
    private String bit25;
    private String bit20;
    private String bit15;
    private String bit10;
    private String bit5;
    private String Hex;

    public Opcode(String instruction,String bit31,String bit25,String bit20, String bit15, String bit10,String bit5, String Hex){
        this.instruction = instruction;
        this.bit31 = bit31;
        this.bit25 = bit25;
        this.bit20 = bit20;
        this.bit15 = bit15;
        this.bit10 = bit10;
        this.bit5 = bit5;
        this.Hex = Hex;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getBit31() {
        return bit31;
    }

    public void setBit31(String bit31) {
        this.bit31 = bit31;
    }

    public String getBit25() {
        return bit25;
    }

    public void setBit25(String bit25) {
        this.bit25 = bit25;
    }

    public String getBit20() {
        return bit20;
    }

    public void setBit20(String bit20) {
        this.bit20 = bit20;
    }

    public String getBit15() {
        return bit15;
    }

    public void setBit15(String bit15) {
        this.bit15 = bit15;
    }

    public String getBit10() {
        return bit10;
    }

    public void setBit10(String bit10) {
        this.bit10 = bit10;
    }

    public String getBit5() {
        return bit5;
    }

    public void setBit5(String bit5) {
        this.bit5 = bit5;
    }

    public String getHex() {
        return Hex;
    }

    public void setHex(String hex) {
        Hex = hex;
    }
}
