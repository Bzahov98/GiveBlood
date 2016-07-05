package digimark.giveblood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Help extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void RegPomogni(View view){
        Intent intent = new Intent(this, RegHelpActivity.class);
        startActivity(intent);
    }
}

