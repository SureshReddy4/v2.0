package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class I_LoginPage 
{
	private WebDriver driver;
	private WebDriverWait Wait;
	
	/*
	 * Elements
	 */
	By Username = By.id("UserName");
	By Password = By.id("Password");
	By Loginbtn = By.id("btnLogIn");
	By LError 	= By.id("divErrMsg");
	
	public I_LoginPage(WebDriver driver,WebDriverWait wait)
	{
		this.driver = driver;
		this.Wait = wait;
		
	}
	
	public I_LoginPage(WebDriver driver2) 
	{
		this.driver = driver2;
	}

	public void PageCheck()
	{
		/*
		 * Here We will check whether the login page is displayed
		 */
		WebElement U = driver.findElement(Username);
		if(U.isDisplayed())
		{
			System.out.println("Login Page is Displayed");
		}
		else
		{
			System.out.println("Login Page is not Displayed");
		}
	}
	
	public void Login(String User,String Pass)
	{
		driver.findElement(Username).sendKeys(User);
		driver.findElement(Password).sendKeys(Pass);
		driver.findElement(Loginbtn).click();
		
		try
		{
			WebElement Error = driver.findElement(LError);
			if(Error.isDisplayed())
			{
				System.out.println(Error.getText());
			}
		}catch(Exception one)
		{
			System.out.println("User successfully Logged In");
		}
	}
	
	

}
