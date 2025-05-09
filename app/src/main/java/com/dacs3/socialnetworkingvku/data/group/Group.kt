package com.dacs3.socialnetworkingvku.data.group

import com.dacs3.socialnetworkingvku.data.user.User
import java.time.LocalDateTime

data class Group(

    val groupId: Long = 0,

    val groupname: String = "",  // Đảm bảo groupname không phải null

    val owner: User,  // Hibernate sẽ gán owner sau

    val createAt: LocalDateTime = LocalDateTime.now()
)