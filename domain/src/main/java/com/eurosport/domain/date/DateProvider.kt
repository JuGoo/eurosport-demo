package com.eurosport.domain.date

import java.util.Date

interface DateProvider {
    val currentDate: Date
}

object DateProviderImpl : DateProvider {
    override val currentDate: Date
        get() = Date()
}