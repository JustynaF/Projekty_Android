package justynafirkowska.datacollector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public DbHelper db;
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText birthET;
    private ListView personsLV;
    private Button addPersonB;
    private ArrayList<Person> persons;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.firstNameET = (EditText) findViewById(R.id.Name);
        this.lastNameET = (EditText) findViewById(R.id.LastName);
        this.birthET = (EditText) findViewById(R.id.BirthDate);

        this.db = ((App)getApplication()).db;
        //this.db.cleanPersons();
        this.persons = this.db.getAllPersons();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.persons);
        this.personsLV = (ListView)findViewById(R.id.Persons);
        this.personsLV.setAdapter(arrayAdapter);

        this.addPersonB = (Button) findViewById(R.id.AddPerson);
        this.addPersonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateNewPerson()) {
                    String fName = firstNameET.getText().toString();
                    String lName = lastNameET.getText().toString();
                    String birth = birthET.getText().toString();

                    db.insertPerson(fName, lName, birth);
                    db.insertHistory("Added new person: " + fName + " " + lName);
                    RefreshPersonsListView();

                    firstNameET.setText("");
                    lastNameET.setText("");
                    birthET.setText("");
                }
            }
        });

        this.personsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person =(Person) (personsLV.getItemAtPosition(position));
                PersonEditDialog dialog = new PersonEditDialog();
                dialog.showDialog(MainActivity.this, person);
            }
        });
    }
    public void RefreshPersonsListView() {
        this.persons.clear();
        this.persons.addAll(this.db.getAllPersons());
        ((ArrayAdapter)this.personsLV.getAdapter()).notifyDataSetChanged();
    }
    private boolean ValidateNewPerson() {
        String fName = this.firstNameET.getText().toString();
        String lName = this.lastNameET.getText().toString();
        String birth = this.birthET.getText().toString();
        boolean validDate = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.BirthFormat));
        try {
            dateFormat.parse(birth);
        }
        catch (ParseException ex) {
            validDate = false;
        }

        return !fName.isEmpty()&& !lName.isEmpty() && validDate;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_persons) {
            Intent intent = getIntent();
            startActivity(intent);
        } else if (id == R.id.nav_camera) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    //...
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.justynafirkowska.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_photos) {
            Intent intent = new Intent(MainActivity.this, Photos.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = new File(getFilesDir(), "Pictures");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
