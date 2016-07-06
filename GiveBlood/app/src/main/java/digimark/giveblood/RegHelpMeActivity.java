package digimark.giveblood;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import javax.net.ssl.HttpsURLConnection;


public class RegHelpMeActivity extends AppCompatActivity {

    private RadioGroup group;
    private RadioButton radioButton;

    private ContentValues cv = new ContentValues();
    private EditText name;
    private EditText date_of_birth;
    private EditText phone_number;
    private EditText email;
    private String blood_type = "A+";
    private EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_help_me_acticity);
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
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://192.168.88.167/GiveBlood/register_giver.php");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                cv.put("name", nameValue);
                cv.put("date_of_birth", dateValue);
                cv.put("phone_number", phoneValue);
                cv.put("email", emailValue);
                cv.put("blood_type", blood_type);
                cv.put("city", cityValue);

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
                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("");
                //output.append(System.getProperty("line.separator") + "Request Parameters " + cv);
                //output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
                //output.append(System.getProperty("line.separator")  + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

                RegHelpMeActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        outputView.setText(output);
                        progress.dismiss();

                    }
                });

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
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
