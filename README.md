# Android Multiple File Selector Dialog



###Introduction

![Screenshot](https://github.com/paulasiimwe/Android-Multiple-file-Selector-Dialog/raw/master/Sample.png)

**Supports API 8(+)**

This is a free to use,change and reproduce Android Library file selector dialog whose birth arose from this question I posted on Stackoverflow

http://stackoverflow.com/questions/22095441/android-multiple-file-selector-chooser-dialog

This library starts a file/folder selector activity and returns the file(s) (Yes Multiple option too) or folder.
The library is still very simple and not very aesthetically pleasing so your contribution is highly welcome.

Im looking forward to assistence to 
-add thumbnail views for image files listed.
-the activity starting from the last folder location accessed, with the checked files still checked. 
 But any other improvements are highly welcome.

Im not yet conversant with gradle so I don't know if it will work with such systems. But on eclipse simply add the library in your project's android properties.
Those on Android Studio, hit the project properties button, go to modules, click the green "+" button at the top, select import module, navigate to the library, go through the prompts then once it's imported. click on your project's module, click on the "+" button on the right, add module dependancy, click Parian...


###Usage
Add these activities in your manifest.
```
<activity
            android:name="paul.arian.fileselector.FileSelectionActivity" />
<activity
            android:name="paul.arian.fileselector.FolderSelectionActivity" />

```
Then also **add merge 1.01.jar** located in the repo to this library's build path or module

#### File Selector

To start the fileSelector 
first import.
```java
import paul.arian.fileselector.FileSelectionActivity;
```
use this code

```java
Intent intent = new Intent(getBaseContext(), FileSelectionActivity.class);
                startActivityForResult(intent, 0);
```

To capture the result, use this method.

```java
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            ArrayList<File> Files = (ArrayList<File>) data.getSerializableExtra(FILES_TO_UPLOAD); //file array list
            String [] files_paths; //string array
            int i = 0;

            for(File file : Files){
                //String fileName = file.getName();
                String uri = file.getAbsolutePath();
                files_paths[i] = uri.toString(); //storing the selected file's paths to string array files_paths
                i++;
            }
        }else{
        }

    }

```

#### Folder Selector

To start folder selection activity,

import:
```java
import paul.arian.fileselector.FolderSelectionActivity;
```
to start use this code.
```java
Intent intent = new Intent(getBaseContext(), FolderSelectionActivity.class);
                startActivityForResult(intent, 2);
```
To capture, use this method.

```
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK){
            String FolderPath = data.getSerializableExtra(FILES_TO_UPLOAD).toString(); //The path of folder(directory) is stored in FolderPath string.
        }
    }
```

###### Credits
Massive credit goes to Arian JM of Madrid who created the majority of this library.

Here is his profile on stackoverflow http://stackoverflow.com/users/3290971/arianjm

Looking forward to your feedback, collaboration and assistence.

regards,

Paul Asiimwe,

Kampala, Uganda,

https://google.com/+PaulAsiimwe

https://twitter.com/_paulasiimwe
