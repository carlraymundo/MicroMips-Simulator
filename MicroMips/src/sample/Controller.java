package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

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
            new Register("R14","0000000000000000")
    );

    public ObservableList<Object> sampleOpCode = FXCollections.observableArrayList(
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH"),
            new Opcode("DADDIU R1, R0, #0003","000000","00000","00000","00000","00000","00000","ABCDEFGH")
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

    }

    //goto button setonclick
    public void btnGoto(){

    }

    //load button setonclick
    public void btnLoad(){

    }

    //reset button setonclick
    public void btnReset(){

    }

    //next cycle button setonclick
    public void btnNextCycle(){

    }
}

