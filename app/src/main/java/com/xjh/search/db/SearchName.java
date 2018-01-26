package com.xjh.search.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author chensong
 * @date 2018/1/19 17:18
 */
@Entity
public class SearchName {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "NAME")
    private String name;

    @Generated(hash = 1654382222)
    public SearchName(Long _id, String name) {
        this._id = _id;
        this.name = name;
    }

    @Generated(hash = 533937156)
    public SearchName() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
