package IN.HR.android.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InputDataAdapterSQL extends ArrayAdapter<InputDataModal
        /* extend this so the getter and setter work*/  > {

    private  Context mContext;
    int mResources;

    // create a constructor for this class
    public InputDataAdapterSQL(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResources = resource;
    }
    //get the view in this adapter to access data


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // using getter and setter here to get the value from the Person class
        String name = getItem(position).getProductName();
        String price = getItem(position).getProductPrice();
        String model = getItem(position).getProductModel();
        String company= getItem(position).getProductCompany();
        String date = getItem(position).getPurchaseDate();
        String from = getItem(position).getPurchasedFrom();
        String serial = getItem(position).getSerialNumber();

        // create an object of the modal class
        InputDataModal inputDataModal = new InputDataModal(name, price, model, company,date,from, serial);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(mResources, parent, false);

        TextView textViewProductName = convertView.findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = convertView.findViewById(R.id.textViewProductPrice);
        TextView textViewProductCompany = convertView.findViewById(R.id.textViewProductCompany);
        TextView textViewProductModel = convertView.findViewById(R.id.textViewProductModel);
        TextView textViewPurchaseDate = convertView.findViewById(R.id.textViewPurchaseDate);
        TextView textViewPurchaseFrom = convertView.findViewById(R.id.textViewPurchaseFrom);
        TextView textViewSerial = convertView.findViewById(R.id.textViewSerialNumber);

        textViewProductName.setText(name);
        textViewProductPrice.setText(price);
        textViewProductCompany.setText(company);
        textViewProductModel.setText(model);
        textViewPurchaseDate.setText(date);
        textViewPurchaseFrom.setText(from);
        textViewSerial.setText(serial);

        return  convertView;
    }
}
