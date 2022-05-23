package controller;


import domain.Agent;
import domain.Produs;
import events.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import observer.Observer;
import repo.ProdusRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPageController implements Observer<EventType> {
    private Agent agent;
    private ProdusRepo produsRepo;
    private List<Produs> cos;

    @FXML TableView<Produs> table;
    @FXML TableColumn<?,?> denumire;
    @FXML TableColumn<?,?> cantitate;
    @FXML TableColumn<?,?> pret;
    @FXML TextField cantitateText;

    private ObservableList<Produs> modelProdus = FXCollections.observableArrayList();

    public MainPageController() {
    }

    public void init( Agent agent, ProdusRepo produsRepo){
        this.produsRepo=produsRepo;
        this.produsRepo.addObserver(this);
        this.agent=agent;
        denumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        pret.setCellValueFactory(new PropertyValueFactory<>("pret"));
        modelProdus.setAll(get());
        table.setItems(modelProdus);
        cos=new ArrayList<>();
    }

    private List<Produs> get(){
        for(var i : produsRepo.getAll())
            System.out.println(i.getClass()+" "+i.getDenumire());
        return produsRepo.getAll();
    }

    public void addButtonClick( ) {
        Produs pr=table.getSelectionModel().getSelectedItem();
        long can=Long.parseLong(cantitateText.getText());
        if (can>pr.getCantitate())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cantitate indisponibila!");
            alert.showAndWait();
        }
        else{
            Produs temp=new Produs(pr.getDenumire(),pr.getPret(),can);
            cos.add(temp);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Produs adaugat cu succes!");
            alert.showAndWait();
            cantitateText.clear();
        }
    }

    public void cosButtonClick( ) {
        FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("cosPage.fxml"));
        Parent croot= null;
        try {
            croot = cloader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CosController ctrl = cloader.getController();
        Stage stage=new Stage();
        stage.setScene(new Scene(croot));
        stage.show();
        ctrl.init(agent,this.produsRepo, this.cos);
    }


    @Override
    public void update(EventType eventType) {
        modelProdus.setAll(get());
    }
}
