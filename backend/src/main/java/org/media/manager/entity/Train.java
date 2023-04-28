package org.media.manager.entity;

import javax.persistence.*;

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private int maxSeats;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public int getMaxSeats() {
        return maxSeats;
    }
}
