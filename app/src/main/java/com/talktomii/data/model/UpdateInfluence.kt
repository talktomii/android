package com.talktomii.data.model

import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin.SocialNetwork

data class UpdateInfluence(
    var userName: String ="",
//    var name: String ="",
    var fname: String ="",
    var lname: String ="",
    var location: String ="",
    var price: Int = 0,
    var socialNetwork: ArrayList<SocialNetwork> = arrayListOf(),
    var availaibility: ArrayList<Availaibility> = arrayListOf(),
    var interest: ArrayList<Interest> = arrayListOf()
)

data class UpdateUser(
    var fname: String ="",
    var lname: String ="",
    var userName: String =""
)