package com.oneclick.utils.library

import android.app.Application
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Author : Darshan Popat
 * Date : 09 December 2020
 * Description : Utils class for Handling DateFormation and Conversion in a single class only.
 */
open class AppDateFormatUtils(private var application: Application) {

    companion object {
        fun newInstance(application: Application): AppDateFormatUtils {
            return AppDateFormatUtils(application)
        }

        const val DATE_FORMAT_IN_TIME = "hh:mm a"
        const val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DATE_FORMAT_DATE = "dd MMM yyyy hh:mm a"

    }

    private val fromServer = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, Locale.getDefault())

    /**
     * Author : Darshan Popat
     * Date : 09 December 2020
     * Description : Following function will take date in string format, and convert it to hh:mm a (01:00 PM) format.
     */
    fun formatDate(date: String, conversationFormat: String, isTimeInUTC: Boolean): String {
        return if (date != "") {
            if (isTimeInUTC) {
                fromServer.timeZone = TimeZone.getTimeZone("UTC")
            } else {
                fromServer.timeZone = TimeZone.getDefault()
            }

            val myFormat = SimpleDateFormat(conversationFormat, Locale.getDefault())
            myFormat.format(fromServer.parse(date))
        } else {
            ""
        }
    }

    /**
     * Author : Darshan Popat
     * Date : 09 December 2020
     * Description : Following function will take date in string format, and convert it to dd MMM yyyy with suffix (10th May 2020) format.
     */
    fun formatDateWithSuffix(date: String, isTimeInUTC: Boolean): String {
        if (date != "") {
            var stringDateSuffix: String = ""

            if (isTimeInUTC) {
                fromServer.timeZone = TimeZone.getTimeZone("UTC")
            } else {
                fromServer.timeZone = TimeZone.getDefault()
            }

            val formatSplitDate = SimpleDateFormat("dd", Locale.getDefault())
            val formatSplitMonthYear = SimpleDateFormat("MMM yyyy", Locale.getDefault())

            stringDateSuffix = when (formatSplitDate.format(fromServer.parse(date)).toInt()) {
                1, 21, 31 -> {
                    "st"
                }
                2, 22 -> {
                    "nd"
                }
                3, 23 -> {
                    "rd"
                }
                else -> {
                    "th"
                }
            }

            return application.getString(
                R.string.text_date_format,
                formatSplitDate.format(
                    fromServer.parse(date)
                ),
                stringDateSuffix,
                formatSplitMonthYear.format(
                    fromServer.parse(date)
                )
            )
        } else {
            return ""
        }
    }

    /**
     * Author : Darshan Popat
     * Date : 08 April 2021
     * Description : Following function will take two dates in string format, and find the difference between them.
     */
    fun printDifferenceBetweenDates(
        startDate: String,
        endDate: String,
        isTimeInUTC: Boolean
    ): String {
        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            if (isTimeInUTC) {
                fromServer.timeZone = TimeZone.getTimeZone("UTC")
            } else {
                fromServer.timeZone = TimeZone.getDefault()
            }

            val dateStart: Date = fromServer.parse(startDate) as Date
            val dateEnd: Date = fromServer.parse(endDate) as Date

            val differenceTime = dateEnd.time - dateStart.time

            var stringAgoTime = ""

            if (TimeUnit.MILLISECONDS.toHours(differenceTime) > 0) {
                stringAgoTime =
                    String.format("%02dh ", TimeUnit.MILLISECONDS.toHours(differenceTime))
            }

            if ((TimeUnit.MILLISECONDS.toMinutes(differenceTime) - TimeUnit.HOURS.toMinutes(
                    TimeUnit.MILLISECONDS.toHours(
                        differenceTime
                    )
                )) > 0
            ) {
                stringAgoTime +=
                    String.format(
                        "%02dm ",
                        (TimeUnit.MILLISECONDS.toMinutes(differenceTime) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(
                                differenceTime
                            )
                        ))
                    )
            }

            if ((TimeUnit.MILLISECONDS.toSeconds(differenceTime) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        differenceTime
                    )
                )) > 0
            ) {
                stringAgoTime +=
                    String.format(
                        "%02ds",
                        (TimeUnit.MILLISECONDS.toSeconds(differenceTime) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                differenceTime
                            )
                        ))
                    )
            }

            return stringAgoTime.trim()

        } else {
            return ""
        }

    }

}