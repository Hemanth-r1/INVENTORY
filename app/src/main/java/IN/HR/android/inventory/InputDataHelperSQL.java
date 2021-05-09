package IN.HR.android.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class InputDataHelperSQL extends SQLiteOpenHelper {

    // TODO: THIS CLASS IS USED TO ADD AND GET DATA FROM SQL DATA BASE

    public static final String DATABASE_NAME = "inventory.db";
    public static final String TABLE_NAME = "InventoryTable";
    public static final String COL1 = "ID";
    public static final String COL2 = "ProductName";
    public static final String COL3 = "ProductPrice";
    public static final String COL4 = "ProductModel";
    public static final String COL5 = "ProductCompany";
    public static final String COL6 = "PurchaseDate";
    public static final String COL7 = "PurchaseFrom";
    public static final String COL8 = "SerialNo";


    //default constructor
    public InputDataHelperSQL(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create a new data base using query
        String createTable = "CREATE TABLE" + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ "ProductName,"
                + "ProductPrice," +"ProductModel,"+ "ProductCompany,"
                +"PurchaseDate,"+ "PurchasedFrom)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //dont create if table exists
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
    }

    public boolean addData(String ProductName,String ProductPrice,
            String ProductModel, String ProductCompany,
                           String PurchaseDate, String PurchasedFrom, String SerialNo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2 ,ProductName);
        contentValues.put(COL3, ProductPrice);
        contentValues.put(COL4 , ProductModel);
        contentValues.put(COL5 ,ProductCompany);
        contentValues.put( COL6, PurchaseDate);
        contentValues.put(COL7, PurchasedFrom);
        contentValues.put(COL8, SerialNo);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return  false;
        }else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return  data;
    }
}
