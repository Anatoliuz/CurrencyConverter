package com.currency.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(schema = "currencies", name = "history")
public class HistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "in_cur")
    private Long inCur;

    @Column(name = "out_cur")
    private Long outCur;

    @Column(name = "in_sum")
    private double inSum;

    @Column(name = "out_sum")
    private double outSum;

    private LocalDateTime date;

    public HistoryEntry() {
    }

    public HistoryEntry(Long inCur, Long outCur, double inSum, double outSum, LocalDateTime date) {
        this.inCur = inCur;
        this.outCur = outCur;
        this.inSum = inSum;
        this.outSum = outSum;
        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInCur() {
        return inCur;
    }

    public void setInCur(Long inCur) {
        this.inCur = inCur;
    }

    public Long getOutCur() {
        return outCur;
    }

    public void setOutCur(Long outCur) {
        this.outCur = outCur;
    }

    public double getInSum() {
        return inSum;
    }

    public void setInSum(double inSum) {
        this.inSum = inSum;
    }

    public double getOutSum() {
        return outSum;
    }

    public void setOutSum(double outSum) {
        this.outSum = outSum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
