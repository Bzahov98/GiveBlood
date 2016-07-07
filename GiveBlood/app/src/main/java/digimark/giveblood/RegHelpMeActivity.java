package digimark.giveblood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import digimark.giveblood.listview.DetailInfo;
import digimark.giveblood.listview.HeaderInfo;
import digimark.giveblood.listview.ListAdapter;


public class RegHelpMeActivity extends AppCompatActivity {


	private LinkedHashMap<String, HeaderInfo> myDepartments = new LinkedHashMap<String, HeaderInfo>();
	private ArrayList<HeaderInfo> deptList = new ArrayList<HeaderInfo>();

	private ListAdapter listAdapter;
	private ExpandableListView myList;
	private TextView asd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_help_me_acticity); // need to make an abstract class next
		asd = (TextView) findViewById(R.id.testtext);
		loadData();

		myList = (ExpandableListView) findViewById(R.id.expaListViewHelpMe);

		listAdapter = new ListAdapter(this, deptList, this);
		//attach the adapter to the list
		myList.setAdapter(listAdapter);

		expandAll();

		myList.setOnChildClickListener(myListItemClicked);
		//listener for group heading click
		myList.setOnGroupClickListener(myListGroupClicked);

		asd.setText(deptList.get(0).getProductList().get(2).getName());
	}

	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			myList.expandGroup(i);
		}
	}

	private void loadData() {

		addProduct("Контакти, Телефон", "Имена: ");
		addProduct("Контакти, Телефон", "Дата на раждане: ");
		addProduct("Контакти, Телефон", "Ëmail: ");
		addProduct("Кръвна Група", "A");
		addProduct("Кръвна Група", "AB+");
		addProduct("Град", "");
		addProduct("Описание", "");

	}

	private int addProduct(String baseSection, String secondarySection) {

		int groupPosition = 0;

		if (baseSection == "Кръвна Група") {
			Toast.makeText(getBaseContext(), "YAYWdyawy ", Toast.LENGTH_LONG).show();
		}
		//check the hash map if the group already exists
		HeaderInfo headerInfo = myDepartments.get(baseSection);
		//add the group if doesn't exists
		if (headerInfo == null) {
			headerInfo = new HeaderInfo();
			headerInfo.setName(baseSection);
			myDepartments.put(baseSection, headerInfo);
			deptList.add(headerInfo);
		}

		//get the children for the group
		ArrayList<DetailInfo> secondaryList = headerInfo.getProductList();
		//size of the children list
		int listSize = secondaryList.size();
		//add to the counter
		listSize++;

		//create a new child and add that to the group
		DetailInfo detailInfo = new DetailInfo();
		detailInfo.setSequence(String.valueOf(listSize));
		detailInfo.setName(secondarySection);
		secondaryList.add(detailInfo);
		headerInfo.setProductList(secondaryList);

		//find the group position inside the list
		groupPosition = deptList.indexOf(headerInfo);
		return groupPosition;
	}


	//our child listener
	private ExpandableListView.OnChildClickListener myListItemClicked = new ExpandableListView.OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v,
		                            int groupPosition, int childPosition, long id) {

			//get the group header
			HeaderInfo headerInfo = deptList.get(groupPosition);
			//get the child info
			DetailInfo detailInfo = headerInfo.getProductList().get(childPosition);
			//display it or do something with it
			//Toast.makeText(getBaseContext(), "Clicked on Detail " + headerInfo.getName()
			//		+ "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
			return false;
		}

	};

	//our group listener
	private ExpandableListView.OnGroupClickListener myListGroupClicked = new ExpandableListView.OnGroupClickListener() {

		public boolean onGroupClick(ExpandableListView parent, View v,
		                            int groupPosition, long id) {

			//get the group header
			HeaderInfo headerInfo = deptList.get(groupPosition);
			//display it or do something with it
			//Toast.makeText(getBaseContext(), "Child on Header " + headerInfo.getName(),
			//		Toast.LENGTH_LONG).show();

			return false;
		}

	};

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
	}


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
    }*/

}

