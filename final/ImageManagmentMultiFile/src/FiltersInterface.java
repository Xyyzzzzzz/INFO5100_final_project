import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FiltersInterface implements EventHandler<ActionEvent>  {
    private static Stage window;
    private static Scene filterScene,prevScene;
    private static Button backButton;
    private static GridPane filtersGridPane = new GridPane();

    @Override
    public void handle(ActionEvent actionEvent) {

        // Each time we clear the children
        filtersGridPane.getChildren().clear();
        try {
            final FileChooser fileChooser = new FileChooser();
            FileInputStream input;
            Image image;
            ImageView imageView1,imageView2;

            File file = fileChooser.showOpenDialog(window);

            filtersGridPane.setHgap(20);
            filtersGridPane.setVgap(20);

            //set color adjust parameters
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(0.1);
            colorAdjust.setHue(-0.05);
            colorAdjust.setBrightness(0.1);
            colorAdjust.setSaturation(0.2);

            //set greyscale parameters
            ColorAdjust greyScale = new ColorAdjust();
            greyScale.setSaturation(-1);

            if (file != null) {
                input = new FileInputStream(file);
                image = new Image(input);

                //adjust image color
                imageView1 = new ImageView(image);
                imageView1.setFitWidth(150);
                imageView1.setFitHeight(150);
                imageView1.setEffect(colorAdjust);
                filtersGridPane.add(imageView1, 1, 1);

                //greyscale image
                imageView2 = new ImageView(image);
                imageView2.setFitWidth(150);
                imageView2.setFitHeight(150);
                imageView2.setEffect(greyScale);
                filtersGridPane.add(imageView2, 2, 1);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        window.setScene(filterScene);
        window.show();
    }

    FiltersInterface(Stage window, Scene prevScene) {
        this.window = window;
        this.prevScene = prevScene;

        //create back button
        backButton = new Button("Back");
        backButton.setOnAction(e -> {
            window.setScene(prevScene);
            return;
        });
        backButton.setMaxSize(200,50);
        backButton.setStyle("-fx-font-size: 20px; -fx-font-weight: Bold");

        // create label
        Label label = new Label("Filtered images");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        label.setStyle("-fx-font-size: 35px; -fx-font-weight: Bold");

        // set images layout
        filtersGridPane.setHgap(20);
        filtersGridPane.setVgap(20);

        layout.getChildren().addAll(label, filtersGridPane, backButton);

        // Initialize currentFiltersScene
        filterScene = new Scene(layout, 500, 500);
    }
}