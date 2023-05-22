package com.openwell.flashsale.mall.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class Order  implements Serializable {

    /*
     * 用来标识是普通订单还是秒杀订单
     * P:普通订单
     * K:秒杀订单
     * */
    private String orderType;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.order_id
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Long orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.order_sn
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String orderSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.user_id
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.order_status
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Integer orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.shipping_status
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Integer shippingStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.pay_status
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Integer payStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.consignee
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String consignee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.area
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String area;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.address
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.zipcode
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String zipcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.mobile
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.email
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.shipping_code
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String shippingCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.shipping_name
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String shippingName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.pay_code
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String payCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.pay_name
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String payName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.invoice_title
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String invoiceTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.goods_price
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal goodsPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.shipping_price
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal shippingPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.user_money
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal userMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.coupon_price
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal couponPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.integral
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Integer integral;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.integral_money
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal integralMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.order_amount
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal orderAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.total_amount
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal totalAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.add_time
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Long addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.shipping_time
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Long shippingTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.confirm_time
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Long confirmTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.pay_time
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Long payTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.order_prom_id
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Short orderPromId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.order_prom_amount
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal orderPromAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.discount
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private BigDecimal discount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.user_note
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String userNote;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.admin_note
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String adminNote;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.parent_sn
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private String parentSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.is_distribut
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Boolean isDistribut;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_order.receive_time
     *
     * @mbggenerated Wed Feb 28 17:18:49 CST 2018
     */
    private Long receiveTime;
}
