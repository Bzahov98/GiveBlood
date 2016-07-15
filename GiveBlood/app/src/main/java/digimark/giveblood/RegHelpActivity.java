package digimark.giveblood;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class RegHelpActivity extends RegBaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_pomogni);

        current_path = "/GiveBlood/register_giver.php";
        //-------------------------------------------------------------------
        date = (Button) findViewById(R.id.date_picker);
        //-------------------------------------------------------------------

        blood_header = findViewById(R.id.header2_bloodtype);
        setContent_blood(findViewById(R.id.group2child));

        town_header = findViewById(R.id.header3_town);
        town_content = findViewById(R.id.group3child);

        setHeader_contacts(findViewById(R.id.header1_contacts));
        setContent_contacts(findViewById(R.id.group1child));

        ((TextView) getHeader_contacts().findViewById(R.id.view_head)).setText("Контакти, Телефон");

        View name = findViewById(R.id.child1_names);
        ((TextView) (name != null ? name.findViewById(R.id.listview_childItem) : null)).setText("Име:");

        setTelephone(findViewById(R.id.child1_number));
        ((TextView) getTelephone().findViewById(R.id.listview_childItem)).setText("Телефон: ");
        ((EditText) telephone.findViewById(R.id.edit_text)).setInputType(InputType.TYPE_CLASS_PHONE);

        setEmail(findViewById(R.id.child1_email));
        ((EditText) email.findViewById(R.id.edit_text)).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ((TextView) getEmail().findViewById(R.id.listview_childItem)).setText("Email: ");

        ((TextView) blood_header.findViewById(R.id.view_head)).setText("Кръвна група:");
        ((ImageView)  blood_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.kravnagrupaicn);
        ((TextView) blood_header.findViewById(R.id.head_extra_text_view)).setText(blood_type);

        ((TextView) town_header.findViewById(R.id.view_head)).setText("Избери Град:");
        ((ImageView)  town_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
        town_listView = (ListView) findViewById(R.id.list_town);

        //---------------------------Set Show/Hide events------------------------------------

        setClicks(getHeader_contacts(), getContent_contacts());
        setClicks(blood_header, getContent_blood());
        setClicks(town_header, town_content);

        //---------------------------ListView Towns-----------------------------------

        ShowListView(true); // true for town, false for hospital

        //-----------------------Get EditText-------------
        //-----------------------------------end--------------------------------------------


    }

    @Override
    public void uiAction(final android.support.v7.app.AlertDialog result_output, final String response, final CharSequence output, final Dialog progress) {
        RegHelpActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                result_output.setTitle(response);
                result_output.setMessage(output);
                progress.dismiss();
                result_output.show();

            }
        });
    }

    @Override
    protected void SetValueCV() {
        //do nothing
    }

    @Override
    protected void setOutput() {
        //do nothing
    }
}