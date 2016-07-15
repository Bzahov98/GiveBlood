package digimark.giveblood;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

/**
 * Created by Bobo-PC on 14.7.2016 г..
 */
public abstract class RegBaseActivity extends AppCompatActivity{

	protected static Calendar c = Calendar.getInstance();
	protected static int Y = (c.get(Calendar.YEAR))-18;
	protected static int M = c.get(Calendar.MONTH);
	protected static int D = c.get(Calendar.DAY_OF_MONTH);

	protected final ContentValues cv = new ContentValues();
	protected final StringBuilder output = new StringBuilder("");
	protected JSONObject data = null;

	protected EditText name_input;
	protected EditText phone_number_input;
	protected EditText mail_input;

	protected String hospital = "";
	protected String city = "";
	protected String blood_type = "A-";
	protected static String dateValue = "";

	protected String current_Ip = "http://192.168.88.150"; // !!<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<!!
	protected String current_path;

	protected static final String TAG_DATA = "data";
	protected static final String TAG_NAME = "name";
	protected static final String TAG_DATE_OF_BIRTH = "date_of_birth";
	protected static final String TAG_PHONE_NUMBER = "phone_number";
	protected static final String TAG_EMAIL = "email";
	protected static final String TAG_BLOOD_TYPE = "blood_type";
	protected static final String TAG_CITY = "city";
	protected static final String TAG_HOSPITAL = "hospital";
	protected static final String TAG_DESCRIPTION = "description";
	protected static final String TAG_DEPARTMENT = "department";
	protected static final String TAG_QUANTITY = "quantity";
	protected static final String TAG_MESSAGE = "message";
	protected static final String TAG_RESPONSE = "response";


	protected static Button date;
	protected RadioGroup group;
	protected RadioButton radioButton;

	protected View blood_header;
	protected View content_blood;

	protected View content_contacts;
	protected View header_contacts;

	protected View telephone;
	protected View email;

	protected View town_header;
	protected View town_content;
	protected ListView town_listView;

	protected View hospital_header;
	protected View hospital_content;
	protected ListView hospitals_listView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	protected void setClicks(final View header, final View content)
	{
		header.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				TextView temp = (TextView) header.findViewById(R.id.head_extra_text_view);

				if(content.getVisibility() == View.VISIBLE)
				{
					content.setVisibility(View.GONE);
					if(temp.getText().equals("✍"))
					{
						temp.setText("");
					}
				}
				else
				{
					content.setVisibility(View.VISIBLE);
					if(temp.getText().equals(""))
					{
						temp.setText("✍");
					}
				}

			}
		});
	}

	protected void showDatePickerDialog(View view) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	protected void onRadioButtonClicked(View view) {
		group = (RadioGroup) findViewById(R.id.radioGroup);
		int radioButtonID = group != null ? group.getCheckedRadioButtonId() : 0;
		radioButton = (RadioButton) findViewById(radioButtonID);
		blood_type = radioButton != null ? radioButton.getText().toString() : null;
		((TextView)  findViewById(R.id.header2_bloodtype).findViewById(R.id.head_extra_text_view)).setText(blood_type);
	}

	public View getContent_blood() {
		return content_blood;
	}

	protected void setContent_blood(View content_blood) {
		this.content_blood = content_blood;
	}

	protected View getContent_contacts() {
		return content_contacts;
	}

	protected void setContent_contacts(View content_contacts) {
		this.content_contacts = content_contacts;
	}

	protected View getHeader_contacts() {
		return header_contacts;
	}

	protected void setHeader_contacts(View header_contacts) {
		this.header_contacts = header_contacts;
	}

	protected View getTelephone() {
		return telephone;
	}

	protected void setTelephone(View telephone) {
		this.telephone = telephone;
	}

	protected View getEmail() {
		return email;
	}

	protected void setEmail(View email) {
		this.email = email;
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

	protected class PostClass extends AsyncTask<String, Void, Void> {

		protected final Context context;
		protected ProgressDialog progress;
		protected AlertDialog result_output;
		protected JSONObject jsonObject;
		protected String message = "";
		protected String response = "";

		final String nameValue =  ((EditText) findViewById(R.id.child1_names).findViewById(R.id.edit_text)).getText().toString();
		final String phoneValue = ((EditText) findViewById(R.id.child1_number).findViewById(R.id.edit_text)).getText().toString();
		final String emailValue = ((EditText) findViewById(R.id.child1_email).findViewById(R.id.edit_text)).getText().toString();

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

				URL url = new URL(current_Ip + current_path);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();

				cv.put(TAG_NAME, nameValue);
				cv.put(TAG_DATE_OF_BIRTH, dateValue);
				cv.put(TAG_PHONE_NUMBER, phoneValue);
				cv.put(TAG_EMAIL, emailValue);
				cv.put(TAG_BLOOD_TYPE, blood_type);
				cv.put(TAG_CITY, city);
				SetValueCV();

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
				setOutput();

				output.append(System.getProperty("line.separator") + System.getProperty("line.separator") + "Message : " + System.getProperty("line.separator") + message);

				uiAction(result_output, response, output, progress);

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

	protected abstract void setOutput();

	protected abstract void SetValueCV();

	protected abstract void uiAction(AlertDialog result_output, String response, CharSequence output, Dialog progress);

	protected String getQuery(ContentValues params) throws UnsupportedEncodingException
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

	protected void ShowListView(final boolean isTown) {
		String[] values1;
		final View header;
		final View content;
		final ListView listView;

		if (isTown){
			values1 = getResources().getStringArray(R.array.towns);
			listView = town_listView;
		}
		else {
			values1 = getResources().getStringArray(R.array.hospitals);
			listView = hospitals_listView;
		}

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.list_view_liw, android.R.id.text1, values1);

		listView.setAdapter(adapter1);

		if (isTown){
			header = findViewById(R.id.header3_town);
			content = findViewById(R.id.group3child);

		}
		else {
			header = findViewById(R.id.header4_hospital);
			content = findViewById(R.id.group4child);
		}

		UIUtils.setListViewHeightBasedOnItems(listView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String itemValue = (String) listView.getItemAtPosition(position);

				((TextView) header.findViewById(R.id.view_head)).setText(itemValue);
				content.setVisibility(View.GONE);
				((TextView) header.findViewById(R.id.head_extra_text_view)).setText("");

				if (isTown) {
					city = itemValue;
	//				town_content.setVisibility(View.GONE);
				}
				else {
					hospital = itemValue;
				}
			}
		});

		listView.setScrollContainer(false);

	}
}
