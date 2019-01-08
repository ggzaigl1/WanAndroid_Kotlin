package com.example.gab.kotlin.bean

import java.io.Serializable

/**
 * Created by 初夏小溪
 * data :2019/1/7 0007 11:31
 */
class LoginBean : Serializable {

    /**
     * collectIds : [2481]
     * email :
     * icon :
     * id : 3555
     * password : tmdligen
     * type : 0
     * username : ggzaigl1
     */

    private var email: String? = null
    private var icon: String? = null
    private var id: Int = 0
    private var password: String? = null
    private var type: Int = 0
    private var username: String? = null
    private var collectIds: List<Int>? = null

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String) {
        this.icon = icon
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getType(): Int {
        return type
    }

    fun setType(type: Int) {
        this.type = type
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getCollectIds(): List<Int>? {
        return collectIds
    }

    fun setCollectIds(collectIds: List<Int>) {
        this.collectIds = collectIds
    }
}