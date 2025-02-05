package AppLogic;

public enum filePath {
    GAMEDATAJSON("src/main/resources/serverData/Data.json");

    private String filePath;

    filePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
