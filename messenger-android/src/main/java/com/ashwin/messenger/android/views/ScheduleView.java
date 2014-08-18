package com.ashwin.messenger.android.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashwin.messenger.android.R;
import com.ashwin.messenger.model.Course;
import com.ashwin.messenger.model.Timing;
import com.ashwin.messenger.model.Timing.Day;

public class ScheduleView extends FrameLayout {
	
	public ScheduleView(Context context) {
	    this(context, null);
	}

	public ScheduleView(Context context, AttributeSet attrs) {
	    this(context, attrs, 0);
	}

	public ScheduleView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    setBackgroundResource(R.drawable.bg_card);
	    
	    inflate(context, R.layout.view_schedule, this);
	    
	    // Initialize Test Data
	    List<Course> courses = new ArrayList<Course>();
	    List<Timing> timings = new ArrayList<Timing>();
	    timings.add(new Timing(null, "BUR", "1.234", Day.MONDAY, 1407546000000L, 1407553200000L));
	    timings.add(new Timing(null, "SZE", "2.345", Day.WEDNESDAY, 1407470400000L, 1407474000000L));
	    courses.add(new Course(null, "UGS", "12345", "Middle East Today", "303", timings));
		
	    // Create a relativeLayout for each day of the week. Set the layout params, so that each layout
	    // gets stretched the same amount by the parent linear layout.
	    RelativeLayout[] layouts = new RelativeLayout[Timing.Day.values().length];
	    for(int i = 0; i < layouts.length; i++) {
	    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
	    	params.setMargins(2, 0, 0, 0);

	    	RelativeLayout layout = new RelativeLayout(context);
	    	layout.setBackgroundResource(R.color.white);
	    	layout.setLayoutParams(params);
	    	layouts[i] = layout;
	    }
	    
	    // Used to determine the height/offset of events
	    int blockSizeMinutes = 15;
	    int pixelsPerBlock = 13;
	    
	    // Find the earliest class, for the sake of development we are going
	    // to assume 8am. However realistically, these times could change depending
	    // on the student. Hours are in military/24 hour time.
	    int startBlocks = 8 * (60 / blockSizeMinutes);
	    
	    // Add a textView for each timing of each course.
	    for(Course course : courses) {
	    	for(Timing timing : course.getTimings()) {
	    		// The height of the textView is equal to the number of blocks between the start and
	    		// end time multiplied by the number of pixels per block.
	    		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	    				LayoutParams.MATCH_PARENT,
	    				(int) (pixelsPerBlock * (timing.getEndTime() - timing.getStartTime()) / (60000 * blockSizeMinutes)));
	    		
	    		// Set the top margin as the number of blocks between the start time and startBlocks
	    		// multipled by the number of pixels per block.
	    		Calendar cal = Calendar.getInstance();
	    		cal.setTimeInMillis(timing.getStartTime());
	    		int timingBlocks = (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)) / blockSizeMinutes;
	    		params.setMargins(0, (int) (pixelsPerBlock * (timingBlocks - startBlocks)), 0, 0);
	    		
	    		// Generate a random color for the background of the text view. However we don't want this color
	    		// to be too light, or the white text will not show. Therefore, we limit the values to ones < 200
	    		Random rnd = new Random(); 
	    		int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));   
	    		
	    		TextView textView = new TextView(context);
	    		textView.setLayoutParams(params);
	    		textView.setBackgroundColor(color);
	    		textView.setGravity(Gravity.CENTER);
	    		
	    		// Set the text of the textView from Html, so that the different lines have different styles.
	    		textView.setText(Html.fromHtml(
	    				"<b>" + course.getDepartment() + " " + course.getNumber() + "</b><br />"+
	    				"<small>" + timing.getBuilding() + " " + timing.getRoom() + "</small>"));
	    		
	    		// Add the timing to the relative view for the correct day of the week
	    		layouts[timing.getDay().ordinal()].addView(textView);
	    	}
	    }
	    
	    // Add the list of relative layouts to the parent linear layout
	    LinearLayout schedule = (LinearLayout) findViewById(R.id.ScheduleView_schedule);
	    for(RelativeLayout layout : layouts)
	    	schedule.addView(layout);
	    
	    
//	    // Create a relative layout for each day of the week
//	    LinearLayout schedule = (LinearLayout) findViewById(R.id.ScheduleView_schedule);
//	    RelativeLayout[] layouts = new RelativeLayout[Timing.Day.values().length];
//	    for(int i = 0; i < Timing.Day.values().length; i++) {
//	    	RelativeLayout layout = new RelativeLayout(context);
//	    	layout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
//	    	schedule.addView(layout);
//	    }
//	    
//	    
//	    for(Course course : courses) {
//	    	for(Timing timing : course.getTimings()) {
//	    		TextView textView = new TextView(context);
//	    		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (pixelsPerMinute * (timing.getEndTime() - timing.getStartTime()) / 60000));
//	    		params.setMargins(0, (int) (pixelsPerMinute * (timing.getStartTime() - startTimeMillis) / 60000), 0, 0);
//	    		textView.setBackgroundResource(R.color.grey);
//	    		textView.setLayoutParams(params);
//	    		textView.setText(course.getDepartment() + " " + course.getNumber());
//	    		layouts[timing.getDay().ordinal()].addView(textView);
//	    	}
//	    }
//	    
//	    for(RelativeLayout layout : layouts)
//	    	schedule.addView(layout);
	}
}
