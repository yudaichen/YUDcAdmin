package org.ydc.system.system.utils;

import org.ydc.system.system.entity.Organization;

import java.util.ArrayList;
import java.util.List;

public class OrganizationUtils {
    private Long id;
    private String name;
    private Long pid;
    private String code;
    private String address;
    private String contact;
    private String phone;
    private Integer seq;
    private boolean open;
    private List<Organization> chidren = new ArrayList<Organization>();

    public OrganizationUtils() {
    }

    public OrganizationUtils(Long id, String name, Long pid, String code, String address, String contact, String phone, Integer seq, boolean open, List<Organization> chidren) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.code = code;
        this.address = address;
        this.contact = contact;
        this.phone = phone;
        this.seq = seq;
        this.open = open;
        this.chidren = chidren;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<Organization> getChidren() {
        return chidren;
    }

    public void setChidren(List<Organization> chidren) {
        this.chidren = chidren;
    }
}
