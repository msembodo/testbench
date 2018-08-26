package com.idemia.tec.testbench.view;

import com.idemia.tec.testbench.TestbenchApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;
import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootLayoutController {

    static Logger logger = Logger.getLogger(RootLayoutController.class.getName());

    private TestbenchApplication application;

    @Autowired
    private TestBenchController testBench;

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private Menu menuRun;

    private StatusBar appStatusBar;

    public RootLayoutController() {}

    public void setMainApp(TestbenchApplication application) {
        this.application = application;
    }

    @FXML
    private void initialize() {
        appStatusBar = new StatusBar();
        rootBorderPane.setBottom(appStatusBar);

    }

    @FXML
    private void handleMenuQuit() {
        // quit application
        Platform.exit();
    }

    @FXML
    private void handleMenuNewSession() {
        Tab tabAtr = new Tab();
        tabAtr.setText("ATR");
        testBench.getModulesPane().getTabs().add(tabAtr);

        menuRun.setDisable(false);
    }

}
