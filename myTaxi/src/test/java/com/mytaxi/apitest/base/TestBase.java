package com.mytaxi.apitest.base;

import com.mytaxi.apitest.constants.ApiConstants;
import com.mytaxi.apitest.utility.ExtentManager;
import com.mytaxi.apitest.utility.ReportUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;

public class TestBase {

    public static Properties testProps;
    protected static ExtentTest test;
    private static ExtentReports extent;
    public static Logger logger = Logger.getLogger(com.mytaxi.apitest.base.TestBase.class.getName());
    final String filePath = "testReport.html";


    @BeforeSuite
    protected void setup(ITestContext context) {
        extent = ExtentManager.getReporter(filePath);
        extent.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extent-config.xml"));
        setupSuite();
        ReportUtil.initReports(logger);
    }

    public void setupSuite() {
        testProps = readTestProps(ApiConstants.TEST_RESOURCE_DIR + "test.properties");
        String filePath = System.getProperty("user.dir") + File.separator + "log4j.properties";
        Properties log4jProps = readPropsFile(filePath);
        PropertyConfigurator.configure(log4jProps);
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

    public Properties readPropsFile(String filePath) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public Properties readTestProps(String filePath) {
        Properties testProps = readPropsFile(filePath);
        Iterator<Object> iter = testProps.keySet().iterator();

        while (iter.hasNext()) {
            String key = iter.next().toString();
            String value = System.getProperty(key);
            if (value != null)
                testProps.setProperty(key, value);
        }
        return testProps;
    }

}
