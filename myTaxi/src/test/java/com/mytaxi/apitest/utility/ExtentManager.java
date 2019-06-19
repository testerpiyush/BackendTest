package com.mytaxi.apitest.utility;

import com.relevantcodes.extentreports.ExtentReports;

import javax.mail.MessagingException;


public class ExtentManager {
    private static ExtentReports extent;

    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
            extent.addSystemInfo("Test", "BackendTest");

        }

        return extent;
    }

    public static synchronized void closeReporter(String to, String username, String password, String env,
                                                  String ismailrequired, int pt, int ft, int st) throws MessagingException {
        extent.flush();
        extent.close();


    }

    public static synchronized void closeReporter() throws MessagingException {
        extent.flush();
        extent.close();
    }

}
