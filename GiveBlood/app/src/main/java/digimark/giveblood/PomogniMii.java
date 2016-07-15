package digimark.giveblood;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PomogniMii extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomogni_mii);
    }

    public void RegPomogniMi(View view){
        Intent intent = new Intent(PomogniMii.this, RegHelpMeActivity.class);
        startActivity(intent);
    }
}
