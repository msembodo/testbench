package com.idemia.tec.testbench;

import com.idemia.tec.testbench.model.AdvSaveVariable;
import com.idemia.tec.testbench.model.VariableMapping;
import com.idemia.tec.testbench.view.RootLayoutController;
import com.idemia.tec.testbench.view.TestBenchController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class TestbenchApplication extends Application {

	private ConfigurableApplicationContext springContext;

	private BorderPane rootLayout;
	private Stage primaryStage;
	
	private ObservableList<AdvSaveVariable> advSaveVariables = FXCollections.observableArrayList();
	private ObservableList<VariableMapping> mappings = FXCollections.observableArrayList();
	
	public TestbenchApplication() {
		// add sample variables
//		advSaveVariables.add(new AdvSaveVariable("USIM_ICCID", "89034563729453"));
//		advSaveVariables.add(new AdvSaveVariable("GSM_IMSI", "57832534"));
		
		// add sample mappings
//		mappings.add(new VariableMapping("ICCID", "USIM_ICCID", null, false));
//		mappings.add(new VariableMapping("IMSI", "USIM_IMSI", null, false));
//		mappings.add(new VariableMapping("GPIN1", null, "31313131FFFFFFFF", true));
	}

	public static void main(String[] args) {
		launch(TestbenchApplication.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TestBench");
		
		initRootLayout();
		showTestBench();
	}
	
	public ObservableList<AdvSaveVariable> getAdvSaveVariables() {
		return advSaveVariables;
	}

	public ObservableList<VariableMapping> getMappings() {
		return mappings;
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(TestbenchApplication.class);
	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/RootLayout.fxml"));
			loader.setControllerFactory(springContext::getBean);
			rootLayout = (BorderPane) loader.load();
			
			new JMetro(JMetro.Style.LIGHT).applyTheme(rootLayout);
			
			// give controller access to main application
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showTestBench() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TestBench.fxml"));
			loader.setControllerFactory(springContext::getBean);
			AnchorPane testBench = (AnchorPane) loader.load();

			rootLayout.setCenter(testBench);

			// give controller access to main application
			TestBenchController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
