package com.ae.ajnetworkdemo

data class UserResponse(
    var page: Int = 0,
    var per_page: Int = 0,
    var total: Int = 0,
    var total_pages: Int = 0,
    var data: ArrayList<SubData>,
    var support: SupportData
)

data class SubData(
    var id: Int = 0,
    var email: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var avatar: String? = null
)

data class SupportData(
    var url: String? = null,
    var text: String? = null
)

data class NewUser(
    var name: String? = null,
    var job: String? = null
)

data class NewUserResponse(
    var name: String? = null,
    var job: String? = null,
    var id: String? = null,
    var createdAt: String? = null
)