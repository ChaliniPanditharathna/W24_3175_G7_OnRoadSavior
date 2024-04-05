package com.example.w24_3175_g7_onroadsavior.Database;

import static java.time.LocalDate.now;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.w24_3175_g7_onroadsavior.UserHelperClass;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "onRoadSaviorDB";

    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User ( ID TEXT PRIMARY KEY,Name VARCHAR(255) NOT NULL,Username VARCHAR(100) NOT NULL,Email VARCHAR(255) NOT NULL, Phone_NO VARCHAR(20), User_Type VARCHAR(255) NOT NULL, Created_Date LOCALDATE NOT NULL)");

        db.execSQL("CREATE TABLE ServiceProvider (ID TEXT PRIMARY KEY, Location VARCHAR(255) NOT NULL, BreakDownType VARCHAR(255)\n)");

        db.execSQL("CREATE TABLE BreakDownRequest ( ID INTEGER PRIMARY KEY AUTOINCREMENT,Created_Date TEXT NOT NULL,Updated_Date TEXT, User_ID TEXT NOT NULL, Provider_ID TEXT NOT NULL, Breakdown_Type TEXT,Location TEXT,Description TEXT, Image TEXT, Status VARCHAR(100) NOT NULL ,FOREIGN KEY (User_ID) REFERENCES User(ID),FOREIGN KEY (Provider_ID) REFERENCES ServiceProvider(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS ServiceProvider");
        db.execSQL("DROP TABLE IF EXISTS BreakDownRequest");
        onCreate(db);
    }

    //CRUD - Table: User
    public boolean addUser(String uId, String fullName, String username, String email, String contactNumber, String userType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Date createdDate = new Date();

        contentValues.put("ID", uId);
        contentValues.put("Name", fullName);
        contentValues.put("Username", username);
        contentValues.put("Email", email);
        contentValues.put("Phone_NO", contactNumber);
        contentValues.put("User_Type", userType);
        contentValues.put("Created_Date", String.valueOf(createdDate));

        long result = db.insert("User", null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public UserHelperClass getUserData(String uId) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM User WHERE ID='" + uId + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            String fullName = cursor.getString(1);
            String username = cursor.getString(2);
            String email = cursor.getString(3);
            String contactNum = cursor.getString(4);
            String userType = cursor.getString(5);

            UserHelperClass user = new UserHelperClass();
            user.setFullName(fullName);
            user.setUserName(username);
            user.setEmail(email);
            user.setContactNumber(contactNum);
            user.setUserType(userType);

            return user;
        }

        return null;
    }

    // Clear user table
    public void clearUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("User", null, null);
        db.close();
    }

    //CRUD - Table: ServiceProvider
    public boolean addServiceProvider(String uId, String location, String serviceType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Date createdDate = new Date();

        contentValues.put("ID", uId);
        contentValues.put("Location", location);
        contentValues.put("BreakDownType", serviceType);

        long result = db.insert("ServiceProvider", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public UserHelperClass getServiceProviderData(String uId) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM ServiceProvider WHERE ID='" + uId + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {


            String location = cursor.getString(1);
            String serviceType = cursor.getString(2);

            UserHelperClass serviceProvider = new UserHelperClass();
            serviceProvider.setLocation(location);
            serviceProvider.setServiceType(serviceType);

            return serviceProvider;
        }

        return null;
    }

    // Clear user table
    public void clearServiceProviderTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ServiceProvider", null, null);
        db.close();
    }

    public void addRequest(String createdDate, String updatedDate, String userID, String providerID, String breakdownType, String address, String description, String image, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO BreakDownRequest (Created_Date, Updated_Date, User_ID, Provider_ID, Breakdown_Type, Location, Description, Image, Status) VALUES ('2024-03-12', NULL, '8nBXqXMikLRNuMBkOm9v9pU3VdL2', '5utKiBSA4Ec60prqeE1kGs56uq63', 'Plumbing', '14820 90B Avenue, Surrey, BC', 'The plumbing vehicle is experiencing a significant issue that hampers its functionality and ability to fulfill service requests effectively.', NULL, 'Pending')\n");
    }

    public Cursor getBreakdownRequestData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select *  from BreakDownRequest", null);
        return cursor;
    }

    public Cursor getRequestData(String uid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select b.Breakdown_Type, b.Location, b.Description, b.Created_Date, b.Updated_Date, b.Image, b.User_ID, b.Provider_ID," +
                "u.Name,u.Phone_No, b.ID, b.Status, b.Image  from BreakDownRequest as b INNER JOIN User as u ON b.user_id = u.id WHERE (Status ='Pending' OR Status ='Accept') and Provider_Id = '" + uid + "'", null);
        return cursor;
    }

    public Cursor getRequestHistoryData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select b.Breakdown_Type, b.Location, b.Description, b.Created_Date, b.Updated_Date, b.Image, b.User_ID, b.Provider_ID," +
                "u.Name,u.Phone_No, b.ID, b.Status, b.Image  from BreakDownRequest as b INNER JOIN User as u ON b.user_id = u.id WHERE Status ='Done' OR Status ='Reject'", null);
        return cursor;
    }

    public void acceptRequest(int breakDownRequestId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE BreakDownRequest SET Status = 'Accept' WHERE ID = " + breakDownRequestId + "");
    }

    public void rejectRequest(int breakDownRequestId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE BreakDownRequest SET Status = 'Reject' WHERE ID = " + breakDownRequestId + "");
    }

    public boolean updateStatus(String breakDownRequestId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("UPDATE BreakDownRequest SET Status = 'Done' WHERE ID = " + breakDownRequestId + "");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
