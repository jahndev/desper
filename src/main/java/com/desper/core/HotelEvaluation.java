package com.desper.core;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "des_hotels_evaluation")
@NamedQueries({
    @NamedQuery(
        name = "com.desper.core.HotelEvaluation.findByHotel",
        query = "Select h from HotelEvaluation h where h.dhe_hotel_id = :hotelId"
    ),
    @NamedQuery(
        name = "com.desper.core.HotelEvaluation.findByUser",
        query = "Select h from HotelEvaluation h where h.dhe_user_name = :userName"
    ),
    @NamedQuery(
        name = "com.desper.core.HotelEvaluation.findByHotelAndUser",
        query = "Select h from HotelEvaluation h where h.dhe_user_name = :userName AND h.dhe_hotel_id = :hotelId"
    )
})
public class HotelEvaluation implements java.io.Serializable {
    @Id
    @Column(name = "dhe_hotel_id", nullable = false)
    private Long dhe_hotel_id; //representa el hotel id

    @Id
    @Column(name = "dhe_user_name", nullable = false)
    private String dhe_user_name; //representa el nombre del usuario

    @Column(name = "dhe_evaluation", nullable = false)
    private Integer dhe_evaluation; //representa like/dislike

    public static int LIKE = 0;
    public static int DISLIKE = 1;

    public HotelEvaluation() {
    }

    public HotelEvaluation(Long dhe_hotel_id, String dhe_user_name, Integer dhe_evaluation) {
        this.dhe_evaluation = dhe_evaluation;
        this.dhe_hotel_id=  dhe_hotel_id;
        this.dhe_user_name= dhe_user_name;
    }

    public Long getDhe_hotel_id() {
        return dhe_hotel_id;
    }

    public void setDhe_hotel_id(Long dhe_hotel_id) {
        this.dhe_hotel_id = dhe_hotel_id;
    }

    public String getDhe_user_name() {
        return dhe_user_name;
    }

    public void setDhe_user_name(String dhe_user_name) {
        this.dhe_user_name = dhe_user_name;
    }

    public Integer getDhe_evaluation() {
        return dhe_evaluation;
    }

    public void setDhe_evaluation(Integer dhe_evaluation) {
        this.dhe_evaluation = dhe_evaluation;
    }
}
