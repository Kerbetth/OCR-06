package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InteractiveShell {

    private static AccountService accountService = new AccountService();
    private static InputReaderUtil inputReaderUtil = new InputReaderUtil();
    private static boolean continueApp = true;
    private static Boolean isconnected = true;

    private static final Logger logger = LogManager.getLogger("InteractiveShell");

    public static void loadWelcomeInterface() {
        System.out.println("Welcome to Transferapp by Paymybuddy");
        while (continueApp) {
            loadWelcomeMenue();
            int option = inputReaderUtil.readInt("Please select an option.");
            switch (option) {
                case 1: {
                    isconnected = accountService.getConnection();
                    if (isconnected)
                        loadAccountInterface();
                    break;
                }
                case 2: {
                    System.out.println("Exiting from the system!");
                    continueApp = false;
                    break;
                }
                default:
                    System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
            }
        }
    }

    public static void loadAccountInterface() {
        while (isconnected) {
            loadAccountMenue();
            int option = inputReaderUtil.readInt("Please select an option.");
            switch (option) {
                case 1: {
                    accountService.addABuddy();
                    break;
                }
                case 2: {
                    accountService.addABankAccount();
                    break;
                }
                case 3: {
                    accountService.depositMoney();
                    break;
                }
                case 4: {
                    accountService.sendMoneyToARelative();
                    break;
                }
                case 5: {
                    accountService.sendMoneyToBankAccount();
                    break;
                }
                case 6: {
                    accountService.getAccountInfo();
                    break;
                }
                case 7: {
                    accountService.getTransactionInfo();
                    break;
                }
                case 8: {
                    System.out.println("You are disconnected");
					isconnected = false;
					loadWelcomeInterface();
                    break;
                }
                default:
                    System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
            }
        }
    }

    private static void loadWelcomeMenue() {
        System.out.println("1: enter your email and password");
        System.out.println("2: Shutdown System");
    }

    private static void loadAccountMenue() {
        System.out.println("1: Add a buddy on my list");
        System.out.println("2: Add a bank account");
        System.out.println("3: Deposit money on my account");
        System.out.println("4: Send money to my friends");
        System.out.println("5: Send money to my bank account");
        System.out.println("6: See my account info");
        System.out.println("7: See all of my transactions");
        System.out.println("8: Disconnect");
    }
}
