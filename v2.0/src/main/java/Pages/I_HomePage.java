package Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class I_HomePage 
{
	private WebDriver driver ;
	private WebDriverWait wait;
	private Actions a;
	
	
	By MenuIcon = By.className("tMenu");
	
	public I_HomePage(WebDriver driver, WebDriverWait wait,Actions a)
	{
		this.driver = driver;
		this.wait 	= wait; 	
		this.a		=a;
	}
	
	public void HomePageCheck()
	{
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(MenuIcon));
			System.out.println("Home Page is Displayed");
		}catch(Exception e)
		{
			System.out.println("Home Page is not displayed");
		}
		
	}
	
	public boolean MainModuleClick(String Module) throws InterruptedException
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("grd_loading_Image")));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("tMenu")));
		a.moveToElement(driver.findElement(By.className("tMenu"))).click().build().perform();
		Thread.sleep(500);
		a.moveToElement(driver.findElement(By.xpath("//div[@id='divtop']//a//div[text()='"+Module+"']//parent::div//parent::a"))).build().perform();
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@id='divtop']//a//div[text()='"+Module+"']//parent::div//parent::a")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("grd_loading_Image")));
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	public void SubModuleClick(String SubM) throws InterruptedException
	{
		try
			{
				WebDriverWait wait3 = new WebDriverWait(driver,30);
				wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divleft']//ul//li//a//div//p[text()='"+SubM+"']/parent::div/parent::a")));
				a.moveToElement(driver.findElement(By.xpath("//div[@id='divleft']//ul//li//a//div//p[text()='"+SubM+"']/parent::div/parent::a"))).click().build().perform();
				Thread.sleep(3000);
			}
			catch(MoveTargetOutOfBoundsException e)
			{
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@id='divleft']//ul//li//a//div//p[text()='"+SubM+"']/parent::div/parent::a")));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("grd_loading_Image")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divleft']//ul//li//a//div//p[text()='"+SubM+"']/parent::div/parent::a")));
				driver.findElement(By.xpath("//div[@id='divleft']//ul//li//a//div//p[text()='"+SubM+"']/parent::div/parent::a")).click();
				Thread.sleep(3000);
				
			}
			catch(Exception sub)
			{
				sub.printStackTrace();
				System.out.println("Unknown issue");
				WebDriverWait wait4 = new WebDriverWait(driver,30);
				try
				{
					wait4.until(ExpectedConditions.elementToBeClickable(By.linkText(SubM))).click();
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("grd_loading_Image")));
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(3000);
				}
				catch(Exception er1)
				{
					wait4.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='left']//div//span"))).click();
					wait4.until(ExpectedConditions.elementToBeClickable(By.linkText(SubM))).click();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(3000);
				}
			}
		
			
	}
	
	public boolean left()
	{
		try
		{
			driver.findElement(By.id("dashboardtabs")).isDisplayed();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
}
