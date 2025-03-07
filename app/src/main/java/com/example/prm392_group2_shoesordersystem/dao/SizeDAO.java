package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Insert;

import com.example.prm392_group2_shoesordersystem.entity.Size;

public interface SizeDAO {
    @Insert
    long insertSize(Size size);
}
