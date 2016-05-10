/*
 *Test on SearchWithNegative class
 *
 * Author: Xu Chen(A0144702N)
 */
package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchWithNegativeTest {
	
	SearchWithNegative getList(int length){
		return new SearchWithNegative(length);
		
	}

	@Test
	public void test() {
		SearchWithNegative testObj = getList(10);
		testObj.add(10);
		assertEquals("AddTest", "10",testObj.toString());
		testObj.add(12);
		assertEquals("AddTest", "10 12", testObj.toString());
	}
	
	@Test
	public void searchTest1(){
		SearchWithNegative testList = getList(6);
		testList.add(0);
		testList.add(100);
		testList.add(1000);
		testList.add(13);
		testList.add(4);
		
		boolean result = testList.search(2500);
		
		String expected = "-2500 0 100 1000 13 4";
		assertEquals("SearchTest1", false, result);
		assertEquals("SearchTest1", expected, testList.toString());
	}
	
	@Test
	public void searchTest2()
	{
		SearchWithNegative testList = getList(6);
		testList.add(3);
		testList.add(15);
		testList.add(25);
		testList.add(1000);
		testList.add(1500);
		
		boolean result = testList.search(800);
		String expected1 = "-800 3 15 25 1000 1500";
		assertEquals("SearchTest2", false, result);
		assertEquals("SearchTest2", expected1, testList.toString());
		
		boolean result2 = testList.search(15);
		String expected2 = "15 -800 3 25 1000 1500";
		assertEquals("SearchTest2", true, result2);
		assertEquals("SearchTest2", expected2, testList.toString());
		
		boolean result3 = testList.search(800);
		String expected3 = "-800 15 3 25 1000 1500";
		assertEquals("SearchTest3", false, result3);
		assertEquals("SearchTest3", expected3, testList.toString());
	}
	
	@Test
	public void errorTest1()
	{
		try
		{
			SearchWithNegative testList = getList(1);
			testList.add(0);
			testList.add(1);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			assertTrue(false);
		}
		catch(NullPointerException e)
		{
			assertTrue(false);
		}
		catch(Exception e)
		{
		}
	}

}
