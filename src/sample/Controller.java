package sample;

import Model.Instruction;
import Model.Type655556;
import Model.Type655_16;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.scene.paint.Color;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


/** Checklist of implemented instructions
 *  [/] LD - OpCode
 *  [/] SD - OpCode
 *  [/] DADDIU - OpCode
 *  [ ] DADDU
 *  [ ] DSUBU
 *  [ ] BC
 *  [ ] BLTC
 *  [/] DAUI - OpCode
 */

public class Controller implements Initializable {

    @FXML
    private TextArea instructionsArea;

    @FXML
    private TableView<Object> opcodeTable;

    @FXML
    private TableColumn<Opcode, String> tableInstruction;

    @FXML
    private TableColumn<Opcode, String> table31;

    @FXML
    private TableColumn<Opcode, String> table25;

    @FXML
    private TableColumn<Opcode, String> table20;

    @FXML
    private TableColumn<Opcode, String> table15;

    @FXML
    private TableColumn<Opcode, String> table10;

    @FXML
    private TableColumn<Opcode, String> table5;

    @FXML
    private TableColumn<Opcode, String> tableHex;

    @FXML
    private TableView<Object> registerTable;

    @FXML
    private TableColumn<Register, String> tblregister;

    @FXML
    private TableColumn<Register, String> tblvalue;

    @FXML
    private TableView<Object> dataTable;

    @FXML
    private Button GotoBtn;

    @FXML
    private Button LoadBtn;

    @FXML
    private Button NextBtn;

    @FXML
    private Button CycleNextBtn;

    @FXML
    private TextArea showCycle;

    @FXML
    private TableColumn<Data, String> tbldata;

    @FXML
    private TableColumn<Data, String> tblrep;

    private String[] arrSInstructions;
    private ArrayList<String> arrInstructionList = new ArrayList<>(Arrays.asList("LD",
            "SD",
            "DADDIU",
            "DADDU",
            "DSUBU",
            "BC",
            "BLTC",
            "DAUI"));
    private int pointer = 0;
    private String[] sRegisters = new String[32];
    private ArrayList<Register> registerArraylist = new ArrayList<Register>();
    private ObservableList<Object> sampleOpCode = FXCollections.observableArrayList();
    private ArrayList<Opcode> instructionSet = new ArrayList<>();
    private String NPC = "0100";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //load register
        for(int i = 0;i < 32; i++){
            Register reg = new Register("R" + i + " ","0000000000000000");
            registerArraylist.add(reg);
        }

        ObservableList<Object> registers = FXCollections.observableArrayList(registerArraylist);


        tblregister.setCellValueFactory(new PropertyValueFactory<Register,String>("RegisterNum"));
        tblvalue.setCellValueFactory(new PropertyValueFactory<Register,String>("Value"));
        registerTable.setItems(registers);
        registerTable.setEditable(true);
        tblvalue.setCellFactory(TextFieldTableCell.<Register>forTableColumn());
        tblvalue.setOnEditCommit(
                (TableColumn.CellEditEvent<Register,String> t)->{
                    if(t.getTablePosition().getRow() != 0 && t.getNewValue().length() == 16 && isValidHex(t.getNewValue()))
                    ((Register) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setValue(t.getNewValue());
                    else registerTable.refresh();
                    System.out.println(t.getNewValue());
                }
        );

        //load data
        ArrayList<Data> dataArrayList = new ArrayList<Data>();
        for (int i = 0; i < 256; i++) {
            dataArrayList.add(new Data(extendBin(decToHex(i), 4).toUpperCase(), "00"));
        }

        ObservableList<Object> data = FXCollections.observableArrayList(dataArrayList);

        tbldata.setCellValueFactory(new PropertyValueFactory<Data,String>("Address"));
        tblrep.setCellValueFactory(new PropertyValueFactory<Data,String>("Representation"));
        dataTable.setItems(data);

        dataTable.setEditable(true);
        tblrep.setCellFactory(TextFieldTableCell.<Data>forTableColumn());
        tblrep.setOnEditCommit(
                (TableColumn.CellEditEvent<Data,String> t)->{
                    if(t.getNewValue().length() == 2)
                        ((Data) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setRepresentation(t.getNewValue());
                    else dataTable.refresh();
                    System.out.println(t.getNewValue());
                }
        );


        //load opcodes
        tableInstruction.setCellValueFactory(new PropertyValueFactory<Opcode,String>("instruction"));
        table31.setCellValueFactory(new PropertyValueFactory<Opcode,String>("bit31"));
        table25.setCellValueFactory(new PropertyValueFactory<Opcode,String>("bit25"));
        table20.setCellValueFactory(new PropertyValueFactory<Opcode,String>("bit20"));
        table15.setCellValueFactory(new PropertyValueFactory<Opcode,String>("bit15"));
        table10.setCellValueFactory(new PropertyValueFactory<Opcode,String>("bit10"));
        table5.setCellValueFactory(new PropertyValueFactory<Opcode,String>("bit5"));
        tableHex.setCellValueFactory(new PropertyValueFactory<Opcode,String>("Hex"));
        opcodeTable.setItems(sampleOpCode);
        opcodeTable.setStyle("-fx-font-size: 9 calibri");


        for (int i = 0; i < sRegisters.length; i++)
            sRegisters[i] = "R"+i;

        CycleNextBtn.setDisable(true);



    }

    //goto button setonclick
    public void btnGoto(){
    }

    //load button setonclick
    public void btnLoad(){
        reset();
        getInstructions();
        //insert the function/method call for error checking here
        for (int i = 0; i < arrSInstructions.length; i++) {
            if(!isLineValid(arrSInstructions[i], i, arrSInstructions.length)) {
                CycleNextBtn.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error at line " + (i + 1), ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
                break;
            }
        }

//        for (int i = 0; i < sRegisters.length; i++) {
//            System.out.println(sRegisters[i]);
//        }

        //insert the function/method call for executing the code
//        for (int i = 0; i < arrInstructionList.size(); i++) {
//            System.out.println(arrInstructionList.get(i));
//        }

        //Ignore this for loop, delete this code before submission
//        for (int i = 0; i < arrSInstructions.length; i++)
//            System.out.println(arrSInstructions[i]);
        CycleNextBtn.setDisable(false);
    }


    //Should reset the contents of the table
    //reset button setonclick
    public void btnReset(){
        reset();
    }

    private void reset(){
        //Add more reset function/method call here
        showCycle.setText("");
        NPC = "0100";
        pointer = 0;
        resetRegisters();
        resetOpcode();
        CycleNextBtn.setDisable(true);
        //Refreshes the table so the old data wouldn't reappear when it is replaced
        dataTable.refresh();
        registerTable.refresh();
    }

    //Adds a row in the opcode table depending on the instruction executed
    //next cycle button setonclick
    public void btnNextCycle(){
       if(!(pointer >= instructionSet.size())) {
          String alu =  extendBin(aluOutput(getRegisterValue(btod(instructionSet.get(pointer).getBit25())),
                   getRegisterValue(btod(instructionSet.get(pointer).getBit20())),
                   extendBin(binToHex(instructionSet.get(pointer).getImmediate()),16) ), 16);
           Cycle cycle = new Cycle(instructionSet.get(pointer).getHex(),
                   extendBin(decToHex(hextoDec(NPC) + 4), 16),
                   getRegisterValue(btod(instructionSet.get(pointer).getBit25())),
                   getRegisterValue(btod(instructionSet.get(pointer).getBit20())),
                   extendBin(binToHex(instructionSet.get(pointer).getImmediate()),16),
                   alu, isBC() ? "1" : "0"
                   , extendBin(decToHex(hextoDec(NPC) + 4), 16), isLoad() ? alu : "n/a", isStore() ? "n/a" : "n/a",
                   alu);
           if(
                   instructionSet.get(pointer).getInstruction().contains("DADDIU") ||
                   instructionSet.get(pointer).getInstruction().contains("DAUI")
           )setRegisterValue(btod(instructionSet.get(pointer).getBit20()), alu);
           if(instructionSet.get(pointer).getInstruction().contains("DADDU") ||
                   instructionSet.get(pointer).getInstruction().contains("DSUBU"))
               setRegisterValue(btod(instructionSet.get(pointer).getBit15()), alu);

           showCycle.appendText("IR: " + cycle.getIR() + "\n" +
                   "NPC: " + cycle.getNPC() + "\n" +
                   "A: " + cycle.getA() + "\n" +
                   "B: " + cycle.getB() + "\n" +
                   "IMM: " + cycle.getIMM() + "\n" +
                   "ALUOUTPUT: " + cycle.getALUOUTPUT() + "\n" +
                   "COND: " + cycle.getCOND() + "\n" +
                   "PC: " + cycle.getPC() + "\n" +
                   "LMD: " + cycle.getLMD() + "\n" +
                   "Range: " + cycle.getRANGE() + "\n" +
                   "Rn: " + cycle.getRN() + "\n" + "\n"
           );
           pointer++;
           NPC = extendBin(decToHex(hextoDec(NPC) + 4), 4);
       }else {CycleNextBtn.setDisable(true);}
    }

    private boolean isBC(){
        return instructionSet.get(pointer).getInstruction().contains("BC");
    }
    private boolean isStore(){
        return instructionSet.get(pointer).getInstruction().contains("SD");
    }

    private boolean isLoad(){
        return instructionSet.get(pointer).getInstruction().contains("LD");
    }

    private String aluOutput(String a, String b, String op){
        if(instructionSet.get(pointer).getInstruction().contains("DADDIU")){
            return decToHex(hextoDec(a) + hextoDec(op));
        }else if(instructionSet.get(pointer).getInstruction().contains("DADDU")) {
            return decToHex(hextoDec(a) + hextoDec(b));
        }else if(instructionSet.get(pointer).getInstruction().contains("DAUI"))
            return decToHex(hextoDec(a) + hextoDec(b));
        else if(instructionSet.get(pointer).getInstruction().contains("DSUBU"))
            return decToHex(hextoDec(a) - hextoDec(b));
        else if(instructionSet.get(pointer).getInstruction().contains("LD") || instructionSet.get(pointer).getInstruction().contains("SD"))
            return decToHex(hextoDec(a) + hextoDec(op));
        else return "0";
    }

    /*** PLACE ALL LOGICAL METHODS/FUNCTIONS HERE ***/

    //Retrieves all instruction from the instructions input table
    private void getInstructions(){
        arrSInstructions = instructionsArea.getText().split("\n");
    }

    //Resets the value of each register back to its initial value
    private void resetRegisters(){
//        for (int i = 0; i < registers.size(); i++)
//            ((Register)registers.get(i)).setValue("0000000000000000");
    }

    private void resetOpcode(){
        opcodeTable.getItems().clear();
    }

    private boolean isInstructionValid(String sInstruction, String line, int no, int max){
        String method = line.substring(sInstruction.length() + 1).trim();
        String[] csv = method.split(",");
        if(sInstruction.equals("LD") && checkLDSD(sInstruction, csv,"110111")){
            return true;
        }
        else if(sInstruction.equals("SD") && checkLDSD(sInstruction, csv, "111111")) {
            return true;
        }
        else if(sInstruction.equals("DADDIU") && checkDADI(sInstruction, csv, "011001", false)) {
            return true;
        }
        else if(sInstruction.equals("DADDU") && checkDADU(sInstruction, csv, "000000", "00000", "101101")){
            return true;
        }
        else if(sInstruction.equals("DSUBU") && checkDADU(sInstruction, csv, "000000", "00000", "101111")) {
            return true;
        }
        else if(sInstruction.equals("BC")) {
            String count = checkBC(sInstruction, method, "110010", line, no, max);
            if(count != null) {
//                if()

                return true;
            }else return false;
        }
        else if(sInstruction.equals("BLTC")) {
           return true;
        }
        else if(sInstruction.equals("DAUI") && checkDADI(sInstruction, csv, "011101", true)){
            return true;
        }
        else return false;
    }


    private boolean checkDADU(String ins, String[] method, String sOpCode, String sa, String func){
        if(method.length == 3){
            String rd = method[0].trim();
            String rt = method[2].trim();
            String rs = method[1].trim();
            if(Arrays.asList(sRegisters).contains(rd) &&
                    Arrays.asList(sRegisters).contains(rt) &&
                    Arrays.asList(sRegisters).contains(rs)){
                Type655556 s = new Type655556(sOpCode,
                        extendBin(hexToBin("" + rs.charAt(1)), 5),
                        extendBin(hexToBin("" + rt.charAt(1)), 5),
                        extendBin(hexToBin("" + rd.charAt(1)), 5),
                        sa, func);
                Opcode opcode = new Opcode(ins + " " + method[0] + ", " + method[1]+ ", " +method[2],
                        sOpCode, s.getsRs(), s.getsRt(), s.getsRd(), s.getsSa(), s.getsFunc(), binToHex(s.getAll()));
                sampleOpCode.add(opcode);
                instructionSet.add(opcode);
                return true;
            }
        }

        return false;
    }

    private String checkBC(String ins, String method, String sOpCode, String whole, int curr, int max){
        int count = 0;
        boolean found = false;
        if(!method.contains(" ")){
            for (int i = curr + 1; i < max; i++) {
                if(arrSInstructions[i].contains(method)){
                    found = !found;
                    break;
                }
                count++;
            }

            if(!found){
                count = 0;
                for (int i = curr+1; i >= 0; i--) {
                    if(arrSInstructions[i].contains(method)){
                        found = !found;
                        break;
                    }
                    count--;
                }
            }
        }
        if(!found)
            return null;
        else return Integer.toString(count);
    }

//    private int findCurrentLoc(String whole){
//        for (int i = 0; i < arrSInstructions; i++) {
//
//        }
//    }

//    private boolean countoffSet(String ins, String[] method, String sOpCode,)

    private boolean checkDADI(String ins, String[] method, String sOpCode, boolean isDaui){
        if(method.length == 3){
            String immediate = method[2].trim().substring(1);
            String rt = method[0].trim();
            String rs = method[1].trim();
            System.out.println(immediate + " " + " " + rt + " " + rs);
            if(method[2].trim().length() == 5 && method[2].trim().charAt(0) == '#' && Arrays.asList(sRegisters).contains(rt) &&
                    Arrays.asList(sRegisters).contains(rs) && isValidHex(immediate)) {
                Type655_16 s = new Type655_16(sOpCode,
                        extendBin(hexToBin("" + rs.charAt(1)), 5),
                        extendBin(hexToBin("" + rt.charAt(1)), 5),
                        extendBin(hexToBin(immediate), 16));

                if(isDaui && s.getsRs().equals("00000")){
                    return false;
                }

                String[] splitOffset = splitOffset(extendBin(hexToBin(immediate), 16));
                Opcode opcode = new Opcode(ins+ " " + method[0] + ", " + method[1] + ", " + method[2],
                        s.getsOpCode(),
                        s.getsRs(),
                        s.getsRt(),
                        splitOffset[0],
                        splitOffset[1],
                        splitOffset[2],
                        binToHex(s.getAll()));
                sampleOpCode.add(opcode);
                instructionSet.add(opcode);
                return true;
            }
        }
        return false;
    }

    private boolean checkLDSD(String ins, String[] method, String sOpCode){
        if (method.length == 2){
            String offset;
            String base;
            method[0] = method[0].trim();
            method[1] = method[1].trim();
            if(method[1].length() == 8 && Arrays.asList(sRegisters).contains(method[0])){
                offset = method[1].toUpperCase().substring(0, 4);
                base = method[1].substring(5, 7);
                if(isValidHex(offset) && Arrays.asList(sRegisters).contains(base)){
                    Type655_16 s =  new Type655_16(sOpCode, extendBin(hexToBin(""+base.charAt(1)), 5),
                            extendBin(hexToBin(""+method[0].charAt(1)), 5), extendBin(hexToBin(offset), 16));
                    String[] splitOffset = splitOffset(s.getsVariable());
                    Opcode opcode = new Opcode(ins + " " + method[0]+", "+ offset+"(" + base +")",
                            s.getsOpCode(),
                            s.getsRs(),
                            s.getsRt(),
                            splitOffset[0],
                            splitOffset[1],
                            splitOffset[2],
                            binToHex(s.getAll()));
                    sampleOpCode.add(opcode);
                    instructionSet.add(opcode);
                    return true;
                }
            }
        }

        return false;
    }

    private String extendBin(String bin, int n){
        while (bin.length() < n)
            bin = "0" + bin;
        return bin;
    }

    private boolean isValidHex(String hex){
        hex = hex.toUpperCase();
        System.out.println("HExed");
        for (int i = 0; i < hex.length(); i++) {
            if(!(hex.charAt(i) >= '0' && hex.charAt(i) < '9' || hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F'))
                return false;
        }
        return true;
    }

    private boolean isLineValid(String line, int no, int max){
        StringBuilder s = new StringBuilder();
        line = line.trim();
        for (int i = 0; i < line.length(); i++) {
            s.append(line.charAt(i));

            if(arrInstructionList.contains(s.toString())) {
                try {
                    if (line.charAt(i + 1) != ' ')
                        return false;
                    else break;
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }
            }else if(line.charAt(i) == ':'){
                try {
                    if (line.charAt(i + 1) != ' ')
                        return false;
                    else{
                        StringBuilder b = new StringBuilder();
                        line = line.substring(i + 1).trim();
                        for (int j = i + 1; j < line.length(); j++) {
                            b.append(line.charAt(j));
                            if(arrInstructionList.contains(b.toString())) {
                                try {
                                    if (line.charAt(i + 1) != ' ')
                                        return false;
                                    else {
                                        s = b;
                                        break;
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    return false;
                                }
                            }
                            else if(line.charAt(i) == ' ' || i + 1 >= line.length()){
                                System.out.println(s.toString());
                                return false;
                            }
                        }
                        break;
                    }
                }catch (IndexOutOfBoundsException e){
                    return false;
                }
            }
            else if(line.charAt(i) == ' ' || i + 1 >= line.length()){
                System.out.println(s.toString());
                return false;
            }
        }
        System.out.println(s.toString());
        if(isInstructionValid(s.toString(), line, no, max))
            return true;
        else return false;
    }

    private int hextoDec(String hex){
        return Integer.parseInt(hex, 16);
    }

    private String decToHex(int dec){
        return Integer.toString(dec,16);
    }

    private String getRegisterValue(int n){
        return tblregister.getTableView().getItems().get(n).getValue();
    }
    private void setRegisterValue(int n, String s){
        tblregister.getTableView().getItems().get(n).setValue(s);
    }

    private String binToHex(String binary){
        int digitNumber = 1;
        int sum = 0;
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < binary.length(); i++){
            if(digitNumber == 1)
                sum+=Integer.parseInt(binary.charAt(i) + "")*8;
            else if(digitNumber == 2)
                sum+=Integer.parseInt(binary.charAt(i) + "")*4;
            else if(digitNumber == 3)
                sum+=Integer.parseInt(binary.charAt(i) + "")*2;
            else if(digitNumber == 4 || i < binary.length()+1){
                sum+=Integer.parseInt(binary.charAt(i) + "")*1;
                digitNumber = 0;
                if(sum < 10)
                    s.append(sum);
                else if(sum == 10)
                    s.append("A");
                else if(sum == 11)
                    s.append("B");
                else if(sum == 12)
                    s.append("C");
                else if(sum == 13)
                    s.append("D");
                else if(sum == 14)
                    s.append("E");
                else if(sum == 15)
                    s.append("F");
                sum=0;
            }
            digitNumber++;
        }
        return s.toString();
    }

    private String hexToBin(String hex){
        return new BigInteger(hex, 16).toString(2);
    }
    
    private int btod(String bin){
        return Integer.parseInt(bin,2);
    }
    private String[] splitOffset(String offset){
        String[] strings = new String[4];
//        strings[0] = offset.substring(0, 1);
        strings[0] = offset.substring(0, 5);
        strings[1] = offset.substring(5, 10);
        strings[2] = offset.substring(10, 16);
        return strings;
    }
}