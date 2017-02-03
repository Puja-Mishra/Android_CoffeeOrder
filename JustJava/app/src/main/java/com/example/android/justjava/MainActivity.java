package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.os.Build.VERSION_CODES.N;
import static java.lang.System.exit;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display(quantity);
    }
    int quantity = 1;
    /**
     * This method is called when the order button is clicked.
     */


    public void submitOrder(View view) {
        displayPrice(quantity * 5);
        Boolean chocolateVariable;

        CheckBox chocolateV=(CheckBox) findViewById(R.id.chocolate);
        chocolateVariable=chocolateV.isChecked();

        Boolean whippedCreamVariable;
        CheckBox whippedCream= (CheckBox) findViewById(R.id.whippedCream);
        whippedCreamVariable=whippedCream.isChecked();

        EditText txtName = (EditText) findViewById(R.id.textName);
        String name= txtName.getText().toString();

        EditText txtEmail = (EditText) findViewById(R.id.textEmail);
        String email= txtName.getText().toString();
        String emailArr[]={email};

        int price=calculatePrice(quantity,whippedCreamVariable,chocolateVariable);
        String summary=createOrderSummary(price,whippedCreamVariable,chocolateVariable,name);
        //displayMesssage(summary);
        //String add[]={"pujasmishra@hotmail.com","kevindsilva78@gmail.com"};
        String Sub="first email intent";
        composeEmail(emailArr,Sub,summary);
        //String priceMessage ="The price for " + quantity + " cups of coffee is $\n"+price;
        //displayMesssage(priceMessage);
        //displayPrice(price);

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6,-122.3"));
//        if(intent.resolveActivity(getPackageManager())!= null){
//            startActivity(intent);
//        }
    }

    public void composeEmail(String[] addresses, String subject,String summary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    public String createOrderSummary(int price,boolean wcv,boolean cv,String edittxt){
        String summary="Name="+edittxt;
        summary=summary+"\nAdd chocolate? "+cv;
        summary=summary+"\nAdd whipped cream? "+wcv;
        summary=summary+"\nQuantity= "+quantity;
        summary=summary+ "\nTotal =$"+price+"\n";
        summary=summary+"Thankyou";
        return summary;
    }

    private int calculatePrice(int quantity,Boolean wcv,boolean cv){
        int price = quantity * 5;
        if(wcv){
            int nprice=quantity*1;
            price=price+nprice;
        }
        if(cv){
            int nprice=quantity*2;
            price=price+nprice;
        }

        return price;
    }

    public void increment(View view) {

        if(quantity==100){
            String str="Max quantity is 100";
            Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
        //displayPrice(quantity * 5);
    }

    public void decrement(View view) {

        if(quantity==1){
            String str="Min quantity is 1";
            Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
        //displayPrice(quantity * 5);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);

        quantityTextView.setText("" + number);


    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMesssage(String Message){
        TextView orderSummaryTextView =(TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(Message);
    }
}
