package com.currency.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(schema = "currencies", name = "history")
public class HistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "in_cur", nullable = false)
    private Currency inCur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "out_cur", nullable = false)
    private Currency outCur;

    @Column(name = "in_sum")
    private double inSum;

    @Column(name = "out_sum")
    private double outSum;

    private LocalDateTime date;

    public HistoryEntry() {
    }

    public HistoryEntry(Currency inCur, Currency outCur, double inSum, double outSum, LocalDateTime date) {
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

    public Currency getInCur() {
        return inCur;
    }

    public void setInCur(Currency inCur) {
        this.inCur = inCur;
    }

    public Currency getOutCur() {
        return outCur;
    }

    public void setOutCur(Currency outCur) {
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
