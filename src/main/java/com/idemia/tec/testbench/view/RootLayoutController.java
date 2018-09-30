package com.idemia.tec.testbench.view;

import com.idemia.tec.testbench.TestbenchApplication;
import com.idemia.tec.testbench.model.AdvSaveVariable;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RootLayoutController {
	
	static Logger logger = Logger.getLogger(RootLayoutController.class);

    private TestbenchApplication application;
    private File selectedVarFile;

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
    private void handleMenuLoadVariables() {
    	// user select variable file
    	FileChooser variableFileChooser = new FileChooser();
    	variableFileChooser.setTitle("Select MCC exported variable file");
    	variableFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Variables data", "*.txt"));
    	selectedVarFile = variableFileChooser.showOpenDialog(application.getPrimaryStage());
    	if (selectedVarFile != null) {
    		try {
				Scanner scanner = new Scanner(selectedVarFile);
				List<String> definedVariables = new ArrayList<>();
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.startsWith(".DEFINE"))
						definedVariables.add(line);
				}
				logger.info(String.format("Variable file selected: %s", selectedVarFile.getAbsolutePath()));
				appStatusBar.setText("Variables loaded.");
				for (String line : definedVariables) {
					String[] components = line.split("\\s+");
					application.getAdvSaveVariables().add(new AdvSaveVariable(components[1].substring(1), components[2]));
					testBench.getCmbMccVar().getItems().add(components[1].substring(1));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    		
    	}
    }

    @FXML
    private void handleMenuNewSession() {
        Tab tabAtr = new Tab();
        tabAtr.setText("ATR");
        testBench.getModulesPane().getTabs().add(tabAtr);

        menuRun.setDisable(false);
    }

}
