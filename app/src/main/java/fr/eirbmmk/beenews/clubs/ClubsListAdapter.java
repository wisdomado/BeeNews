package fr.eirbmmk.beenews.clubs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fr.eirbmmk.beenews.R;
//import fr.eirbmmk.beenews.news.NewsItem;
//import com.eirbmmk.app.news.SchoolNews;
//import com.eirbmmk.app.news.Tweet;

//import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * List Adapter class to display news in an custom ListView
 */
public class ClubsListAdapter extends BaseAdapter {

    /**
     * Private Class to simplify Image Downloading from the Internet
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private Context mContext;
    private ArrayList<Club> mClubs;

    public ClubsListAdapter(Context context, ArrayList<Club> clubs){
        mContext = context;
        mClubs = clubs;
    }

    @Override
    public int getCount() {
        return mClubs.size();
    }

    @Override
    public Object getItem(int i) {
        return mClubs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.clubs_list_item, null);
        }


        TextView clubName = (TextView) view.findViewById(R.id.club_name);
        clubName.setText(mClubs.get(i).getName());

        ImageView clubLogo = (ImageView) view.findViewById(R.id.club_logo);

        Log.d("URL","" +  mClubs.get(i).getIconURL());

        new DownloadImageTask(clubLogo)
                .execute(mClubs.get(i).getIconURL());


        return view;
    }
}
