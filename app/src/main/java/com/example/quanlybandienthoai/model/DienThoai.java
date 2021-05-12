package com.example.quanlybandienthoai.model;

import java.io.Serializable;

public class DienThoai implements Serializable {
    private int id ;
    private String tenDienThoai;
    private String heDieuHanh;
    private  String ManHinh;
    private String cameraTruoc;
    private String cameraSau;
    private  String Chip;
    private String Ram;
    private String BoNhoTrong ;
    private String PIN;
    private byte[] hinhAnh;
    private int giaTien;
    private String gioiThieu;
    private int HangDT;


    public DienThoai(int id, String tenDienThoai, String heDieuHanh, String manHinh, String cameraTruoc, String cameraSau, String chip, String ram, String boNhoTrong, String PIN, byte[] hinhAnh, int giaTien, String gioiThieu, int hangDT) {
        this.id = id;
        this.tenDienThoai = tenDienThoai;
        this.heDieuHanh = heDieuHanh;
        ManHinh = manHinh;
        this.cameraTruoc = cameraTruoc;
        this.cameraSau = cameraSau;
        Chip = chip;
        Ram = ram;
        BoNhoTrong = boNhoTrong;
        this.PIN = PIN;
        this.hinhAnh = hinhAnh;
        this.giaTien = giaTien;
        this.gioiThieu = gioiThieu;
        HangDT = hangDT;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public String getManHinh() {
        return ManHinh;
    }

    public void setManHinh(String manHinh) {
        ManHinh = manHinh;
    }

    public String getCameraTruoc() {
        return cameraTruoc;
    }

    public void setCameraTruoc(String cameraTruoc) {
        this.cameraTruoc = cameraTruoc;
    }

    public String getCameraSau() {
        return cameraSau;
    }

    public void setCameraSau(String cameraSau) {
        this.cameraSau = cameraSau;
    }

    public String getChip() {
        return Chip;
    }

    public void setChip(String chip) {
        Chip = chip;
    }

    public String getRam() {
        return Ram;
    }

    public void setRam(String ram) {
        Ram = ram;
    }

    public String getBoNhoTrong() {
        return BoNhoTrong;
    }

    public void setBoNhoTrong(String boNhoTrong) {
        BoNhoTrong = boNhoTrong;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public int getHangDT() {
        return HangDT;
    }

    public void setHangDT(int hangDT) {
        HangDT = hangDT;
    }
}
