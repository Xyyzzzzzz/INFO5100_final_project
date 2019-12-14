import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.List;

public final class UpLoadInterface implements EventHandler<ActionEvent> {

    private static Stage window;
    private static Scene uploadScene, prevScene;
    private static GridPane gridPane = new GridPane();
    private static Button downloadButton,backButton;
    private static List<File> files;
    private static final FileChooser fileChooser = new FileChooser();

    @Override
    public void handle(ActionEvent actionEvent) {

        FileInputStream input;
        Image image;
        ImageView imageView;

        //clear the children
        gridPane.getChildren().clear();

        //users can select one or more images
        files = fileChooser.showOpenMultipleDialog(window);

        //stay at the start page if the user click cancel in selection page
        if (files == null) {
            return;
        }else {
            int row, col;

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);

                //layout all uploaded images in 4 * 4 format
                row = i % 4;
                col = i / 4;
                try {
                    input = new FileInputStream(file);
                    image = new Image(input);
                    imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);

                    gridPane.add(imageView, row, col);

                    //get the image metadata: camera, width, height, coordinate
                    javaxt.io.Image imageInfo = new javaxt.io.Image(file.getPath());
                    java.util.HashMap<Integer, Object> exif = imageInfo.getExifTags();
                    double[] coordinate = imageInfo.getGPSCoordinate();

                    //display metadata of each image
                    if (coordinate != null) {
                        Tooltip t1 = new Tooltip("Camera: " + exif.get(0x0110) + "\n"
                                + "Width x Height: " + imageInfo.getWidth() + " x " + imageInfo.getHeight() +"\n"
                                + "Coordinate: " + coordinate[0] + ", " + coordinate[1]+  "\n");
                        Tooltip.install(imageView, t1);
                    } else {
                        Tooltip t = new Tooltip("Camera: " + exif.get(0x0110) + "\n"
                                + "Width x Height: " + imageInfo.getWidth() + " x " + imageInfo.getHeight() +"\n");
                        Tooltip.install(imageView, t);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        window.setScene(uploadScene);
        downloadButton.setOnAction(new DownloadInterface(files, window));

        window.show();
    }

    UpLoadInterface(Stage window, Scene prevScene) {
        this.window = window;
        this.prevScene = prevScene;

        // create downloadButton
        downloadButton = new Button("Download");
        downloadButton.setMaxSize(200,50);
        downloadButton.setStyle("-fx-font-size: 20px; -fx-font-weight: Bold");

        // create back button
        backButton = new Button("Back");
        backButton.setMaxSize(200,50);
        backButton.setStyle("-fx-font-size: 20px; -fx-font-weight: Bold");
        backButton.setOnAction(e -> {
            window.setScene(prevScene);
            return;
        });

        // create label
        Label label=new Label("Uploaded Images");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        label.setStyle("-fx-font-size: 35px; -fx-font-weight: Bold");

        // set images layout
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        HBox hbox = new HBox(30);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(backButton,downloadButton);

        //images are laid out in vertical column
        layout.getChildren().addAll(label, gridPane, hbox);

        // Initialize uploadScene
        uploadScene = new Scene(layout, 500, 500);
    }
}
