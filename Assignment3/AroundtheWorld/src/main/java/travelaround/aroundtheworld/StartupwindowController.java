package travelaround.aroundtheworld;


import AppLogic.ImportantFolder;
import AppLogic.LoadData;
import AppLogic.countDown;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartupwindowController {

    @FXML
    private Button StartButton;

    @FXML
    private Button closeProgramButton;

    @FXML
    private Button defultOkButton;

    @FXML
    private ImageView displayDialogImage;

    @FXML
    private Label headerLabe;


    @FXML
    private DialogPane dailogwindow;


    @FXML
    private TextField portEntry;

    @FXML
    private Label warningLabel;
    private int cusPort;


    @FXML
    public void initialize() {

    }

    public void switchNow(){
        boolean isInt = isInterger(portEntry.getText());
        if(isInt) {

            ViewSwitcher.switchTo(view.GAMEPLAYVIEW);
            retrieveCustomPort();
            var countdown = new countDown();
            countdown.startCountdown();

        }else {
            Alert alreat = new Alert(Alert.AlertType.WARNING);
            alreat.setTitle("Not an Interger");
            alreat.setHeaderText(null);
            alreat.setContentText("Port number can be integer only");
            portEntry.setText("");
        }
    }

    public void switchNowThan(){
        findDefualtPort();
      //  startServerTask(cusPort);
        ViewSwitcher.switchTo(view.STARTUPVIEW);
    }

    public void retrieveCustomPort(){
        cusPort = Integer.parseInt(portEntry.getText());
        System.out.println("Custom port: " + cusPort);

    }

   public boolean isInterger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
   }


//
//    public void startServerTask(int port) {
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                "./gradlew", "startServer", "-Pport=" + port
//        );
//        processBuilder.redirectErrorStream(true);
//
//        try {
//            Process process = processBuilder.start();
//            new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line); // Log Gradle output
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//
//            System.out.println("Server started on port " + port);
//        } catch (IOException e) {
//            System.err.println("Failed to start server: " + e.getMessage());
//        }
//    }

    public void findDefualtPort(){

        String folderPath = ImportantFolder.NETWORKFOLDER.getFolderName();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles(); // retrieve all the folder

        if(listOfFiles != null && listOfFiles.length > 0) {
            File neededFile = listOfFiles[0];
            String fullPath = neededFile.getAbsolutePath();

            try {

                String conntent = new String(Files.readAllBytes(Paths.get(fullPath)));
                JSONObject obj = new JSONObject(conntent);

                 cusPort = obj.getInt("port");
                System.out.println("current port: " + cusPort);
            }catch (IOException e){
                System.err.println("Failed to find defualt port: " + e.getMessage());
            }
        }

    }


}
