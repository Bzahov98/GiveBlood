package digimark.giveblood;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class RegHelpActivity extends AppCompatActivity {

    private RadioGroup group;
    private RadioButton radioButton;

    private ContentValues cv = new ContentValues();
    private EditText name;
    private EditText date_of_birth;
    private EditText phone_number;
    private EditText email;
    private String blood_type = "";
    private EditText city;

    private static final String TAG_DATA = "data";
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE_OF_BIRTH = "date_of_birth";
    private static final String TAG_PHONE_NUMBER = "phone_number";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_BLOOD_TYPE = "blood_type";
    private static final String TAG_CITY = "city";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_RESPONSE = "response";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_help_acticity);

        name = (EditText) findViewById(R.id.NameTxt);
        date_of_birth = (EditText) findViewById(R.id.dateTxt);
        phone_number = (EditText) findViewById(R.id.PhoneTxt);
        email = (EditText) findViewById(R.id.EmailTxt);
        city = (EditText) findViewById(R.id.CityTxt);
    }

    public  void onRadioButtonClicked(View view) {
        group = (RadioGroup) findViewById(R.id.radioGroup);
        int radioButtonID = group.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioButtonID);
        blood_type = radioButton.getText().toString();
    }

    public void sendPostRequest(View View) {
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

        String nameValue =  name.getText().toString();
        String dateValue = date_of_birth.getText().toString();
        String phoneValue = phone_number.getText().toString();
        String emailValue = email.getText().toString();
        String cityValue = city.getText().toString();

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

                URL url = new URL("http://192.168.1.118/GiveBlood/register_giver.php");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                cv.put(TAG_NAME, nameValue);
                cv.put(TAG_DATE_OF_BIRTH, dateValue);
                cv.put(TAG_PHONE_NUMBER, phoneValue);
                cv.put(TAG_EMAIL, emailValue);
                cv.put(TAG_BLOOD_TYPE, blood_type);
                cv.put(TAG_CITY, cityValue);

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

                output.append(System.getProperty("line.separator") + System.getProperty("line.separator") + "Message : " + System.getProperty("line.separator") + message);

                RegHelpActivity.this.runOnUiThread(new Runnable() {

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

