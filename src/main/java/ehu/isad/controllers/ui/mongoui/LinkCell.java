package ehu.isad.controllers.ui.mongoui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class LinkCell<T> extends TableCell<T, String> {

    private  Hyperlink link;

    public LinkCell() {
        link = new Hyperlink();
        link.setOnAction(evt -> {
            System.out.println("estekan sakatu dut ");
        });
    }


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        link = new Hyperlink(item);
        link.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if( Desktop.isDesktopSupported() )
                {
                    new Thread(() -> {
                        try {
                            Desktop.getDesktop().browse( new URI( link.getText() ) );
                        } catch (IOException | URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }).start();
                }
            }
        });
        setGraphic(empty ? null : link);
    }

}
