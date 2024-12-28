package com.cat201;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.sql.*;

import com.cat201.controller.AuthController;
import com.cat201.controller.NavController;
import com.cat201.utils.Utils;

public class Main extends Application implements AuthController.LoginCallback {

    static Connection conn;

	@Override
	public void start(Stage primaryStage) {
		// uncomment for development
		// showMainPage(primaryStage);
		// uncomment for production
		try {
			AuthController authController = new AuthController(primaryStage, conn, this);
			authController.createLogin();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLoginSuccess(Stage primaryStage) {
		showMainPage(primaryStage);
	}

	public void showMainPage(Stage primaryStage) {
		Utils.get().setPrimaryStage(primaryStage);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
			VBox root = loader.load();

			// Use css file for styling
			root.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

			primaryStage.setTitle("Main");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Add navbar controller 
		NavController navController = new NavController(primaryStage, conn);
		navController.createEventListeners();
	}
	
	public static void main(String[] args) throws Exception{
        String url = "jdbc:sqlite:lawfirm.db";
        conn = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
		launch(args);
	}
}
