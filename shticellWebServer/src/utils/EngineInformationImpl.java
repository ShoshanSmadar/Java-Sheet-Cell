package utils;

import engine.Engine;
import sheetRights.SheetRights;

import java.util.*;

import static constant.Constants.*;
import static utils.constants.Constants.*;

public class EngineInformationImpl implements EngineInformation {
    String sheetOwner;
    Engine engine;
    String sheetSize;
    Map<String,SheetRights> readingRights;
    Map<String,SheetRights> writingRights;
    List<String> history;

//    public static final String READING_PERMISSION_ASK =" asked for reading permission";
//    public static final String READING_PERMISSION_APPROVED  =" approved reading permission for";
//    public static final String READING_PERMISSION_REJECTED  =" rejected reading permission for ";
//    public static final String WRITING_PERMISSION_ASK =" asked for writing permission";
//    public static final String WRITING_PERMISSION_APPROVED =" approved writing permission for ";
//    public static final String WRITING_PERMISSION_REJECTED  =" rejected writing permission for ";

    public EngineInformationImpl(String sheetOwner, Engine engine, String sheetSize) {
        this.sheetOwner = sheetOwner;
        this.engine = engine;
        this.sheetSize = sheetSize;
        readingRights = new HashMap<>();
        readingRights.put(sheetOwner, SheetRights.OWNER);
        writingRights = new HashMap<>();
        writingRights.put(sheetOwner, SheetRights.OWNER);
        history = new ArrayList<>();
        history.add(ADDED_SHEET + sheetOwner);
    }

    @Override
    public void askReadingPermission(String userAskingPermission){
        if(readingRights.get(userAskingPermission) != SheetRights.APPROVED){
            readingRights.replace(userAskingPermission, SheetRights.PENDING);
        }
        history.add(userAskingPermission + READING_PERMISSION_ASK);
    }

    @Override
    public void askWritingPermission(String userAskingPermission){
        if(writingRights.get(userAskingPermission) != SheetRights.APPROVED){
            writingRights.replace(userAskingPermission, SheetRights.PENDING);
        }
        history.add(userAskingPermission + WRITING_PERMISSION_ASK);
    }

    @Override
    public void addNewUser(String newUser){
        writingRights.put(newUser, SheetRights.NONE);
        readingRights.put(newUser, SheetRights.NONE);
        history.add(newUser + NEW_USER_ADDED);
    }

    @Override
    public void addOldUser(String oldUser) {
        writingRights.put(oldUser, SheetRights.NONE);
        readingRights.put(oldUser, SheetRights.NONE);
    }

    @Override
    public void allowReadingPermission(String userAskingPermission, boolean allow){
        if(allow){
            readingRights.replace(userAskingPermission, SheetRights.APPROVED);
            history.add(sheetOwner + READING_PERMISSION_APPROVED + userAskingPermission);
        }
        else {
            readingRights.replace(userAskingPermission, SheetRights.REJECTED);
            history.add(sheetOwner + READING_PERMISSION_REJECTED + userAskingPermission);
        }
    }

    @Override
    public void allowWritingPermission(String userAskingPermission, boolean allow){
        if(allow){
            writingRights.replace(userAskingPermission, SheetRights.APPROVED);
            history.add(sheetOwner + WRITING_PERMISSION_APPROVED + userAskingPermission);
        }
        else {
            writingRights.replace(userAskingPermission, SheetRights.REJECTED);
            history.add(sheetOwner + WRITING_PERMISSION_REJECTED + userAskingPermission);
        }
    }

    @Override
    public String getSheetOwner() {
        return sheetOwner;
    }

    @Override
    public Engine getEngine() {
        return engine;
    }

    @Override
    public String getFileSize() {
        return sheetSize;
    }

    @Override
    public List<String> getHistory(){
        return history;
    }

    @Override
    public Map<String,SheetRights> getReadingRights(){
        return readingRights;
    }

    @Override
    public Map<String,SheetRights> getWritingRights(){
        return writingRights;
    }

    @Override
    public  Map<String, String> getAllRequests(){
        Map<String, String> requests = new HashMap<>();
        for(Map.Entry<String,SheetRights> entry : readingRights.entrySet()){
            if(entry.getValue() == SheetRights.PENDING){
                requests.put(entry.getKey(), READ_PERMISSION);
            }
        }
        for(Map.Entry<String,SheetRights> entry : writingRights.entrySet()){
            if(entry.getValue() == SheetRights.PENDING){
                requests.put(entry.getKey(), WRITE_PERMISSION);
            }
        }
        return requests;
    }

    @Override
    public Map<String, SheetRights> getSpecificUsersPermissionsForAllSheet(String userName){
        Map<String, SheetRights> userPermissions = new HashMap<>();
        userPermissions.put(READ_PERMISSION, readingRights.get(userName));
        userPermissions.put(WRITE_PERMISSION, writingRights.get(userName));
        return userPermissions;
    }

    @Override
    public String getCurrentUserPermission(String userName){
        if(userName.equals(sheetOwner)){
            return OWNER_PERMISSION;
        }
        if(readingRights.get(userName) == SheetRights.APPROVED){
            return READ_PERMISSION;
        }
        if(writingRights.get(userName) == SheetRights.APPROVED){
            return WRITE_PERMISSION;
        }
        else return NONE_PERMISSION;
    }

}
