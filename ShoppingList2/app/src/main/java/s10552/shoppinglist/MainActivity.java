package s10552.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends OptionsHandler {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOptions();
        setContentView(R.layout.activity_main);

        final Button optionsActivityButton = (Button) findViewById(R.id.options);
        final Button productListActivityButton = (Button) findViewById(R.id.list);

        final Intent productListActivityIntent = new Intent(this, ProductListActivity.class);
        final Intent optionsActivityIntent = new Intent(this, OptionsActivity.class);


        productListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(productListActivityIntent);
            }
        });

        optionsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(optionsActivityIntent);
            }
        });
    }
}
