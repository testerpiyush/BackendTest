package com.mytaxi.apitest.utility;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ReportUtil extends CustomListeners {
    private static final String ReportDirectory = System.getProperty("user.dir") + "/test-output";
    public static Map<Long, ExtentTest> testReportMap = new HashMap<>();
    public static Logger logger;
    private static WebDriver driver;
    private static ExtentReports extent;


    public static void initReports(Logger loggerTemp) {
        try {
            logger = loggerTemp;
            //Read extent config file and load it

            String ReportingFile = ReportDirectory + "/test-report.html";
            extent = new ExtentReports(ReportingFile, true, NetworkMode.ONLINE);
            extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startTest(String testCaseName) {
        try {
            testReportMap.put(Thread.currentThread().getId(), extent.startTest(testCaseName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void endTest() {
        try {
            //write result to report
            extent.endTest(testReportMap.get(Thread.currentThread().getId()));
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void endReports() {
        try {
            extent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Writing Steps
    public static void log(String step, String details, String status) {
        try {
            ExtentTest extentTest = testReportMap.get(Thread.currentThread().getId());
            switch (status.trim().toLowerCase()) {
                case "pass":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.PASS, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "fail":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.FAIL, step, details);
                    extentTest.addScreenCapture(details);
                    Reporter.log(step + "-" + details);
                    break;
                case "info":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.INFO, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "skip":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.SKIP, step, details);
                    Reporter.log(step + "-" + details);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String step, String details, String status, Exception exception) {
        try {
            ExtentTest extentTest = testReportMap.get(Thread.currentThread().getId());
            switch (status.trim().toLowerCase()) {
                case "pass":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.PASS, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "fail":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.FAIL, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "info":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.INFO, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "skip":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.SKIP, step, details);
                    Reporter.log(step + "-" + details);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String step, String details, String status, AssertionError error) {
        try {
            ExtentTest extentTest = testReportMap.get(Thread.currentThread().getId());
            switch (status.trim().toLowerCase()) {
                case "pass":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.PASS, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "fail":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.FAIL, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "info":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.INFO, step, details);
                    Reporter.log(step + "-" + details);
                    break;
                case "skip":
                    logger.log(Level.INFO, "[" + Thread.currentThread().getName() + "] " + step + "-" + details);
                    extentTest.log(LogStatus.SKIP, step, details);
                    Reporter.log(step + "-" + details);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

