package digimark.giveblood;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;


public class RegHelpMeActivity extends Activity {

/*
	private LinkedHashMap<String, HeaderInfo> myDepartments = new LinkedHashMap<String, HeaderInfo>();
	private ArrayList<HeaderInfo> deptList = new ArrayList<HeaderInfo>();

	private ListAdapter listAdapter;
	private ExpandableListView myList;
	private TextView asd;*/


	public static String dateValue;
	public static Button date;
	private RadioGroup group;
	private RadioButton radioButton;
	private String blood_type;

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

		blood_header = findViewById(R.id.header2_bloodtype);
			setContent_blood(findViewById(R.id.group2child));

		town_header = findViewById(R.id.header3_town);
		hospital_header =  findViewById(R.id.header4_hospital);
		hospital_content = findViewById(R.id.list_hospital);
		town_content = findViewById(R.id.list_town);


		setHeader_contacts(findViewById(R.id.header1_contacts));
		setContent_contacts(findViewById(R.id.group1child));



		((TextView) getHeader_contacts().findViewById(R.id.view_head)).setText("Контакти, Телефон");

			View name = findViewById(R.id.child1_names);
				((TextView) name.findViewById(R.id.listview_childItem)).setText("Име:");

		View date_content = findViewById(R.id.child1_date);

		setTelephone(findViewById(R.id.child1_number));
			((TextView) getTelephone().findViewById(R.id.listview_childItem)).setText("Телефон: ");

		setEmail(findViewById(R.id.child1_email));
			((TextView) getEmail().findViewById(R.id.listview_childItem)).setText("Email: ");


		((TextView) blood_header.findViewById(R.id.view_head)).setText("Кръвна група:");
		((ImageView)  blood_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.kravnagrupaicn);

		((TextView) town_header.findViewById(R.id.view_head)).setText("Избери Град:");
		((ImageView)  town_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
		town_listView = (ListView) findViewById(R.id.list_hospital);


		((TextView) hospital_header.findViewById(R.id.view_head)).setText("Избери Болница:");
		((ImageView) hospital_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
		hospitals_listView = (ListView) findViewById(R.id.list_hospital);
		((ImageView)  hospital_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);

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
		description_context.setText("Необходимо количество: ");

		//---------------------------Set Show/Hide events------------------------------------

		setClicks(getHeader_contacts(), getContent_contacts());
		setClicks(blood_header, getContent_blood());
		setClicks(town_header, town_content);
		setClicks(hospital_header, hospital_content);
		setClicks(department_header, town_listView);
		setClicks(description_header, findViewById(R.id.group6child));

		//---------------------------ListView Hospitals-----------------------------------



		String[] values1 = getResources().getStringArray(R.array.towns);

		Log.d("values", "vliz1a");

		for(String s : values1){
			Log.d("values",s);
		}

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values1);

		town_listView.setAdapter(adapter1);
		town_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String itemValue = (String) town_listView.getItemAtPosition(position);

				((TextView) town_header.findViewById(R.id.view_head)).setText(itemValue);

			}
		});

		//---------------------------ListView Hospitals-----------------------------------

		;

		String[] values = getResources().getStringArray(R.array.hospitals);

		Log.d("values", "vliza");

		for(String s : values){
			Log.d("values",s);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		hospitals_listView.setAdapter(adapter);
		hospitals_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String itemValue = (String) hospitals_listView.getItemAtPosition(position);

				((TextView) hospital_header.findViewById(R.id.view_head)).setText(itemValue);

			}
		});

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
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			dateValue = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
			date.setText(dateValue);
		}
	}
}

/*



	public void PostReq(View view) {
	    /*try {
            URL url = new URL("http://192.168.88.167/GiveBlood/register_consumer.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            cv.put("name", (String) asd.getText());

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(getQuery(cv));
            bw.flush();
            bw.close();
            os.close();
            int responseCode=urlConnection.getResponseCode();
            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
            asd.setText(response);
            urlConnection.connect();
            urlConnection.disconnect();
        }
        catch (MalformedURLException ex) {
            Log.e("httptest",Log.getStackTraceString(ex));
        }
        catch (IOException ex) {
            Log.e("httptest",Log.getStackTraceString(ex));
        }*/





    /*private String getQuery(ContentValues params) throws UnsupportedEncodingException
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
 } */

	//}*/