package ru.fantlab.android.ui.modules.author.overview

import android.os.Bundle
import io.reactivex.functions.Consumer
import ru.fantlab.android.R
import ru.fantlab.android.helper.BundleConstant
import ru.fantlab.android.provider.rest.DataManager
import ru.fantlab.android.ui.base.mvp.presenter.BasePresenter

class AuthorOverviewPresenter : BasePresenter<AuthorOverviewMvp.View>(),
		AuthorOverviewMvp.Presenter {

	@com.evernote.android.state.State
	var authorId: Int? = null

	override fun onFragmentCreated(bundle: Bundle?) {
		if (bundle?.getInt(BundleConstant.EXTRA) == null) {
			throw NullPointerException("Either bundle or AuthorId is null")
		}
		authorId = bundle.getInt(BundleConstant.EXTRA)
		authorId?.let {
			makeRestCall(
					DataManager.getAuthor(it, showBiography = true)
							.map { it.get() }
							.toObservable(),
					Consumer { authorResponse ->
						sendToView { it.onInitViews(authorResponse.author, authorResponse.biography) }
					}
			)
		}
	}

	override fun onError(throwable: Throwable) {
		authorId?.let { onWorkOffline(it) }
		super.onError(throwable)
	}

	override fun onWorkOffline(id: Int) {
		sendToView { it.showMessage(R.string.error, R.string.failed_data) }
	}
}