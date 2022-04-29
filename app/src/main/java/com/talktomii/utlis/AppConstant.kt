package com.talktomii.utlis

import com.talktomii.R
import com.talktomii.data.model.admin1.BadgesItem

object LoginType {
    const val INFLUANCER_ROLE = "61aa0389803e260c3821ad14"
    const val USER_ROLE = "61aa0369803e260c3821ad0a"
}


const val USER_DATA = "user data"
const val USER_LANGUAGE = "user language"
const val PUSH_DATA = "PUSH_DATA"
const val SUCESS = "200"
const val uid = "6239a38713ac0523946f5872"

const val PLACE_FINDER_KEY = "AIzaSyC1v2OWDn7WJ93Qp_XC1Ofl1zlOtiB_zh4"

var monthsarray = arrayOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

class AppConstant {
    companion object {
        public fun getBadgesArrayList(): ArrayList<BadgesItem> {
            var badgesArrayList: ArrayList<BadgesItem> = arrayListOf()
            badgesArrayList.add(BadgesItem(0, "Fun", R.drawable.ic_badges_5))
            badgesArrayList.add(BadgesItem(0, "Humor", R.drawable.ic_badges_4))
            badgesArrayList.add(BadgesItem(0, "Smart", R.drawable.ic_badge_1))
            badgesArrayList.add(BadgesItem(0, "Knowledgeable", R.drawable.ic_badge_2))
            badgesArrayList.add(BadgesItem(0, "Kind", R.drawable.ic_badges_6))
            badgesArrayList.add(BadgesItem(0, "Talkative", R.drawable.ic_badge_3))
            return badgesArrayList
        }
    }
}




