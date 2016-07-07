package digimark.giveblood.listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import digimark.giveblood.BaseActivity;
import digimark.giveblood.R;

public class ListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<HeaderInfo> deptList;
	private Activity aactivity;

	public ListAdapter(Context context, ArrayList<HeaderInfo> deptList, Activity activity) {
		this.context = context;
		this.deptList = deptList;
		this.aactivity = activity;
	}

	@Override
	public int getGroupCount() {
		return deptList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<DetailInfo> productList = deptList.get(groupPosition).getProductList();
		return productList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return deptList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<DetailInfo> productList = deptList.get(groupPosition).getProductList();
		return productList.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		HeaderInfo headerInfo = (HeaderInfo) getGroup(groupPosition);
		if (convertView == null)
		{
			LayoutInflater inf  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.list_group_heading,null);
		}

		TextView heading = (TextView) convertView.findViewById(R.id.view_head);
		heading.setText(headerInfo.getName().trim());
		return convertView;
	}

	public int getItemViewType(int groupPosition) {
		boolean a = ((HeaderInfo) getGroup(groupPosition)).getName().contains("Кръвна");
		return (a) ? 2 : 1;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


		DetailInfo detailInfo = ((HeaderInfo) getGroup(groupPosition)).getProductList().get(childPosition);
		int type = getItemViewType(groupPosition);
		//int type = 2;
		LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			switch (type) {
				case 1:
					convertView = inf.inflate(R.layout.list_child_row, parent, false);

					break;
				case 2:
					convertView = inf.inflate(R.layout.list_child_blood_groups, parent, false);
					break;
			}
		}

		switch (type) {
			case 1:
				TextView sequence = (TextView) convertView.findViewById(R.id.listview_sequence);
				sequence.setText(detailInfo.getSequence()+ ") ");

				TextView childName = (TextView) aactivity.findViewById(R.id.listview_childItem);
				childName.setText(detailInfo.getName());
			case 2:
				RadioGroup radioGroup = (RadioGroup) aactivity.findViewById(R.id.km);
				//radioGroup.check(R.id.radioButton);

				RadioButton radioButton1 = (RadioButton) aactivity.findViewById(R.id.radioButton);
				radioButton1.setText(detailInfo.getName().trim());
//				radioButton1.setText(detailInfo.getName().trim());
				break;
		}
/*
		DetailInfo detailInfo = ((HeaderInfo) getGroup(groupPosition)).getProductList().get(childPosition);

		LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			convertView = inf.inflate(R.layout.list_child_row, null);
		}
		TextView sequence = (TextView) convertView.findViewById(R.id.listview_sequence);
		sequence.setText(detailInfo.getSequence().trim() + ") ");

		TextView childName = (TextView) convertView.findViewById(R.id.listview_childItem);
		childName.setText(detailInfo.getName().trim());*/
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
