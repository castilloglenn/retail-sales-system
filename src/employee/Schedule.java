package employee;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Database;
import utils.LogConstants;
import utils.Logger;
import utils.Utility;

public class Schedule {
	
	private final String BR = "\n";
	private final String ENTRY = "  • ";
	private String format;
	
	private String date;
	private ArrayList<Long> managers, seniorSupers, juniorSupers, 
		cashiers, inventoryClerks, storeAssistants;
	private HashMap<Long, String[]> timeManagers, timeSeniorSupers, 
		timeJuniorSupers, timeCashiers, timeInventoryClerks, timeStoreAssistants;
	
	private Database db;
	private Logger log;
	private Utility ut;
	
	public Schedule(Database db, Logger log, Utility ut) {
		this.db = db; this.log = log; this.ut = ut;
		managers = new ArrayList<>();
		seniorSupers = new ArrayList<>();
		juniorSupers = new ArrayList<>();
		cashiers = new ArrayList<>();
		inventoryClerks = new ArrayList<>();
		storeAssistants = new ArrayList<>();
		
		timeManagers = new HashMap<>();
		timeSeniorSupers = new HashMap<>();
		timeJuniorSupers = new HashMap<>();
		timeCashiers = new HashMap<>();
		timeInventoryClerks = new HashMap<>();
		timeStoreAssistants = new HashMap<>();
		
		format = format();
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public boolean log(long manager_id) {
		logEach(managers, timeManagers);
		logEach(seniorSupers, timeSeniorSupers);
		logEach(juniorSupers, timeJuniorSupers);
		logEach(inventoryClerks, timeInventoryClerks);
		logEach(cashiers, timeCashiers);
		logEach(storeAssistants, timeStoreAssistants);
		
		log.newLog(manager_id, LogConstants.SCHEDULE, LogConstants.MAIN, format);
		ut.writeFile(
			"Schedule for " + date.substring(0, 2) + "-" + date.substring(3, 5) + "-"
				+ date.substring(6), format);
		return true;
	}
	
	private void logEach(ArrayList<Long> ids, HashMap<Long, String[]> scheds) {
		if (!ids.isEmpty()) {
			for (long id : ids) {
				String[] sched = scheds.get(id);
				String message = sched[0] + "," + sched[1];
				log.newLog(id, LogConstants.SCHEDULE, LogConstants.SUB, message);
			}
		}
	}
	
	public String format() {
		StringBuilder sb = new StringBuilder();
		sb.append(((date == null) ? "" : date) + BR + BR);
		
		sb.append(elaborate("Managers", managers, timeManagers));
		sb.append(elaborate("Senior Supervisors", seniorSupers, timeSeniorSupers));
		sb.append(elaborate("Junior Supervisors", juniorSupers, timeJuniorSupers));
		sb.append(elaborate("Inventory Clerks", inventoryClerks, timeInventoryClerks));
		sb.append(elaborate("Cashiers", cashiers, timeCashiers));
		sb.append(elaborate("Store Assistants", storeAssistants, timeStoreAssistants));
		
		format = sb.toString();
		return format;
	}
	
	private String elaborate(String title, ArrayList<Long> id, HashMap<Long, String[]> values) {
		if (id.isEmpty()) return title + BR + ENTRY + BR;
		
		StringBuilder sb = new StringBuilder(title + BR);
		for (long e : id) {
			Object[] details = db.fetchEmployeeByID(e);
			String name = details[1] + " " + details[2] + " " + details[3];
			String[] sched = values.get(e);
			sb.append(ENTRY + sched[0] + "-" + sched[1] + " @ " + name + BR);
		}
		
		return sb.toString();
	}
	
	public String check() {
		String[] errors = new String[5];
		String error = "Please review the following:" + BR;
		boolean flagged = false;
		
		if (date == null) errors[1] = "• Date cannot be empty." + BR;
		if (managers.isEmpty() && seniorSupers.isEmpty()) 
			errors[2] = "• No Manager/Senior Supervisor found in this schedule." + BR;
		if (cashiers.isEmpty()) errors[3] = "• There must be at least 1 cashier." + BR;
		if (inventoryClerks.isEmpty()) errors[4] = "• There must be at least 1 inventory clerk." + BR;
		
		for (String s : errors) {
			if (s != null) {
				flagged = true;
				error += s;
			}
		}

		return (flagged) ? error : null;
	}
	
	public boolean add(long id, String in, String out) {
		String level = Long.toString(id).substring(1, 2);
		switch (Integer.parseInt(level)) {
			case 5:
				if (managers.contains(id)) return false;
				managers.add(id);
				timeManagers.put(id, new String[] {in, out});
				break;
			case 4:
				if (seniorSupers.contains(id)) return false;
				seniorSupers.add(id);
				timeSeniorSupers.put(id, new String[] {in, out});
				break;
			case 3:
				if (juniorSupers.contains(id)) return false;
				juniorSupers.add(id);
				timeJuniorSupers.put(id, new String[] {in, out});
				break;
			case 2:
				if (cashiers.contains(id)) return false;
				cashiers.add(id);
				timeCashiers.put(id, new String[] {in, out});
				break;
			case 1:
				if (inventoryClerks.contains(id)) return false;
				inventoryClerks.add(id);
				timeInventoryClerks.put(id, new String[] {in, out});
				break;
			case 0:
				if (storeAssistants.contains(id)) return false;
				storeAssistants.add(id);
				timeStoreAssistants.put(id, new String[] {in, out});
				break;
		}
		return true;
	}
	
	public boolean remove(long id) {
		String level = Long.toString(id).substring(1, 2);
		switch (Integer.parseInt(level)) {
			case 5:
				if (!managers.contains(id)) return false;
				managers.remove(id);
				timeManagers.remove(id);
				break;
			case 4:
				if (!seniorSupers.contains(id)) return false;
				seniorSupers.remove(id);
				timeSeniorSupers.remove(id);
				break;
			case 3:
				if (!juniorSupers.contains(id)) return false;
				juniorSupers.remove(id);
				timeJuniorSupers.remove(id);
				break;
			case 2:
				if (!cashiers.contains(id)) return false;
				cashiers.remove(id);
				timeCashiers.remove(id);
				break;
			case 1:
				if (!inventoryClerks.contains(id)) return false;
				inventoryClerks.remove(id);
				timeInventoryClerks.remove(id);
				break;
			case 0:
				if (!storeAssistants.contains(id)) return false;
				storeAssistants.remove(id);
				timeStoreAssistants.remove(id);
				break;
		}
		return true;
	}
	
}
