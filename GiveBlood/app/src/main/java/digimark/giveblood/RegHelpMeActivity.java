package digimark.giveblood;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class RegHelpMeActivity extends RegBaseActivity {


	private EditText name_input;
	private EditText phone_number_input;
	private EditText mail_input;
	private EditText descr_input;
	private EditText dep_input;
	private EditText quantity_input;

	private String city = "";
	private String blood_type = "A-";

	private View blood_header;
	private View town_header;
	private View department_header;
	private View description_header;
	private TextView department_context;
	private TextView description_context;
	private TextView description_amount_context;
	private View town_content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_pomognimi_acticity);

		current_path = "/GiveBlood/register_consumer.php";

		//------------------------Date Options--------------------------------------
			date = (Button) findViewById(R.id.date_picker);
		//--------------------------------------------------------------------------

		blood_header = findViewById(R.id.header2_bloodtype);
			setContent_blood(findViewById(R.id.group2child));

		town_header = findViewById(R.id.header3_town);
		town_content = findViewById(R.id.group3child);

		hospital_header =  findViewById(R.id.header4_hospital);
		hospital_content = findViewById(R.id.group4child);



		setHeader_contacts(findViewById(R.id.header1_contacts));
		setContent_contacts(findViewById(R.id.group1child));



		((TextView) getHeader_contacts().findViewById(R.id.view_head)).setText("Контакти, Телефон");

		View name = findViewById(R.id.child1_names);
			((TextView) (name != null ? name.findViewById(R.id.listview_childItem) : null)).setText("Име:");

		setTelephone(findViewById(R.id.child1_number));
			((TextView) getTelephone().findViewById(R.id.listview_childItem)).setText("Телефон: ");

		((EditText) telephone.findViewById(R.id.edit_text)).setInputType(InputType.TYPE_CLASS_PHONE);
		setEmail(findViewById(R.id.child1_email));
			((TextView) getEmail().findViewById(R.id.listview_childItem)).setText("Email: ");
		((EditText) email.findViewById(R.id.edit_text)).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

		((TextView) blood_header.findViewById(R.id.view_head)).setText("Кръвна група:");
		((ImageView)  blood_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.kravnagrupaicn);
		((TextView) blood_header.findViewById(R.id.head_extra_text_view)).setText(blood_type);

		((TextView) town_header.findViewById(R.id.view_head)).setText("Избери Град:");
		((ImageView)  town_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
			town_listView = (ListView) findViewById(R.id.list_town);


		((TextView) hospital_header.findViewById(R.id.view_head)).setText("Избери Болница:");
		((ImageView) hospital_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
			hospitals_listView = (ListView) findViewById(R.id.list_hospital);

		department_header = findViewById(R.id.header5_department);
		((TextView) department_header.findViewById(R.id.view_head)).setText("Отделение:");
		((ImageView) department_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
		department_context = ((TextView) findViewById(R.id.child5_depart).findViewById(R.id.listview_childItem));
			department_context.setText("Отделение:");

		description_header = findViewById(R.id.header6_description);
		((ImageView) (description_header != null ? description_header.findViewById(R.id.head_icon) : null)).setImageResource(R.drawable.mestopolojenieicn);
		((TextView) description_header.findViewById(R.id.view_head)).setText("Описание:");
			description_context = ((TextView) findViewById(R.id.child6_descr).findViewById(R.id.listview_childItem));
			description_context.setText("Описание: ");
			description_amount_context = ((TextView) findViewById(R.id.child6_amount).findViewById(R.id.listview_childItem));
			((EditText) findViewById(R.id.child6_amount).findViewById(R.id.edit_text)).setInputType(InputType.TYPE_CLASS_PHONE);
			description_amount_context.setText("Необходимо количество: ");

		//---------------------------Set Show/Hide events------------------------------------

		setClicks(getHeader_contacts(), getContent_contacts());
		setClicks(blood_header, getContent_blood());
		setClicks(town_header, town_content);
		setClicks(hospital_header, hospital_content);
		setClicks(department_header, findViewById(R.id.group5child));
		setClicks(description_header, findViewById(R.id.group6child));

		//---------------------------ListView Hospitals-----------------------------------

			ShowListView(true); // true for town, false for hospital

		//---------------------------ListView Hospitals-----------------------------------

			ShowListView(false); // true for town, false for hospital

		//--------------------------Get EditText-------------------------------------------
		name_input = (EditText) findViewById(R.id.child1_names).findViewById(R.id.edit_text);
		phone_number_input = (EditText) findViewById(R.id.child1_number).findViewById(R.id.edit_text);
		mail_input = (EditText) findViewById(R.id.child1_email).findViewById(R.id.edit_text);
		dep_input = (EditText) findViewById(R.id.child5_depart).findViewById(R.id.edit_text);
		descr_input = (EditText) findViewById(R.id.child6_descr).findViewById(R.id.edit_text);
		quantity_input = (EditText) findViewById(R.id.child6_amount).findViewById(R.id.edit_text);
		//-----------------------------------end--------------------------------------------


	}

	@Override
	public void uiAction(final android.support.v7.app.AlertDialog result_output, final String response, final CharSequence output, final Dialog progress) {

		RegHelpMeActivity.this.runOnUiThread(new Runnable() {

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
		cv.put(TAG_HOSPITAL, hospital);
		cv.put(TAG_DESCRIPTION, descr_input.getText().toString());
		cv.put(TAG_DEPARTMENT, dep_input.getText().toString());
		cv.put(TAG_QUANTITY, quantity_input.getText().toString());
	}

	@Override
	protected void setOutput() {

		try {
			output.append(System.getProperty("line.separator") + data.getString(TAG_PHONE_NUMBER));
			output.append(System.getProperty("line.separator") + data.getString(TAG_EMAIL));
			output.append(System.getProperty("line.separator") + data.getString(TAG_BLOOD_TYPE));
			output.append(System.getProperty("line.separator") + data.getString(TAG_CITY));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}