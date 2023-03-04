package s10552.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class OptionsHandler extends AppCompatActivity {

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void setOptions() {

        try {
            SharedPreferences settings =
                    getSharedPreferences("mypref", Context.MODE_PRIVATE);

            String fontSizePref = settings.getString("FONT_SIZE", "Medium");
            Boolean nightMode = settings.getBoolean("NIGHT_MODE", false);

            int themeID = R.style.FontSizeMedium;
            if (fontSizePref.equals("Small")) {
                themeID = R.style.FontSizeSmall;
            } else if (fontSizePref.equals("Large")) {
                themeID = R.style.FontSizeLarge;
            }
            setTheme(themeID);


            if(nightMode){
                themeID = R.style.NightTheme;
            } else {
                themeID = R.style.AppTheme;
            }
            setTheme(themeID);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
