package com.idemia.tec.testbench;

import com.idemia.tec.testbench.view.RootLayoutController;
import com.idemia.tec.testbench.view.TestBenchController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class TestbenchApplication extends Application {

	private ConfigurableApplicationContext springContext;

	static Logger logger = Logger.getLogger(TestbenchApplication.class.getName());

	private BorderPane rootLayout;
	private Stage primaryStage;

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

			// give controller access to main app
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

			// give controller access to main app
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
