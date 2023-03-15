package com.example.calendar_custom_library.theme

sealed class TypeCalenderCustom {
    object DefaultCalender :TypeCalenderCustom()
    object CalenderMarkColor : TypeCalenderCustom()

    object CalenderDetailed : TypeCalenderCustom()
}