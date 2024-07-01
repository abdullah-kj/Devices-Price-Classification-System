package com.example.devicespriceclassifier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int battery_power;

    private boolean blue;

    private double clock_speed;

    private boolean dual_sim;

    private int fc;

    private boolean four_g;

    private int int_memory;

    private double m_dep;

    private int mobile_wt;

    private int n_cores;

    private int pc;

    private int px_height;

    private int px_width;

    private int ram;

    private int sc_h;

    private int sc_w;

    private int talk_time;

    private boolean three_g;

    private boolean touch_screen;

    private boolean wifi;

    private int price_range;


}
