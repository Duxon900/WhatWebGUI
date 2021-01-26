package ehu.isad.controllers.ui.mongoui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CMSMongoController {

    @FXML
    private TextField textField;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private Button scanBtn;

    @FXML
    private TableView<?> cmsTable;

    @FXML
    private TableColumn<?, ?> starColumn;

    @FXML
    private TableColumn<?, ?> urlColumn;

    @FXML
    private TableColumn<?, ?> cmsColumn;

    @FXML
    private TableColumn<?, ?> lastUpdatedColumn;

    @FXML
    private TableColumn<?, ?> starColumn1;

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

}
