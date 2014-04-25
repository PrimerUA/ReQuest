package com.skylion.request.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import com.skylion.request.utils.DateTimeSelectorListener;

public class DateTimeSelector {
	
	private DatePickerDialog.OnDateSetListener date;
	private Calendar myCalendar;
	private String selectedDate;
	private DateTimeSelectorListener event;
	
	public DateTimeSelector(){
	}	
	
	public void init(Calendar instance) {
		myCalendar = instance;
		date = new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        setDateString();
			}
		};
	}
	
	private void setDateString() {
		String myFormat = "dd.MM.yyyy"; 
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    selectedDate = (sdf.format(myCalendar.getTime()));
	    
	    event.updateDate();
	}
	
	public void openDateDialog(FragmentActivity activiti) {
		new DatePickerDialog(activiti, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	}
	
	public String getDateString() {
		return this.selectedDate;
	}
		
	public void setListener(DateTimeSelectorListener dateTimeSelectorListener) {
		event = dateTimeSelectorListener;;
	}
}
