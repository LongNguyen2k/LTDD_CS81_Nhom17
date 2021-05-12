package com.example.quanlybandienthoai.model;

public class GioHang {
    private int idDienThoai;
    private String tenDienThoai;
    private int giaTien;
    private byte[] hinhAnhDT;
    private int soLuongDienThoai;
    private int giaTienDienThoai;
    public GioHang(int idDienThoai, String tenDienThoai, int giaTien, byte[] hinhAnhDT, int soLuongDienThoai,int giaTienDienThoai) {
        this.setIdDienThoai(idDienThoai);
        this.setTenDienThoai(tenDienThoai);
        this.setGiaTien(giaTien);
        this.setHinhAnhDT(hinhAnhDT);
        this.setSoLuongDienThoai(soLuongDienThoai);
        this.setGiaTienDienThoai(giaTienDienThoai);
    }


    public int getIdDienThoai() {
        return idDienThoai;
    }

    public void setIdDienThoai(int idDienThoai) {
        this.idDienThoai = idDienThoai;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public byte[] getHinhAnhDT() {
        return hinhAnhDT;
    }

    public void setHinhAnhDT(byte[] hinhAnhDT) {
        this.hinhAnhDT = hinhAnhDT;
    }

    public int getSoLuongDienThoai() {
        return soLuongDienThoai;
    }

    public void setSoLuongDienThoai(int soLuongDienThoai) {
        this.soLuongDienThoai = soLuongDienThoai;
    }

    public int getGiaTienDienThoai() {
        return giaTienDienThoai;
    }

    public void setGiaTienDienThoai(int giaTienDienThoai) {
        this.giaTienDienThoai = giaTienDienThoai;
    }
}
