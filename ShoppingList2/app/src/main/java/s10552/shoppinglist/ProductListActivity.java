package s10552.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import s10552.shoppinglist.entities.Product;

public class ProductListActivity extends OptionsHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        final ListView listView = (ListView) findViewById(R.id.product_list_view);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Shopping List");

        final Button addProductButton = (Button) findViewById(R.id.add_product);
        final Intent addProductActivityIntent = new Intent(this, AddProductActivity.class);
        final Intent editProductActivityIntent = new Intent(this, EditProductActivity.class);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addProductActivityIntent);
            }
        });

        myRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String
                    previousChildName) {
                Product product = dataSnapshot.getValue(Product.class);
                adapter.add(product.getName());
            }
            public void onChildRemoved(DataSnapshot dataSnapshot){
                Product product = dataSnapshot.getValue(Product.class);
                adapter.remove(product.getName());
            }
            public void onChildChanged(DataSnapshot dataSnapshot, String
                    previousChildName){
                Product product = dataSnapshot.getValue(Product.class);
                adapter.remove(previousChildName);
                adapter.add(product.getName());
            }
            public void onChildMoved(DataSnapshot dataSnapshot, String
                    previousChildName){
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG:", "Failed to read value.", error.toException());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, final int position, long id) {

                editProductActivityIntent.putExtra("product", (String) listView.getItemAtPosition(position));
                startActivity(editProductActivityIntent);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {

                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(getResources().getString(R.string.ALERT_DIALOG_TITLE));
                builder.setMessage(getResources().getString(R.string.ALERT_DIALOG_TEXT));
                builder.setIconAttribute(android.R.attr.alertDialogIcon);

                builder.setPositiveButton(getResources().getString(R.string.ALERT_DIALOG_YES), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {

                        Query myQuery = myRef.orderByChild("name").equalTo((String) listView.getItemAtPosition(pos));
                        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    DataSnapshot firstChild =
                                            dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.ALERT_DIALOG_NO), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ii) {
                                dialog.dismiss();
                            }
                        }
                );

                builder.show();
                return true;
            }
        });
    }
}
