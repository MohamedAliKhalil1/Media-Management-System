import presentation.guiModelView.ModelView;
import userInterface.guiMitCliThread.CliThread;
import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import controller.MVcontroller;
import userInterface.cliView.View;
import data.producerManager.ProducersManager;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ZusätzlicheAnforderungen extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userInterface/guiView/MyView.fxml"));
        Parent root = loader.load();

        ModelView modelView = loader.getController();
        primaryStage.setTitle("Media Management");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();

        BigDecimal size = null;
        boolean flag = true;
        AtomicReference<String> input = new AtomicReference<>();
        while (flag) {
            TextInputDialog dialog = new TextInputDialog("Enter Size in Giga bytes");
            dialog.setTitle("Set Memory size");
            dialog.setHeaderText("Enter Size in Giga bytes");
            dialog.setContentText("Size:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                input.set(name);
            });
            try {
                size = new BigDecimal(Integer.parseInt(input.get())*1024);
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("falsche Angaben");
            }
        }

        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(size);
        MediaVerwalter crud = new MediaVerwalter(producersManager, memoryManager);
        View parser = new View();
        MVcontroller model = new MVcontroller(parser, crud);
        model.setViewAnzeigen(false);
        modelView.setModel(model);

        Thread thread = new CliThread(modelView.getModel());
        thread.start();
    }
}
