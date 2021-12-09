 package com.utec.android.helloworld;

 import android.content.Intent;
 import android.net.Uri;
 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
 import android.widget.CheckBox;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

     private int quantity = 2;
     private int price = 5;
     private int priceMeat = 2;
     private int pricePineapple = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, "You can not buy more than 10.", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You can buy at least 1.", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView txtQuantityId = findViewById(R.id.txt_quantity_id);
        txtQuantityId.setText("" + number);
    }

    public void submitOrder(View view) {
        EditText nameEditText = findViewById(R.id.name_edit_text_view_id);
        String name = nameEditText.getText().toString();
        CheckBox checkBoxMeatId = findViewById(R.id.checkbox_meat_id);
        boolean hasMeat = checkBoxMeatId.isChecked();
        CheckBox checkBoxPineappleId = findViewById(R.id.checkbox_pineapple_id);
        boolean hasPineapple = checkBoxPineappleId.isChecked();
        Log.i(this.getClass().getName(), name);
        Log.i(this.getClass().getName(), hasMeat? "true" : "false");
        Log.i(this.getClass().getName(), hasPineapple? "true" : "false");
        String priceMessage =  createOrderSummary(name, hasMeat, hasPineapple);
        Log.i(this.getClass().getName(), priceMessage);
        TextView priceMessageTextView = findViewById(R.id.summary_order_txt);
        priceMessageTextView.setText(priceMessage);

        //Create the text message with a String
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("mailto:"));
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "orden de compra");
        sendIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }

    }

    private String createOrderSummary(String name,  boolean hasMeat, boolean hasPineapple) {
        String txtYes = getString(R.string.yes);
        String txtNo = getString(R.string.no);
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_has_meat, (hasMeat ? txtYes : txtNo));
        priceMessage += "\n" + getString(R.string.order_summary_has_pineapple, (hasPineapple ? txtYes : txtNo));

        priceMessage += "\n" + getString(R.string.total_order, price*quantity +
                (hasMeat ? priceMeat : 0) + (hasPineapple ? pricePineapple : 0));

        priceMessage += "\n" + getString(R.string.order_summary_thanks);
        return priceMessage;
    }


}