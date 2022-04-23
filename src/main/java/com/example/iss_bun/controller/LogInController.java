package com.example.iss_bun.controller;


import com.example.iss_bun.repo.AgentRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    private AgentRepo agentRepo;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button logInButton;
    private Stage stage;

    public LogInController() {
    }

    public void init(AgentRepo a){
        this.agentRepo=a;
    }

    public void logInButtonClick( ) throws IOException {
        var agent=agentRepo.findOneByUsername(username.getText());
        System.out.println(agent.getPassword());
        if (agent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The account doesn't exist");
            alert.showAndWait();
        } else if (password.getText() == agent.getPassword()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Wrong password!");
            alert.showAndWait();
        } else {
            FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("mainPage.fxml"));
            Parent croot=cloader.load();


            MainPageController ctrl = cloader.getController();
            ctrl.init(agent);

            Stage stage=new Stage();
            stage.setScene(new Scene(croot));
            stage.show();

//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainPage.fxml"));
//
////            MainPageController mainPageController=fxmlLoader.getController();
////            mainPageController.init(agent.getId());
//            stage = (Stage) logInButton.getScene().getWindow();
//            Scene scene = null;
//            try {
//                scene = new Scene(fxmlLoader.load());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            stage.setScene(scene);

        }
    }
}
