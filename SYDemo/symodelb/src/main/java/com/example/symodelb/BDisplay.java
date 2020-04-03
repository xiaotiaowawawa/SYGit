package com.example.symodelb;

import com.afirez.spi.SPI;
import com.example.commondapi.Display;
import com.google.auto.service.AutoService;

@SPI(path = "BDisplay")
public class BDisplay implements Display{
    public BDisplay() {
    }

    @Override
    public String display() {
        return "B";
    }
}
