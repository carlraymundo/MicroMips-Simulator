package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


/** Checklist of implemented instructions
 *  [ ] LD
 *  [ ] SD
 *  [ ] DADDIU
 *  [ ] DADDU
 *  [ ] DSUBU
 *  [ ] BC
 *  [ ] BLTC
 *  [ ] DAUI
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
    private TableView<?> dataTable;

    @FXML
    private Button GotoBtn;

    @FXML
    private Button LoadBtn;

    @FXML
    private Button NextBtn;

    @FXML
    private Button CycleNextBtn;

    @FXML
    private TableView<?> cyclesTable;

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

    public ObservableList<Object> registers = FXCollections.observableArrayList(
            new Register("R0","0000000000000000"),
            new Register("R1","0000000000000000"),
            new Register("R2","0000000000000000"),
            new Register("R3","0000000000000000"),
            new Register("R4","0000000000000000"),
            new Register("R5","0000000000000000"),
            new Register("R6","0000000000000000"),
            new Register("R7","0000000000000000"),
            new Register("R8","0000000000000000"),
            new Register("R9","0000000000000000"),
            new Register("R10","0000000000000000"),
            new Register("R11","0000000000000000"),
            new Register("R12","0000000000000000"),
            new Register("R13","0000000000000000"),
            new Register("R15","0000000000000000"),
            new Register("R16","0000000000000000"),
            new Register("R17","0000000000000000"),
            new Register("R18","0000000000000000"),
            new Register("R19","0000000000000000"),
            new Register("R20","0000000000000000"),
            new Register("R21","0000000000000000"),
            new Register("R22","0000000000000000"),
            new Register("R23","0000000000000000"),
            new Register("R24","0000000000000000"),
            new Register("R25","0000000000000000"),
            new Register("R26","0000000000000000"),
            new Register("R27","0000000000000000"),
            new Register("R28","0000000000000000"),
            new Register("R29","0000000000000000"),
            new Register("R30","0000000000000000"),
            new Register("R31","0000000000000000")
    );

    public ObservableList<Object> sampleOpCode = FXCollections.observableArrayList(

        new Opcode("DADDIU R1, R2, #0002","000000","00000","00000","00000","00000","00000","ABCDEGSF")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //load register
        tblregister.setCellValueFactory(new PropertyValueFactory<Register,String>("RegisterNum"));
        tblvalue.setCellValueFactory(new PropertyValueFactory<Register,String>("Value"));
        registerTable.setItems(registers);

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
        opcodeTable.setStyle("-fx-font-size: 9 arial");

        CycleNextBtn.setDisable(true);
    }

    //goto button setonclick
    public void btnGoto(){

    }

    //load button setonclick
    public void btnLoad(){
        getInstructions();
        //insert the function/method call for error checking here
        for (int i = 0; i < arrSInstructions.length; i++) {
            if(!isLineValid(arrSInstructions[i])) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error at line " + (i + 1), ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
                break;
            }
        }
        //insert the function/method call for executing the code
//        for (int i = 0; i < arrInstructionList.size(); i++) {
//            System.out.println(arrInstructionList.get(i));
//        }


        //Ignore this for loop, delete this code before submission
//        for (int i = 0; i < arrSInstructions.length; i++)
//            System.out.println(arrSInstructions[i]);
    }


    //Should reset the contents of the table
    //reset button setonclick
    public void btnReset(){
        //Add more reset function/method call here
        pointer = 0;
        resetRegisters();
        resetOpcode();
        //Refreshes the table so the old data wouldn't reappear when it is replaced
        opcodeTable.refresh();
        dataTable.refresh();
        cyclesTable.refresh();
        registerTable.refresh();
    }

    //Adds a row in the opcode table depending on the instruction executed
    //next cycle button setonclick
    public void btnNextCycle(){
        sampleOpCode.add(new Opcode("123123", "12312", "1232131", "12312312", "12312", "1231231231", "1231231231", "12312312"));
    }


    /*** PLACE ALL LOGICAL METHODS/FUNCTIONS HERE ***/

    //Retrieves all instruction from the instructions input table
    private void getInstructions(){
        arrSInstructions = instructionsArea.getText().split("\n");
    }

    //Resets the value of each register back to its initial value
    private void resetRegisters(){
        for (int i = 0; i < registers.size(); i++)
            ((Register)registers.get(i)).setValue("0000000000000000");
    }

    private void resetOpcode(){
        sampleOpCode.removeAll();
    }

    private boolean isInstructionValid(String sInstruction, String line){
        String method = line.substring(sInstruction.length() + 1).trim();
        if(sInstruction.equals("LD")){

        }
        else if(sInstruction.equals("SD"))
            System.out.println("ld");
        else if(sInstruction.equals("DADDIU"))
            System.out.println("DADDIU");
        else if(sInstruction.equals("DADDU"))
            System.out.println("DADDU");
        else if(sInstruction.equals("DSUBU"))
            System.out.println("DSUBU");
        else if(sInstruction.equals("BC"))
            System.out.println("BC");
        else if(sInstruction.equals("BLTC"))
            System.out.println("BLTC");
        else if(sInstruction.equals("DAUI"))
            System.out.println("DAUI");
        else return false;
        System.out.println(method);
        return true;
    }


//    private boolean checkL


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
        isInstructionValid(s.toString(), line);
        return true;
    }
}

