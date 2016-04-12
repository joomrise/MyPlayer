package sample;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Created by pbweb on 08.04.16.
 */
public class Player extends BorderPane
{
    private Media media;
    private MediaPlayer player;
    private MediaView view;
    private Pane mpane;
    private MediaBar mediaBar;

    public MediaPlayer getPlayer()
    {
        return player;
    }

    Player(String filePath)
    {
        media = new Media(filePath);
        player = new MediaPlayer(media);
        view  = new MediaView(player);

        //we put the media into pane which is located in the center
        mpane = new Pane();
        mpane.getChildren().add(view);
        setCenter(mpane);

        mediaBar = new MediaBar(player);
        //the mediabar is on the bottom of the player
        setBottom(mediaBar);

        //css style of the BorderPane
        setStyle("-fx-background-color: #000");
        player.play();
    }
}
