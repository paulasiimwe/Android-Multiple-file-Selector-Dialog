package paul.arian.fileselector;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.commonsware.cwac.merge.MergeAdapter;

public class FileSelectionActivity extends Activity {

    private static final String TAG = "FileSelection";
    private static final String FILES_TO_UPLOAD = "upload";
    private File mainPath = new File(Environment.getExternalStorageDirectory()+"");
    private ArrayList<File> resultFileList;

    private ListView directoryView;
    private ArrayList<File> directoryList = new ArrayList<File>();
    private ArrayList<String> directoryNames = new ArrayList<String>();
    //private ListView fileView;
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<String> fileNames = new ArrayList<String>();
    Button ok, all;
    TextView path;
    Boolean Switch = false;

    Integer[] imageId = {
            R.drawable.document,
            R.drawable.document_gray,
            R.drawable.folder,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selection);
        //getActionBar().setDisplayHomeAsUpEnabled(true);



        directoryView = (ListView)findViewById(R.id.directorySelectionList);
        ok = (Button)findViewById(R.id.ok);
        all = (Button)findViewById(R.id.all);
        path = (TextView)findViewById(R.id.folderpath);

        loadLists();




        directoryView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File lastPath = mainPath;
                try {
                    if (position < directoryList.size()) {
                        mainPath = directoryList.get(position);
                        loadLists();
                    }
                }catch (Throwable e){
                    mainPath = lastPath;
                    loadLists();
                }

			}
		});

       ok.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ok();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!Switch){
                    for (int i = directoryList.size(); i < directoryView.getCount(); i++){
                        directoryView.setItemChecked(i, true);
                    }
                    all.setText(getString(R.string.none));
                    Switch = true;
                }else if(Switch){
                    for (int i = directoryList.size(); i < directoryView.getCount(); i++) {
                        directoryView.setItemChecked(i, false);
                    }
                    all.setText(getString(R.string.all));
                    Switch = false;
                }
                }

        });
    }

    public void onBackPressed() {
        try {
            if(mainPath.equals(Environment.getExternalStorageDirectory().getParentFile().getParentFile())){
                finish();
            }else{
                File parent = mainPath.getParentFile();
                mainPath = parent;
                loadLists();
            }

        }catch (Throwable e){

        }
    }

	public void ok(){
    	Log.d(TAG, "Upload clicked, finishing activity");


        resultFileList = new ArrayList<File>();

        for(int i = 0 ; i < directoryView.getCount(); i++){
        	if(directoryView.isItemChecked(i)){
        		resultFileList.add(fileList.get(i-directoryList.size()));
        	}
        }
        if(resultFileList.isEmpty()){
        	Log.d(TAG, "Nada seleccionado");
        	finish();
        }
        Log.d(TAG, "Files: "+resultFileList.toString());
        Intent result = this.getIntent();
        result.putExtra(FILES_TO_UPLOAD, resultFileList);
        setResult(Activity.RESULT_OK, result);
    	finish();
    }

	private void loadLists(){
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		};
		FileFilter directoryFilter = new FileFilter(){
			public boolean accept(File file){
				return file.isDirectory();
			}
		};

		//if(mainPath.exists() && mainPath.length()>0){
			//Lista de directorios
			File[] tempDirectoryList = mainPath.listFiles(directoryFilter);
			directoryList = new ArrayList<File>();
			directoryNames = new ArrayList<String>();
			for(File file: tempDirectoryList){
				directoryList.add(file);
				directoryNames.add(file.getName());
			}
			ArrayAdapter<String> directoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, directoryNames);


			//Lista de ficheros
			File[] tempFileList = mainPath.listFiles(fileFilter);
			fileList = new ArrayList<File>();
    		fileNames = new ArrayList<String>();
    		for(File file : tempFileList){
    			fileList.add(file);
    			fileNames.add(file.getName());
    		}



            path.setText(mainPath.toString());
            iconload();
            setTitle(mainPath.getName());
		//}
	}

    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_file_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }**/

    public void iconload(){
        String[] foldernames = new String[directoryNames.size()];
        foldernames = directoryNames.toArray(foldernames);

        String[] filenames = new String[fileNames.size()];
        filenames = fileNames.toArray(filenames);

        CustomListSingleOnly adapter1 = new CustomListSingleOnly(FileSelectionActivity.this, directoryNames.toArray(foldernames), imageId[2]);
        CustomList adapter2 = new CustomList(FileSelectionActivity.this, fileNames.toArray(filenames), imageId[0]);


        MergeAdapter adap = new MergeAdapter();

        adap.addAdapter(adapter1);
        adap.addAdapter(adapter2);


        directoryView.setAdapter(adap);
    }

}
