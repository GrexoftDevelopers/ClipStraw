package widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FaizZy on 08-01-2016.
 */
public class LayerBackgroundAdapter extends BaseAdapter {

    private Context mContext;

    LayoutInflater inflater;

    public List<Integer> mBgImages=new ArrayList<Integer>();
   // public Integer [] mBgImagsId;



    public LayerBackgroundAdapter(Context ctx, LayoutInflater inflater) {
        mContext=ctx; this.inflater = inflater;

    }

    @Override
    public int getCount() {
        return mBgImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mBgImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout mImageView;
         if(convertView == null) {
            //mImageView = new ImageView(mContext);
            //mImageView.setLayoutParams(new GridView.LayoutParams(75, 75));



            //mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
             mImageView = (LinearLayout)inflater.inflate(R.layout.thumbnail_item, null, false);
           // mImageView.setPadding(0, 0, 5, 0);
        }else
        {
           mImageView = (LinearLayout) convertView;
        }

        //mImageView.setImageResource(mBgImages.get(position));
                return mImageView;
    }
}

