package ehu.isad.controllers.ui.mongoui;

import ehu.isad.controllers.ui.CMSController;
import ehu.isad.model.ServerCMSModel;
import ehu.isad.utils.CmsMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CMSMongoController implements Initializable {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField textField;

    @FXML
    private Button scanBtn;

    /*----Table----*/
    @FXML
    private TableView<CmsMongo> cmsTable;

    @FXML
    private TableColumn<?, ?> starColumn;

    @FXML
    private TableColumn<CmsMongo, ?> urlColumn;

    @FXML
    private TableColumn<CmsMongo, ?> cmsColumn;

    @FXML
    private TableColumn<CmsMongo, ?> lastUpdatedColumn;

    /*------MenuItem-----*/
    @FXML
    private MenuItem openBrowser;

    @FXML
    private MenuItem favUnFav;

    @FXML
    private MenuItem scanTwitter;

    @FXML
    private MenuItem scanReddit;

    @FXML
    private MenuItem scanTumblr;

    @FXML
    private MenuItem targetTwitter;

    @FXML
    private MenuItem targetFacebook;

    @FXML
    private MenuItem targetReddit;

    @FXML
    private MenuItem targetTumblr;

    private static CMSMongoController instance=new CMSMongoController();

    @FXML
    void onBrowserRow(ActionEvent event) {
    }

    @FXML
    void onClick(ActionEvent event) {
        /**
         * onClick scan button
         */


    }

    @FXML
    void onFavUnFavRow(ActionEvent event) {

    }

    @FXML
    void onKeyPressed(KeyEvent event) {

    }

    @FXML
    void scan(ActionEvent event) {

    }

    @FXML
    void target(ActionEvent event) {

    }

    public static CMSMongoController getInstance() { return instance; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItems();
        filterAll();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("All");
        list.add("Favorites");
        comboBox.setValue("All");
        comboBox.setItems(list);
        comboBox.setOnAction(e -> {
            String value = comboBox.getValue();
            if(value.equals("Favorites")){
                filterFavorites();
            }else{
                filterAll();
            }
        });
        serverCMSController.linkClick(cmsTable);
        style();

    }

    private void setItems() {
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("target"));
        urlColumn.setCellFactory(tc -> new LinkCell());
        cmsColumn.setCellValueFactory(new PropertyValueFactory<>("plugins"));
    }
}
