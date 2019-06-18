package com.mytaxi.apitest.base;

import com.mytaxi.apitest.utility.ReportUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;

public class TestBase {

    public static Properties testProps;
    protected static ExtentTest test;
    private static ExtentReports extent;
    private static Logger logger = Logger.getLogger(TestBase.class.getName());
    final String filePath = "testReport.html";


    @BeforeSuite
    protected void setup(ITestContext context) {
        extent.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extent-config.xml"));
        setupSuite();
        ReportUtil.initReports(logger);
    }

    public void setupSuite() {
        logger.info("Suite Setup Completed");
    }


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        ReportUtil.startTest(method.getName());

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        ReportUtil.endTest();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        logger.info("Suite execution completed.");
        ReportUtil.endReports();
    }

}
