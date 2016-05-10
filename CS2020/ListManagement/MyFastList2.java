/*
 * Class MyFastList2
 * 
 * Author: Xu Chen(A0144702N)
 * 
 * Description: this class extends FixedLengthList. 
 * 				It adds items to a list and search an item from a list. 
 * 
 * 
 * Constructor: MyFastList2(int length)
 * 
 * Public Class Methods: 
 * 	1. boolean search(int key) @ Override 
 * 
 * Inherited Class Methods:
 * 	1. boolean add
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

public class MyFastList2 extends FixedLengthList  {
	
	/***************
	 * Constructor:builds a MyFastClass2
	 * @param length
	 */
	public MyFastList2(int length){
		super(length);
		
	}
	
	
	/********************
	 * Public Class Methods
	 ********************/
	
	/**
	 * this method overrides the search method from its parent class. 
	 * It searches an item in the list by using sequential search. 
	 * If the item is found, it will move the item towards the beginning, but not the first, by halving its 
	 * distance from the beginning. 
	 * 
	 * @param: int key
	 */
	@Override
	public boolean search(int key){		
		//declare temp, which will store the value of the item found in the list. 
		int temp;
		
		//using sequential search to find the key. returns true when an item is found. 
		for(int i = 0; i<= m_max;i++){
			if(m_list[i] == key){
				temp = m_list[i];
				
				//move half of the items before the found item back
				for(int j = i; j> i/2; j--){
					m_list[j] = m_list[j-1];
				}
				
				//update the first item of the list intervened 
				m_list[i/2] = temp;
				
				return true;
			}
		}
		
		//return false if key not found.
		return false;
	}
		
}
