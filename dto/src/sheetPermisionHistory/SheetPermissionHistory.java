package sheetPermisionHistory;

import sheetRights.SheetRights;

public class SheetPermissionHistory {
    private String userName;
    private SheetRights sheetRights;
    private String rightsType;

    public SheetPermissionHistory(String userName, SheetRights sheetRights, String rightsType) {
        this.userName = userName;
        this.sheetRights = sheetRights;
        this.rightsType = rightsType;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public SheetRights getSheetRights() {
        return sheetRights;
    }
    public void setSheetRights(SheetRights sheetRights) {
        this.sheetRights = sheetRights;
    }
    public String getRightsType() {
        return rightsType;
    }
    public void setRightsType(String rightsType) {
        this.rightsType = rightsType;
    }
}
