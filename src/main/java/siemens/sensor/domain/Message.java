package siemens.sensor.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table
@ToString(of = {"id", "coordinates", "temperature"})
@EqualsAndHashCode(of = {"id"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@NotNull
    //@Size(max = 180, message = "Координаты должны быть в пределе от -180 до 180 градусов")
    private Double coordinates;
    //@Size(max = 1000, message = "Температура должна быть в пределе от -297 до 1000 градусов Цельсия")
    private Double temperature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double coordinates) {
        this.coordinates = coordinates;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
