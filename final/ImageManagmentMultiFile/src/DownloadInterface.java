import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.util.List;

public class DownloadInterface implements EventHandler<ActionEvent> {
    private List<File> files;
    private Window window;
    private static changeFormat changeFormat;

    public DownloadInterface(List<File> files, Window window) {
        changeFormat = new changeFormat();
        this.files = files;
        this.window = window;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("output");
        fileChooser.getExtensionFilters().addAll
                (new FileChooser.ExtensionFilter("PNG","*.png"),
                        new FileChooser.ExtensionFilter("GIF", "*.gif"),
                        new FileChooser.ExtensionFilter("BMP","*.bmp"),
                        new FileChooser.ExtensionFilter("JPG","*.jpg"));

        File file1 = fileChooser.showSaveDialog(window);

        changeFormat.change(files, file1.getAbsolutePath());
    }
}
