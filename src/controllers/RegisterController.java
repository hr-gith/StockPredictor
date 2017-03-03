package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.AccountsCollection;

/**
 * @author Barkha
 */
public class RegisterController extends Application {

    public void start(Stage primaryStage) {
        // Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Creating First Name label
        Label firstName = new Label("First Name:");
        grid.add(firstName, 0, 0);
        // Defining the Name text field
        final TextField name = new TextField();
        name.setPromptText("Enter your first name.");
        name.setPrefColumnCount(10);
        name.getText();
        GridPane.setConstraints(name, 1, 0);
        grid.getChildren().add(name);

        // Creating Last Name label
        Label LName = new Label("Last Name:");
        grid.add(LName, 0, 1);
        // Defining the Last Name text field
        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your last name.");
        GridPane.setConstraints(lastName, 1, 1);
        grid.getChildren().add(lastName);

        // Creating Email Id label
        Label emailId = new Label("Email Id:");
        grid.add(emailId, 0, 2);
        // Defining the email text field
        final TextField email = new TextField();
        email.setPrefColumnCount(15);
        email.setPromptText("Enter your email id");
        GridPane.setConstraints(email, 1, 2);
        grid.getChildren().add(email);

        // Creating password label
        Label pswd = new Label("Password:");
        grid.add(pswd, 0, 3);
        //Defining Password field
        final PasswordField pwBox = new PasswordField();
        pwBox.setPrefColumnCount(15);
        pwBox.setPromptText("Enter your password");
        GridPane.setConstraints(pwBox, 1, 3);
        grid.getChildren().add(pwBox);

        // Defining the Submit button
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 5, 0);
        grid.getChildren().add(submit);

        // Creating label for displaying validation
        final Label validationLabel = new Label();
        GridPane.setConstraints(validationLabel, 0, 5);
        GridPane.setColumnSpan(validationLabel, 2);
        grid.getChildren().add(validationLabel);
        validationLabel.setTextFill(Color.web("#FF0000"));

        // Setting action for Submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((name.getText() == null || name.getText().isEmpty())){
                    validationLabel.setText("Enter first name");
                }
                else if((lastName.getText() == null || lastName.getText().isEmpty())){
                    validationLabel.setText("Enter last name");
                }
                else if((email.getText() == null || email.getText().isEmpty())){
                    validationLabel.setText("Enter Email Id");
                }
                else if((pwBox.getText() == null || pwBox.getText().isEmpty())){
                    validationLabel.setText("Enter password");
                }
                else{
                    AccountsCollection ac = new AccountsCollection();
                    ac.LoadAccounts();
                    AccountsCollection.Add(name.getText(),lastName.getText(),email.getText(),pwBox.getText());
                    AccountsCollection.WriteToJson();
                    validationLabel.setText("Registration is successfully done");
                    validationLabel.setTextFill(Color.web("#008000"));

                    // Defining the login button
                    Button backBtn = new Button("Back to the login page");
                    GridPane.setConstraints(backBtn, 0, 7,3,1);
                    grid.getChildren().add(backBtn);

                    //Setting an action for the Clear button
                    backBtn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            (new AccountController()).start(primaryStage);
                        }
                    });
                }
            }
        });

        // Defining the Clear button
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 5, 1);
        grid.getChildren().add(clear);

        //Setting an action for the Clear button
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.clear();
                lastName.clear();
                email.clear();
                pwBox.clear();
            }
        });

        Scene scene = new Scene(grid, 500, 250);
        primaryStage.setTitle("Registration Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
