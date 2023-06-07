package com.laowang.mymall.nosql.elasticsearch.document;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @program: my-mall
 * @description: ES-商品模块-产品-属性-值
 * @author: Laowang
 * @create: 2023-05-28 11:12
 */
public class EsPmsProductAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private Long productId;

    private Long productAttributeId;

    //    @ApiModelProperty(value = "手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开")
    @Field(type = FieldType.Keyword)
    private String value;
    //属性参数：0->规格；1->参数
    private Integer type;
    //属性名称
    @Field(type= FieldType.Keyword)
    private String name;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
