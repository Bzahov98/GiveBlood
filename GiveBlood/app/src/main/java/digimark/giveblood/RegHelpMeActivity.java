package digimark.giveblood;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import digimark.giveblood.listview.DetailInfo;
import digimark.giveblood.listview.HeaderInfo;
import digimark.giveblood.listview.ListAdapter;


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
	View content_blood;
	View content_contacts;
	View header_contacts;
	View date_content;

	View telephone;
	View email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_pomognimi_acticity);

		date = (Button) findViewById(R.id.date_picker);
		blood_header = findViewById(R.id.header2_bloodtype);
		content_blood = findViewById(R.id.group2child);

		//((TextView) blood_header.findViewById(R.id.child2_bloodtype)).setText("Кръвна Група: ");
		View town = findViewById(R.id.header3_town);
		View hospital =  findViewById(R.id.header4_hospital);



		header_contacts = findViewById(R.id.header1_contacts);
		content_contacts = findViewById(R.id.group1child);



		((TextView) header_contacts.findViewById(R.id.view_head)).setText("Контакти, Телефон");

			View name = findViewById(R.id.child1_names);
				((TextView) name.findViewById(R.id.listview_childItem)).setText("Име:");

		View date_content = findViewById(R.id.child1_date);

		telephone = findViewById(R.id.child1_number);
			((TextView) telephone.findViewById(R.id.listview_childItem)).setText("Телефон: ");

		email = findViewById(R.id.child1_email);
			((TextView) email.findViewById(R.id.listview_childItem)).setText("Email: ");



			((TextView) findViewById(R.id.child1_number).findViewById(R.id.listview_childItem)).setText("Телефон: ");
		//	((TextView) header_contacts.findViewById(R.id.listview_childItem)).setText("Еmail: ");

		((TextView) blood_header.findViewById(R.id.view_head)).setText("Кръвна група:");
		((ImageView)  blood_header.findViewById(R.id.head_icon)).setImageResource(R.drawable.kravnagrupaicn);

		((TextView) town.findViewById(R.id.view_head)).setText("Избери Град:");
		((ImageView)  town.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);

		((TextView) hospital.findViewById(R.id.view_head)).setText("Избери Болница:");
		((ImageView)  hospital.findViewById(R.id.head_icon)).setImageResource(R.drawable.mestopolojenieicn);
		((TextView) findViewById(R.id.header5_department).findViewById(R.id.view_head)).setText("Отделение:");
		((TextView) findViewById(R.id.header6_description).findViewById(R.id.view_head)).setText("Описание:");
//
// loadData();

		setClicks(header_contacts, content_contacts);
		setClicks(blood_header, content_blood);

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


//		myList = (ExpandableListView) findViewById(R.id.expaListViewHelpMe);

//		listAdapter = new ListAdapter(this, deptList);
//attach the adapter to the list
//myList.setAdapter(listAdapter);

//expandAll();

//myList.setOnChildClickListener(myListItemClicked);
//listener for group heading click
//myList.setOnGroupClickListener(myListGroupClicked);

//asd.setText(deptList.get(0).getProductList().get(2).getName());
	/*}
*/
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