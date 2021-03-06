package ru.fantlab.android.provider.rest

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.rx.rxObject
import com.github.kittinunf.fuel.rx.rxResponsePair
import com.github.kittinunf.fuel.rx.rxString
import com.github.kittinunf.result.map
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.reactivex.Single
import ru.fantlab.android.BuildConfig
import ru.fantlab.android.data.dao.model.AttachUrl
import ru.fantlab.android.data.dao.model.Login
import ru.fantlab.android.data.dao.response.*
import java.io.File

object DataManager {

	val gson = Gson()

	fun getAuthors(sort: Int): Single<AuthorsResponse> =
			getAuthorsPath(sort)
					.httpGet()
					.rxObject(AuthorsResponse.Deserializer())
					.map { it.get() }

	fun getAwards(
			nonfant: Boolean,
			sortOption: AwardsSortOption = AwardsSortOption.BY_NAME
	): Single<AwardsResponse> =
			getAwardsPath(nonfant, sortOption)
					.httpGet()
					.rxObject(AwardsResponse.Deserializer())
					.map { it.get() }

	fun getForums(): Single<ForumsResponse> =
			getForumsPath()
					.httpGet()
					.rxObject(ForumsResponse.Deserializer())
					.map { it.get() }

	fun getTopics(id: Int,
				  page: Int,
				  perPage: Int
	): Single<ForumResponse> =
			getTopicsPath(id, page, perPage)
					.httpGet()
					.rxObject(ForumResponse.Deserializer())
					.map { it.get() }

	fun getTopicMessages(id: Int,
						 page: Int,
						 order: TopicMessagesSortOption = TopicMessagesSortOption.BY_NEW,
						 perPage: Int
	): Single<ForumTopicResponse> =
			getTopicMessagesPath(id, page, order, perPage)
					.httpGet()
					.rxObject(ForumTopicResponse.Deserializer())
					.map { it.get() }

	fun getCommunities(): Single<CommunitiesResponse> =
			getCommunitiesPath()
					.httpGet()
					.rxObject(CommunitiesResponse.Deserializer())
					.map { it.get() }

	fun getBlogs(page: Int,
				 limit: Int,
				 sortOption: BlogsSortOption
	): Single<BlogsResponse> =
			getBlogsPath(page, limit, sortOption)
					.httpGet()
					.rxObject(BlogsResponse.Deserializer())
					.map { it.get() }

	fun getBlogArticles(id: Int,
						page: Int,
						limit: Int
	): Single<BlogArticlesResponse> =
			getBlogArticlesPath(id, page, limit)
					.httpGet()
					.rxObject(BlogArticlesResponse.Deserializer())
					.map { it.get() }

	fun getBlogArticle(id: Int
	): Single<BlogArticleResponse> =
			getBlogArticlePath(id)
					.httpGet()
					.rxObject(BlogArticleResponse.Deserializer())
					.map { it.get() }

	fun getAuthor(
			id: Int,
			showBiography: Boolean = false,
			showAwards: Boolean = false,
			showLinguaProfile: Boolean = false,
			showBiblioBlocks: Boolean = false,
			showClassificatory: Boolean = false,
			sortOption: BiblioSortOption = BiblioSortOption.BY_YEAR
	): Single<AuthorResponse> =
			getAuthorPath(id, showBiography, showAwards, showLinguaProfile, showBiblioBlocks, showClassificatory, sortOption)
					.httpGet()
					.rxObject(AuthorResponse.Deserializer())
					.map { it.get() }

	fun getAuthorEditions(
			authorId: Int,
			showEditionsBlocks: Boolean = false
	): Single<AuthorEditionsResponse> =
			getAuthorEditionsPath(authorId, showEditionsBlocks)
					.httpGet()
					.rxObject(AuthorEditionsResponse.Deserializer())
					.map { it.get() }

	fun getAuthorResponses(
			authorId: Int,
			page: Int = 1,
			sortOption: ResponsesSortOption = ResponsesSortOption.BY_DATE
	): Single<ResponsesResponse> =
			getAuthorResponsesPath(authorId, page, sortOption)
					.httpGet()
					.rxObject(ResponsesResponse.Deserializer(perPage = 50))
					.map { it.get() }

	fun getAward(
			id: Int,
			showNomi: Boolean,
			showContests: Boolean,
			sortOption: AwardSortOption = AwardSortOption.BY_CONTEST
	): Single<AwardResponse> =
			getAwardPath(id, showNomi, showContests, sortOption)
					.httpGet()
					.rxObject(AwardResponse.Deserializer())
					.map { it.get() }

	fun getPersonAwards(
			personId: Int,
			personType: String
	): Single<PersonAwardsResponse> =
			getPersonAwardsPath(personId, personType)
					.httpGet()
					.rxObject(PersonAwardsResponse.Deserializer())
					.map { it.get() }
	fun getWork(
			id: Int,
			showAwards: Boolean = false,
			showChildren: Boolean = false,
			showClassificatory: Boolean = false,
			showEditionsBlocks: Boolean = false,
			showEditionsInfo: Boolean = false,
			showFilms: Boolean = false,
			showLinguaProfile: Boolean = false,
			showParents: Boolean = false,
			showTranslations: Boolean = false
	): Single<WorkResponse> =
			getWorkPath(id, showAwards, showChildren, showClassificatory, showEditionsBlocks,
					showEditionsInfo, showFilms, showLinguaProfile, showParents, showTranslations)
					.httpGet()
					.rxObject(WorkResponse.Deserializer())
					.map { it.get() }

	fun getWorkResponses(
			workId: Int,
			page: Int = 1,
			sortOption: ResponsesSortOption = ResponsesSortOption.BY_DATE
	): Single<ResponsesResponse> =
			getWorkResponsesPath(workId, page, sortOption)
					.httpGet()
					.rxObject(ResponsesResponse.Deserializer(perPage = 15))
					.map { it.get() }

	fun getWorkAnalogs(
			workId: Int
	): Single<WorkAnalogsResponse> =
			getWorkAnalogsPath(workId)
					.httpGet()
					.rxObject(WorkAnalogsResponse.Deserializer())
					.map { it.get() }

	fun getEdition(
			id: Int,
			showContent: Boolean = false,
			showAdditionalImages: Boolean = false
	): Single<EditionResponse> =
			getEditionPath(id, showContent, showAdditionalImages)
					.httpGet()
					.rxObject(EditionResponse.Deserializer())
					.map { it.get() }

	fun getUser(
			id: Int
	): Single<UserResponse> =
			getUserPath(id)
					.httpGet()
					.rxObject(UserResponse.Deserializer())
					.map { it.get() }

	fun getTranslatorInformation(
			id: Int,
			showBio: Boolean = false,
			showAwards: Boolean = false,
			showTranslated: Boolean = false
	): Single<TranslatorResponse> =
			getTranslatorInformationPath(id, showBio, showAwards, showTranslated)
					.httpGet()
					.rxObject(TranslatorResponse.Deserializer())
					.map { it.get() }

	fun getUserMarks(
			userId: Int,
			page: Int = 1,
			typeOption: MarksTypeOption = MarksTypeOption.ALL,
			sortOption: MarksSortOption = MarksSortOption.BY_MARK
	): Single<MarksResponse> =
			getUserMarksPath(userId, page, typeOption, sortOption)
					.httpGet()
					.rxObject(MarksResponse.Deserializer(perPage = 200))
					.map { it.get() }

	fun getUserMarksMini(
			userId: Int,
			workIds: String
	): Single<MarksMiniResponse> =
			getUserMarksMiniPath(userId, workIds)
					.httpGet()
					.rxObject(MarksMiniResponse.Deserializer())
					.map { it.get() }

	fun getUserResponses(
			userId: Int,
			page: Int = 1,
			sortOption: ResponsesSortOption = ResponsesSortOption.BY_DATE
	): Single<ResponsesResponse> =
			getUserResponsesPath(userId, page, sortOption)
					.httpGet()
					.rxObject(ResponsesResponse.Deserializer(perPage = 50))
					.map { it.get() }

	fun getUserBookcases(
			userId: Int
	): Single<BookcasesResponse> =
			getUserBookcasesPath(userId)
					.httpGet()
					.rxObject(BookcasesResponse.Deserializer())
					.map { it.get() }

	fun getPersonalBookcases(
	): Single<BookcasesResponse> =
			getPersonalBookcasesPath()
					.httpGet()
					.rxObject(BookcasesResponse.Deserializer())
					.map { it.get() }

	fun getPersonalBookcaseInformation(
			bookcaseId: Int
	): Single<BookcaseInformationResponse> =
			getPersonalBookcaseInformationPath(bookcaseId)
					.httpGet()
					.rxObject(BookcaseInformationResponse.Deserializer())
					.map { it.get() }

	fun getPersonalEditionBookcase(
			bookcaseId: Int,
			offset: Int = 0
	): Single<BookcaseEditionsResponse> =
			getPersonalBookcasePath(bookcaseId, offset)
					.httpGet()
					.rxObject(BookcaseEditionsResponse.Deserializer(perPage = 10))
					.map { it.get() }

	fun getPersonalWorkBookcase(
			bookcaseId: Int,
			offset: Int = 0
	): Single<BookcaseWorksResponse> =
			getPersonalBookcasePath(bookcaseId, offset)
					.httpGet()
					.rxObject(BookcaseWorksResponse.Deserializer(perPage = 10))
					.map { it.get() }

	fun getPersonalFilmBookcase(
			bookcaseId: Int,
			offset: Int = 0
	): Single<BookcaseFilmsResponse> =
			getPersonalBookcasePath(bookcaseId, offset)
					.httpGet()
					.rxObject(BookcaseFilmsResponse.Deserializer(perPage = 10))
					.map { it.get() }

	fun getUserEditionBookcase(
			userId: Int,
			bookcaseId: Int,
			offset: Int = 0
	): Single<BookcaseEditionsResponse> =
			getUserBookcasePath(userId, bookcaseId, offset)
					.httpGet()
					.rxObject(BookcaseEditionsResponse.Deserializer(perPage = 10))
					.map { it.get() }

	fun getUserWorkBookcase(
			userId: Int,
			bookcaseId: Int,
			offset: Int = 0
	): Single<BookcaseWorksResponse> =
			getUserBookcasePath(userId, bookcaseId, offset)
					.httpGet()
					.rxObject(BookcaseWorksResponse.Deserializer(perPage = 10))
					.map { it.get() }

	fun getUserFilmBookcase(
			userId: Int,
			bookcaseId: Int,
			offset: Int = 0
	): Single<BookcaseFilmsResponse> =
			getUserBookcasePath(userId, bookcaseId, offset)
					.httpGet()
					.rxObject(BookcaseFilmsResponse.Deserializer(perPage = 10))
					.map { it.get() }

	fun createBookcase(
			type: String,
			name: String,
			publicBookcase: String,
			bookcaseComment: String?
	): Single<String> =
			createBookcasePath()
					.httpPost(listOf(
							"name" to name,
							"type" to type,
							"shared" to publicBookcase,
							"comment" to bookcaseComment
					))
					.rxString()
					.map { it.get() }

	fun updateBookcase(
			bookcaseId: Int,
			type: String,
			name: String,
			publicBookcase: String,
			bookcaseComment: String?
	): Single<String> =
			updateBookcasePath(bookcaseId)
					.httpPost(listOf(
							"name" to name,
							"type" to type,
							"shared" to publicBookcase,
							"comment" to bookcaseComment
					))
					.rxString()
					.map { it.get() }

	fun deletePersonalBookcase(
			bookcaseId: Int
	): Single<String> =
			deletePersonalBookcasePath(bookcaseId)
					.httpDelete()
					.rxString()
					.map { it.get() }

	fun includeItemToBookcase(
			bookcaseId: Int,
			entityId: Int,
			include: String
	): Single<String> =
			includeItemToBookcasePath(bookcaseId, entityId, include)
					.httpPost()
					.rxString()
					.map { it.get() }

	fun updateBookcaseItemComment(
			bookcaseId: Int,
			entityId: Int,
			text: String
	): Single<String> =
			updateBookcaseItemCommentPath(bookcaseId, entityId)
					.httpPost(listOf(
							"txt" to text
					))
					.rxString()
					.map { it.get() }

	fun getBookcaseInclusions(
			bookcaseType: String,
			entityId: Int
	): Single<BookcaseInclusionResponse> =
			getBookcaseInclusionsPath(bookcaseType, entityId)
					.httpGet()
					.rxObject(BookcaseInclusionResponse.Deserializer())
					.map { it.get() }


	fun sendUserMark(
			workId: Int,
			toWorkId: Int,
			mark: Int
	): Single<MarkResponse> =
			sendUserMarkPath(workId, toWorkId, mark)
					.httpGet()
					.rxObject(MarkResponse.Deserializer())
					.map { it.get() }

	fun sendResponseVote(
			responseId: Int,
			voteType: String
	): Single<String> =
			sendResponseVotePath(responseId, voteType)
					.httpGet()
					.rxString()
					.map { it.get() }

	fun sendArticleLike(
			articleId: Int,
			likeType: Boolean
	): Single<BlogArticlesVoteResponse> =
			sendArticleLikePath(articleId)
					.httpPut(listOf("like" to likeType))
					.rxObject(BlogArticlesVoteResponse.Deserializer())
					.map { it.get() }

	fun sendMessage(
			userId: Int,
			message: CharSequence?,
			mode: String
	): Single<String> =
			sendMessagePath(userId)
					.httpPost(listOf(
							"message" to message,
							"mode" to mode,
							"action" to "/user$userId/sendprivatemessage"
					))
					.rxString()
					.map { it.get() }

	fun sendTopicMessage(
			topicId: Int,
			message: CharSequence?
	): Single<TopicMessageResponse> =
			sendTopicMessagePath(topicId)
					.httpPost(listOf(
							"id" to topicId,
							"message" to message
					))
					.rxObject(TopicMessageResponse.Deserializer())
					.map { it.get() }

	fun sendTopicMessageDraft(
			topicId: Int,
			message: CharSequence?
	): Single<TopicMessageDraftResponse> =
			topicDraftPath(topicId)
					.httpPut(listOf(
							"id" to topicId,
							"message" to message
					))
					.rxObject(TopicMessageDraftResponse.Deserializer())
					.map { it.get() }

	fun deleteTopicMessage(
			messageId: Int
	): Single<String> =
			topicMessagePath(messageId)
					.httpDelete(listOf(
							"id" to messageId
					))
					.rxString()
					.map { it.get() }

	fun editTopicMessage(
			messageId: Int,
			message: CharSequence?
	): Single<String> =
			topicMessagePath(messageId)
					.httpPut(listOf(
							"id" to messageId,
							"message" to message
					))
					.rxString()
					.map { it.get() }

	fun confirmTopicDraft(
			topicId: Int
	): Single<TopicMessageResponse> =
			topicDraftPath(topicId)
					.httpPost()
					.rxObject(TopicMessageResponse.Deserializer())
					.map { it.get() }

	fun deleteTopicDraft(
			topicId: Int
	): Single<String> =
			topicDraftPath(topicId)
					.httpDelete()
					.rxString()
					.map { it.get() }

	fun getTopicAttachUrl(
			messageId: String,
			filename: String,
			filepath: String
	): Single<Triple<String, String, String>> =
			attachTopicUrlPath(messageId)
					.httpGet(listOf(
							"file_name" to filename
					))
					.rxObject(AttachUrlResponse.Deserializer())
					.map { Triple(it.get().url, filename, filepath) }

	fun getTopicDraftAttachUrl(
			topicId: Int,
			filename: String,
			filepath: String
	): Single<Triple<String, String, String>> =
			attachTopicDraftUrlPath(topicId)
					.httpGet(listOf(
							"file_name" to filename
					))
					.rxObject(AttachUrlResponse.Deserializer())
					.map { Triple(it.get().url, filename, filepath) }

	fun sendTopicAttach(
			url: String,
			filename: String,
			file: File,
			requestHandler: ProgressCallback
	): Single<Pair<Int, String>> =
			url
					.httpUpload(method = Method.PUT)
					.add(FileDataPart(file))
					.requestProgress(requestHandler)
					.rxResponsePair()
					.map { Pair(it.first.statusCode, filename) }

	fun sendResponse(
			workId: Int,
			message: CharSequence?,
			mode: String
	): Single<String> =
			sendResponsePath(workId)
					.httpPost(listOf(
							"message" to message,
							"mode" to mode,
							"autosave" to "0"
					))
					.rxString()
					.map { it.get() }

	fun editResponse(
			workId: Int,
			commentId: Int,
			newText: CharSequence?
	): Single<String> =
			editResponsePath(workId, commentId)
					.httpPost(listOf(
							"message" to newText
					))
					.rxString()
					.map { it.get() }

	fun sendClassification(
			workId: Int,
			query: String
	): Single<String> =
			sendClassificationPath(workId, query)
					.httpGet()
					.rxString()
					.map { it.get() }

	fun login(
			login: String,
			password: String
	): Single<LoginResponse> =
			loginPath()
					.httpPost(listOf(
							"login" to login,
							"password" to password
					))
					.rxObject(LoginResponse.Deserializer())
					.map { it.get() }

	fun refreshToken(
			refreshToken: String
	): Login =
			refreshTokenPath()
					.httpPost(listOf(
							"refresh_token" to refreshToken
					))
					.responseObject(LoginResponse.Deserializer())
					.third
					.map { it.login }
					.get()

	fun searchAuthors(
			query: String,
			page: Int = 1
	): Single<SearchAuthorsResponse> =
			searchAuthorsPath(query, page)
					.httpGet()
					.rxObject(SearchAuthorsResponse.Deserializer(perPage = 25))
					.map { it.get() }

	fun searchWorks(
			query: String,
			page: Int = 1
	): Single<SearchWorksResponse> =
			searchWorksPath(query, page)
					.httpGet()
					.rxObject(SearchWorksResponse.Deserializer(perPage = 25))
					.map { it.get() }

	fun searchEditions(
			query: String,
			page: Int = 1
	): Single<SearchEditionsResponse> =
			searchEditionsPath(query, page)
					.httpGet()
					.rxObject(SearchEditionsResponse.Deserializer(perPage = 25))
					.map { it.get() }

	fun searchAwards(
			query: String,
			page: Int = 1
	): Single<SearchAwardsResponse> =
			searchAwardsPath(query, page)
					.httpGet()
					.rxObject(SearchAwardsResponse.Deserializer(perPage = 25))
					.map { it.get() }

	fun getLastResponses(
			page: Int = 1
	): Single<ResponsesResponse> =
			getLastResponsesPath(page)
					.httpGet()
					.rxObject(ResponsesResponse.Deserializer(perPage = 50))
					.map { it.get() }

	fun getPublishers(
			page: Int = 1,
			sort: String = PublishersSortOption.BY_NAME.value,
			countryId: Int = 0,
			type: Int = 0
	): Single<PublishersResponse> =
			getPublishersPath(page, sort, countryId, type)
					.httpGet()
					.rxObject(PublishersResponse.Deserializer(perPage = 250))
					.map { it.get() }

	fun getPubnews(
			page: Int = 1,
			sort: String = PubnewsSortOption.BY_DATE.value,
			lang: Int,
			pubId: Int
	): Single<PubnewsResponse> =
			getPubnewsPath(page, sort, lang, pubId)
					.httpGet()
					.rxObject(PubnewsResponse.Deserializer())
					.map { it.get() }


	fun getPubplans(
			page: Int = 1,
			sort: String = PubplansSortOption.BY_CORRECT.value,
			lang: Int,
			pubId: Int
	): Single<PubplansResponse> =
			getPubplansPath(page, sort, lang, pubId)
					.httpGet()
					.rxObject(PubplansResponse.Deserializer())
					.map { it.get() }

	fun getAutplans(
			page: Int = 1,
			sort: String = AutplansSortOption.BY_CORRECT.value,
			lang: Int
	): Single<AutplansResponse> =
			getAutplansPath(page, sort, lang)
					.httpGet()
					.rxObject(AutplansResponse.Deserializer())
					.map { it.get() }

	fun getNews(
			page: Int = 1,
			perPage: Int = 15
	): Single<NewsResponse> =
			getNewsPath(page, perPage)
					.httpGet()
					.rxObject(NewsResponse.Deserializer(perPage = perPage))
					.map { it.get() }

	fun getContest(
			contestId: Int,
			includeWorks: Boolean
	): Single<ContestResponse> =
			getContestPath(contestId, includeWorks)
					.httpGet()
					.rxObject(ContestResponse.Deserializer())
					.map { it.get() }

	fun getWorkTypes(
	): Single<String> =
			getWorkTypesPath()
					.httpGet()
					.rxString()
					.map { it.get() }
}

//region Sort options
enum class BiblioSortOption(val value: String) {
	BY_YEAR("year"),
	BY_RATING("rating"),
	BY_MARK_COUNT("markcount"),
	BY_RUS_NAME("rusname"),
	BY_NAME("name"),
	BY_WRITE_YEAR("writeyear")
}

enum class MarksTypeOption(val value: String) {
	ALL("all"),
	NOVEL("novel"),
	STORY("story")
}

enum class MarksSortOption(val value: String) {
	BY_MARK("mark"),
	BY_NAME("name"),
	BY_AUTHOR("autor"),
	BY_DATE("date"),
	BY_YEAR("year")
}

enum class TranslationsSortOption(val value: String) {
	BY_YEAR("year"),
	BY_AUTHOR("autor"),
	BY_TYPE("type")
}

enum class ResponsesSortOption(val value: String) {
	BY_DATE("date"),
	BY_RATING("rating"),
	BY_MARK("mark")
}

enum class AwardsSortOption(val value: String) {
	BY_NAME("name"),
	BY_COUNTRY("country"),
	BY_TYPE("type"),
	BY_LANG("lang")
}

enum class AwardSortOption(val value: String) {
	BY_CONTEST("contest"),
	BY_NOMI("nomi")
}

enum class PublishersSortOption(val value: String) {
	BY_NAME("name"),
	BY_COUNT("editions_count"),
	BY_COUNTRY("country"),
	BY_CITY("city")
}

enum class PubnewsSortOption(val value: String) {
	BY_DATE("date"),
	BY_POPULARITY("popularity"),
	BY_TYPE("type"),
	BY_PUBLISHER("pub"),
	BY_AUTHOR("author"),
	BY_NAME("title")
}

enum class PubplansSortOption(val value: String) {
	BY_CORRECT("correct"),
	BY_POPULARITY("popularity"),
	BY_DATE("date"),
	BY_TYPE("type"),
	BY_PUBLISHER("pub"),
	BY_AUTHOR("author"),
	BY_NAME("title")
}

enum class AutplansSortOption(val value: String) {
	BY_CORRECT("correct"),
	BY_POPULARITY("popularity"),
	BY_AUTHOR("author"),
	BY_NAME("title")
}

enum class TopicMessagesSortOption(val value: String) {
	BY_NEW("0"),
	BY_OLD("1")
}

enum class BlogsSortOption(val value: String) {
	BY_UPDATE("update"),
	BY_ARTICLES("article"),
	BY_SUBSCRIBERS("subscriber")
}
//endregion

//region Urls
fun getAuthorsPath(sort: Int) =
		"/autors/$sort".toAbsolutePathWithApiVersion()

fun getAwardsPath(
		nonfant: Boolean,
		sortOption: AwardsSortOption = AwardsSortOption.BY_NAME
) = "/awards?nonfant=${nonfant.toInt()}&sort=${sortOption.value}".toAbsolutePathWithApiVersion()

fun getForumsPath() = "/forums".toAbsolutePathWithTestApiVersion()

fun getCommunitiesPath() = "/communities".toAbsolutePathWithTestApiVersion()

fun getBlogsPath(
		page: Int,
		limit: Int,
		sortOption: BlogsSortOption
) = "/blogs?page=$page&limit=$limit&sort=${sortOption.value}".toAbsolutePathWithTestApiVersion()

fun getBlogArticlesPath(
		id: Int,
		page: Int,
		limit: Int
) = "/blogs/$id?page=$page&limit=$limit".toAbsolutePathWithTestApiVersion()

fun getBlogArticlePath(
		id: Int
) = "/blog_articles/$id".toAbsolutePathWithTestApiVersion()

fun getTopicsPath(
		id: Int,
		page: Int,
		perPage: Int
) = "/forums/$id?page=$page&limit=$perPage".toAbsolutePathWithTestApiVersion()

fun getTopicMessagesPath(
		id: Int,
		page: Int,
		order: TopicMessagesSortOption,
		perPage: Int
) = "/topics/$id?page=$page&sortAsc=${order.value}&limit=$perPage".toAbsolutePathWithTestApiVersion()

fun getAuthorPath(
		id: Int,
		showBiography: Boolean = false,
		showAwards: Boolean = false,
		showLinguaProfile: Boolean = false,
		showBiblioBlocks: Boolean = false,
		showClassificatory : Boolean = false,
		sortOption: BiblioSortOption = BiblioSortOption.BY_YEAR
) = ("/autor/$id?biography=${showBiography.toInt()}&awards=${showAwards.toInt()}" +
		"&la_resume=${showLinguaProfile.toInt()}&biblio_blocks=${showBiblioBlocks.toInt()}&classificatory=${showClassificatory.toInt()}" +
		"&sort=${sortOption.value}").toAbsolutePathWithApiVersion()

fun getAuthorEditionsPath(
		authorId: Int,
		showEditionsBlocks: Boolean = false
) = "/autor/$authorId/alleditions?editions_blocks=${showEditionsBlocks.toInt()}"
		.toAbsolutePathWithApiVersion()

fun getAuthorResponsesPath(
		authorId: Int,
		page: Int = 1,
		sortOption: ResponsesSortOption = ResponsesSortOption.BY_DATE
) = "/autor/$authorId/responses?page=$page&sort=${sortOption.value}".toAbsolutePathWithApiVersion()

fun getPersonAwardsPath(
		personId: Int,
		personType: String
) = "/$personType/$personId/awards".toAbsolutePathWithApiVersion()

fun getAwardPath(
		id: Int,
		showNomi: Boolean,
		showContests: Boolean,
		sortOption: AwardSortOption = AwardSortOption.BY_CONTEST
) = ("/award/$id?include_nomi=${showNomi.toInt()}&include_contests=${showContests.toInt()}" +
		"&sort=${sortOption.value}").toAbsolutePathWithApiVersion()

fun getWorkPath(
		id: Int,
		showAwards: Boolean = false,
		showChildren: Boolean = false,
		showClassificatory: Boolean = false,
		showEditionsBlocks: Boolean = false,
		showEditionsInfo: Boolean = false,
		showFilms: Boolean = false,
		showLinguaProfile: Boolean = false,
		showParents: Boolean = false,
		showTranslations: Boolean = false
) = ("/work/$id?awards=${showAwards.toInt()}&children=${showChildren.toInt()}" +
		"&classificatory=${showClassificatory.toInt()}&editions_blocks=${showEditionsBlocks.toInt()}" +
		"&editions_info=${showEditionsInfo.toInt()}&films=${showFilms.toInt()}" +
		"&la_resume=${showLinguaProfile.toInt()}&parents=${showParents.toInt()}" +
		"&translations=${showTranslations.toInt()}").toAbsolutePathWithApiVersion()

fun getWorkResponsesPath(
		workId: Int,
		page: Int = 1,
		sortOption: ResponsesSortOption = ResponsesSortOption.BY_DATE
) = "/work/$workId/responses?page=$page&sort=${sortOption.value}".toAbsolutePathWithApiVersion()

fun getWorkAnalogsPath(
		workId: Int
) = "/work/$workId/similars".toAbsolutePathWithApiVersion()

fun getEditionPath(
		id: Int,
		showContent: Boolean = false,
		showAdditionalImages: Boolean = false
) = "/edition/$id?content=${showContent.toInt()}&images_plus=${showAdditionalImages.toInt()}"
		.toAbsolutePathWithApiVersion()

fun getUserPath(
		id: Int
) = "/user/$id".toAbsolutePathWithApiVersion()

fun getTranslatorInformationPath(
		id: Int,
		showBio: Boolean = false,
		showAwards: Boolean = false,
		showTranslated: Boolean = false
) = "/translator/$id?awards=${showAwards.toInt()}&biography=${showBio.toInt()}&translated=${showTranslated.toInt()}".toAbsolutePathWithApiVersion()

fun getUserMarksPath(
		userId: Int,
		page: Int = 1,
		typeOption: MarksTypeOption = MarksTypeOption.ALL,
		sortOption: MarksSortOption = MarksSortOption.BY_DATE
) = "/user/$userId/marks/extended?page=$page&type=${typeOption.value}&sort=${sortOption.value}"
		.toAbsolutePathWithApiVersion()

fun getUserMarksMiniPath(
		userId: Int,
		workIds: String
) = "/user/$userId/marks?w=$workIds&mini=1".toAbsolutePathWithApiVersion()

fun getUserResponsesPath(
		userId: Int,
		page: Int = 1,
		sortOption: ResponsesSortOption = ResponsesSortOption.BY_DATE
) = "/user/$userId/responses?page=$page&sort=${sortOption.value}".toAbsolutePathWithApiVersion()

fun getPersonalBookcasesPath(
) = "/my/bookcases".toAbsolutePathWithApiVersion()

fun getUserBookcasesPath(
		userId: Int
) = "/user/$userId/bookcases".toAbsolutePathWithApiVersion()

fun getPersonalBookcaseInformationPath(
		bookcaseId: Int
) = "/my/bookcases/$bookcaseId/description".toAbsolutePathWithApiVersion()

fun getUserBookcasePath(
		userId: Int,
		bookcaseId: Int,
		offset: Int = 0
) = "/user/$userId/bookcase/$bookcaseId?offset=$offset".toAbsolutePathWithApiVersion()

fun getPersonalBookcasePath(
		bookcaseId: Int,
		offset: Int = 0
) = "/my/bookcases/$bookcaseId/items?offset=$offset".toAbsolutePathWithApiVersion()

fun createBookcasePath(
) = "/my/bookcases/add".toAbsolutePathWithApiVersion()

fun updateBookcasePath(
		bookcaseId: Int
) = "/my/bookcases/$bookcaseId/edit".toAbsolutePathWithApiVersion()

fun deletePersonalBookcasePath(
		bookcaseId: Int
) = "/my/bookcases/$bookcaseId/delete".toAbsolutePathWithApiVersion()

fun includeItemToBookcasePath(
		bookcaseId: Int,
		entityId: Int,
		include: String
) = "/my/bookcases/$bookcaseId/items/$entityId/$include".toAbsolutePathWithApiVersion()

fun updateBookcaseItemCommentPath(
		bookcaseId: Int,
		entityId: Int
) = "/my/bookcases/$bookcaseId/items/$entityId/editcomm".toAbsolutePathWithApiVersion()

fun getBookcaseInclusionsPath(
		bookcaseType: String,
		entityId: Int
) = "/my/bookcases/type/$bookcaseType/$entityId".toAbsolutePathWithApiVersion()

fun sendUserMarkPath(
		workId: Int,
		toWorkId: Int,
		mark: Int
) = "/work$workId/ajaxsetmark${mark}towork$toWorkId".toAbsolutePath()

fun sendResponseVotePath(
		responseId: Int,
		voteType: String
) = "/vote$responseId$voteType".toAbsolutePath()

fun sendArticleLikePath(
		articleId: Int
) = "/blog_articles/$articleId/like".toAbsolutePathWithTestApiVersion()

fun sendMessagePath(
		userId: Int
) = "/user$userId/sendprivatemessage"
		.toAbsolutePath()

fun sendTopicMessagePath(
		topicId: Int
) = "/topics/$topicId/message"
		.toAbsolutePathWithTestApiVersion()

fun sendTopicMessageDraftPath(
		topicId: Int
) = "/topics/$topicId/message_draft"
		.toAbsolutePathWithTestApiVersion()

fun topicMessagePath(
		messageId: Int
) = "/forum_messages/$messageId"
		.toAbsolutePathWithTestApiVersion()

fun topicDraftPath(
		topicId: Int
) = "/topics/$topicId/message_draft"
		.toAbsolutePathWithTestApiVersion()

fun attachTopicUrlPath(
		messageId: String
) = "/forum_messages/$messageId/file_upload_url"
		.toAbsolutePathWithTestApiVersion()

fun attachTopicDraftUrlPath(
		topicId: Int
) = "/topics/$topicId/message_draft/file_upload_url"
		.toAbsolutePathWithTestApiVersion()

fun sendResponsePath(
		workId: Int
) = "/work$workId/addresponse".toAbsolutePath()

fun editResponsePath(
		workId: Int,
		commentId: Int
) = "/work$workId/editresponse$commentId/editresponse${commentId}ok".toAbsolutePath()

fun sendClassificationPath(
		workId: Int,
		query: String
) = "/genrevote$workId?$query".toAbsolutePath()

fun loginPath() = "/auth/login".toAbsolutePathWithTestApiVersion()

fun refreshTokenPath() = "/auth/refresh".toAbsolutePathWithTestApiVersion()

fun searchAuthorsPath(
		query: String,
		page: Int = 1
) = "/search-autors?q=$query&page=$page".toAbsolutePathWithApiVersion()

fun searchWorksPath(
		query: String,
		page: Int = 1
) = "/search-works?q=$query&page=$page".toAbsolutePathWithApiVersion()

fun searchEditionsPath(
		query: String,
		page: Int = 1
) = "/search-editions?q=$query&page=$page".toAbsolutePathWithApiVersion()

fun searchAwardsPath(
		query: String,
		page: Int = 1
) = "/search-awards?q=$query&page=$page".toAbsolutePathWithApiVersion()

fun getLastResponsesPath(
		page: Int = 1
) = "/responses?page=$page".toAbsolutePathWithApiVersion()

fun getPublishersPath(
		page: Int,
		sort: String,
		countryId: Int,
		type: Int
) = "/publishers?page=$page&sort=$sort&country_id=$countryId&type=$type".toAbsolutePathWithApiVersion()

fun getPubnewsPath(
		page: Int,
		sort: String,
		lang: Int,
		pubId: Int
) = "/pubnews?page=$page&lang=$lang&sort=$sort&pub_id=$pubId".toAbsolutePathWithApiVersion()

fun getPubplansPath(
		page: Int,
		sort: String,
		lang: Int,
		pubId: Int
) = "/pubplans?page=$page&lang=$lang&sort=$sort&pub_id=$pubId".toAbsolutePathWithApiVersion()

fun getAutplansPath(
		page: Int,
		sort: String,
		lang: Int
) = "/autplans?page=$page&sort=$sort&lang=$lang".toAbsolutePathWithApiVersion()

fun getNewsPath(
		page: Int,
		perPage: Int
) = "/news?page=$page&mpp=$perPage".toAbsolutePathWithApiVersion()

fun getContestPath(
		contestId: Int,
		includeWorks: Boolean
) = "/contest/$contestId?include_works=${includeWorks.toInt()}".toAbsolutePathWithApiVersion()

fun getWorkTypesPath(
) = "/conf/worktypes.json".toAbsolutePathWithApiVersion()
//endregion

//region Utils
fun String.toAbsolutePath() = "https://fantlab.ru$this"

fun String.toAbsolutePathWithApiVersion() = "https://api.fantlab.ru$this"

fun String.toAbsolutePathWithTestApiVersion() = "https://api.test.dev3.fantlab.org/v${BuildConfig.API_REVISION}$this"
//endregion

fun Boolean.toInt(): Int = if (this) 1 else 0

fun JsonElement?.isNullable(): Boolean {
	return when {
		this == null -> true
		this.isJsonNull -> true
		this.toString() == "null" -> true
		else -> false
	}
}