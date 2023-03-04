package s10552.shoppinglist;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import s10552.shoppinglist.entities.Product;


public class AddProductActivity extends OptionsHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOptions();
        setContentView(R.layout.activity_add_product);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Shopping List");

        final EditText addProductName = (EditText) findViewById(R.id.name);
        final EditText addProductQuantity = (EditText) findViewById(R.id.quantity);
        final EditText addProductPrice = (EditText) findViewById(R.id.price);

        final Button saveButton = (Button) findViewById(R.id.save);
        final Button cancelButton = (Button) findViewById(R.id.cancel);


        final Intent productListActivityIntent = new Intent(this, ProductListActivity.class);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(productListActivityIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = addProductName.getText().toString();
                String quantityString = addProductQuantity.getText().toString();
                String priceString = addProductPrice.getText().toString();

                if(name.isEmpty() || quantityString.isEmpty() || priceString.isEmpty()){
                    Toast.makeText(AddProductActivity.this, getResources().getString(R.string.FIELDS_REQUIRED_ERROR), Toast.LENGTH_SHORT).show();
                    return;
                }


                int quantity = Integer.parseInt(quantityString);
                double price = Double.parseDouble(priceString);

                Product product = new Product();
                product.setName(name);
                product.setQuantity(quantity);
                product.setPrice(price);
                product.setBought(0);

                DatabaseReference childRef = myRef.push();
                childRef.setValue(product);

                startActivity(productListActivityIntent);
                Toast.makeText(AddProductActivity.this, getResources().getString(R.string.SAVED), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
