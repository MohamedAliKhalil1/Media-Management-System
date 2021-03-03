package presentation.guiModelView;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import data.mediaDB.MediaContentUploadable;
import data.mediaDB.implementation.UploaderBeschreibung;
import data.producerManager.ProducersManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import controller.MVcontroller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class ModelView <T extends MediaContentUploadable> implements PropertyChangeListener {

    private PieChart.Data usedSlice;
    private PieChart.Data freeSlice;
    private ObservableList<PieChart.Data> pieChartData;
    public StringProperty textProperty;
    private StringProperty pieChartTitle;
    public ObservableList<T> outputProperty;
    public ObservableList<UploaderBeschreibung> outputPropertyProduzent;
    private ObservableList<T> tauschenProperty;
    public ProducersManager producersManager;
    private MVcontroller model;



    @FXML ListView listview;
    @FXML ListView listviewTarget;
    @FXML ListView listviewProduzent;
    @FXML PieChart piechart;
    @FXML private TextArea textarea;


    @FXML private void initialize(){
        this.textarea.textProperty().bindBidirectional(this.textProperty);
        this.piechart.titleProperty().bindBidirectional(this.pieChartTitle);
        this.listview.setItems(this.outputProperty);
        this.listviewProduzent.setItems(this.outputPropertyProduzent);
        this.listviewTarget.setItems(this.tauschenProperty);
        this.piechart.setData(this.pieChartData);
        this.piechart.setTitle("Memory Größe");
        this.piechart.setLabelLineLength(10);
        this.textarea.setPromptText("Enter the producer name and the the media ");
        this.listviewTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
                listviewTarget.setStyle("-fx-background-color: linear-gradient(#328BDB 0%, #207BCF 25%, #1973C9 75%, #0A65BF 100%);");
            }
        });
    }

    public ModelView(){
        this.textProperty = new SimpleStringProperty();
        this.pieChartTitle = new SimpleStringProperty();
        this.outputProperty = FXCollections.observableArrayList();
        this.outputPropertyProduzent = FXCollections.observableArrayList();
        this.tauschenProperty = FXCollections.observableArrayList();
        this.usedSlice = new PieChart.Data("verbraucht", 0.0);
        this.freeSlice = new PieChart.Data("Frei", 100.0);
        this.pieChartData = FXCollections.observableArrayList(this.freeSlice, this.usedSlice);
    }

    public void setModel(MVcontroller model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.pieChartTitle.set("Memory Größe " + model.getGesamtSizeOfMemory() + " MBs");
    }

    public MVcontroller getModel() {
        return model;
    }

    private void updateChart(BigDecimal frei, BigDecimal gesamtSize){
        this.pieChartData.get(1).setPieValue(gesamtSize.subtract(frei).doubleValue());
        this.pieChartData.get(0).setPieValue(frei.doubleValue());
        this.pieChartTitle.set("restlicher Platz " + model.getRestSizeOfMemory() + " MBs");
    }

       public void update(List resultToShow){
        this.textProperty.setValue(null);
        this.updateChart(this.model.getRestSizeOfMemory(), this.model.getGesamtSizeOfMemory());
        if (resultToShow != null) {
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    outputProperty.setAll(resultToShow);
                   outputPropertyProduzent.setAll(model.getProducers());
                }
            });
        }
    }

    public void hochladen(ActionEvent actionEvent) {
        this.model.setCurrentMode(":c");
        this.model.start(this.textProperty.get());
    }

   public void loeschen(ActionEvent actionEvent) {
           try {
               if (this.listview.getItems().size() != 0 && this.listview.getSelectionModel().getSelectedItem() != null) {
                   this.model.setCurrentMode(":d");
                   this.model.start((this.outputProperty.get(this.listview.getSelectionModel().getSelectedIndex()).getAddr()));
               }
           } catch (Exception e){ // nur für Test nach eingegeben Adress löschen
               if (!this.model.hasMode(":d"))
                   this.model.start(":d");
               this.model.start(this.textProperty.get());
           }
        }

    public void nachProduzent(ActionEvent actionEvent) {
        this.model.nachProduzentenSortieren();
    }

    public void nachAdress(ActionEvent actionEvent) {
        this.model.nachAdressSortieren();
    }

    public void nachAbrufen(ActionEvent actionEvent) {
        this.model.nachAbrufSortieren();
    }

    public void auflisten(ActionEvent actionEvent) {
        if (this.textProperty.get() != null) {
            this.model.setCurrentMode(":r");
            this.model.start("content " + this.textProperty.get());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a type of media");
            alert.showAndWait();
        }
    }

    public void abrufen(ActionEvent actionEvent) {
        try {
            if (this.listview.getItems().size() != 0 && this.listview.getSelectionModel().getSelectedItem() != null) {
                this.model.update((this.outputProperty.get(this.listview.getSelectionModel().getSelectedIndex()).getAddr()));
            }
        } catch (Exception e){ // nur für Test nach eingegeben Adress Access count +1
                this.model.update(this.textProperty.get());
            }
    }

    public void list1OnDragDetected(MouseEvent mouseEvent) {
    ClipboardContent content = new ClipboardContent();
    Dragboard db = this.listview.startDragAndDrop(TransferMode.ANY);
    content.putString(String.valueOf(this.listview.getSelectionModel().getSelectedIndex()));
    db.setContent(content);
    }

    public void list2OnDragDropped(DragEvent dragEvent) {
        this.listviewTarget.setStyle("-fx-control-inner-background: white ;");
        this.tauschenProperty.add(this.outputProperty.get(Integer.parseInt(dragEvent.getDragboard().getString())));
        if (this.tauschenProperty.size() == 2){
            this.tauschen((T)this.tauschenProperty.get(0), (T)this.tauschenProperty.get(1));
            this.tauschenProperty.clear();
            this.update(this.listview.getItems().sorted());
        }

    }
    private void tauschen(T media1, T media2){
        String Addr1 = media1.getAddr();
        media1.setAdress(media2.getAddr());
        media2.setAdress(Addr1);
    }

    public void saveJBP(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        this.model.setCurrentMode(":p");
        this.model.start("saveJBP");
    }

    public void loadJBP(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        this.model.setCurrentMode(":p");
        this.model.start("loadJBP");
        //this.model.loadJBP(file.getPath());
    }

    public void saveJOS(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        this.model.setCurrentMode(":p");
        this.model.start("saveJOS");
    }


    public void loadJOS(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        //this.model.loadJOS(file.getPath());
        this.model.setCurrentMode(":p");
        this.model.start("loadJOS");
    }

    public void saveMediaRandom(ActionEvent actionEvent) {
        if (this.listview.getSelectionModel().getSelectedItem() != null){
            this.model.setCurrentMode(":p");
            this.model.start("save " + this.outputProperty.get(this.listview.getSelectionModel().getSelectedIndex()).getAddr());
        }
    }

    public void loadMediaRandom(ActionEvent actionEvent) {
        if (this.textProperty.get() != null){
            this.model.setCurrentMode(":p");
            this.model.start("load " + this.textProperty.get());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid address of media to load !!");
            alert.showAndWait();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(new Runnable() {
            @Override public void run() {

                if (evt.getPropertyName().startsWith("save")){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText(evt.getPropertyName());
                    alert.showAndWait();
                } else if (evt.getPropertyName().startsWith("Failed")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(evt.getPropertyName());
                    alert.showAndWait();
                } else {
                    update((List) evt.getNewValue());
                }
            }
        });
    }

    public void loeschenProduzent(ActionEvent actionEvent) {
        try {
            if (this.listviewProduzent.getItems().size() != 0 && this.listviewProduzent.getSelectionModel().getSelectedItem() != null) {
                this.model.setCurrentMode(":d");
                this.model.start((this.outputPropertyProduzent.get(this.listviewProduzent.getSelectionModel().getSelectedIndex()).getName()));
            }
        } catch (Exception e){ // nur für Test nach eingegeben Adress löschen
            if (!this.model.hasMode(":d"))
                this.model.start(":d");
            this.model.start(this.textProperty.get());
        }
    }
}
