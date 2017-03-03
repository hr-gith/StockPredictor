package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.AccountsCollection;

import java.io.IOException;

/**
 * @author Barkha
 */
public class AccountController extends Application {
    public void start(Stage primaryStage) {
        // Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Creating User Id label
        Label UserId = new Label("User Id:");
        grid.add(UserId, 0, 0);
        // Defining the u text field
        final TextField user = new TextField();
        user.setPrefColumnCount(15);
        user.setPromptText("Enter your user id");
        GridPane.setConstraints(user, 1, 0);
        grid.getChildren().add(user);

        // Creating password label
        Label loginpswd = new Label("Password:");
        grid.add(loginpswd, 0, 1);
        //Defining Password field
        final PasswordField LoginpwBox = new PasswordField();
        LoginpwBox.setPrefColumnCount(15);
        LoginpwBox.setPromptText("Enter your password");
        GridPane.setConstraints(LoginpwBox, 1, 1);
        grid.getChildren().add(LoginpwBox);

        // Defining the Login button
        Button logInBtn = new Button("Log In");
        GridPane.setConstraints(logInBtn, 5, 0);
        grid.getChildren().add(logInBtn);

        // Defining the Login button
        Button registerBtn = new Button("Register");
        GridPane.setConstraints(registerBtn, 5, 1);
        grid.getChildren().add(registerBtn);

        // Creating label for displaying validation
        final Label validationlabel = new Label();
        GridPane.setConstraints(validationlabel, 0, 3);
        GridPane.setColumnSpan(validationlabel, 2);
        grid.getChildren().add(validationlabel);
        validationlabel.setTextFill(Color.web("#FF0000"));

        // Setting action for Login button
        logInBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((user.getText() == null || user.getText().isEmpty())){
                    validationlabel.setText("Enter user Id");
                }
                else if((LoginpwBox.getText() == null || LoginpwBox.getText().isEmpty())){
                    validationlabel.setText("Enter Password");
                }
                else{
                    AccountsCollection ac = new AccountsCollection();
                    ac.LoadAccounts();
                    Boolean match = ac.SearchAccounts(user.getText(), LoginpwBox.getText());
                    if (match.equals(true)){
                        // redirect to main page of application
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../views/stockPredictor.fxml"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        primaryStage.setTitle("Stock predictor");
                        primaryStage.setScene(new Scene(root, 1000, 600));
                        primaryStage.show();
                    }
                    else{
                        validationlabel.setText("Incorrect Username OR Password");
                    }
                }
            }
        });

        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                (new RegisterController()).start(primaryStage);
            }
        });

        Scene scene = new Scene(grid, 500, 250);

        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
