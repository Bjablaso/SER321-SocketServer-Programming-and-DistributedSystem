package AppLogic;

public enum ImportantFolder {
    NETWORKFOLDER("networkconnectivity");


    private String folderName;
    ImportantFolder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
