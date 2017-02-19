package justynafirkowska.calculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class History extends AppCompatActivity {

    private static final Logger logger = LoggerManager.getLogger();
    String FILENAME = "calcHistory";
    FileInputStream fis = null;
    FileOutputStream fos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try {
            logger.i("Loading history file");
            fis = openFileInput(FILENAME);
        } catch (Exception e) {
            logger.w("Problem with loading history file");
            try {
                fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                fos.close();
                fis = openFileInput(FILENAME);
            } catch (Exception ex) {
                logger.e("Could not load history file", e);
                throw new RuntimeException(ex);
            }
        }

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            logger.i("Reading history file");
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            logger.e("Could not read history file", e);
            throw new RuntimeException(e);
        }

        TextView history = (TextView) findViewById(R.id.historyTextView);
        history.setMovementMethod(new ScrollingMovementMethod());
        logger.d("Appending history to text view");
        history.setText(sb);

        logger.d("Config back button");
        Button back = (Button) findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logger.d("Config clear button");
        Button clear = (Button) findViewById(R.id.clearHistory);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = getFilesDir();
                File file = new File(dir, FILENAME);
                boolean deleted = file.delete();

                if(deleted) {
                    logger.i("History deleted");
                    Toast.makeText(History.this, "History deleted.", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    startActivity(intent);
                }
                else {
                    logger.e("Error while deleting history");
                    Toast.makeText(History.this, "Error while deleting history.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
