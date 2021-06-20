package vn.hoanguyen.learning.bottomsheet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Hoa Nguyen on Jun 15 2021.
 */
@Parcelize
data class Person(
    val name: String
) : Parcelable
