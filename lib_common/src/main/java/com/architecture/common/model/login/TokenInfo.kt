package com.architecture.common.model.login
import com.google.gson.annotations.SerializedName


/**
 *
 * Created by xuying on 2018/5/9.
 */

data class TokenInfo(
    @SerializedName("access_token") var accessToken: String = "",
    @SerializedName("expires_in") var expiresIn: Int = 0,
    @SerializedName("token_type") var tokenType: String = "",
    @SerializedName("scope") var scope: Any? = Any(),
    @SerializedName("refresh_token") var refreshToken: String = "",
    @SerializedName("expire_at") var expireAt: Int = 0,
    @SerializedName("im_code") var imCode: Int = 0,
    @SerializedName("im_msg") var imMsg: String = "",
    @SerializedName("im_accid") var imAccid: String = "",
    @SerializedName("im_token") var imToken: String = ""
)