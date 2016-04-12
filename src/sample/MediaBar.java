package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;

/**
 * Created by pbweb on 11.04.16.
 */
//the class which is for creating a horizontal mediabar at the bottom of the player
public class MediaBar extends HBox
{
    private MediaPlayer mediaPlayer;
    private Slider timeSlider = new Slider();
    private Slider volumeSlider = new Slider();

    //Initially the button is on pause
    private Button playButton = new Button("||");
    private Label volumeLabel = new Label("Volume: ");

    MediaBar(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;

        //HBox will be in the center
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10,10,10,10));
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMinWidth(30);

        //100 is default volume value for the player
        volumeSlider.setValue(100);

        //the timeslider is with the highest priority
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        playButton.setPrefWidth(30);

        //Handling events of the PlayButton
        playButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                MediaPlayer.Status status = mediaPlayer.getStatus();
                if(status == MediaPlayer.Status.PLAYING)
                {
                    //The statement means: if we got to the end of the video
                    if(mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration()))
                    {
                        //We play the video again from the beginning
                        mediaPlayer.seek(mediaPlayer.getStartTime());
                        mediaPlayer.play();
                    }
                    else
                    {
                        //we pause the video
                        mediaPlayer.pause();
                        playButton.setText(">");
                    }

                }

                if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED)
                {
                    //we pause the video
                    mediaPlayer.play();
                    playButton.setText("||");
                }
            }
        });

        //The time slider will change during the video progress. Here the code for it
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener()
        {
            //we are listening to invalidation events of the currentimeProperty
            @Override
            public void invalidated(Observable observable)
            {
                updateValuses();
            }
        });

        //Setting listener to value property of TimeSlider - if we change timeslider - the movie progress jumps to the corresponding point
        timeSlider.valueProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                if(timeSlider.isPressed())
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue()/100));
            }
        });

        //Adding volumeSlider listener
        volumeSlider.valueProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                if(volumeSlider.isPressed())
                    mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });



        //adding the elements in the specific order
        getChildren().add(playButton);
        getChildren().add(timeSlider);
        getChildren().add(volumeLabel);
        getChildren().add(volumeSlider);

    }

    //method updates timeslider values
    private void updateValuses()
    {
        Platform.runLater(new Runnable()
        {
            //this runnable object will be put into eventqueue, and ran later in JavaFx Thread.
            @Override
            public void run()
            {
                timeSlider.setValue(mediaPlayer.getCurrentTime().toMillis()/mediaPlayer.getTotalDuration().toMillis()*100);
            }
        });
    }

}
