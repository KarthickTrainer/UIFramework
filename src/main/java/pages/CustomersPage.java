package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import utils.selenium.Utils;

public class CustomersPage extends Utils {
	
	@FindBy(xpath="//div[contains(@class,'search-and-filter')]//i[contains(text(),'filter_list')]")
	WebElement filtersButton;
	
	@FindBy(xpath="//div//span[text()='Select a field']")
	WebElement selectFieldTextbox;
	
	@FindBy(xpath="//li[@class='c-beta-select__list-search']//input")
	WebElement searchBox;
	
	@FindBy(xpath="(//li[contains(@class,'select__list')])[2]")
	WebElement selectFirstItem;
	
	@FindBy(xpath="//*[contains(@class,'c-beta-filter-entry__value ember-view')]//input")
	WebElement searchtext;
	
	@FindBy(xpath="//*[contains(@class,'c-beta-filter-footer__apply')]")
	WebElement applyFiltersButton;
	
	@FindBy(xpath="//table//tbody//tr[1]/td[5]")
	WebElement emailfieldinTable;
	
	@FindBy(xpath="//table//tbody//tr[1]/td[2]")
	WebElement namefieldinTable;
	
	public CustomersPage() {
		PageFactory.initElements(Utils.driver, this);
		
	}
	
	public void searchCustomers(String key ,String input) throws Exception {
		
		boolean result = false;
		clickElement(filtersButton, "FilterButton");
	
		clickElement(selectFieldTextbox, "selectFieldTextbox");
		Thread.sleep(1000);
		//jstypetext(selectFieldTextbox, "FirstName","FirstName");
		if(key.contentEquals("firstName")) {
		typeText(searchBox, key,"FirstName");}
		else
		typeText(searchBox, "email","email");
			
		
		clickElement(selectFirstItem, "selectFirstItem");

		//clickElement(selectFieldTextbox, "selectFieldTextbox");
		if(key.contentEquals("firstName")){
		typeText(searchtext,"Tomasa","FirstName");}
		else
		typeText(searchtext,input,"email");	
		
		clickElement(searchtext, "Search Box");
		clickElement(applyFiltersButton, "applyFiltersButton");
		
		validateSearchResults(key,input);
		
		
	}

	private void validateSearchResults(String key , String value) {
		String actual  = null;
		try {
			if(key.contentEquals("email")) {
		actual = emailfieldinTable.getText();
			}else
				actual = namefieldinTable.getText();
		
		System.out.println(actual);
		
		Assert.assertEquals(value, actual);
		test.log(Status.PASS, "Validation successful");
		}
		catch(Exception e) {
			System.out.println("Exception occured");
			test.log(Status.FAIL, "Search validation failed" );
			
		}
		
	}
	
	
	
	
	
	

}
