package ru.fantlab.android.data.dao.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Work(
		val authors: ArrayList<Author>,
		val image: String?,
		@SerializedName("image_preview") val preview: String?,
		@SerializedName("lang") val lang: String?,
		@SerializedName("lang_code") val lang_code: String?,
		@SerializedName("publish_statuses") val publishStatuses: ArrayList<String>,
		val rating: Rating,
		val title: String,
		@SerializedName("val_responsecount") val responseCount: Int,
		@SerializedName("work_description") val description: String?,
		@SerializedName("work_description_author") val descriptionAuthor: String?,
		@SerializedName("work_id") val id: Int,
		@SerializedName("work_lp") val hasLinguaProfile: Int?,
		@SerializedName("work_name") val name: String,
		@SerializedName("work_name_alts") val nameAlts: ArrayList<String>,
		@SerializedName("work_name_bonus") val nameBonus: String?,
		@SerializedName("work_name_orig") val nameOrig: String,
		@SerializedName("work_notes") val notes: String,
		@SerializedName("work_notfinished") val notFinished: Int,
		@SerializedName("work_parent") val parentId: Int,
		@SerializedName("work_preparing") val preparing: Int,
		@SerializedName("work_published") val published: Int,
		@SerializedName("work_type") val type: String,
		@SerializedName("work_type_id") val typeId: Int,
		@SerializedName("work_type_name") val typeName: String,
		@SerializedName("work_year") val year: Int?,
		@SerializedName("work_year_of_write") val yearOfWrite: Int?
) : Parcelable {
	@Keep
	@Parcelize
	data class Author(
			val id: Int,
			@SerializedName("is_opened") val isOpened: Int,
			val name: String,
			@SerializedName("name_orig") val nameOrig: String,
			val type: String
	) : Parcelable {
		override fun toString() = name
	}

	@Keep
	@Parcelize
	data class Rating(
			val rating: String,
			@SerializedName("voters") val votersCount: String
	) : Parcelable
}