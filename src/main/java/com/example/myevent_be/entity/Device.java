package com.example.myevent_be.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String description;
    String image;

    public BigDecimal getHourly_rental_fee() {
        return hourly_rental_fee;
    }

    public void setHourly_rental_fee(BigDecimal hourly_rental_fee) {
        this.hourly_rental_fee = hourly_rental_fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Set<DeviceRental> getDeviceRentals() {
        return deviceRentals;
    }

    public void setDeviceRentals(Set<DeviceRental> deviceRentals) {
        this.deviceRentals = deviceRentals;
    }

    BigDecimal hourly_rental_fee;
    int quantity;
    Date created_at;
    Date update_at;

    @OneToMany(mappedBy = "device")
    Set<DeviceRental> deviceRentals;
}
