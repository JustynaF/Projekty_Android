package justynafirkowska.datacollector;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonEditDialog {
    public void showDialog(final Activity activity, final Person person) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.person_edit_dialog);

        SimpleDateFormat dateFormat =  new SimpleDateFormat(activity.getString(R.string.BirthFormat));

        ((EditText) dialog.findViewById(R.id.EditFirstName)).setText(person.getFirstName());
        ((EditText) dialog.findViewById(R.id.EditLastName)).setText(person.getLastName());
        ((EditText) dialog.findViewById(R.id.EditBirthDate)).setText(dateFormat.format(person.getBirth()));

        Button cancelButton = (Button) dialog.findViewById(R.id.Cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button updateButton = (Button)dialog.findViewById(R.id.UpdatePersonButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = ((EditText) dialog.findViewById(R.id.EditFirstName)).getText().toString();
                String lName = ((EditText) dialog.findViewById(R.id.EditLastName)).getText().toString();
                String birth = ((EditText) dialog.findViewById(R.id.EditBirthDate)).getText().toString();
                if (ValidateNewPerson(activity, fName, lName, birth)) {
                    ((MainActivity)activity).db.updatePerson(person.getId(), fName, lName, birth);
                    ((MainActivity)activity).db.insertHistory("Updated person id " + person.getId());
                    ((MainActivity)activity).RefreshPersonsListView();
                    dialog.dismiss();
                }
            }
        });

        Button deleteButton = (Button)dialog.findViewById(R.id.DeletePersonButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)activity).db.deletePerson(person.getId());
                ((MainActivity)activity).db.insertHistory("Deleted person id " + person.getId());
                ((MainActivity)activity).RefreshPersonsListView();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean ValidateNewPerson(Activity activity, String fName, String lName, String birth) {
        boolean validDate = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat(activity.getString(R.string.BirthFormat));
        try {
            dateFormat.parse(birth);
        }
        catch (ParseException ex) {
            validDate = false;
        }

        return !fName.isEmpty()&& !lName.isEmpty() && validDate;
    }
}
