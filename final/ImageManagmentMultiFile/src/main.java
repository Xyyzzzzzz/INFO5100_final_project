import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class main extends Application {

    private static Button uploadButton, filterButton;
    private static Stage window;
    private static Scene startScene;
    private static FiltersInterface filtersInterface;
    private static UpLoadInterface uploadInterface;

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;

        //set overall layout
        VBox layout = new VBox(30);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        Label label = new Label("Image Management Tool");

        //set upload button
        uploadButton = new Button("Upload");
        uploadButton.setMaxSize(200,50);

        //set filter button
        filterButton = new Button("Filters");
        filterButton.setMaxSize(200,50);

        layout.getChildren().addAll(label, uploadButton, filterButton);

        //set start Scene
        startScene = new Scene(layout, 500, 500);
        window.setScene(startScene);
        window.setTitle("Image Management Tool");

        //set actions to upload and filter buttons
        uploadInterface = new UpLoadInterface(window, startScene);
        uploadButton.setOnAction(uploadInterface);

        filtersInterface = new FiltersInterface(window, startScene);
        filterButton.setOnAction(filtersInterface);

        //set parameters to label, upload button and filter button
        label.setStyle("-fx-font-size: 35px; -fx-font-weight: Bold");
        uploadButton.setStyle("-fx-font-size: 20px; -fx-font-weight: Bold");
        filterButton.setStyle("-fx-font-size: 20px; -fx-font-weight: Bold");

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
