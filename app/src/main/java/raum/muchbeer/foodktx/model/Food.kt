package raum.muchbeer.foodktx.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "food_tbl")
data class Food(
    @PrimaryKey
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("image")
    val image : String
)
