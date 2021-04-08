package employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import main.Main;
import utils.Database;
import utils.LogConstants;
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
	
	public Payroll(Database db, Logger log, Utility ut, long manager_id) {
		this.db = db; this.log = log; 
		schedules = log.getEmployeeScheduleMap(db.fetchAllEmployeeID());
		setCutoffs();
		generatePayroll();
		log.newLog(manager_id, LogConstants.PAYROLL, LogConstants.MAIN, format.toString());
		String prev = previousCutoff.substring(0, 10).replace('/', '-');
		String curr = currentCutoff.substring(0, 10).replace('/', '-');
		ut.writeFile(
			"Payroll from " + prev + " to " 
			+ curr, format.toString());
		JOptionPane.showMessageDialog(null, 
			"Successfully generated new payroll!",
			"Payroll Generated | " + Main.SYSTEM_NAME, 
			JOptionPane.INFORMATION_MESSAGE);
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
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
		double total_pay = 0;
		
		format = new StringBuilder(
			previousCutoff.substring(0, 10) + "-" + currentCutoff.substring(0, 10) + BR + BR 
			+ "Payroll Report:" + BR);
		for (long employee_id : schedules.keySet()) {
			Object[] emp = db.fetchEmployeeByID(employee_id);
			String fullName = emp[1] + " " + emp[2] + " " + emp[3];
			
			HashMap<String, String[]> daySched = schedules.get(employee_id);
			if (daySched.isEmpty()) {
				format.append(ENTRY + employee_id + " " + fullName + " has no schedule." + BR);
			} else {
				int emp_sched = daySched.size();
				double emp_attendance = 0;
				double basic = Double.parseDouble(emp[5].toString()) / 2;
				double income = Double.parseDouble(emp[7].toString());
				double contribution = Double.parseDouble(emp[8].toString()) / 2;
				double deductions = Double.parseDouble(emp[9].toString());
				
				for (String date : daySched.keySet()) {
					daySched.get(date);
					
					String[] result = log.parseAttendanceDateTime(employee_id, date);
					if (result != null) {
						emp_attendance++;
					}
				}
				
				double attendanceRatio = emp_attendance / emp_sched;
				double netPay = (basic * attendanceRatio) - (contribution - deductions);
				
				format.append(ENTRY + employee_id + " " + fullName 
					+ " Schedule Days: " + emp_sched
					+ " Attendance: " + String.format("%,.0f", emp_attendance)
					+ " Late/Absent Fees: " + String.format("Php %,.2f", deductions)
					+ " Contributions: " + String.format("Php %,.2f", contribution)
					+ " Total Gross Pay: " + String.format("Php %,.2f", basic + income)
					+ " Total Net Pay: " + String.format("Php %,.2f", netPay)
					+ BR);
				
				total_pay += (basic * attendanceRatio);
			}
		}
		
		format.append(BR + "Total Amount to be paid to employees:" + String.format("Php %,.2f", total_pay));
	}
	
}
