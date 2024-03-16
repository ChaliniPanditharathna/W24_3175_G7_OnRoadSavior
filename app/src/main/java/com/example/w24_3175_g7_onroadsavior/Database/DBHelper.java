package com.example.w24_3175_g7_onroadsavior.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "onRoadSaviorDB";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User ( ID INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR(255) NOT NULL,Username VARCHAR(100) UNIQUE NOT NULL,Email VARCHAR(255) UNIQUE NOT NULL, Phone_NO VARCHAR(20), Created_Date LOCALDATE NOT NULL,Password VARCHAR(255) NOT NULL)");

        db.execSQL("CREATE TABLE ServiceProvider (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255) NOT NULL,Username VARCHAR(100) UNIQUE NOT NULL,Email VARCHAR(255) UNIQUE NOT NULL,Phone_No VARCHAR(20),Created_Date LOCALDATE NOT NULL,BreakDownType VARCHAR(255), Password VARCHAR(255) NOT NULL\n)");

        db.execSQL("CREATE TABLE BreakDownRequest ( ID INTEGER PRIMARY KEY AUTOINCREMENT,Created_Date LOCALDATE NOT NULL,Updated_Date LOCALDATE, User_ID INTEGER NOT NULL, Provider_ID INTEGER NOT NULL, Breakdown_Type TEXT,Location TEXT,Description TEXT, Image BLOB,FOREIGN KEY (User_ID) REFERENCES User(ID),FOREIGN KEY (Provider_ID) REFERENCES ServiceProvider(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS ServiceProvider");
        db.execSQL("DROP TABLE IF EXISTS BreakDownRequest");
        onCreate(db);
    }

    public void addRequest() {
        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("INSERT INTO User (Name, Username, Email, Created_Date, Phone_NO, Password) VALUES ('John User', 'user1', 'john@example.com', '2024-03-12', '1234567890', 'password123')\n");

        db.execSQL("INSERT INTO ServiceProvider (Name, Username, Email, Phone_NO, Created_Date, BreakDownType, Password) VALUES ('Service Provider 1', 'provider1', 'provider1@example.com', '0987654321', '2024-03-12', 'Plumbing', 'providerpass')\n");

        db.execSQL("INSERT INTO BreakDownRequest (Created_Date, Updated_Date, User_ID, Provider_ID, Breakdown_Type, Location, Description, Image) VALUES ('2024-03-12', NULL, 1, 1, 'Plumbing', '14820 90B Avenue, Surrey, BC', 'Issue Description', NULL)\n");

        db.execSQL("INSERT INTO BreakDownRequest (Created_Date, Updated_Date, User_ID, Provider_ID, Breakdown_Type, Location, Description, Image) VALUES ('2024-03-12', NULL, 1, 1, 'Plumbing', '543 90B Avenue, Burnaby, BC', 'Issue Description', NULL)\n");

        db.execSQL("INSERT INTO BreakDownRequest (Created_Date, Updated_Date, User_ID, Provider_ID, Breakdown_Type, Location, Description, Image) VALUES ('2024-03-12', NULL, 1, 1, 'Plumbing', '56 78B, New Westminister, BC', 'Issue Description', NULL)\n");

        db.execSQL("INSERT INTO BreakDownRequest (Created_Date, Updated_Date, User_ID, Provider_ID, Breakdown_Type, Location, Description, Image) VALUES ('2024-03-12', NULL, 1, 1, 'Plumbing', '167, Freser Hyway, BC', 'Issue Description', NULL)\n");

    }

    public Cursor getRequestData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select b.Breakdown_Type, b.Location, b.Description, b.Created_Date, b.Updated_Date, b.Image, b.User_ID, b.Provider_ID," +
                "u.Name,u.Phone_No  from BreakDownRequest as b INNER JOIN User as u ON b.user_id = u.id", null);
        return  cursor;
    }
}
