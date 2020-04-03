package com.example.smodela;

import com.afirez.spi.SPI;
import com.example.commondapi.Display;
import com.google.auto.service.AutoService;

@SPI(path = "ADisplay")
public class ADisplay implements Display{
    public ADisplay() {
    }

    @Override
    public String display() {
        return "A";
    }
}
