// enter "mvn javafx:run" in the terminal to run
// image used: https://www.shutterstock.com/search/cute-pig-icon
// gif used: https://tenor.com/search/running-pig-gifs

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


// main class
public class Main extends Application
{

    // starts program
    public static void main(String[] args)
    {
        launch(args);
    }


    // creates scene
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Version 2.0");
        stage.show();
    }
}