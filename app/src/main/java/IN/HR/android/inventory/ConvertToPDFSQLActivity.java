package IN.HR.android.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertToPDFSQLActivity extends AppCompatActivity {

    Button saveAndPrint, printBtn;
    EditText editTextName, editTextPhone, editTextQty;
    Spinner spinner;
    //string array
    String[]itemList;
    int[] itemPrice;
    ArrayAdapter<String> arrayAdapter;
    ConvertToPDFHelperSQL helperSQL;
    SQLiteDatabase sqLiteDatabase;
    Date date = new Date();

    String datePattern = "dd-MM-YYYY";
    SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    String timePattern = "hh:mm a";
    SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_pdfsql);
        callFindVewById();
        helperSQL = new ConvertToPDFHelperSQL(this);
        sqLiteDatabase = helperSQL.getWritableDatabase();

        callOnClickListener();
    }

    private void callFindVewById() {
        saveAndPrint = findViewById(R.id.btnSaveAndPrint);
        printBtn = findViewById(R.id.btnPrint);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextQty = findViewById(R.id.editTextQty);
        spinner = findViewById(R.id.spinner);
        itemList = new String[]{"hello", "World", "from", "String", "Array"};
        itemPrice = new int[]{100,200,300,400,500};
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        spinner.setAdapter(arrayAdapter);

    }

    private void callOnClickListener() {

        saveAndPrint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String customerName = String.valueOf(editTextName.getText());
                String contactNo = String.valueOf(editTextPhone.getText());
                String item = spinner.getSelectedItem().toString();
                //int qty = Integer.parseInt(String.valueOf(editTextQty.getText()));
                int qty = Integer.parseInt(editTextQty.getText().toString());
                int amount = qty * itemPrice[ spinner.getSelectedItemPosition()];
                helperSQL.insert(customerName, contactNo, date.getTime(), item, qty, amount);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    printInvoice();
                }
                Toast.makeText(ConvertToPDFSQLActivity.this,"Successfully Converted To PDF", Toast.LENGTH_LONG ).show();
            }
        });

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConvertToPDFSQLActivity.this, PrintOldPDFSQL.class);
                startActivity(intent);
            }
        });
    }

    //Step 4

    private void printInvoice() {

        PdfDocument pdfDocument = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            pdfDocument = new PdfDocument();

            Paint paint = new Paint();

            String[] column = {"invoiceNo", "customerName", "contactNo", "date", "item", "qty", "amount"};

            Cursor cursor = sqLiteDatabase.query("PdfTABLE", column, null, null, null, null, null);
            cursor.move(cursor.getCount());

            /*
             for A4 size sheet width = 20.98 and height = 29.66, builder below used POST SCRIPT as unit
             which in turn is defined as 1/72 inch
            so divide the STANDARD WIDTH and HEIGHT in CM by 2.54 [result inch]
            multiply the value by 72 to get POST SCRIPT value
            Width = 20.98 cm for A4
            Height  = 29.66 cm for A4
            POSTSCRIPT width =  20.98 / 2.54 = 8.26 => 8.26 * 72 = 594.70 => 595 int
            POSTSCRIPT height = 29.66 / 2.54 = 11.67 => 11.67 * 72 = 840.75 => 841 int
             */
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 841, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            //first text
            paint.setTextSize(20);
            canvas.drawText("Custom Builds", 250, 30, paint);

            //second text
            paint.setTextSize(10);
            canvas.drawText("#21, Rajeev Gandhi Nagar, Nandini Layout, Bengaluru 96", 200, 45, paint);

            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Invoice Number", canvas.getWidth() - 50, 30, paint);

            canvas.drawText(String.valueOf(cursor.getInt(0)), canvas.getWidth() - 30, 30, paint);
            paint.setTextAlign(Paint.Align.LEFT);

            paint.setColor(Color.rgb(150, 150, 150));
            canvas.drawRect(30, 50, canvas.getWidth() - 30, 55, paint);

            paint.setColor(Color.BLACK);
            canvas.drawText("Date :", 30, 70, paint);
            canvas.drawText(dateFormat.format(cursor.getLong(3)), 70, 70, paint);

            canvas.drawText("Time :", 150, 70, paint);
            //canvas.drawText(timeFormat.format(cursor.getLong(3)), canvas.getWidth() - 40, 200, paint);
            canvas.drawText(timeFormat.format(cursor.getLong(3)), 180, 70, paint);
            paint.setTextAlign(Paint.Align.LEFT);

            // billing from
            paint.setColor(Color.rgb(150, 150, 150));
            canvas.drawRect(30, 80, 80, 95, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText("FROM", 32, 90, paint);

            paint.setColor(Color.BLACK);
            canvas.drawText("KIRAN R ", 30, 105, paint);
            canvas.drawText("CUSTOM BUILDS TECHNOLOGY", 30, 120, paint);
            canvas.drawText("NANDINI LAYOUT, BENGALURU 96", 30, 135, paint);
            canvas.drawText("MOBILE NUMBER: 9739942971", 30, 150, paint);
            paint.setTextAlign(Paint.Align.RIGHT);

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("GST IN: 29JVVPK7688R1ZL", canvas.getWidth() - 30, 120, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // billing to
            paint.setTextAlign(Paint.Align.LEFT);

            paint.setColor(Color.rgb(150, 150, 150));
            canvas.drawRect(30, 170, 80, 182, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText("BILL TO", 32, 180, paint);

            paint.setColor(Color.BLACK);
            canvas.drawText("Customer Name:", 30, 195, paint);
            canvas.drawText(cursor.getString(1), 150, 195, paint);

            canvas.drawText("Mobile Number:", 30, 210, paint);
            canvas.drawText(cursor.getString(2), 150, 210, paint);

            canvas.drawText("Address:", 30, 225, paint);
            canvas.drawText(cursor.getString(2), 150, 225, paint);
            paint.setTextAlign(Paint.Align.LEFT);

            // draw vertical line
            paint.setColor(Color.rgb(150, 150, 150));
            canvas.drawRect(75, 265, 78, 280, paint);
            canvas.drawRect(150, 265, 153, 280, paint);
            canvas.drawRect(275, 265, 278, 280, paint);

            // create rectangular bar
            paint.setColor(Color.rgb(150, 150, 150));
            canvas.drawRect(30, 250, canvas.getWidth() - 40, 270, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText("Item", 35, 265, paint);
            canvas.drawText("Qty", 100, 265, paint);
            canvas.drawText("Amount", 190, 265, paint);

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            paint.setColor(Color.BLACK);
            canvas.drawText(cursor.getString(4), 35, 280, paint);
            canvas.drawText(String.valueOf(cursor.getInt(5)), 100, 280, paint);
            canvas.drawText(String.valueOf(cursor.getInt(6)), 190, 280, paint);

            paint.setColor(Color.rgb(150, 150, 150));
            canvas.drawRect(30, 290, canvas.getWidth() - 40, 295, paint);

            paint.setColor(Color.BLACK);
            canvas.drawText("Sub Total", 100, 310, paint);
            canvas.drawText("Tax 4%", 100, 320, paint);

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("TOTAL", 100, 335, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));



            /*
            //







            canvas.drawText(String.valueOf(cursor.getInt(6)), 1050, 1320, paint);
            canvas.drawText(String.valueOf(cursor.getInt(6) * 4 / 100), 1050, 1390, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(String.valueOf(cursor.getInt(6) + (cursor.getInt(6) * 4 / 100)), 1050, 1460, paint);

            canvas.drawText("Make all check payable to \"Custom Builds\"", 50, 1600, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

             */
            canvas.drawText("Thank you very much", 50, 1680, paint);
            pdfDocument.finishPage(page);

            File file = new File(this.getExternalFilesDir("/"), cursor.getInt(0) + "CustomBuilds.pdf");

            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();
        }
    }
}