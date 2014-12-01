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

public class CustomListSingleOnly extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] web;
    String ParentFolder;
    public CustomListSingleOnly(Activity context, String[] web ,String path) {
        super(context, R.layout.list_single_only, web);
        this.context = context;
        this.web = web;
            ParentFolder = path;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_only, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        if((new File(ParentFolder+"/"+web[position])).isDirectory()){
            imageView.setImageResource(R.drawable.folder);//sets to folder
        }else if((new File(ParentFolder+"/"+web[position])).isFile()) {//sets to file
            Picasso.with(context).load(
                    new File(
                            ParentFolder + "/" + web[position]
                    )).placeholder(R.drawable.document_gray).resize(50, 50).into(imageView);
        }
        return rowView;
    }

}

