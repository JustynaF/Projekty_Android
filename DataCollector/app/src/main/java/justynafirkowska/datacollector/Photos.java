package justynafirkowska.datacollector;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Photos extends AppCompatActivity {

    TableLayout image_table;
    ArrayList<Drawable> image_drawable=new ArrayList<Drawable>();
    File[] files;
    Map<Drawable,String> dict = new HashMap<Drawable,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        image_table = (TableLayout) findViewById(R.id.image_table);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        files = storageDir.listFiles();
        Log.d("filesSize", Integer.toString(files.length));

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String pathName = file.getAbsolutePath();
            Drawable d = Drawable.createFromPath(pathName);
            image_drawable.add(d);
            dict.put(d, pathName);
        }

        updateImageTable();
    }

    public void updateImageTable()
    {
        image_table.removeAllViews();

        if(dict.size() > 0)
        {
            for(final Map.Entry<Drawable, String> entry : dict.entrySet())
            {
                TableRow tableRow=new TableRow(this);
                tableRow.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
                tableRow.setPadding(5, 5, 5, 5);
                for(int j=0; j<1; j++)
                {
                    ImageView image=new ImageView(this);
                    image.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

                    /*Bitmap bitmap = BitmapFactory.decodeFile(image_list.get(i).toString().trim());
                    bitmap = Bitmap.createScaledBitmap(bitmap,500, 500, true);
                    Drawable d=loadImagefromurl(bitmap);*/
                    image.setBackgroundDrawable(entry.getKey());

                    Button button = new Button(this);
                    button.setText("DELETE");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            File fdelete = new File(entry.getValue());
                            fdelete.delete();
                            recreate();
                        }
                    });

                    tableRow.addView(image, 400, 400);
                    tableRow.addView(button);
                }
                image_table.addView(tableRow);
            }
        }
    }
}
