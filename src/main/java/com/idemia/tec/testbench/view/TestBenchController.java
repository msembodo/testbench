package com.idemia.tec.testbench.view;

import com.google.gson.Gson;
import com.idemia.tec.testbench.TestbenchApplication;
import com.idemia.tec.testbench.model.VariableMapping;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.apache.log4j.Logger;
import org.hildan.fxgson.FxGson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBenchController {
	
	@FXML
	private TableView<VariableMapping> tblMapping;
	@FXML
	private TableColumn<VariableMapping, String> clmnMappedTo;
	@FXML
	private TableColumn<VariableMapping, String> clmnMccVar;
	@FXML
	private TextField txtMappedTo;
	@FXML
	private ComboBox<String> cmbMccVar;
	@FXML
	private CheckBox chkFixedVal;
	@FXML
	private TextField txtMccVar;
	
    static Logger logger = Logger.getLogger(TestBenchController.class.getName());

    private TestbenchApplication application;

    @FXML
    private TabPane modulesPane;

    @Autowired
    private RootLayoutController root;

    public TestBenchController() {}

    public void setMainApp(TestbenchApplication application) {
        this.application = application;
        
        // add observable list data to table
        tblMapping.setItems(application.getMappings());
        
        // initialise MCC variable combo box
        // TODO: in real case this list will be populated from MCC advance save
    	for (VariableMapping mapping : application.getMappings()) {
    		if (!mapping.isFixed())
    			cmbMccVar.getItems().add(mapping.getMccVariable());
    	}
    	
//    	listMapping();
    }

    @FXML
    private void initialize() {
    	// initialise mapping table
    	clmnMappedTo.setCellValueFactory(cellData -> cellData.getValue().mappedVariable());
    	clmnMccVar.setCellValueFactory(cellData -> cellData.getValue().mccVariable());
    	
    	// clear mapping display
    	showMappings(null);
    	
    	// listen for selection changes and show mapping detail when changed
    	tblMapping.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> showMappings(newValue));
    	
    	chkFixedVal.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (chkFixedVal.isSelected()) {
					cmbMccVar.setDisable(true);
					txtMccVar.setDisable(false);
				} else {
					cmbMccVar.setDisable(false);
					txtMccVar.setDisable(true);
				}
			}
		});
    }
    
    private void showMappings(VariableMapping mapping) {
    	if (mapping != null) {
    		// fill mappings with info from mapping object
    		txtMappedTo.setText(mapping.getMappedVariable());
    		if (mapping.isFixed()) {
    			chkFixedVal.setSelected(true);
    			txtMccVar.setText(mapping.getValue());
    		} else {
    			chkFixedVal.setSelected(false);
    			cmbMccVar.setValue(mapping.getMccVariable());
			}
    	} else {
			txtMappedTo.setText("");
			txtMccVar.setText("");
			cmbMccVar.setValue(null);
		}
    }

    public TabPane getModulesPane() {
        return modulesPane;
    }
    
    @FXML
    private void handleBtnUpdateMapping() {
    	if (mappedVariableExist(txtMappedTo.getText())) {
    		VariableMapping selectedMapping = tblMapping.getSelectionModel().getSelectedItem();
    		if (chkFixedVal.isSelected()) {
    			selectedMapping.setFixed(true);
    			selectedMapping.setMccVariable(null);
    			selectedMapping.setValue(txtMccVar.getText());
    		} else {
    			selectedMapping.setFixed(false);
    			selectedMapping.setMccVariable(cmbMccVar.getValue());
    			selectedMapping.setValue(null);
			}
    	} else {
			// add new mapping
    		String mappedVariable = txtMappedTo.getText();
    		boolean fixed = false;
    		String mccVariable = cmbMccVar.getValue();
    		String value = null;
    		if (chkFixedVal.isSelected()) {
    			fixed = true;
    			mccVariable = null;
    			value = txtMccVar.getText();
    		}
    		application.getMappings().add(new VariableMapping(mappedVariable, mccVariable, value, fixed));
    	}
//    	listMapping();
    }
    
    @FXML
    private void handleBtnDeleteMapping() {
	    if (application.getMappings().size() > 0) {
	    	int selectedIndex = tblMapping.getSelectionModel().getSelectedIndex();
	    	tblMapping.getItems().remove(selectedIndex);
	    	showMappings(null);
	    	tblMapping.getSelectionModel().clearSelection();
//	    	listMapping();
    	}
    }
    
    private boolean mappedVariableExist(String testMappedVariable) {
    	for (VariableMapping mapping : application.getMappings()) {
    		if (mapping.getMappedVariable().equals(testMappedVariable))
    			return true;
    	}
    	return false;
    }
    
    private void listMapping() {
    	Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    	System.out.println(gson.toJson(application.getMappings()));
    }

}
