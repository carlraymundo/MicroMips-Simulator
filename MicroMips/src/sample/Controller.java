package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private TextArea instructionsArea;

    @FXML
    private TableView<?> opcodeTable;

    @FXML
    private TableView<?> registerTable;

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

}

