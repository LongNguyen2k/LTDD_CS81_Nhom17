package com.example.quanlybandienthoai.model;

public class HangDT {
    private int idHang;
    private String tenHang;
    private byte[] HinhAnh;

    public HangDT(int idHang, String tenHang, byte[] hinhAnh) {
        this.setIdHang(idHang);
        this.setTenHang(tenHang);
        this.setHinhAnh(hinhAnh);
    }





    public int getIdHang() {
        return idHang;
    }

    public void setIdHang(int idHang) {
        this.idHang = idHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }



}
