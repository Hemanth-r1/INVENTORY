package IN.HR.android.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Input_Data_Activity extends AppCompatActivity {
    Spinner spinner;
    List<String> categories = new ArrayList<String>();
    EditText ProductName, ProductPrice, ProductCompany, ProductModel, PurchaseDate, PurchasedFrom, SerialNumber;
    InputDataHelperSQL inputDataHelperSQL;
    TextView textViewProductName, textViewProductPrice , textViewProductCompany,
            textViewProductModel , textViewPurchaseDate, textViewPurchaseFrom , textViewSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        spinner = findViewById(R.id.spinner);

        ProductName = findViewById(R.id.editText_product_name);
        ProductPrice = findViewById(R.id.editText_product_price);
        ProductCompany = findViewById(R.id.editText_product_company);
        ProductModel = findViewById(R.id.editText_product_model);
        PurchaseDate = findViewById(R.id.editText_product_purchased_date);
        PurchasedFrom = findViewById(R.id.editText_product_purchased_from);
        SerialNumber = findViewById(R.id.editText_product_serial_no);

        // create a list of items to show in spinner
        categories.add("Processor");
        categories.add("Mother Board");
        categories.add("Graphics Card");
        categories.add("Ram");
        categories.add("SSD");
        categories.add("HDD");
        categories.add("CPU Fan");
        categories.add("PSU");
        categories.add("Cabinet");
        categories.add("Case Fans");
        categories.add("Keyboard");
        categories.add("Mouse");
        categories.add("Monitor");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    public void ViewData(View view) {

        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductPrice = findViewById(R.id.textViewProductPrice);
        textViewProductCompany = findViewById(R.id.textViewProductCompany);
        textViewProductModel = findViewById(R.id.textViewProductModel);
        textViewPurchaseDate = findViewById(R.id.textViewPurchaseDate);
        textViewPurchaseFrom = findViewById(R.id.textViewPurchaseFrom);
        textViewSerial = findViewById(R.id.textViewSerialNumber);

        ListView listView = findViewById(R.id.listView);
        inputDataHelperSQL = new InputDataHelperSQL(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor name = inputDataHelperSQL.getListContents();
        Cursor price = inputDataHelperSQL.getListContents();
        Cursor model = inputDataHelperSQL.getListContents();
        Cursor company = inputDataHelperSQL.getListContents();
        Cursor date = inputDataHelperSQL.getListContents();
        Cursor from = inputDataHelperSQL.getListContents();
        Cursor serial = inputDataHelperSQL.getListContents();

        if (name.getCount() == 0) {
            Toast.makeText(Input_Data_Activity.this, "the database is empty", Toast.LENGTH_LONG).show();
        } else {
            while (name.moveToNext()) {

                /*
           theList.add(1, data.getString(1));
           theList.add(2, data.getString(2));
           theList.add(3, data.getString(3));
                //TODO only showing a single text view here and layout is android.R.layout.simple_list_item_1, need to change it
                 */

                theList.add(name.getString(1));
                theList.add(price.getString(2));
                theList.add(model.getString(3));
                theList.add(company.getString(1));
                theList.add(from.getString(2));
                theList.add(date.getString(3));
                theList.add(serial.getString(1));

                //ViewListAdapterSQL listAdapter = new ViewListAdapterSQL(this, R.layout.list_item_view_sql, theList);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                //ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }
    }

    public void AddSQL(View view) {
        String name = ProductName.getText().toString();
        String price = ProductPrice.getText().toString();
        String company = ProductCompany.getText().toString();
        String model = ProductModel.getText().toString();
        String date = PurchaseDate.getText().toString();
        String from = PurchasedFrom.getText().toString();
        String serial = SerialNumber.getText().toString();
        inputDataHelperSQL.addData(name,price,company,model,date,from,serial);

    }

    public void ClearText(View view) {
    }
}