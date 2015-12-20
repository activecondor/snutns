package snu.talkandshow;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Seungyong on 2015-12-19.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    public Dialog onCreateDialog(Bundle SavedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }
    public void onTimeSet(TimePicker view, int hour, int minute){
        EditText time_edit = (EditText) getActivity().findViewById(R.id.date_edit2);
        time_edit.setText(hour+":"+minute);
    }
}
