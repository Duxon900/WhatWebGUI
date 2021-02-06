package ehu.isad.controllers.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.controllers.ui.mongoui.CMSMongoController;
import ehu.isad.model.MongoUser;
import ehu.isad.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Parent paneCMS;
    CMSController cmsController;
    private Parent paneServer;
    ServerController serverController;
    private Parent paneFormatter;
    FormatterController formatterController;
    private Parent paneHistory;
    HistoryController historyController;
    private Parent paneSettings;
    SettingsController settingsController;
    private  Parent paneMultiAdd;
    MultiController multiController;
    private Parent paneSecurity;
    SecurityController securityController;
    private Parent paneCharts;
    ChartController chartController;

    int output=1;

    /*----Mongo Controllers----*/
    private Parent CMSMongoPane;
    CMSMongoController cmsMongoController;


    @FXML
    private Pane pane;

    @FXML
    private Button btnCMS;

    @FXML
    private Button btnServer;

    @FXML
    private Button btnFormatter;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnMultiAdd;

    @FXML
    private Button btnSecurity;

    @FXML
    private Button btnCharts;

    @FXML
    private Label lbl_title;

    @FXML
    private FontAwesomeIconView btn_x;

    @FXML
    private Button minimize;

    private static MainController instance = new MainController();

    private MainController(){
    }

    public static MainController getInstance() { return instance; }

    @FXML
    void handleClick(ActionEvent actionEvent) {

        pane.getChildren().clear();
        if (actionEvent.getSource() == btnCMS) {
            output = 1;
            if(MongoUser.getInstance().getCollection().equals("")) {
                cmsController.setItems();
                pane.getChildren().add(paneCMS);
                lbl_title.setText("CMS");
            }
            else{
                pane.getChildren().add(CMSMongoPane);
                lbl_title.setText("CMS Mongo");
            }
        }
        if (actionEvent.getSource() == btnServer) {
            output = 2;
            serverController.setItems();
            pane.getChildren().add(paneServer);
            lbl_title.setText("Server");
        }
        if (actionEvent.getSource() == btnFormatter) {
            output = 3;
            pane.getChildren().add(paneFormatter);
            lbl_title.setText("Formatter");
        }
        if (actionEvent.getSource() == btnHistory) {
            output = 4;
            historyController.setItems();
            pane.getChildren().add(paneHistory);
            lbl_title.setText("History");
        }
        if (actionEvent.getSource() == btnSettings) {
            output = 6;
            pane.getChildren().add(paneSettings);
            lbl_title.setText("Settings");
        }
        if (actionEvent.getSource() == btnMultiAdd) {
            output = 7;
            pane.getChildren().add(paneMultiAdd);
            lbl_title.setText("Multi-add");
        }
        if (actionEvent.getSource() == btnCharts) {
            pane.getChildren().add(paneCharts);
            lbl_title.setText("Charts");
        }

        if (actionEvent.getSource() == btnSecurity) {
            pane.getChildren().add(paneSecurity);
            lbl_title.setText("Security");
        }
    }


    @FXML
    void close() {
        File tab = new File(System.getProperty("user.home")+"/"+ Utils.getProperties().getProperty("pathToFolder")+".tab");
        if (tab.exists()){tab.delete();}
        try {
            FileOutputStream fos = new FileOutputStream(tab);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(String.valueOf(output));
            bw.newLine();
            bw.close();
        } catch (Exception e) {e.printStackTrace();}

        ((Stage)btn_x.getScene().getWindow()).close();
    }

    @FXML
    void hoverClose(){
        //TODO
        //btn_x.set
    }

    public void showPopUp() throws IOException {
        File done = new File(System.getProperty("user.home")+"/"+Utils.getProperties().getProperty("pathToFolder")+".done");
        if(!done.exists()){
            done.createNewFile();
            FXMLLoader loaderTutorial = new FXMLLoader(getClass().getResource("/panes/tutorial.fxml"));
            loaderTutorial.setController(TutorialController.getInstance());
            Parent root = loaderTutorial.load();
            Stage stage = new Stage();
            stage.setTitle("WhatWebGUI tutorial");
            stage.getIcons().add(new Image(MainController.class.getResourceAsStream("/img/iconsmall.png")));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void openTab() {
        File tab = new File(System.getProperty("user.home")+"/"+ Utils.getProperties().getProperty("pathToFolder")+".tab");
        if (tab.exists()){
            FileReader fr;
            try {
                fr = new FileReader(tab);
                BufferedReader br=new BufferedReader(fr);
                String line;
                boolean gone=false;
                int lineint;
                while((line=br.readLine())!=null && !gone) {
                    try{
                    lineint = Integer.parseInt(line);}
                    catch (Exception e){lineint=1;}
                    switch(lineint){
                        case 2:
                            pane.getChildren().add(paneServer);
                            lbl_title.setText("Server");
                            break;
                        case 3:
                            pane.getChildren().add(paneFormatter);
                            lbl_title.setText("Formatter");
                            break;
                        case 4:
                            pane.getChildren().add(paneHistory);
                            lbl_title.setText("History");
                            break;
                        case 5:
                            pane.getChildren().add(paneSecurity);
                            lbl_title.setText("Security");
                            break;
                        case 6:
                            pane.getChildren().add(paneSettings);
                            lbl_title.setText("Settings");
                            break;
                        case 7:
                            pane.getChildren().add(paneMultiAdd);
                            lbl_title.setText("Multi-add");
                            break;
                        case 9:
                            pane.getChildren().add(paneCharts);
                            lbl_title.setText("Charts");
                        default:
                            pane.getChildren().add(paneCMS);
                            lbl_title.setText("CMS");
                            break;
                    }
                }
                fr.close();
            } catch(IOException e){ pane.getChildren().add(paneCMS);}
        } else {
            pane.getChildren().add(paneCMS);
        }


    }

    void getPanels() throws IOException {
        FXMLLoader loaderpane1 = new FXMLLoader(MainController.class.getResource("/panes/CMSPane.fxml"));
        cmsController = CMSController.getInstance();
        loaderpane1.setController(cmsController);
        paneCMS = loaderpane1.load(); //cms

        FXMLLoader loaderpaneCMSMongo = new FXMLLoader(MainController.class.getResource("/panes/Mongo/CMSPaneMongo.fxml"));
        cmsMongoController = CMSMongoController.getInstance();
        loaderpaneCMSMongo.setController(cmsMongoController);
        CMSMongoPane = loaderpaneCMSMongo.load(); //cmsMongo

        FXMLLoader loaderpane2 = new FXMLLoader(MainController.class.getResource("/panes/ServerPane.fxml"));
        serverController = ServerController.getInstance();
        loaderpane2.setController(serverController);
        paneServer = loaderpane2.load(); //server

        FXMLLoader loaderpane3 = new FXMLLoader(MainController.class.getResource("/panes/FormatterPane.fxml"));
        formatterController = FormatterController.getInstance();
        loaderpane3.setController(formatterController);
        paneFormatter = loaderpane3.load(); //formatter

        FXMLLoader loaderpane4 = new FXMLLoader(MainController.class.getResource("/panes/HistoryPane.fxml"));
        historyController = HistoryController.getInstance();
        loaderpane4.setController(historyController);
        paneHistory = loaderpane4.load(); //history

        FXMLLoader loaderpane6 = new FXMLLoader(MainController.class.getResource("/panes/SettingsPane.fxml"));
        settingsController = SettingsController.getInstance();
        loaderpane6.setController(settingsController);
        paneSettings = loaderpane6.load(); //settings

        FXMLLoader loaderpane7 = new FXMLLoader(MainController.class.getResource("/panes/MultiAddPane.fxml"));
        multiController = MultiController.getInstance();
        loaderpane7.setController(multiController);
        paneMultiAdd = loaderpane7.load(); //Multi-add option

        FXMLLoader loaderpane8 = new FXMLLoader(MainController.class.getResource("/panes/SecurityPane.fxml"));
        securityController = SecurityController.getInstance();
        loaderpane8.setController(securityController);
        paneSecurity = loaderpane8.load(); //security

        FXMLLoader loaderpane9 = new FXMLLoader(MainController.class.getResource("/panes/ChartsPane.fxml"));
        chartController = chartController.getInstance();
        loaderpane9.setController(chartController);
        paneCharts = loaderpane9.load(); //charts
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try { getPanels(); } catch (IOException e) { e.printStackTrace();}
        pane.getChildren().clear();
        openTab();
    }
}