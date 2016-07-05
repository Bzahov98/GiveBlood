package digimark.giveblood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    protected RelativeLayout campLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void showCampaign(View view){
        //Toast.makeText(getApplicationContext(), "show", Toast.LENGTH_SHORT).show();
        campLayout = (RelativeLayout) findViewById(R.id.CampLayout);
        if (!campLayout.isShown()){
            campLayout.setVisibility(View.VISIBLE);
        }
    }

    public void hideCampaign(View view){
        //Toast.makeText(getApplicationContext(), "hide", Toast.LENGTH_SHORT).show();
        if (campLayout.isShown()){
            campLayout.setVisibility(View.INVISIBLE);
        }
    }
}
