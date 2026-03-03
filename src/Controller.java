import java.awt.Desktop;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;


// provides logic for the FXML UI
public class Controller
{
    // variables
    @FXML
    private Button addImgButton;

    @FXML
    private TextArea imgPathTextArea;

    @FXML
    private Button addFolderButton;

    @FXML
    private TextArea folderPathTextArea;

    @FXML
    private Rectangle coverRectangle;

    @FXML
    private ImageView progressBarImageView;

    @FXML
    private Label resultLabel;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button viewImgButton;


    // methods
    // user clicks 'Add Image' to add an image for which he expects to find a match in folders
    @FXML
    void clickAddImg(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        File selectedImg = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        
        if (selectedImg != null)
        {
            addImgButton.setVisible(false);
            imgPathTextArea.setText(selectedImg.getAbsolutePath());
        }
    }


    // user clicks the trash bin icon to clear image path
    @FXML
    void clickClearImg(ActionEvent event)
    {
        imgPathTextArea.clear();
        addImgButton.setVisible(true);
    }


    // user clicks 'Add Folder' to add a folder containing multiple images
    @FXML
    void clickAddFolder(ActionEvent event)
    {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedFolder = dirChooser.showDialog(((Button) event.getSource()).getScene().getWindow());
        
        if (selectedFolder != null)
        {
            folderPathTextArea.appendText(selectedFolder.getAbsolutePath() + "\n");
        }
    }


    // user clicks the trash bin icon to clear folder paths
    @FXML
    void clickClearFolders(ActionEvent event)
    {
        folderPathTextArea.clear();
    }


    // !!!only returns the fist result
    // user clicks 'Search' to get the file path of the matching image in folder
    // displays the image path or "not found" to the user
    @FXML
    void clickSearch(ActionEvent event)
    {
        // resets the output area
        resultLabel.setVisible(false);
        outputTextArea.setVisible(false);
        viewImgButton.setVisible(false);

        // one threads starts to play animation, does not block the following code
        playAnimation();

        // reads input
        String imgPath = imgPathTextArea.getText();

        List<String> folderPaths = folderPathTextArea.getText()
        .lines()
        .collect(java.util.stream.Collectors.toList());

        // multithread search (searches multiple folders simultaneously)
        Task<String> searchTask = new Task<>()
        {
            @Override
            // defines what searchTask should do
            protected String call()
            {
                // creates 3 threads
                ExecutorService threads = Executors.newFixedThreadPool(3);
                List<Future<String>> results = new ArrayList<>();
        
                // distributes tasks
                try
                {
                    for (String folderPath : folderPaths)
                    {
                        Future<String> result = threads.submit(() -> SearchByImage.Search(folderPath, imgPath));
                        results.add(result);
                    }
        
                    // reads results
                    for(Future<String> current : results)
                    {
                        String result = current.get();

                        if (!result.equals("0"))
                        {
                            return result;
                        }
                    }
                    
                    return "0";
                }
               
                catch (Exception e)
                {
                    System.out.println(e);
                    return "0";
                }

                finally
                {
                    threads.shutdown();
                }
            }
        };

        // defines what happens if searchTask succeeds
        searchTask.setOnSucceeded(e ->
        {
            String result = searchTask.getValue();
            resultLabel.setVisible(true);

            if(!result.equals("0"))
            {
                outputTextArea.setText(result);
                viewImgButton.setVisible(true);
            }
            else
            {
                outputTextArea.setText("not found");
            }

            outputTextArea.setVisible(true);
        });

        // defines what happens if searchTask fails
        searchTask.setOnFailed(e ->
        {
            resultLabel.setVisible(true);
            outputTextArea.setText("not found");
            outputTextArea.setVisible(true);
        });

        // starts the searchTask running
        Thread thread = new Thread(searchTask);
        thread.setDaemon(true);
        thread.start();
    }


    // user clicks 'View Image' to preview the image found
    @FXML
    void clickViewImg(ActionEvent event)
    {
        String imgPath = outputTextArea.getText();
        File img = new File(imgPath);

        try
        {
            Desktop.getDesktop().open(img);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    // method that plays the animation in the image view
    private void playAnimation()
    {
        coverRectangle.setVisible(true);
        progressBarImageView.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e ->
        {
            progressBarImageView.setVisible(false);
            coverRectangle.setVisible(false);
        });
        
        pause.play();
    }
}