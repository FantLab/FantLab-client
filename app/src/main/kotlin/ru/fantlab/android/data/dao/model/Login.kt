package ru.fantlab.android.data.dao.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.requery.*
import kotlinx.android.parcel.Parcelize
import ru.fantlab.android.App
import ru.fantlab.android.data.dao.model.LoginType.USER_ID
import ru.fantlab.android.helper.PrefGetter
import java.util.*

@Parcelize
@Entity @Table(name = "login")
data class Login(
		@get:Column(name = "id") @get:Key var userId: Int,
		@get:Column var login: String,
		@get:Column var avatar: String,
		@get:Column var fio: String,
		@get:Column var sex: String,
		@SerializedName("birthday") @get:Column(name = "birthday") var birthDay: Date?,
		@get:Column(name = "class") var userClass: Int,
		@get:Column(name = "class_name") var className: String,
		@get:Column var level: Float,
		@get:Column var location: String?,
		@get:Column(name = "country_name") var countryName: String?,
		@get:Column(name = "country_id") var countryId: Int?,
		@get:Column(name = "city_name") var cityName: String?,
		@get:Column(name = "city_id") var cityId: Int?,
		@get:Column(name = "reg_date") var dateOfReg: Date,
		@get:Column(name = "last_action_date") var dateOfLastAction: Date,
		@get:Column(name = "timer") var userTimer: Long,
		@get:Column var sign: String?,
		@SerializedName("markcount") @get:Column(name = "mark_count") var markCount: Int,
		@SerializedName("responsecount") @get:Column(name = "response_count") var responseCount: Int,
		@SerializedName("descriptioncount") @get:Column(name = "description_count") var descriptionCount: Int,
		@SerializedName("classifcount") @get:Column(name = "classif_count") var classifCount: Int,
		@SerializedName("votecount") @get:Column(name = "vote_count") var voteCount: Int,
		@SerializedName("topiccount") @get:Column(name = "topic_count") var topicCount: Int,
		@SerializedName("messagecount") @get:Column(name = "message_count") var messageCount: Int,
		@get:Column(name = "bookcase_count") var bookcaseCount: Int,
		@SerializedName("curator_autors") @get:Column(name = "curator_authors") var curatorAuthors: Int,
		@get:Column(name = "ticket_count") var ticketsCount: Int,
		@SerializedName("autor_id") @get:Column(name = "author_id") var authorId: Int?,
		@SerializedName("autor_name") @get:Column(name = "author_name") var authorName: String?,
		@SerializedName("autor_name_orig") @get:Column(name = "author_name_orig") var authorNameOrig: String?,
		@SerializedName("autor_is_opened") @get:Column(name = "author_is_opened") var authorIsOpened: Int?,
		@get:Column(name = "blog_id") var blogId: Int?,
		@get:Column(name = "blocked") var block: Int,
		@get:Column(name = "block_start_date") var dateOfBlock: Date?,
		@get:Column(name = "block_end_date") var dateOfBlockEnd: Date?,
		@get:Column var token: String?
) : Persistable, Parcelable

fun Login.saveLoggedUser(): Observable<Boolean> {
	return Observable.fromPublisher { s ->
		this.token = PrefGetter.getToken()
		App.dataStore
				.toBlocking()
				.delete(Login::class.java)
				.where(USER_ID.eq(this.userId))
				.get()
				.value()
		App.dataStore
				.toBlocking()
				.insert(this)
		s.onNext(true)
		s.onComplete()
	}
}

fun getLoggedUser(): Login? {
	return App.dataStore
			.select(Login::class.java)
			.get()
			.firstOrNull()
}

fun logout() {
	val loggedUser = getLoggedUser() ?: return
	App.dataStore.toBlocking().delete(loggedUser)
}