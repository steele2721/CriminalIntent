package com.androidapps.robertsteele.criminalintent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "arg_date";
    public static final String EXTRA_DATE =
            "com.androidapps.robertsteele.criminalIntent.date";

    private DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void sendResults(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE, date);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }


    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int day = mDatePicker.getDayOfMonth();
                        int month = mDatePicker.getMonth();
                        int year = mDatePicker.getYear();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResults(Activity.RESULT_OK, date);
                    }
                })
                .create();
        return alertDialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }


}
