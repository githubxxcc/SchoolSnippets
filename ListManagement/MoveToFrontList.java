/*
 * Class MoveToFrontList
 * 
 * Author: Xu Chen(A0144702N)
 * 
 * Description: this class implements FixedLengthList.  
 * 				It supposes to search an item from a list faster. 
 * 
 * Constructor: MoveToFrontList(int length)
 * 
 * Public Class Methods: 
 * 	1. boolean search(int key) @ Override 
 * 
 * Inherited Parameters:
 * 	1. int m_length
 * 	2. int[] m_list
 * 	3. int m_max
 * 
 * Acknowledgement: 
 * 	Special thanks to comments on NB, Sheng Xuan, and Zhang Hanming for inspiration. 
 */


//this class is part of the sg.edu.nus.cs2020 package 
package sg.edu.nus.cs2020;

public class MoveToFrontList extends FixedLengthList {
	
	/**
	 * Constructor: builds a MoveToFrontList
	 */
	public MoveToFrontList(int length){
		super(length);
	}
	
	/**
	 * override search(int key)
	 */
	@Override 
	public boolean search(int key){
		
		int temp;
		
		//linear search to find the key
		for(int i = 0; i <= m_max; i++){
			
			if(m_list[i] == key) {
				//once found, move it to the front of the list and move the intervening part back
				temp = m_list[i];
				
				for(int j = i; j>0; j--){
					m_list[j] = m_list[j-1];
				}
				
				//set the first as the key
				m_list[0] = temp;
				
				return true;
			}
		}
		//return false if not found
		return false;
	}
	
}
