package sheetUserInformation;

public class sheetUserInformation {
    String key;
    String sheetOwner;
    String fileSize;
    String userPermission;

    public sheetUserInformation(String key, String sheetOwner, String fileSize, String userPermission) {
        this.key = key;
        this.sheetOwner = sheetOwner;
        this.fileSize = fileSize;
        this.userPermission = userPermission;
    }

    public void setUserPermission(String userPermission) {
        this.userPermission = userPermission;
    }
    public String getUserPermission() {
        return userPermission;
    }
}
