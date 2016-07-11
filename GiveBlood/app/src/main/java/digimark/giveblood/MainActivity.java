package digimark.giveblood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Visibility;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity{
    ImageView image;
    SeekBar seekBar;
    TextView textView;
    private RelativeLayout campLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //campLayout = (RelativeLayout) findViewById(R.id.CampLayout);
        //campLayout.setVisibility(View.INVISIBLE);
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int seekBarProgress = 50;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBarProgress > 90){
                    Toast.makeText(getApplicationContext(), "Помогни ми", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), PomogniMii.class);
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }else if (seekBarProgress < 10){
                    Toast.makeText(getApplicationContext(), "Помогни", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), Help.class);
                    startActivity(intent);
                }
                seekBar.setProgress(50);
                seekBarProgress = 50;
            }

        });


       /* image = (ImageView) findViewById(R.id.imageView);

        image.setImageResource(R.drawable.example);*/

    }
    public void PomogniMi(View view) {
        Intent intent = new Intent(this, Help.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

