/*
 * class HerbertLog 
 * 
 * Updated By: Xu Chen(A0144702N)
 * 
 * Description: updated methods- calculateSalary, calculateMinimumWork.
 */

package sg.edu.nus.cs2020;

//  Import file handling classes
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Stack;

/**
 * class HerbertLog
 * The HerbertLog class records the jobs worked by Herbert, and
 * the wages paid to Herbert, over the last period of employment.
 * The constructor opens the specified log-file, and the get(.) method
 * returns records from the file.
 * 
 */
public class HerbertLog {
		
	/**
	 *  Public static final constants
	 */
	
	// Separator character used in the database file
	public static final String SEP = ":";
	// Length of each record in the database
	public static final int rLength = 18;
	// Padding character for database file
	public static final char PADDING = '.';

	/**
	 * Private state for the HerbertLog
	 */
	// Filename where the database can be found
	private String m_name = null;
	// Variable that points to the database, once opened
	private File m_file = null;
	// Variable for reading from the database file
	private RandomAccessFile m_inRAM = null;
	// Size of the database: number of available records
	private long m_numMinutes = 0;
	
	/**
	 * Debugging information
	 */
	// Number of "get" operations performed on the database
	// Note this is primarily for debugging.
	protected long m_numGets = 0;
	
	/**
	 * Constructor 
	 * @param filename File where the database can be found.
	 * The specified file must exist, and must contain records
	 * in the proper format. 
	 **/
	HerbertLog(String fileName){
		// Save the filename
		m_name = fileName;
		// Next, we open the file
		try {
			// Open the file
			m_file = new File(m_name);
			m_inRAM = new RandomAccessFile(m_file, "r");
			
			// Calculate the number of records in the database by
			// dividing the number of characters by the length of each record
			long numChars = m_inRAM.length();
			m_numMinutes = numChars/rLength;
		} catch (IOException e) {
			System.out.println("Error opening file: " + e);
		}
	}
	
	/**
	 * size
	 * @return the number of records in the database
	 */
	public long numMinutes(){
		return m_numMinutes;
	}

        /**
	 * numGets : primarily for debugging
         * @return number of times get has been called
         */
         public long numGets(){
	         return m_numGets;
         }

	/**
	 * get
	 * @param i specified the record number to retrieve, starting from 0
	 * @return the specified record, if it exists, or null otherwise
	 */
	public Record get(long i){
		
		// Increment the number of "get" operations
		m_numGets++;
		
		// Check for errors: if i is too large or too small, fail
		if (i > numMinutes()) return null;
		if (i < 0) return null;
		
		// Retrieve the proper record
		try {
			// First, calculate the offset into the file, and seek to that location
			long numChars = i*rLength;			
			m_inRAM.seek(numChars);
			
			// Next, read in rLength bytes
			// Recall that rLength is the length of one record
			byte[] entry = new byte[rLength];
			m_inRAM.read(entry);
			
			// Now, convert the string to a record.
			// Convert it to a string...
			String line = new String(entry);
			// .. parse the string using the record separator
			String[] tokens = line.split(SEP);
			// Every record should have 2 or 3 components
			assert(tokens.length==2 || tokens.length==3);
			// The first token is the name
			String name = tokens[0];
			// The second token is the height
			int height = Integer.parseInt(tokens[1]);
			return new Record(name, height);
			
		} catch (IOException e) {
			System.out.println("Error getting data from file: " + e);
		}
		// If the record wasn't found, for any reason, return null
		return null;
	}
	
	
	/**
	 * this method calculates the amount of salary Herbert makes. 
	 * 
	 * Description:
	 * 	1. it checks if the first and the last line of the log are the same(meaning working for one employer only)
	 * If the same, just return the last line of the log.---- Else it will search backwards to find out the the maximum
	 * salary for previous employers 
	 * 	
	 * 	2. It then conducts a binary search to find out the last line of each employer, and add the income to the totalincome
	 * 
	 * 	3. the while loop will break if it reaches the end of the log. 
	 * 
	 * 
	 * @return total salary
	 */
	public int calculateSalary(){
		
		/********************
		 * Method Variables
		 ********************/
		
		//the start and the end index of the file
		long startIndex = 0;
		long lastIndex = numMinutes()-1;
		
		//the target index which will be checked against the start index line
		long targetIndex= (lastIndex+startIndex)/2;
		
		//total salary
		int salary = 0;
		
		//record instances that will be used to store the line
		Record startRecord = null;
		Record lastRecord = null;
		Record targetRecord = null;
		
		//Record stack used to store and retrieve a specific record that has been gotten. 
		Stack<Record> record = new Stack<Record>();
		
		//Index stack which stores the corresponding index of the record in the above stack. 
		Stack<Long> indexRecord = new Stack<Long>();
		
		/************************
		 * Method implementation
		 **********************/
		
		//get the start and the end of the list
		startRecord = get(startIndex);
		lastRecord = get(lastIndex);
		
		//compare their names, if the same return the last line's wage, else continue. 
		if(startRecord.getName().equals(lastRecord.getName())) return lastRecord.getWages();
		
		//while loop exit condition: when the entire list is searched and compared.  
		while(startIndex != targetIndex){
			
			//get the record for the target index
			targetRecord = get(targetIndex);
			
			//Layer 1 control flow: if the startIndex and targetIndex is next to each other
			if(startIndex+1 == targetIndex){
				
				//Layer 2.1 control flow:
				//if the two records have different names, it indicates a change of employer.
				//it will then add the wages of the startRecord into the total salary.
				if(!targetRecord.getName().equals(startRecord.getName())){
					salary += startRecord.getWages();
				}
				//Layer 2.1 ends

				//Update the startIndex to examine the rest of the list. 
				startIndex = targetIndex;
				
				//Update the startRecord as well
				startRecord = new Record(targetRecord);
				
				//Layer 2:
				//To expand the binary search portion as the startIndex is next to the lastIndex.
				if(startIndex+1 == lastIndex){
					
					//Layer 3:
					//get the lastRecord, lastIndex from the stack if the stack is not empty so that continuing search the rest of the list.
					if(!record.empty()){
						lastRecord = record.pop();
						lastIndex = indexRecord.pop();
					}
					
					//if the stack is empty, it means the lastRecord is the last line of the list already, and the startRecord is the second last. 
					else{
						//Layer 4:
						//if the last two are the same, add the lastRecord's wage
						if(startRecord.getName().equals(lastRecord.getName())){
							salary += lastRecord.getWages();
						}
						//if the last two are different, add both. 
						else{
							salary += lastRecord.getWages()+startRecord.getWages();
						}
						
						//Layer 4 ends
					}
					
					//Layer 3 ends
				}
				
				//Layer 2 ends
			}
			//Layer 1 else condition: if the startIndex and targetIndex are not next to each other
			else{
				
				//Layer 2:
				//if the targetIndex line and the startIndex line indicate the same employer, update the start as the target
				if(startRecord.getName().equals(targetRecord.getName())){
					startIndex = targetIndex;
					startRecord = targetRecord;
				}
				//Layer 2 else condition: if the startIndex and targetIndex are not the same employer.
				else{
					//store the last to the stack
					lastRecord = record.push(lastRecord);
					lastIndex = indexRecord.push(lastIndex);
					
					//narrow the portion by update the last as the target
					lastRecord = targetRecord;
					lastIndex = targetIndex;
				}
				
				//Layer 2 ends
			}
			
			//Layer 1 ends
			
			//update the targetIndex to continue examining the list.
			targetIndex = (lastIndex+startIndex)/2;
		}
			
		return salary;
		
	}
		

	
	/**
	 * this method can be divided into two parts:
	 * 	1. it conducts binary search as the previous calculateSalary method to find out the 
	 * minimum salary Herbert can earn, and records the index of first line of each employer. 
	 * 	2. it then increases the minute of work Herbert does and updates the minIncome, which
	 * will be compared to the goalIncome parameter. 
	 * 	3. it will return -1 if Herbert cannot earn enough SB even if he works for the maximum time. 
	 * 	
	 * @param goalIncome
	 * @return the min minute Herbert has to work 
	 */
	public int calculateMinimumWork(int goalIncome){
				
		/********************
		 * Method Variables
		 ********************/
		
		//the start and the end index of the file
		long startIndex = 0;
		long lastIndex = numMinutes()-1;
		
		//the target index which will be checked against the start index line
		long targetIndex= (lastIndex+startIndex)/2;
		
		//total salary
		int minIncome = 0;
		
		//record instances that will be used to store the line
		Record startRecord = null;
		Record lastRecord = null;
		Record targetRecord = null;
		
		//Record stack used to store and retrieve the first line of each employer. 
		Stack<Record> recordList = new Stack<Record>();
		
		//Index stack which stores the corresponding index of the record in the above stack. 
		Stack<Long> indexList = new Stack<Long>();
		
		// Stacks that are used store the lastRecord during binary search. 
		Stack<Record> lastRecordList = new Stack<Record>();
		Stack<Long> lastIndexList = new Stack<Long>();
		
		// the index of a specific employer, 0 as the first employer in the log.
		int indexEmploy = 0;

		//minimum time Herbet has to work
		int minMinute = 1;
		
		//maximum time Herbet is employed. 
		long maxMinute = 0;
		
		/************************
		 * Method implementation
		 **********************/
		
		//First part. 
		
		//get the start and the end of the list
		startRecord = get(startIndex);
		lastRecord = get(lastIndex);
		
		//record the first line of the log by adding the wages and the index
		recordList.add(startRecord);
		indexList.add((long) 0);
		minIncome += startRecord.getWages();
		
		//while loop exit condition: when the entire list is searched and compared.  
		while(startIndex != targetIndex){
			
			//get the record for the target index
			targetRecord = get(targetIndex);
			
			//Layer 1 control flow: if the startIndex and targetIndex is next to each other
			if(startIndex+1 == targetIndex){
				
				//Layer 2.1 control flow:
				//if the two records have different names, it indicates a change of employer.
				//it will then add the wages of the targetRecord into the total salary.
				if(!targetRecord.getName().equals(startRecord.getName())){
					minIncome += targetRecord.getWages();
					indexList.add(targetIndex);
					recordList.add(targetRecord);
				}
				//Layer 2.1 ends

				//Update the startIndex to examine the rest of the list. 
				startIndex = targetIndex;
				
				//Update the startRecord as well
				startRecord = new Record(targetRecord);
				
				//Layer 2:
				//To expand the binary search portion as the startIndex is next to the lastIndex.
				if(startIndex+1 == lastIndex){
					
					//Layer 3:
					//get the lastRecord, lastIndex from the stack if the stack is not empty so that continuing search the rest of the list.
					if(!lastRecordList.empty()){
						lastRecord = lastRecordList.pop();
						lastIndex = lastIndexList.pop();
					}
					
					//if the stack is empty, it means the lastRecord is the last line of the list already, and the startRecord is the second last. 
					else{
						//Layer 4:
						//if the last two are the same, add the lastRecord's wage
						if(!startRecord.getName().equals(lastRecord.getName())){
							indexList.add(lastIndex);
							recordList.add(lastRecord);
							minIncome += lastRecord.getWages();
						}
						
						//Layer 4 ends
					}
					
					//Layer 3 ends
				}
				
				//Layer 2 ends
			}
			//Layer 1 else condition: if the startIndex and targetIndex are not next to each other
			else{
				
				//Layer 2:
				//if the targetIndex line and the startIndex line indicate the same employer, update the start as the target
				if(startRecord.getName().equals(targetRecord.getName())){
					startIndex = targetIndex;
					startRecord = targetRecord;
				}
				//Layer 2 else condition: if the startIndex and targetIndex are not the same employer.
				else{
					//store the last to the stack
					lastRecord = lastRecordList.push(lastRecord);
					lastIndex = lastIndexList.push(lastIndex);
					
					//narrow the portion by update the last as the target
					lastRecord = targetRecord;
					lastIndex = targetIndex;
				}
				
				//Layer 2 ends
			}
			
			//Layer 1 ends
			
			//update the targetIndex to continue examining the list.
			targetIndex = (lastIndex+startIndex)/2;
		}
		
		//Second Part
		
		// calculate the maximum minute Herbert can work. 
		for(int i = 0; i< indexList.size()-1;i++){
			maxMinute = Math.max(maxMinute, indexList.get(i+1)-indexList.get(i));
		}
		
		//update the minIncome Herbert earns when he works longer. 
		//while loop exit condition: Herbert earns enough income OR min working time exceeds max working time possible
		while(minIncome < goalIncome && minMinute <= maxMinute){
			//increasing working time if we start from the first employer
			if(indexEmploy == 0) minMinute++;
			
			//add the difference Herbert can earn if he works one more minute for a specific employer. 
			// If: 
			//	(It is not the last employer AND Herbert can work one more minute for the current employer without exceeding the maximum working time he can work for this employer)
			//  OR
			//  (It is the last employer AND Herbert can work one more minute for the current without exceeding the maximum line
			if((indexEmploy < indexList.size()-1 && indexList.get(indexEmploy)+minMinute-1 < indexList.get(indexEmploy+1))
					|| (indexEmploy == indexList.size()-1 && indexList.get(indexEmploy)+minMinute-1 <= m_numMinutes-1)){
				// get the Record if Herbert work one more minute for the current employer
				Record tempRecord = get(indexList.get(indexEmploy)+minMinute-1);
				
				// update the minIncome by adding the different as a result of working one more minute.
				minIncome +=  tempRecord.getWages() - recordList.get(indexEmploy).getWages();
				
				// update the recordList where stores the current line Herbert works for a specific employer
				tempRecord = recordList.set(indexEmploy, tempRecord);
			}
			
			//move on to the next employer
			indexEmploy++;
			
			// if all employers have checked, move to the first again. 
			if(indexEmploy == indexList.size()) indexEmploy = 0;
		}
		
		//return
		if(minMinute <= maxMinute) return minMinute;
		else return -1;
	}
		


		/*
		 * Below are another version of search code I wrote using division. However, this is slower and less effective 
		 * than the binary search when handling the large size file, but can be more effective when the file is small with a proper choice of the divider. 
		
		long remainder = m_numMinutes % 5;
		int indexEmploy = 0;
		LinkedList<Record> recordList = new LinkedList<Record>();
		LinkedList<Long> indexList = new LinkedList<Long>();g
		
		Record record1 = null;
		Record record2 = null;
		Record tempRecordHead;
		Record tempRecordTail;
		long tempIndex;
		int minMinute = 1;
		
		long maxMinute = 0;
		
		int minIncome = 0;
		
		record1 = get(0);
		record2 = get(1);
		recordList.add(record1);
		indexList.add((long) 0);
		minIncome += record1.getWages();
		
		if((int)remainder != 0){
		for(long i = 0; i<remainder-1; i++){
			record2 = get(i+1);
			if(!record1.getName().equals(record2.getName())){
				recordList.add(record2);
				indexList.add(i+1);//?
				minIncome += record2.getWages();
			}
			record1 = new Record(record2);
			}
		}
	
		
		for(long i =  remainder+4;i< m_numMinutes; i+=5){
			
			record2 = get(i);
			tempRecordHead = new Record(record1);
			tempRecordTail = new Record(record1);
			
			tempIndex = i-5;
			
			while(!record2.getName().equals(tempRecordTail.getName())){
				tempIndex++;
				tempRecordHead = get(tempIndex);
				
				if(!tempRecordHead.getName().equals(tempRecordTail.getName())){
					recordList.add(tempRecordHead);
					indexList.add(tempIndex);
					minIncome += tempRecordHead.getWages();
				}
				
				tempRecordTail = new Record(tempRecordHead);
			}
			
			record1 = new Record(record2);
			}
		
		*/

	public static void main(String[] args){
		
	}
}
