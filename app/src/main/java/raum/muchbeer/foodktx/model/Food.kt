package raum.muchbeer.foodktx.model

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("image")
    val image : String
)
