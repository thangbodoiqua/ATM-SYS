package com.demo.form.atm;

import org.apache.struts.action.ActionForm;

public class TransferForm extends ActionForm {
    private String amount;
    private String receiverCardNumber;

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReceiverCardNumber() {
        return receiverCardNumber;
    }
    public void setReceiverCardNumber(String receiverCardNumber) {
        this.receiverCardNumber = receiverCardNumber;
    }
}