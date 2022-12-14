package e.erik.tasktimer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Task(val name: String, val description: String, val sortOrder: Int) : Parcelable {
    var id: Long = 0
}