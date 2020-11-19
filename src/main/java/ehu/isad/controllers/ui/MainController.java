package ehu.isad.controllers.ui;

import ehu.isad.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Parent pane1;
    CMSController cmsController;
    private Parent pane2;
    ServerController serverController;
    private Parent pane3;
    FormatterController formatterController;
    private Parent pane4;
    HistoryController historyController;
    private Parent pane5;
    //StatisticsController statisticsController;
    private Parent pane6;
    SettingsController settingsController;

    PopUpSureController popController;
    Scene pop;

    @FXML
    private Pane pane;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Label lbl_title;

    @FXML
    private ImageView btn_x;

    @FXML
    void handleClick(ActionEvent actionEvent) {
        pane.getChildren().clear();
        if (actionEvent.getSource() == btn1) {
            pane.getChildren().add(pane1);
            lbl_title.setText("CMS");
        }
        if (actionEvent.getSource() == btn2) {
            pane.getChildren().add(pane2);
            lbl_title.setText("Server");
        }
        if (actionEvent.getSource() == btn3) {
            pane.getChildren().add(pane3);
            lbl_title.setText("Formatter");
        }
        if (actionEvent.getSource() == btn4) {
            historyController.initializeDatabase();
            pane.getChildren().add(pane4);
            lbl_title.setText("History");
        }
        if (actionEvent.getSource() == btn5) {
            pane.getChildren().add(pane5);
            lbl_title.setText("Statistics");
        }
        if (actionEvent.getSource() == btn6) {
            pane.getChildren().add(pane6);
            lbl_title.setText("Settings");
        }
    }

    @FXML
    void close() {
        ((Stage)btn_x.getScene().getWindow()).close();
    }

    public void showPopUp(String url) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("popUpSure.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            System.out.println(url);
            popController = PopUpSureController.getInstantzia();
            stage.setScene(scene);
            stage.show();
    }
    void getPanels() throws IOException {
        FXMLLoader loaderpane1 = new FXMLLoader(getClass().getResource("/pane1.fxml"));
        pane1 = loaderpane1.load(); //cms
        cmsController = loaderpane1.getController();

        FXMLLoader loaderpane2 = new FXMLLoader(getClass().getResource("/pane2.fxml"));
        pane2 = loaderpane2.load(); //server
        serverController = loaderpane2.getController();

        FXMLLoader loaderpane3 = new FXMLLoader(getClass().getResource("/pane3.fxml"));
        pane3 = loaderpane3.load(); //formatter
        formatterController = loaderpane3.getController();

        FXMLLoader loaderpane4 = new FXMLLoader(getClass().getResource("/pane4.fxml"));
        pane4 = loaderpane4.load(); //history
        historyController = loaderpane4.getController();

        /*FXMLLoader loaderpane5 = new FXMLLoader(getClass().getResource("/pane5.fxml"));
        pane5 = loaderpane5.load(); //stats
        statisticsController = loaderpane4.getController();*/

        FXMLLoader loaderpane6 = new FXMLLoader(getClass().getResource("/pane6.fxml"));
        pane6 = loaderpane6.load(); //settings
        settingsController = loaderpane6.getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Properties p = Utils.getProperties();
        File directory = new File(p.getProperty("pathToFolder"));
        if(!directory.exists()) directory.mkdir();
        Utils.createDirectories();
        try { getPanels(); } catch (IOException e) { e.printStackTrace();}
        pane.getChildren().clear();
    }
}