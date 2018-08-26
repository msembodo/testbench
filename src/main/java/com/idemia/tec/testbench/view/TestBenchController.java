package com.idemia.tec.testbench.view;

import com.idemia.tec.testbench.TestbenchApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBenchController {

    static Logger logger = Logger.getLogger(TestBenchController.class.getName());

    private TestbenchApplication application;

    @FXML
    private TabPane modulesPane;

    @Autowired
    private RootLayoutController root;

    public TestBenchController() {}

    public void setMainApp(TestbenchApplication application) {
        this.application = application;
    }

    @FXML
    private void initialize() {

    }

    public TabPane getModulesPane() {
        return modulesPane;
    }

}
