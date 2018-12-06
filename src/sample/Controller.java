package sample;

import Model.Instruction;
import Model.Type655_16;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


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

        CycleNextBtn.setDisable(true);
        for (int i = 0; i < sRegisters.length; i++)
            sRegisters[i] = "R"+i;

        //load data
        ArrayList<Data> dataArrayList = new ArrayList<Data>();
        for (int i = 0; i < 256; i++) {
            dataArrayList.add(new Data(extendBin(decToHex(i), 4).toUpperCase(), "00"));
        }

        ObservableList<Object> data = FXCollections.observableArrayList(dataArrayList);

        tbldata.setCellValueFactory(new PropertyValueFactory<Data,String>("Address"));
        tblrep.setCellValueFactory(new PropertyValueFactory<Data,String>("Representation"));
        dataTable.setItems(data);

        //display Cycles
        //showCycle.appendText("IR: " + "\n" + "NPC: " + );
    }

    //goto button setonclick
    public void btnGoto(){
        for (int i = 0; i < registerArraylist.size(); i++) {
            System.out.println(registerArraylist.get(i).getValue());
        }
    }

    //load button setonclick
    public void btnLoad(){
        reset();
        getInstructions();
        //insert the function/method call for error checking here
        for (int i = 0; i < arrSInstructions.length; i++) {
            if(!isLineValid(arrSInstructions[i])) {
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

    private boolean isInstructionValid(String sInstruction, String line){
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
        else if(sInstruction.equals("DADDU"))
            System.out.println("DADDU");
        else if(sInstruction.equals("DSUBU"))
            System.out.println("DSUBU");
        else if(sInstruction.equals("BC"))
            System.out.println("BC");
        else if(sInstruction.equals("BLTC"))
            System.out.println("BLTC");
        else if(sInstruction.equals("DAUI") && checkDADI(sInstruction, csv, "011101", true)){
            return true;
        }
        else return false;
        System.out.println(method);
        return true;
    }

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

                if(isDaui && !s.getsRs().equals("00000")){
                    return false;
                }

                String[] splitOffset = splitOffset(extendBin(hexToBin(immediate), 16));
                sampleOpCode.add(new Opcode(ins+ " " + method[0] + ", " + method[1] + ", " + method[2],
                        s.getsOpCode(),
                        s.getsRs(),
                        s.getsRt(),
                        splitOffset[0],
                        splitOffset[1],
                        splitOffset[2],
                        binToHex(s.getAll())));
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
                    sampleOpCode.add(new Opcode(ins + " " + method[0]+", "+ offset+"(" + base +")",
                            s.getsOpCode(),
                            s.getsRs(),
                            s.getsRt(),
                            splitOffset[0],
                            splitOffset[1],
                            splitOffset[2],
                            binToHex(s.getAll())));
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

    private boolean isLineValid(String line){
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
            }
            else if(line.charAt(i) == ' ' || i + 1 >= line.length()){
                System.out.println(s.toString());
                return false;
            }
        }
        System.out.println(s.toString());
        if(isInstructionValid(s.toString(), line))
            return true;
        else return false;
    }

    private String decToHex(int dec){
        return Integer.toString(dec,16);
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

    private String[] splitOffset(String offset){
        String[] strings = new String[4];
//        strings[0] = offset.substring(0, 1);
        strings[0] = offset.substring(0, 5);
        strings[1] = offset.substring(5, 10);
        strings[2] = offset.substring(10, 16);
        return strings;
    }
}