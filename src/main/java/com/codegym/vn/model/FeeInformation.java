package com.codegym.vn.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FeeInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double feeTotal;
    private double feePaid;
    private double remainingPay;

    public FeeInformation(double feeTotal, double feePaid, double remainingPay) {
        this.feeTotal = feeTotal;
        this.feePaid = feePaid;
        this.remainingPay = remainingPay;
    }

    public FeeInformation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(double feeTotal) {
        this.feeTotal = feeTotal;
    }

    public double getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(double feePaid) {
        this.feePaid = feePaid;
    }

    public double getRemainingPay() {
        return remainingPay;
    }

    public void setRemainingPay(double remainingPay) {
        this.remainingPay = remainingPay;
    }
}
