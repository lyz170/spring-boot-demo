package com.mytest.demo.entity.db1;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double area;

    @Column(length = 32, nullable = false)
    private String capital;

    public Country() {
    }

    public Country(String name, Double area, String capital) {
        super();
        this.name = name;
        this.area = area;
        this.capital = capital;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Country [id=" + id + ", name=" + name + ", area=" + area + ", capital=" + capital + "]";
    }
}
