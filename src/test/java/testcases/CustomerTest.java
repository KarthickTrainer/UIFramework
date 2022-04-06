package testcases;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import org.testng.annotations.Test;

import basedriver.BaseClass;
import pages.CustomersPage;

public class CustomerTest extends BaseClass {
	
	@Test
	
	public void searchCustomerByName() throws Exception {
		
		CustomersPage cp = new CustomersPage();
		try {
		 cp.searchCustomers("firstName","Tomasa Hermann");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
@Test
	
	public void searchCustomerByemail() throws Exception {
		
		CustomersPage cp = new CustomersPage();
		try {
		 cp.searchCustomers("email","tomasa.hermann41@example.com");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

@Test

public void searchCustomerTitleValidation() throws Exception {
	
	CustomersPage cp = new CustomersPage();
	try {
		if(driver.getTitle().contentEquals("FalseTitle")) {
		test.log(Status.PASS, "Title Matched");}
		else {
			test.log(Status.FAIL, "Title Not Matched");
		Assert.fail();}

	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
}

}
