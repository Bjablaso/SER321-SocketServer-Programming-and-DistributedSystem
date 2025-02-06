package travelaround.aroundtheworld;

public enum view {
    LANDINGVIEW ("startupwindow.fxml"),
    STARTUPVIEW("hello-view.fxml"),
    GAMEPLAYVIEW("game-window.fxml"),
    SCOREVIEW("score-window.fxml");

        private String filename;
    view(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
