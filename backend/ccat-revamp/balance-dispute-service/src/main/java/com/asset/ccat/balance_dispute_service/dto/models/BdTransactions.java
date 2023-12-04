/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.balance_dispute_service.dto.models;

import java.io.Serializable;
import java.util.ArrayList;


public class BdTransactions implements Serializable{
    
    private ArrayList<BdTransactionDetails> transactionDetails = new ArrayList<>();
    private BdSummary transactionSummary = new BdSummary();

    //Totals for the transaction details, summary totals are in the BdSummary model
    private double totalMBCredit = 0;
    private double totalMBDebit = 0;
    private double totalDACredit = 0;
    private double totalDADebit = 0;
    
    private double totalAmountCredit = 0;
    private double totalAmountDebit = 0;
    private double totalDuration = 0;
    private double totalCost = 0;
    private double totalActualSeconds = 0;
    private double totalFreeSms = 0;
    private double totalInternetUsage = 0;

    public double getTotalDACredit() {
        return totalDACredit;
    }

    public void setTotalDACredit(double totalDACredit) {
        this.totalDACredit = totalDACredit;
    }

    public double getTotalDADebit() {
        return totalDADebit;
    }

    public void setTotalDADebit(double totalDADebit) {
        this.totalDADebit = totalDADebit;
    }

    public double getTotalMBCredit() {
        return totalMBCredit;
    }

    public void setTotalMBCredit(double totalMBCredit) {
        this.totalMBCredit = totalMBCredit;
    }

    public double getTotalMBDebit() {
        return totalMBDebit;
    }

    public void setTotalMBDebit(double totalMBDebit) {
        this.totalMBDebit = totalMBDebit;
    }

    public ArrayList<BdTransactionDetails> getTransactionDetails() { 
        return transactionDetails;
    }

    public void setTransactionDetails(ArrayList<BdTransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public BdSummary getTransactionSummary() {
        return transactionSummary;
    }
 
    public void setTransactionSummary(BdSummary transactionSummary) {
        this.transactionSummary = transactionSummary;
    }

    public double getTotalAmountCredit() {
        return totalAmountCredit;
    }

    public void setTotalAmountCredit(double totalAmountCredit) {
        this.totalAmountCredit = totalAmountCredit;
    }

    public double getTotalAmountDebit() {
        return totalAmountDebit;
    }

    public void setTotalAmountDebit(double totalAmountDebit) {
        this.totalAmountDebit = totalAmountDebit;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public double getTotalFreeSms() {
        return totalFreeSms;
    }

    public void setTotalFreeSms(double totalFreeSms) {
        this.totalFreeSms = totalFreeSms;
    }

    public double getTotalInternetUsage() {
        return totalInternetUsage;
    }

    public void setTotalInternetUsage(double totalInternetUsage) {
        this.totalInternetUsage = totalInternetUsage;
    }

    public double getTotalActualSeconds() {
        return totalActualSeconds;
    }

    public void setTotalActualSeconds(double totalActualSeconds) {
        this.totalActualSeconds = totalActualSeconds;
    }
    
    
}
