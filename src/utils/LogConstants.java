package utils;

public interface LogConstants {

	// Log Types
	static int PAYROLL = 0;
	static int PRODUCT_INQUIRY = 1;
	static int ATTENDANCE = 2;
	static int LATE_ABSENT = 3;
	static int LOST_PASSWORD = 4;
	static int SCHEDULE = 5;
	static int PRODUCT_REQUESTS = 6;
	static int DELIVERY = 7;
	static int PULL_OUT = 8;
	static int SYSTEM_LOG = 9;
	
	// Log Status
	static int UNREAD = 1;
	static int READ = 2;
	static int APPROVED = 3;
	
	// Message Part
	static int MAIN = 0;
	static int SUB = 1;
	
}
