package raum.muchbeer.foodktx.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cheese(
    @PrimaryKey(autoGenerate = true) val id: Int, val name: String
)
