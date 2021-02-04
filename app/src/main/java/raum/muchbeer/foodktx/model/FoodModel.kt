package raum.muchbeer.foodktx.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FoodModel(
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<Food>,
    @SerializedName("totalResults")
    val totalResults: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("results"),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeInt(offset)
        parcel.writeInt(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodModel> {
        override fun createFromParcel(parcel: Parcel): FoodModel {
            return FoodModel(parcel)
        }

        override fun newArray(size: Int): Array<FoodModel?> {
            return arrayOfNulls(size)
        }
    }
}