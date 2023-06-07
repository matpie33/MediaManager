package org.travelling.ticketer.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Delay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Connection connection;

    @Column
    private LocalDate date;

    @Column
    private int delayMinutes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }
}
