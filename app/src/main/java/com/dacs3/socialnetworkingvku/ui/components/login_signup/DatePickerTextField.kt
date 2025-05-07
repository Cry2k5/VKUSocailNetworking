package com.dacs3.socialnetworkingvku.ui.components.login_signup

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.util.*

@Composable
fun DatePickerTextField() {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    // Lưu trữ dialog để không bị tạo lại mỗi recomposition
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                selectedDate = LocalDate.of(year, month + 1, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Hiển thị text field với icon lịch
    CustomTextField(
        value = selectedDate?.toString() ?: "",
        onValueChange = {}, // Không cho phép nhập tay
        label = "Ngày sinh",
        trailingIcon = Icons.Default.CalendarToday,
        onTrailingIconClick = { datePickerDialog.show() }
    )
}
