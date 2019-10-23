package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xkkk
 * @since 2019-10-21
 */
@TableName("mtime_stock_log")
public class MtimeStockLog extends Model<MtimeStockLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;
    /**
     * 秒杀活动id
     */
    @TableField("promo_id")
    private Integer promoId;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 记录创建的时间
     */
    @TableField("create_time")
    private Date createTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MtimeStockLog{" +
        "uuid=" + uuid +
        ", promoId=" + promoId +
        ", amount=" + amount +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
