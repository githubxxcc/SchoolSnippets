/*
 * Class MyFastList
 * 
 * Author: Xu Chen(A0144702N)
 * 
 * Description: this class extends FixedLengthList, with additional three private param. 
 * 				It supposes to search an item from a list faster. 
 * 
 * Assumption: 
 * 	1. The largest int added will not be so big that it exceeds the maximum size allowed for an list.
 * 	2. Searching effiiency is prior to space usage efficiency
 * 	3. The add methods will always and only execute before the search methods. 
 * 
 * 
 * Private Param:
 * 	1. int maxNum
 * 	2. boolean[] memoryList
 * 	3. boolean memoryCreated;
 * 
 * Constructor: MyFastList(int length)
 * 
 * Public Class Methods: 
 * 	1. boolean add(int key) @Override
 * 	2. boolean search(int key) @ Override 
 * 
 * Inherited Parameters:
 * 	1. int m_length
 * 	2. int[] m_list
 * 	3. int m_max
 * 
 * Acknowledgement: 
 * 	Special thanks to comments on NB, Sheng Xuan, and Zhang Hanming for inspiration. 
 */

// this class is part of the sg.edu.nus.cs2020 package
package sg.edu.nus.cs2020;

public class MyFastList extends FixedLengthList{
	
	/************************
	 * Class Member Variables
	 ************************/
	// an int that store the maximum value of the numbers stored in the list
	private int maxNum = 0;
	
	//an boolean list that remembers items being found in previous searches. 
	private boolean[] memoryList = null;
	
	//an boolean param indicates if a memoryList has been created. 
	private boolean memoryCreated = false;
	
	
	/***********************
	 * Constructor: builds a MyFastList.
	 * @param length
	 ***********************/
	public MyFastList(int length){
		super(length);
	}
	
	
	/***********************
	 * Public Class Methods
	 ***********************/
	
	/**
	 * this method overrides the add(int key) from FixedLengthList class.
	 * It adds an int into an int array, and updates the the maxNum of the list
	 * @param : int key
	 */
	@Override
	public boolean add(int key){
		//update the maxNum if the key is larger than the current maxNum.
		if(maxNum < key) {
			maxNum = key;
		}
		return super.add(key);
	}
	
	/**
	 * this method overrides the search(int key) from FixedLengthList class.
	 * It searches the key in the list m_list, and updates the memoryList.
	 * @param int key
	 */
	@Override
	public boolean search(int key){
		
		//creates an memoryList if it has not been created before search.(It is assumed all the items are added) 
		if(!memoryCreated){
		memoryList = new boolean[maxNum+1];
		memoryCreated = true;
		}
		
		//directly return true without sequential search if the key has been searched before.
		if (memoryList[key]) return true;
		
		//search from the beginning of the list
		for (int i=0; i<=m_max; i++){
			//update the memoryList if the key is found in the list, by making the number key item in the memoryList true.
			if (m_list[i] == key){ 
				memoryList[key] = true;
				return true;
			}
		}
		//if it is not found in the list, return false. 
		return false;
	}

}
