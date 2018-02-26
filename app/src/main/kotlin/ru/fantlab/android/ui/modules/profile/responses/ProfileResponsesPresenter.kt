package ru.fantlab.android.ui.modules.profile.responses

import android.view.View
import io.reactivex.functions.Consumer
import ru.fantlab.android.data.dao.model.Response
import ru.fantlab.android.data.dao.model.getResponses
import ru.fantlab.android.data.dao.model.save
import ru.fantlab.android.helper.observe
import ru.fantlab.android.provider.rest.RestProvider
import ru.fantlab.android.ui.base.mvp.presenter.BasePresenter

class ProfileResponsesPresenter : BasePresenter<ProfileResponsesMvp.View>(),
		ProfileResponsesMvp.Presenter {

	private var responses: ArrayList<Response> = ArrayList()
	private var page: Int = 0
	private var previousTotal: Int = 0
	private var lastPage: Int = Integer.MAX_VALUE

	override fun onItemClick(position: Int, v: View?, item: Response) {
		view?.onItemClicked(item)
	}

	override fun onItemLongClick(position: Int, v: View?, item: Response?) {
	}

	override fun getResponses(): ArrayList<Response> = responses

	override fun getCurrentPage(): Int = page

	override fun getPreviousTotal(): Int = previousTotal

	override fun setCurrentPage(page: Int) {
		this.page = page
	}

	override fun setPreviousTotal(previousTotal: Int) {
		this.previousTotal = previousTotal
	}

	override fun onCallApi(page: Int, parameter: Int?): Boolean {
		if (page == 1) {
			lastPage = Integer.MAX_VALUE
			sendToView { it.getLoadMore().reset() }
		}
		setCurrentPage(page)
		if (page > lastPage || lastPage == 0 || parameter == null) {
			sendToView { it.hideProgress() }
			return false
		}
		makeRestCall(RestProvider.getUserService().getResponses(parameter, page), Consumer {
			lastPage = it.last
			manageDisposable(it.items.save())
			sendToView { view ->
				view.onNotifyAdapter(it.items, page)
				view.onSetTabCount(it.totalCount)
			}
		})
		return true
	}

	override fun onError(throwable: Throwable) {
		sendToView { it.getLoadMore().parameter?.let { onWorkOffline(it) } }
		super.onError(throwable)
	}

	override fun onWorkOffline(userId: Int) {
		if (responses.isEmpty()) {
			manageDisposable(
					getResponses(userId).toObservable()
							.observe()
							.subscribe { responses -> sendToView {
								it.onNotifyAdapter(responses, 1)
							} }
			)
		} else {
			sendToView { it.hideProgress() }
		}
	}
}