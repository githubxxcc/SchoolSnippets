/*
 * Class SearchWithNegative
 * 
 * Author: Xu Chen(A0144702N)
 * 
 * Description: this class extends FixedLengthList. 
 * 				It adds items to a list and search an item from a list. 
 * 				If the item search is not found, add or move its negative to the front. 
 * 
 * 
 * Constructor: SearchWithNegative(int length)
 * 
 * Public Class Methods: 
 * 	1. boolean search(int key) @ Override 
 * 	2. boolean add(int key) @Override
 * 
 * Inherited Parameters:
 * 	1. int m_length
 * 	2. int[] m_list
 * 	3. int m_max
 *  
 */

package sg.edu.nus.cs2020;

public class SearchWithNegative extends FixedLengthList {
	
	/*****************
	 * Constructor
	 * @param length
	 ****************/
	public SearchWithNegative(int length){
		super(length);
	}
	
	/**
	 * Description: override add method. 
	 * If the negative of the key exists in the array, replace it with key 
	 */
	@Override
	public boolean add(int key){
		
		//sequential search for the negative of key and replace it
		for(int i =0; i<=m_max; i++){
			if(m_list[i] + key ==0){
				m_list[i] = key;
				return true;
			}
		}
		
		m_max++;
		if (m_max < m_length){
			m_list[m_max] = key;
			return true;
		}
		else{
			System.out.println("Error: list length exceeded.");
			return false;
		}
	}
	
	/**
	 * Description: override search method. It searches an item, and if it is not found, it will add the negative of
	 * 	of it to the front of the list. If found, it will move the item to the front as well.
	 * @param key
	 */
	@Override
	public boolean search(int key){ 
		int temp;
		
		//sequential search through the list
		for(int i = 0; i <= m_max; i++){
			
			//if found an item is either equal to the key or is the negative of the key,
			//bring it to the front of the list
			if(m_list[i] == key || (m_list[i]+ key==0)){
				temp = m_list[i];
				for(int j = i; j>0; j--){
					m_list[j] = m_list[j-1];
				}
				m_list[0] = temp;
				
				//if the found item is the key return true
				//if not, it means it is not in the list, return false
				if(temp == key) return true;
				else return false;
			}
		}
			
		//if neither the negative of the key or the key is found
		//add the negative of the key in to the list when the list is not full
		//update another negative value if the list is full. 
		//return false in the end
		if((m_max +1) >= m_length){// the list is full
			
			//search for negative item from the back, and update a negative value(which is currently least searched for).
			for(int j = m_max; j>=0;j--){
					if(m_list[j]<0) m_list[j] = -1*key;// do nothing if no negative number is found
			}
		}
		else{// the list is not full, update the m_max and add in the negative key
			//move each item backwards
			for(int j = m_max; j>=0;j--){
				m_list[j+1] = m_list[j];
			}
			//update the first item
			m_list[0]= -1*key;
			m_max++;
		}
				
		return false;
			
	}
		
		
		
}
	

