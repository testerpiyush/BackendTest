package com.mytaxi.apitest.utility;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Level;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import static com.mytaxi.apitest.utility.ReportUtil.logger;
import static com.mytaxi.apitest.utility.ReportUtil.testReportMap;

public class CustomListeners extends TestListenerAdapter {

    @SuppressWarnings("deprecation")
    // private static Logger logger = Logger.getLogger(driver.class.getName());
    private int m_count = 0;

    @Override
    public void onTestFailure(ITestResult tr) {
        log("[" + Thread.currentThread().getName() + "] " + "--Test method [" + tr.getName() + "] FAILED. \n Location: " + tr.getTestClass() + " \n and the reason is " + tr.getThrowable().getMessage() + "\n");
        ExtentTest extentTest = testReportMap.get(Thread.currentThread().getId());
        extentTest.log(LogStatus.FAIL, "Test Status", "Test case [" + tr.getName() + "] failed");
        extentTest.log(LogStatus.INFO, "Reason for the failure", tr.getThrowable().getMessage());
        extentTest.log(LogStatus.INFO, "Track Trace Info", tr.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        log("[" + Thread.currentThread().getName() + "] " + "-- Test method [" + tr.getName() + "] SKIPPED.-- \n Location: [" + tr.getTestClass() + "]\n");
        testReportMap.get(Thread.currentThread().getId()).log(LogStatus.INFO, "Test Status", "Test Skipped");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        log("[" + Thread.currentThread().getName() + "] " + "-- Test method [" + tr.getName() + "] PASSED. --\n Location: [" + tr.getTestClass() + "]\n");
        testReportMap.get(Thread.currentThread().getId()).log(LogStatus.PASS, "Test Status", "Test Passed");
    }

    @Override
    public void onTestStart(ITestResult tr) {
        log("[" + Thread.currentThread().getName() + "] " + "-- Test case [" + tr.getName() + "] STARTED.-- \n Location = [" + tr.getTestClass() + "]\n");
        testReportMap.get(Thread.currentThread().getId()).assignCategory(tr.getInstanceName().replace(tr.getName(), ""));
        testReportMap.get(Thread.currentThread().getId()).log(LogStatus.INFO, "Testcase", tr.getName());
    }

    public void log(String string) {
        logger.log(Level.INFO, string);
        if (++m_count % 40 == 0) {
            System.out.println("");
        }
    }


}




