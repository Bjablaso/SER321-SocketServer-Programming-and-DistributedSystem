package travelaround.aroundtheworld;

import AppLogic.LoadData;
import AppLogic.countDown;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LandingController {
    @FXML private HBox gamePlayAreal;
    @FXML private Button playbutton;
    @FXML private Pane playerRanking;
    @FXML private TableView<?> ranktable;
    @FXML private TableColumn<?, ?> rankColumn;
    @FXML private TableColumn<?, ?> nameColumn;
    @FXML private TableColumn<?, ?> searchVolumeColumn;
    @FXML private StackPane landingPage;




    @FXML
    public void initialize() {


    }



    public void switchNow(){
        LoadData loading = new LoadData();
        if(SocketServer.isRunning()){
            int port = SocketServer.getCurrentport();
            if (port < 0){
                throw new RuntimeException("Can't find port");
            }
         //  startClient(port);
            var counter = new countDown();
            counter.startCountdown();
            LoadData load = new LoadData();
            load.loadData();

            ViewSwitcher.switchTo(view.GAMEPLAYVIEW);
            loading.loadData();
        }else{
            // create a window that warn the user to run server
            // if it is not already running or server fials
            System.out.println("Server is not running");
        }
        System.out.println("Is Server Runnning " + SocketServer.isRunning());

    }
//    public void startClient(int port) {
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                "java", "-jar", "build/libs/client.jar", "localhost", String.valueOf(port)
//        );
//        processBuilder.redirectErrorStream(true); // Combine stdout & stderr
//
//        try {
//            Process process = processBuilder.start();
//            new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line); // Log client output in real-time
//                    }
//                } catch (IOException e) {
//                    System.out.println("Error reading client process output: " + e.getMessage());
//                }
//            }).start();
//
//            System.out.println("✅ Client started on port " + port);
//        } catch (IOException e) {
//            System.err.println("❌ Failed to start client: " + e.getMessage());
//        }
//    }



}