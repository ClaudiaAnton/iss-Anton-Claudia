package controller;


import domain.Agent;
import domain.Produs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repo.ProdusRepo;

import java.util.List;

public class CosController {
    private ObservableList<Produs> modelProdus = FXCollections.observableArrayList();

    private Agent agent;
    private ProdusRepo produsRepo;
    private List<Produs> cos;
    @FXML
    TableView<Produs> table;
    @FXML
    TableColumn<?, ?> denumire;
    @FXML
    TableColumn<?, ?> cantitate;
    @FXML
    TableColumn<?, ?> pret;

    public void init(Agent agent, ProdusRepo produsRepo, List<Produs> cos) {
        this.produsRepo = produsRepo;
        this.agent = agent;
        denumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        pret.setCellValueFactory(new PropertyValueFactory<>("pret"));
        this.cos = cos;
        modelProdus.setAll(this.cos);
        table.setItems(modelProdus);
    }


    public void finishButton() {
        for (var i : this.cos) {
            var aux=produsRepo.getOne(i.getDenumire());
            if (i.getCantitate() > aux.getCantitate()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Cantitate invalida!");
                alert.showAndWait();
                return;
            } else {
                produsRepo.update(i.getDenumire(), aux.getCantitate() - i.getCantitate());
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Comanda plasata cu succes!");
        alert.showAndWait();
        modelProdus.clear();
    }
}
