package fr.eirbmmk.beenews;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ListView Adapter for the LeftMenu listView
 */
public class LeftMenuListAdapter extends BaseAdapter {

    /**
     * Application context
     */
	private Context mContext;

    /**
     * List of menus items to display in the left menu listview
     */
	private ArrayList<Screen> mLeftMenuItems;

    /**
     * Constructor of the LeftMenuListAdapter
     *
     * @param context the application context
     * @param leftMenuItems items list to display in the listView
     */
	public LeftMenuListAdapter(Context context, ArrayList<Screen> leftMenuItems){
		mContext = context;
		mLeftMenuItems = leftMenuItems;
	}

	@Override
	public int getCount() {
		return mLeftMenuItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return mLeftMenuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.left_menu_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        int iconResourceId = mContext.getResources().getIdentifier(mLeftMenuItems.get(position).getIconName(), "drawable", mContext.getPackageName());

        imgIcon.setImageResource(iconResourceId);
        txtTitle.setText(mLeftMenuItems.get(position).getTitle());
        
        return convertView;
	}

}
