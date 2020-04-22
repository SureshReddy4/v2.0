package Nav;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Core.CheckingServerResponse;
import Core.MainModule;
import Core.SubModule;
import Pages.I_HomePage;
import Pages.I_LoginPage;

public class Navigation_v2 
{
	static WebDriver driver;
	static WebDriverWait wait;
	static Actions act;
	static int rownum;
	static int j = 1;
	static int rownum2;
	static String submodule;
	static String IES = "IES ::";
//	static String Module = "Purchase";

	@BeforeTest
	public void Before() 
		{
			System.setProperty("Webdriver.gecko.driver", ".//geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 30);
			act = new Actions(driver);
			I_LoginPage LP = new I_LoginPage(driver, wait);

			driver.get("http://192.168.1.98:9020/");
			LP.PageCheck();
			LP.Login("admin", "infi");

			I_HomePage HP = new I_HomePage(driver, wait, act);
			HP.HomePageCheck();
		}

//	@Test
//	public void Testing() throws Exception 
	@Test(dataProvider="dp")
	public void Testing(String Module) throws Exception 
		{
			I_HomePage HP = new I_HomePage(driver, wait, act);
		
			FileInputStream fis = new FileInputStream("C:\\Users\\suresh.b\\git\\v2.0\\v2.0\\src\\main\\java\\Core\\9020.properties");
			Properties p = new Properties();
			p.load(fis);

			FileInputStream fis2 = new FileInputStream("C:\\Users\\suresh.b\\Desktop\\Testing.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(fis2);
			XSSFSheet sheet = wb.getSheetAt(0);

			// Print Module Name in Excel
			sheet.getRow(j).createCell(1).setCellValue(Module);

			if(HP.MainModuleClick(Module))
				{
					try 
						{
							driver.switchTo().alert().dismiss();
							sheet.getRow(j).createCell(3).setCellValue("500");
							sheet.getRow(j).createCell(4).setCellValue("Fail");
							j++;
						}
				
					catch (NoAlertPresentException e) 
				 		{
				 			if (CheckingServerResponse.HTTPLinkstatusChecker(driver.getCurrentUrl()) == 404) 
								{
									sheet.getRow(j).createCell(3).setCellValue("404");
									sheet.getRow(j).createCell(4).setCellValue("Fail");
									driver.navigate().back();
										try {
												driver.switchTo().alert().dismiss();
											}
										catch (Exception e1) 
											{
												System.out.println("Navigation Error");
											}
									j++;
								}
							else if (CheckingServerResponse.HTTPLinkstatusChecker(driver.getCurrentUrl()) == 200) 
								{
									if (driver.getTitle().contains(IES)) 
										{
											sheet.getRow(j).createCell(3).setCellValue("200");
											sheet.getRow(j).createCell(4).setCellValue("Pass");
											
											Long Start = System.currentTimeMillis();
											wait.until(ExpectedConditions.elementToBeClickable(By.className("tMenu")));
											Long End = System.currentTimeMillis();
											Long Time = End - Start;
											sheet.getRow(j).createCell(5).setCellValue(Time);
											j++;

											Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
											String Conurl = "jdbc:sqlserver://" + p.getProperty("ServerName") + "databaseName=" + p.getProperty("databaseName");
											Connection con = DriverManager.getConnection(Conurl, p.getProperty("dbUsername"),p.getProperty("dbPassword"));
											Statement stmt = con.createStatement();
											ResultSet rs;

											String s1 = p.getProperty("sb1") + p.getProperty("AppUser") + p.getProperty("sb2") + Module + p.getProperty("sb3");
											stmt.execute(s1);

											String query3 = "Select Title from #MainModule_List Drop Table #MainModule_List";
											stmt.execute(query3);

											rs = stmt.getResultSet();
											while (rs.next()) 
												{
													rownum2 = rs.getRow();
												}

											List<String> Subm = SubModule.getSubModule("admin", Module);
											for (int i = 0; i < rownum2; i++) 
											{
												submodule = Subm.get(i);

												if (submodule != null) 
													{
														sheet.getRow(j).createCell(2).setCellValue(submodule);

														if(Module.equalsIgnoreCase("Dashboard"))
														{
															driver.findElement(By.linkText(submodule)).click();
															wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("grd_loading_Image")));
														}
														else
														{
															HP.SubModuleClick(submodule);
														}
															
															System.out.println(submodule);
															
															try 
																{	
																	driver.switchTo().alert().dismiss();
																	sheet.getRow(j).createCell(3).setCellValue("500");
																	sheet.getRow(j).createCell(4).setCellValue("Fail");
																	j++;
																}
															catch (NoAlertPresentException eis) 
																{
																	if (CheckingServerResponse.HTTPLinkstatusChecker(driver.getCurrentUrl()) == 404) 
																		{
																			sheet.getRow(j).createCell(3).setCellValue("404");
																			sheet.getRow(j).createCell(4).setCellValue("Fail");
																			driver.navigate().back();
																			JavascriptExecutor je = (JavascriptExecutor) driver;
																			je.executeScript("window.scrollBy(0,-1500)");
																			j++;
																		}

																	else if (CheckingServerResponse.HTTPLinkstatusChecker(driver.getCurrentUrl()) == 200) 
																		{
																			if (driver.getTitle().contains(IES)) 
																				{
																					sheet.getRow(j).createCell(3).setCellValue("200");
																					sheet.getRow(j).createCell(4).setCellValue("Pass");
																					Long sStart = System.currentTimeMillis();
																					try
																					{
																						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("grd_loading_Image")));
																					}
																					catch(Exception e5)
																					{
																						System.out.println("Page is getting stucked at loading");
																						sheet.getRow(j).createCell(6).setCellValue("Taking time to load");
																						driver.navigate().back();
																					}
																					Long sEnd = System.currentTimeMillis();
																					Long sTimes = sEnd - sStart;
																					sheet.getRow(j).createCell(5).setCellValue(sTimes);
																					j++;
																				}
										
										 									else { 
										 											sheet.getRow(j).createCell(4).setCellValue("Fail");
										 											sheet.getRow(j).createCell(6).setCellValue(driver.getTitle());
										 											driver.navigate().back(); 
										 											JavascriptExecutor je = (JavascriptExecutor)
										 											driver; je.executeScript("window.scrollBy(0,-1500)"); 
										 											j++; 
										 										 }
										 
																		}
																	else
																		{
																			sheet.getRow(j).createCell(3).setCellValue(CheckingServerResponse.HTTPLinkstatusChecker(driver.getCurrentUrl()));
																			sheet.getRow(j).createCell(4).setCellValue("Fail");
																			sheet.getRow(j).createCell(6).setCellValue(driver.getTitle());
																			driver.navigate().back();
																			j++;
																		}
																}
													}
												else
													{
														System.out.println("No Submodules are displayed");
													}		
										}//End of IF Main Module - 200
								}
							else
								{
									sheet.getRow(j).createCell(3).setCellValue(CheckingServerResponse.HTTPLinkstatusChecker(driver.getCurrentUrl()));
									sheet.getRow(j).createCell(4).setCellValue("Fail");
									sheet.getRow(j).createCell(6).setCellValue(driver.getTitle());
									driver.navigate().back();
									j++;
								}	
				 		}
				 	}
				}/*End of If*/
			else
				{
					sheet.getRow(j).createCell(1).setCellValue(Module);
					sheet.getRow(j).createCell(5).setCellValue("Application getting hanged at Loading, Skipping Sub Modules");
					driver.navigate().back();
					j++;
				}
		
			FileOutputStream fos = new FileOutputStream("C:\\Users\\suresh.b\\Desktop\\Testing.xlsx");
			wb.write(fos);
			wb.close();
			
		}/*End of Class*/



		@AfterTest
		public void After() throws InterruptedException {
			Thread.sleep(5000);
			driver.quit();
		}

		@DataProvider
		public Object[][] dp() throws Exception 
		{
	
			FileInputStream fis = new FileInputStream("C:\\Users\\suresh.b\\git\\v2.0\\v2.0\\src\\main\\java\\Core\\9020.properties");
			Properties p = new Properties();
			p.load(fis);
	
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String Conurl = "jdbc:sqlserver://" + p.getProperty("ServerName") + "databaseName="+ p.getProperty("databaseName");
			Connection con = DriverManager.getConnection(Conurl, p.getProperty("dbUsername"), p.getProperty("dbPassword"));
			Statement stmt = con.createStatement();
			ResultSet rs;
	
			String query2 = p.getProperty("uq1") + p.getProperty("AppUser") + p.getProperty("uq2");
			stmt.execute(query2);
			
//			String query3 = "Select Title from #MainModule_List where Parent = 0 and URL is not null Drop Table #MainModule_List ";
			String query3 = "Select Title from #MainModule_List Where URL IS NOT NULL and Parent = 0 and Title In ('Dashboard','Sales') Drop Table #MainModule_List";
			stmt.execute(query3);
	
			rs = stmt.getResultSet();
			while (rs.next())
				{
					rownum = rs.getRow();
				}
	
			Object[][] datas = new Object[rownum][1];
	
			List<String> Module = MainModule.dp("admin");
			for (int i = 0; i < rownum; i++) 
				{
					datas[i][0] = Module.get(i);
				}
		return datas;
	}
}
