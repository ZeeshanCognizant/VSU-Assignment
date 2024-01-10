package com.vehicle.Iotproject.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Fleet_Details")
public class Fleet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fleetId;

    private int count;

    private String route;

    @OneToMany(mappedBy = "fleet")
    @JsonIgnoreProperties("fleet")
    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
    private List<Vehicle> vehicleList;

    public Fleet(int fleetId, int count, String route) {
        this.fleetId = fleetId;
        this.count = count;
        this.route = route;
    }
}
