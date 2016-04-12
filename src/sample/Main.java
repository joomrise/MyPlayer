package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application
{
    private Player player;
    private FileChooser fileChooser;

    @Override
    public void start(final Stage primaryStage)
    {
        //Adding file chooser
        MenuItem openFile = new MenuItem("Open");
        Menu file = new Menu("File");
        MenuBar menuBar = new MenuBar();
        file.getItems().add(openFile);
        menuBar.getMenus().add(file);
        fileChooser = new FileChooser();

        if(player == null)
        {
            File f = fileChooser.showOpenDialog(primaryStage);
            if(f != null)
            {
                try
                {
                    player = new Player(f.toURI().toURL().toExternalForm());
                    player.setTop(menuBar);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        openFile.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                player.getPlayer().pause();
                File f = fileChooser.showOpenDialog(primaryStage);
                if(f != null)
                {
                    try
                    {
                        player = new Player(f.toURI().toURL().toExternalForm());
                        player.setTop(menuBar);
                        Scene newScene = new Scene(player, 720, 525, Color.BLACK);
                        primaryStage.setScene(newScene);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        Scene scene = new Scene(player, 720, 525, Color.BLACK);
        player.setTop(menuBar);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
