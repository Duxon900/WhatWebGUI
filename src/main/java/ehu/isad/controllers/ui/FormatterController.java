package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.FormatterDB;
import ehu.isad.model.Extension;
import ehu.isad.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FormatterController {

    @FXML
    private Pane pane3;

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    @FXML
    private Button btn_scan;

    @FXML
    private ComboBox<Extension> combo;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_show;

    FormatterDB formatterDB = FormatterDB.getController();
    private String path= Utils.getProperties().getProperty("pathToCacheFolder");

    @FXML
    void onClick(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        if (btn_scan.equals(btn)) {
            String newLine = System.getProperty("line.separator");
            textArea.setText(getOutput().stream().collect(Collectors.joining(newLine)));
            textArea.setWrapText(true);
        }
        else if (btn_clear.equals(btn)) {
            textArea.clear();
            textField.clear();
        }
        else if (btn_show.equals(btn)) {
            Desktop.getDesktop().open(new File(path));
        }

    }

    private List<String> getOutput() throws IOException {
        List<String> emaitza = new LinkedList<>();
        try {
            String domain = textField.getText().replace("/","").split(":")[1];
            formatterDB.addDomainToDB(domain);
            if (!FormatterDB.getController().formatExists(domain,combo.getValue().getType())){
                executeCommand(combo.getValue(),domain); //This will execute and create the file.
                formatterDB.addFormatToDB(domain,combo.getValue().getType());
            }
            Thread.sleep(10000);
            //This loads the file with the domain name.

            BufferedReader input = new BufferedReader(new FileReader(path+domain+combo.getValue().getExtension()));
            String line;
            while ((line = input.readLine()) != null) { emaitza.add(line); }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return emaitza;
    }

    private void executeCommand(Extension ext, String domain) throws IOException {

        String target = textField.getText();
        String type = ext.getType();
        String extension = ext.getExtension();
        String command = null;
        switch(type) {
            case "shell":
                command = "whatweb --color=never --log-brief=" +path+domain+extension+" "+target;
                break;
            case "ruby":
                command = "whatweb --color=never --log-object=" +path+domain+extension+" "+target;
                break;
            default:
                command = "whatweb --color=never --log-"+type+"=" +path+domain+extension+" "+target;
                break;
        }
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Runtime.getRuntime().exec(System.getenv("wsl " + command));
        } else {
            Runtime.getRuntime().exec(command);
        }
    }

    @FXML
    void initialize() {
        String displayName[] = {"Verbose output","Brief shell output","JSON format file","XML format file","MySQL INSERT format file","Ruby object inspection format","MagicTree XML format file"};
        String extension[] = {".txt",".out",".json",".xml",".sql",".rb",".magictree.xml"};
        String type[] = {"verbose","shell","json","xml","sql","ruby","magictree"};
        ObservableList<Extension> list = FXCollections.observableArrayList();
        for(int i = 0; i < displayName.length; i++){
            list.add(new Extension(displayName[i],extension[i],type[i]));
        }
        combo.setItems(list);
        textArea.setEditable(false);
    }

}
