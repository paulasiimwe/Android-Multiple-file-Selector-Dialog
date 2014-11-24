package paul.arian.fileselector;

/**
 * Created by Paul on 3/7/14.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] web;
    public CustomList(Activity context, String[] web) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        Picasso.with(context).load(
                new File(
                        FileSelectionActivity.mainPath.getPath()+"/"+web[position]
                )).placeholder(R.drawable.document).resize(50, 50).into(imageView);
        return rowView;
    }
}
