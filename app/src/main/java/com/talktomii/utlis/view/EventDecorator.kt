package com.talktomii.utlis.view

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class EventDecorator(private val color: Int, private var dates: Collection<CalendarDay?>?) :
    DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates!!.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(5F, color))
    }


}