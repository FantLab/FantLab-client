package ru.fantlab.android.ui.modules.authors

import android.view.View
import io.reactivex.functions.Consumer
import ru.fantlab.android.R
import ru.fantlab.android.data.dao.model.AuthorInList
import ru.fantlab.android.provider.rest.DataManager
import ru.fantlab.android.ui.base.mvp.presenter.BasePresenter

class AuthorsPresenter : BasePresenter<AuthorsMvp.View>(), AuthorsMvp.Presenter {

	private var authors: ArrayList<AuthorInList> = ArrayList()

	override fun getAuthors(): ArrayList<AuthorInList> = authors

	override fun onItemClick(position: Int, v: View?, item: AuthorInList) {
		view?.onItemClicked(item)
	}

	override fun onItemLongClick(position: Int, v: View?, item: AuthorInList?) {
	}

	override fun onError(throwable: Throwable) {
		onWorkOffline()
		super.onError(throwable)
	}

	override fun onWorkOffline() {
		sendToView { it.showMessage(R.string.error, R.string.failed_data) }
	}

	override fun onReload() {
		makeRestCall(
				DataManager.getAuthors()
						.toObservable(),
				Consumer { authorsResponse ->
					sendToView { it.onNotifyAdapter(authorsResponse.authors) }
				}
		)
	}
}