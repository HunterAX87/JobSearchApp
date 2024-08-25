package com.example.jobsearchapp.data
import android.os.Parcel
import android.os.Parcelable

data class OffersResponse(
    val offers: List<Offer>,
    val vacancies: List<Vacancy> // Если вам нужны вакансии, создайте соответствующий класс
)

data class Offer(
    val id: String,
    val title: String,
    val link: String,
    val button: Button? // Обязательно добавьте класс Button
)

data class Button(
    val text: String
)





data class Vacancy(
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int,
    val description: String?,
    val responsibilities: String?,
    val questions: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        lookingNumber = parcel.readInt(),
        title = parcel.readString() ?: "",
        address = parcel.readParcelable(Address::class.java.classLoader) ?: Address("", "", ""),
        company = parcel.readString() ?: "",
        experience = parcel.readParcelable(Experience::class.java.classLoader) ?: Experience("", ""),
        publishedDate = parcel.readString() ?: "",
        isFavorite = parcel.readByte() != 0.toByte(),
        salary = parcel.readParcelable(Salary::class.java.classLoader) ?: Salary("", ""),
        schedules = parcel.createStringArrayList() ?: emptyList(),
        appliedNumber = parcel.readInt(),
        description = parcel.readString(),
        responsibilities = parcel.readString(),
        questions = parcel.createStringArrayList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(lookingNumber)
        parcel.writeString(title)
        parcel.writeParcelable(address, flags)
        parcel.writeString(company)
        parcel.writeParcelable(experience, flags)
        parcel.writeString(publishedDate)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeParcelable(salary, flags)
        parcel.writeStringList(schedules)
        parcel.writeInt(appliedNumber)
        parcel.writeString(description)
        parcel.writeString(responsibilities)
        parcel.writeStringList(questions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vacancy> {
        override fun createFromParcel(parcel: Parcel): Vacancy {
            return Vacancy(parcel)
        }

        override fun newArray(size: Int): Array<Vacancy?> {
            return arrayOfNulls(size)
        }
    }
}

data class Address(
    val town: String? = "", // Значение по умолчанию
    val street: String? = "", // Значение по умолчанию
    val house: String? = "" // Значение по умолчанию
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(town)
        parcel.writeString(street)
        parcel.writeString(house)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun hashCode(): Int {
        var result = town?.hashCode() ?: 0
        result = 31 * result + (street?.hashCode() ?: 0)
        result = 31 * result + (house?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (town != other.town) return false
        if (street != other.street) return false
        if (house != other.house) return false

        return true
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}

data class Experience(
    val previewText: String?,
    val fullText: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(previewText)
        parcel.writeString(fullText)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun hashCode(): Int {
        var result = previewText?.hashCode() ?: 0
        result = 31 * result + (fullText?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Experience

        if (previewText != other.previewText) return false
        if (fullText != other.fullText) return false

        return true
    }

    companion object CREATOR : Parcelable.Creator<Experience> {
        override fun createFromParcel(parcel: Parcel): Experience {
            return Experience(parcel)
        }

        override fun newArray(size: Int): Array<Experience?> {
            return arrayOfNulls(size)
        }
    }
}

data class Salary(
    val short: String? = "", // Значение по умолчанию
    val full: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(short)
        parcel.writeString(full)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun hashCode(): Int {
        var result = short?.hashCode() ?: 0
        result = 31 * result + (full?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Salary

        if (short != other.short) return false
        if (full != other.full) return false

        return true
    }

    companion object CREATOR : Parcelable.Creator<Salary> {
        override fun createFromParcel(parcel: Parcel): Salary {
            return Salary(parcel)
        }

        override fun newArray(size: Int): Array<Salary?> {
            return arrayOfNulls(size)
        }
    }
}

