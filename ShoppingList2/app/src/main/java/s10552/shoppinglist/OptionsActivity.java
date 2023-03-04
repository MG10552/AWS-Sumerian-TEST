package s10552.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class OptionsActivity extends OptionsHandler {

    private SharedPreferences sharedpreferences;
    private RelativeLayout rl;
    private String[] fontSizes;
    private Boolean nightMode;

    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOptions();
        setContentView(R.layout.activity_options);

        final Button nm = (Button) findViewById(R.id.night_mode);
        final Button fs = (Button) findViewById(R.id.font_size);

        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        fontSizes = getResources().getStringArray(R.array.fontSizes);

        nightMode = sharedpreferences.getBoolean("NIGHT_MODE", false);

        if(nightMode){
            nm.setText(getResources().getString(R.string.light));
        } else {
            nm.setText(getResources().getString(R.string.dark));
        }

        nm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences.edit().putBoolean("NIGHT_MODE", !nightMode).apply();
                recreate();
          }
        });

        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
                builder.setTitle(getResources().getString(R.string.fsize));
                builder.setItems(fontSizes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        sharedpreferences.edit().putString("FONT_SIZE",fontSizes[position]).apply();
                        recreate();
                    }
                });
                builder.create();
                builder.show();

            }
        });
    }

}
