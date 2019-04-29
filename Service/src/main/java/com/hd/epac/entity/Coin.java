package com.hd.epac.entity;

public class Coin {
    private String coinname;

    private String coinprice;

    private String coinchange;

    private String cointrade;

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getCoinprice() {
        return coinprice;
    }

    public void setCoinprice(String coinprice) {
        this.coinprice = coinprice;
    }

    public String getCoinchange() {
        return coinchange;
    }

    public void setCoinchange(String coinchange) {
        this.coinchange = coinchange;
    }

    public String getCointrade() {
        return cointrade;
    }

    public void setCointrade(String cointrade) {
        this.cointrade = cointrade;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "coinname='" + coinname + '\'' +
                ", coinprice='" + coinprice + '\'' +
                ", coinchange='" + coinchange + '\'' +
                ", cointrade='" + cointrade + '\'' +
                '}';
    }
}