package ehu.isad.controllers.ui;

import ehu.isad.model.HistoryModel;
import ehu.isad.model.ServerCMSModel;
import ehu.isad.utils.Url;
import ehu.isad.utils.Utils;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

public class CMSController {

    @FXML
    private Pane pane1;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private Button scanBtn;

    @FXML
    private TableView<ServerCMSModel> cmsTable;

    @FXML
    private TableColumn<ServerCMSModel, String> urlColumn;

    @FXML
    private TableColumn<ServerCMSModel, String> cmsColumn;

    @FXML
    private TableColumn<ServerCMSModel, String> versionColumn;

    @FXML
    private TableColumn<ServerCMSModel, String> lastUpdatedColumn;

    private final ServerCMSController serverCMSController = ServerCMSController.getInstance();
    Url urlUtils = new Url();
    Desktop desktop = java.awt.Desktop.getDesktop();

    private static CMSController instance = new CMSController();

    private CMSController(){};

    public static CMSController getInstance() { return instance; }

    @FXML
    private MenuItem removeRow;
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
    @FXML
    void onBrowserRow(ActionEvent event) {
    }

    @FXML
    void onFavUnFavRow(ActionEvent event) {
    }

    @FXML
    void onRemoveRow(ActionEvent event) {
        cmsTable.getItems().remove(cmsTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void scan(ActionEvent event) throws IOException {
        MenuItem menuitem = (MenuItem) event.getSource();
        ServerCMSModel item = cmsTable.getSelectionModel().getSelectedItem();
        if (item != null){
            String url = null;
            String[] encoded = encoded(item);
            String domain = URLEncoder.encode(item.getUrl().replace("/","").split(":")[1], StandardCharsets.UTF_8);
            if (menuitem.equals(scanTwitter)){
                url = "https://twitter.com/intent/tweet?text=Target%3A%20"+encoded[0]+
                        "%0ACMS%3A%20"+encoded[1]+
                        "%0ACMS%20version%3A%20"+encoded[2]+
                        "%0A%0AScanned%20on%20"+encoded[3]+
                        "%20with%20%40WhatWebGUI&hashtags=whatweb";
            } else if (menuitem.equals(scanReddit)){
                url = "http://www.reddit.com/submit?text=Target%3A%20"+encoded[0]+
                        "%20%20%0ACMS%3A%20"+encoded[1]+
                        "%20%20%0ACMS%20version%3A%20"+encoded[2]+
                        "%20%20%0A%0AScanned%20on%20"+encoded[3]+
                        "%20with%20%5B%40WhatWebGUI%5D(https%3A%2F%2Fwhatwebgui.github.io%2F)"+
                        "&title="+domain+"%20%7C%20CMS%20scan%20%7C%20WhatWebGUI";
            } else if (menuitem.equals(scanTumblr)){
                url = "https://www.tumblr.com/widgets/share/tool?posttype=link&caption=Target%3A%20"+encoded[0]+
                        "%3Cbr%3ECMS%3A%20"+encoded[1]+
                        "%3Cbr%3ECMS%20version%3A%20"+encoded[2]+
                        "%3Cbr%3E%3Cbr%3EScanned%20on%20"+encoded[3]+
                        "%20with%20%3Ca%20href%3D%22https%3A%2F%2Fwhatwebgui.github.io%2F%22%3E%40WhatWebGUI%3C%2Fa%3E"+
                        "&tags=whatwebgui%2Cwhatweb%2C"+domain+"&canonicalUrl="+encoded[0];
            }
            desktop.browse(URI.create(url));
        }
    }

    @FXML
    void target(ActionEvent event) throws IOException {
        MenuItem menuitem = (MenuItem) event.getSource();
        ServerCMSModel item = cmsTable.getSelectionModel().getSelectedItem();
        if (item != null){
            String url = null;
            String targetEncoded = URLEncoder.encode(item.getUrl(), StandardCharsets.UTF_8);
            //String domain = URLEncoder.encode(item.getUrl().replace("/","").split(":")[1], StandardCharsets.UTF_8);
            if (menuitem.equals(targetTwitter)){
                url = "https://twitter.com/intent/tweet?url=";
            } else if (menuitem.equals(targetFacebook)){
                url = "https://www.facebook.com/share.php?u=";
            } else if (menuitem.equals(targetReddit)){
                url = "https://www.reddit.com/submit?url=";
            }else if (menuitem.equals(targetTumblr)){
                url = "https://www.tumblr.com/widgets/share/tool?posttype=link&canonicalUrl=";
            }
            desktop.browse(URI.create(url+targetEncoded));
        }
    }

    private String[] encoded(ServerCMSModel item) {
        return serverCMSController.encoded(item,'c');
    }

    @FXML
    void onClick(ActionEvent event) {
        try {
            if(urlUtils.processUrl(textField.getText())!=null){
                CMS(urlUtils.processUrl(textField.getText()));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void onKeyPressed(KeyEvent event) throws IOException, SQLException {
        if (event.getCode().toString().equals("ENTER")) {
            urlUtils.processUrl(textField.getText());
        }
    }

    void CMS(String url) throws IOException {
        String domain = url.replace("/", "").split(":")[1];
        serverCMSController.click(domain, url);
        cmsTable.setItems(serverCMSController.getCMSList());
        filter();
    }

    public void setItems() {
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        cmsColumn.setCellValueFactory(new PropertyValueFactory<>("cms"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
    }

    private void filter(){
        FilteredList<ServerCMSModel> filteredData = new FilteredList<>(serverCMSController.getCMSList(), b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cmsmodel -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (cmsmodel.getUrl().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (cmsmodel.getCms().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (cmsmodel.getVersion().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else
                    return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ServerCMSModel> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(cmsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        cmsTable.setItems(sortedData);
    }

    @FXML
    void initialize() throws SQLException {
        setItems();
        cmsTable.setItems(serverCMSController.getCMSList());
        filter();
    }
}