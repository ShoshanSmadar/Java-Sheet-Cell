package utils;

import engine.Engine;
import sheetRights.SheetRights;

import java.util.List;
import java.util.Map;

public interface EngineInformation {

    void askReadingPermission(String userAskingPermission);

    void askWritingPermission(String userAskingPermission);


    void addNewUser(String newUser);

    void allowReadingPermission(String userAskingPermission, boolean allow);

    void allowWritingPermission(String userAskingPermission, boolean allow);

    String getSheetOwner();

    Engine getEngine();

    String getFileSize();

    List<String> getHistory();

    Map<String, SheetRights> getReadingRights();

    Map<String,SheetRights> getWritingRights();

    Map<String, String> getAllRequests();

    Map<String, SheetRights> getSpecificUsersPermissionsForAllSheet(String userName);

    String getCurrentUserPermission(String userName);
}
