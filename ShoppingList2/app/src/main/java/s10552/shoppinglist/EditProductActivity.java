package s10552.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import s10552.shoppinglist.entities.Product;

public class EditProductActivity extends OptionsHandler {

    private String name;
    private Product product;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOptions();
        setContentView(R.layout.activity_edit_product);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Shopping List");

        final EditText editProductName = (EditText) findViewById(R.id.edit_activity_name);
        final EditText editProductQuantity = (EditText) findViewById(R.id.edit_activity_quantity);
        final EditText editProductPrice = (EditText) findViewById(R.id.edit_activity_price);

        final Button saveButton = (Button) findViewById(R.id.edit_activity_save);
        final Button cancelButton = (Button) findViewById(R.id.edit_activity_cancel);
        final Button deleteButton = (Button) findViewById(R.id.edit_activity_delete);

        name = getIntent().getStringExtra("product");
        Log.d("a", "-------------------------------------------------------------------------------------"+name);

        final Query myQuery = myRef.orderByChild("name").equalTo(name);
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    key = messageSnapshot.getKey();
                    product = messageSnapshot.getValue(Product.class);
                    editProductName.setText(product.getName());
                    editProductQuantity.setText(""+product.getQuantity());
                    editProductPrice.setText(""+product.getPrice());
                }
                Log.d("a", "-------------------------------------------------------------------------------------downloading value: "+key);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("a", "-------------------------------------------------------------------------------------download error: "+databaseError);
            }
        });

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

                final String name = editProductName.getText().toString();
                String quantityString = editProductQuantity.getText().toString();
                String priceString = editProductPrice.getText().toString();

                if (name.isEmpty() || quantityString.isEmpty() || priceString.isEmpty()) {
                    Toast.makeText(EditProductActivity.this, getResources().getString(R.string.FIELDS_REQUIRED_ERROR), Toast.LENGTH_SHORT).show();
                    return;
                }


                final int quantity = Integer.parseInt(quantityString);
                final double price = Double.parseDouble(priceString);


                product.setName(name);
                product.setQuantity(quantity);
                product.setPrice(price);

                Log.d("", "click: --------------------------------------------------------------------------------------------------");

                final Task<Void> myQuery = myRef.child(key).setValue(product);

                startActivity(productListActivityIntent);
                Toast.makeText(EditProductActivity.this, getResources().getString(R.string.SAVED), Toast.LENGTH_SHORT).show();

            }

        });


    }
    private void UpdateItem (String id, String name, int quantity, double price){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shopping List");

    }
}
