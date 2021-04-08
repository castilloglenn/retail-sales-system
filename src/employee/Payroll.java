package employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import utils.Database;
import utils.Logger;
import utils.Utility;

public class Payroll {
	
	private final String BR = "\n";
	private final String ENTRY = "  • ";
	private StringBuilder format;

	private HashMap<Long, HashMap<String, String[]>> schedules;
	private String previousCutoff, currentCutoff;

	private Database db;
	private Logger log;
	private Utility ut;
	
	public Payroll(Database db, Logger log, Utility ut) {
		this.db = db; this.log = log; this.ut = ut;
		
		schedules = log.getEmployeeScheduleMap(db.fetchAllEmployeeID());
		
		setCutoffs();
		generatePayroll();
	}
	
	private void setCutoffs() {
		Calendar now = Calendar.getInstance();
		int currentDay = now.get(Calendar.DAY_OF_MONTH);
		
		if (currentDay < 15) {
			now.add(Calendar.DAY_OF_MONTH, -(currentDay - 1));
		} else {
			now.add(Calendar.DAY_OF_MONTH, -(currentDay - 15));
		}
		
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		
		Date dateNow = now.getTime();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		previousCutoff = sdf.format(dateNow);

		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		
		int previousCutoff = now.get(Calendar.DAY_OF_MONTH);
		if (previousCutoff >= 15) {
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.add(Calendar.MONTH, 1);
			now.add(Calendar.DAY_OF_MONTH, -1);
			Date dateCutoff = now.getTime();
			currentCutoff = sdf.format(dateCutoff);
		} else {
			now.add(Calendar.DAY_OF_MONTH, 14);
			Date dateCutoff = now.getTime();
			currentCutoff = sdf.format(dateCutoff);
		}
	}
	
	private void generatePayroll() {
		format = new StringBuilder(
			previousCutoff + "-" + currentCutoff + BR + BR 
			+ "Payroll Report:" + BR);
		for (long employee_id : schedules.keySet()) {
			HashMap<String, String[]> daySched = schedules.get(employee_id);
			if (daySched.isEmpty()) {
				Object[] emp = db.fetchEmployeeByID(employee_id);
				String fullName = emp[1] + " " + emp[2] + " " + emp[3];
				format.append(ENTRY + employee_id + " " + fullName + " has no schedule." + BR);
			} else {
				for (String date : daySched.keySet()) {
					String[] inAndOuts = daySched.get(date);
					for (String log : inAndOuts) {
						System.out.println(employee_id + " " + date + " " + log);
					}
				}
			}
		}
	}
	
}
