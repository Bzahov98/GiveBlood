package digimark.giveblood;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class RegHelpMeActivity extends AppCompatActivity {

	public static int Y;
	public static int M;
	public static int D;

	private ContentValues cv = new ContentValues();

	private EditText name_input;
	private EditText phone_number_input;
	private EditText mail_input;
	private EditText descr_input;
	private EditText dep_input;

	private String city = "";
	private String hospital = "";
	private String blood_type = "A-";
	public static String dateValue = "";

	private static final String TAG_DATA = "data";
	private static final String TAG_NAME = "name";
	private static final String TAG_DATE_OF_BIRTH = "date_of_birth";
	private static final String TAG_PHONE_NUMBER = "phone_number";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_BLOOD_TYPE = "blood_type";
	private static final String TAG_CITY = "city";
	private static final String TAG_HOSPITAL = "hospital";
	private static final String TAG_DEPARTMENT = "department";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_RESPONSE = "response";


	public static Button date;
	private RadioGroup group;
	private RadioButton radioButton;

	View blood_header;
	private View content_blood;

	private View content_contacts;
	private View header_contacts;

	private View telephone;
	private View email;
	private View date_content;
	private View town_header;
	private View hospital_header;
	private ListView hospitals_listView;
	private View hospital_content;
	private View department_header;
	private View description_header;
	private TextView department_context;
	private TextView description_context;
	private TextView description_amount_context;
	private View town_content;
	private ListView town_listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_pomognimi_acticity);

		//-------------------------------------------------------------------
		date = (Button) findViewById(R.id.date_picker);

		final Calendar c = Calendar.getInstance();
		Y = c.get(Calendar.YEAR) - 18;
		M = c.get(Calendar.MONTH);
		D = c.get(Calendar.DAY_OF_MONTH);

		//-------------------------------------------------------------------

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
			((TextView) name.findViewById(R.id.listview_childItem)).setText("Име:");

		setTelephone(findViewById(R.id.child1_number));
			((TextView) getTelephone().findViewById(R.id.listview_childItem)).setText("Телефон: ");

		setEmail(findViewById(R.id.child1_email));
			((TextView) getEmail().findViewById(R.id.listview_childItem)).setText("Email: ");

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
			department_context = ((TextView) findViewById(R.id.child5_depart).findViewById(R.id.listview_childItem));
			department_context.setText("Отделение:");

		description_header = findViewById(R.id.header6_description);
		((ImageView) description_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.kolichestvoicn);
		((TextView) description_header.findViewById(R.id.view_head)).setText("Описание:");
			description_context = ((TextView) findViewById(R.id.child6_descr).findViewById(R.id.listview_childItem));
			description_context.setText("Описание: ");
			description_amount_context = ((TextView) findViewById(R.id.child6_amount).findViewById(R.id.listview_childItem));
			description_amount_context.setText("Необходимо количество: ");

		//---------------------------Set Show/Hide events------------------------------------

		setClicks(getHeader_contacts(), getContent_contacts());
		setClicks(blood_header, getContent_blood());
		setClicks(town_header, town_content);
		setClicks(hospital_header, hospital_content);
		setClicks(department_header, findViewById(R.id.group5child));
		setClicks(description_header, findViewById(R.id.group6child));

		//---------------------------ListView Hospitals-----------------------------------



		String[] values1 = getResources().getStringArray(R.array.towns);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values1);

		town_listView.setAdapter(adapter1);
		UIUtils.setListViewHeightBasedOnItems(town_listView);
		town_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String itemValue = (String) town_listView.getItemAtPosition(position);

				((TextView) town_header.findViewById(R.id.view_head)).setText(itemValue);
				town_content.setVisibility(View.GONE);

				city = itemValue;
			}
		});

		town_listView.setScrollContainer(false);


		//---------------------------ListView Hospitals-----------------------------------

		;

		String[] values = getResources().getStringArray(R.array.hospitals);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		hospitals_listView.setAdapter(adapter);
		UIUtils.setListViewHeightBasedOnItems(hospitals_listView);
		hospitals_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String itemValue = (String) hospitals_listView.getItemAtPosition(position);

				((TextView) hospital_header.findViewById(R.id.view_head)).setText(itemValue);
				hospitals_listView.setVisibility(View.GONE);

				hospital = itemValue;
			}
		});

		hospitals_listView.setScrollContainer(false);

		//-----------------------Get EditText-------------
		name_input = (EditText) findViewById(R.id.child1_names).findViewById(R.id.edit_text);
		phone_number_input = (EditText) findViewById(R.id.child1_number).findViewById(R.id.edit_text);
		mail_input = (EditText) findViewById(R.id.child1_email).findViewById(R.id.edit_text);
		dep_input = (EditText) findViewById(R.id.child5_depart).findViewById(R.id.edit_text);
		descr_input = (EditText) findViewById(R.id.child6_descr).findViewById(R.id.edit_text);
		//-----------------------------------end--------------------------------------------


	}

	private void setClicks(View header, final View content)
	{
		header.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(content.getVisibility() == View.VISIBLE)
				{
					content.setVisibility(View.GONE);
				}else
				{
					content.setVisibility(View.VISIBLE);
				}

			}
		});
	}

	public void showDatePickerDialog(View view) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void onRadioButtonClicked(View view) {
		group = (RadioGroup) findViewById(R.id.radioGroup);
		int radioButtonID = group.getCheckedRadioButtonId();
		radioButton = (RadioButton) findViewById(radioButtonID);
		blood_type = radioButton.getText().toString();
		((TextView) blood_header.findViewById(R.id.head_extra_text_view)).setText(blood_type);
	}

	public View getContent_blood() {
		return content_blood;
	}

	public void setContent_blood(View content_blood) {
		this.content_blood = content_blood;
	}

	public View getContent_contacts() {
		return content_contacts;
	}

	public void setContent_contacts(View content_contacts) {
		this.content_contacts = content_contacts;
	}

	public View getHeader_contacts() {
		return header_contacts;
	}

	public void setHeader_contacts(View header_contacts) {
		this.header_contacts = header_contacts;
	}

	public View getTelephone() {
		return telephone;
	}

	public void setTelephone(View telephone) {
		this.telephone = telephone;
	}

	public View getEmail() {
		return email;
	}

	public void setEmail(View email) {
		this.email = email;
	}

	public View getDate_content() {
		return date_content;
	}

	public void setDate_content(View date_content) {
		this.date_content = date_content;
	}

	public static class DatePickerFragment extends DialogFragment
			implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			int year = Y;
			int month = M;
			int day = D;

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month - 1, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			Y = year;
			M = month + 1;
			D = day;
			dateValue = Y + "-" + String.format("%02d", M) + "-" + String.format("%02d", D);
			date.setText(dateValue);
		}
	}

	public void sendPostRequestConsumers(View View) {
		new PostClass(this).execute();
	}

	private class PostClass extends AsyncTask<String, Void, Void> {

		private final Context context;
		private ProgressDialog progress;
		private AlertDialog result_output;
		private JSONObject jsonObject;
		private JSONObject data = null;
		private String message = "";
		private String response = "";

		String nameValue =  name_input.getText().toString();
		String phoneValue = phone_number_input.getText().toString();
		String emailValue = mail_input.getText().toString();
		String depValue = dep_input.getText().toString();
		String descValue = descr_input.getText().toString();

		public PostClass(Context c){
			this.context = c;
		}

		protected void onPreExecute(){
			progress= new ProgressDialog(this.context);
			progress.setMessage("Loading");
			progress.show();

			result_output = new AlertDialog.Builder(this.context).create();

			result_output.setTitle("Response");
			result_output.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
		}

		@Override
		protected Void doInBackground(String... params) {
			try {

				URL url = new URL("http://192.168.88.150/GiveBlood/register_consumer.php");
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();

				cv.put(TAG_NAME, nameValue);
				cv.put(TAG_DATE_OF_BIRTH, dateValue);
				cv.put(TAG_PHONE_NUMBER, phoneValue);
				cv.put(TAG_EMAIL, emailValue);
				cv.put(TAG_BLOOD_TYPE, blood_type);
				cv.put(TAG_CITY, city);
				cv.put(TAG_HOSPITAL, hospital);
				cv.put(TAG_DEPARTMENT, depValue);
				cv.put(TAG_DESCRIPTION, descValue);

				connection.setRequestMethod("POST");
				connection.setReadTimeout(10000);
				connection.setConnectTimeout(15000);
				connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
				connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
				dStream.writeBytes(getQuery(cv));
				dStream.flush();
				dStream.close();

				final StringBuilder output = new StringBuilder("");
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";
				final StringBuilder responseOutput = new StringBuilder();
				System.out.println("output===============" + br);
				while((line = br.readLine()) != null ) {
					responseOutput.append(line);
				}
				br.close();

				output.append(System.getProperty("line.separator") + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

				jsonObject = new JSONObject(output.toString());

				data = jsonObject.getJSONObject(TAG_DATA);
				message = jsonObject.getString(TAG_MESSAGE);
				response = jsonObject.getString(TAG_RESPONSE);


				output.setLength(0);
				output.append("Data send :"+ System.getProperty("line.separator") + data.getString(TAG_NAME));
				output.append(System.getProperty("line.separator") + data.getString(TAG_DATE_OF_BIRTH));
				output.append(System.getProperty("line.separator") + data.getString(TAG_PHONE_NUMBER));
				output.append(System.getProperty("line.separator") + data.getString(TAG_EMAIL));
				output.append(System.getProperty("line.separator") + data.getString(TAG_BLOOD_TYPE));
				output.append(System.getProperty("line.separator") + data.getString(TAG_CITY));
				output.append(System.getProperty("line.separator") + data.getString(TAG_HOSPITAL));
				output.append(System.getProperty("line.separator") + data.getString(TAG_DEPARTMENT));
				output.append(System.getProperty("line.separator") + data.getString(TAG_DESCRIPTION));


				output.append(System.getProperty("line.separator") + System.getProperty("line.separator") + "Message : " + System.getProperty("line.separator") + message);

				RegHelpMeActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						result_output.setTitle(response);
						result_output.setMessage(output);
						progress.dismiss();
						result_output.show();

					}
				});

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private String getQuery(ContentValues params) throws UnsupportedEncodingException
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (String key : params.keySet())
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(key, "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode((String)params.get(key), "UTF-8"));
		}

		return result.toString();
	}
}