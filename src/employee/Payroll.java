package employee;

import java.util.HashMap;

import utils.Database;
import utils.Logger;

public class Payroll {
	
	private HashMap<Long, HashMap<String, String[]>> schedules, attendances;

	private Database db;
	private Logger log;
	
	public Payroll(Database db, Logger log) {
		this.db = db; this.log = log;
		
		schedules = log.getEmployeeScheduleMap(db.fetchAllEmployeeID());
	}
	
	
	// select * from logs where employee_id = 55210406001 and type="ATTENDACE" and date between "2021-04-07 00:00:00" AND "2021-04-08 00:00:00";
}
