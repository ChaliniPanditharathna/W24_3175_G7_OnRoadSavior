package com.example.w24_3175_g7_onroadsavior.Database;

import static java.time.LocalDate.now;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.w24_3175_g7_onroadsavior.UserHelperClass;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
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

        //db.execSQL("CREATE TABLE ServiceProvider (ID TEXT PRIMARY KEY, Location VARCHAR(255) NOT NULL, BreakDownType VARCHAR(255)\n)");

        db.execSQL("CREATE TABLE ServiceProvider (ID TEXT PRIMARY KEY, Location VARCHAR(255) NOT NULL, BreakDownType VARCHAR(255), Rating REAL DEFAULT 0\n)");


        db.execSQL("CREATE TABLE BreakDownRequest ( ID INTEGER PRIMARY KEY AUTOINCREMENT,Created_Date TEXT NOT NULL,Updated_Date TEXT, User_ID TEXT NOT NULL, Provider_ID TEXT NOT NULL, Breakdown_Type TEXT,Location TEXT,Description TEXT, Image TEXT, Status VARCHAR(100) NOT NULL ,FOREIGN KEY (User_ID) REFERENCES User(ID),FOREIGN KEY (Provider_ID) REFERENCES ServiceProvider(ID))");

        db.execSQL("CREATE TABLE Notification ( ID INTEGER PRIMARY KEY AUTOINCREMENT,Created_Date LOCALDATE NOT NULL,Updated_Date LOCALDATE, RECIVER_ID Text NOT NULL, Message TEXT, Status VARCHAR(100) NOT NULL ,FOREIGN KEY (RECIVER_ID) REFERENCES User(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS ServiceProvider");
        db.execSQL("DROP TABLE IF EXISTS BreakDownRequest");
        onCreate(db);
    }

    //CRUD - Table: User
    public boolean addUser(String uId, String fullName, String username, String email, String contactNumber, String userType){
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

    public UserHelperClass getUserData(String uId){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM User WHERE ID='"+uId+"'";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){

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
    public boolean addServiceProvider(String uId, String location, String serviceType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Date createdDate = new Date();

        contentValues.put("ID", uId);
        contentValues.put("Location", location);
        contentValues.put("BreakDownType",serviceType);

        long result = db.insert("ServiceProvider", null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public UserHelperClass getServiceProviderData(String uId){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM ServiceProvider WHERE ID='"+uId+"'";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){


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

        Log.d("breakdownType",breakdownType);
        Log.d("Location",address);

        db.execSQL("INSERT INTO BreakDownRequest (Created_Date, Updated_Date, User_ID, Provider_ID, Breakdown_Type, Location, Description, Image, Status) VALUES ( '"+createdDate+"',  '"+createdDate+"',  '"+userID+"',  '"+providerID+"',  '"+breakdownType+"',  '"+address+"',  '"+description+"',  '"+image+"',  '"+status+"')\n");
        db.execSQL("INSERT INTO Notification (Created_Date, Updated_Date, RECIVER_ID, Message, Status) VALUES ('"+createdDate+"', '"+createdDate+"','"+providerID+"', 'You have a new request',  'Pending')\n");

    }

    public Cursor getBreakdownRequestData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select *  from BreakDownRequest", null);
        return  cursor;
    }

    public void updateProviderRating(String providerId, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Rating", rating);
        db.update("ServiceProvider", values, "ID=?", new String[]{providerId});
        db.close();
    }

    public float getProviderRating(String providerId) {
        float rating = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Rating FROM ServiceProvider WHERE ID = ?", new String[]{providerId});

        if (cursor != null && cursor.moveToFirst()) {
            rating = cursor.getFloat(0);
            cursor.close();
        }

        return rating;
    }

    public Cursor getRequestData(String uid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select b.Breakdown_Type, b.Location, b.Description, b.Created_Date, b.Updated_Date, b.Image, b.User_ID, b.Provider_ID," +
                "u.Name,u.Phone_No, b.ID, b.Status, b.Image  from BreakDownRequest as b INNER JOIN User as u ON b.user_id = u.id WHERE (Status ='Pending' OR Status ='Accept') and Provider_Id = '" + uid + "'", null);
        return cursor;
    }

    public Cursor getRequestHistoryData(String uid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select b.Breakdown_Type, b.Location, b.Description, b.Created_Date, b.Updated_Date, b.Image, b.User_ID, b.Provider_ID," +
                "u.Name,u.Phone_No, b.ID, b.Status, b.Image  from BreakDownRequest as b INNER JOIN User as u ON b.user_id = u.id WHERE Status ='Done' OR Status ='Reject' and Provider_Id = '" + uid + "'", null);
        return cursor;
    }

    public void acceptRequest(int breakDownRequestId, String userId, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE BreakDownRequest SET Status = 'Accept' WHERE ID = " + breakDownRequestId + "");
        db.execSQL("INSERT INTO Notification (Created_Date, Updated_Date, RECIVER_ID, Message, Status) VALUES ('"+createdDate+"', '"+createdDate+"','"+userId+"', 'Your request is accepted',  'Pending')\n");

    }

    public void rejectRequest(int breakDownRequestId, String userId, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE BreakDownRequest SET Status = 'Reject' WHERE ID = " + breakDownRequestId + "");
        db.execSQL("INSERT INTO Notification (Created_Date, Updated_Date, RECIVER_ID, Message, Status) VALUES ('2024-04-5', NULL,'"+userId+"', 'Your have new request',  'Pending')\n");
        db.execSQL("INSERT INTO Notification (Created_Date, Updated_Date, RECIVER_ID, Message, Status) VALUES ('"+createdDate+"', '"+createdDate+"','"+userId+"', 'Your request is rejeced',  'Pending')\n");

    }

    public boolean updateStatus(String breakDownRequestId,String userId, String createdDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("UPDATE BreakDownRequest SET Status = 'Done' WHERE ID = " + breakDownRequestId + "");
            db.execSQL("INSERT INTO Notification (Created_Date, Updated_Date, RECIVER_ID, Message, Status) VALUES ('"+createdDate+"', '"+createdDate+"','"+userId+"', 'Your request is completed',  'Pending')\n");

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int getCountOfServiceRequested(String uId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM BreakDownRequest WHERE User_ID = ?";
        String[] selectionArgs = { uId }; // Pass uId as selection argument

        Cursor cursor = db.rawQuery(query, selectionArgs);
        int count = 0;
        count = cursor.getCount();
        cursor.close();
        return count;

    }

    public int getCountOfServiceProvided(String uId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM BreakDownRequest WHERE Provider_ID = ?";
        String[] selectionArgs = { uId }; // Pass uId as selection argument

        Cursor cursor = db.rawQuery(query, selectionArgs);
        int count = 0;
        count = cursor.getCount();
        cursor.close();
        return count;
    }
    public Cursor getNotificationDetails(String uid){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select ID, Message, Updated_Date from Notification WHERE RECIVER_ID = '"+uid + "' ORDER BY Updated_Date DESC", null);

        return  cursor;
    }

    public void updateNotificationStatus(int notificationId, String currentDateAndTime) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE Notification SET Status = 'Viewed', Updated_Date = '"+currentDateAndTime+"' WHERE ID = "+notificationId);
    }

    /*
    public double getRating(String uId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Rate FROM Rating WHERE UID = ?";
        String[] selectionArgs = { uId }; // Pass uId as selection argument

        Cursor cursor = db.rawQuery(query, selectionArgs);
        double rate = cursor.getColumnIndex("rate"); // Get the rate from the cursor
        cursor.close();

        return rate;
    }
     */
}
