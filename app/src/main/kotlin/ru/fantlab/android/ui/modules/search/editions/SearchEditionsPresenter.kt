package ru.fantlab.android.ui.modules.search.editions

import android.view.View
import io.reactivex.Single
import io.reactivex.functions.Consumer
import ru.fantlab.android.R
import ru.fantlab.android.data.dao.model.SearchAuthor
import ru.fantlab.android.data.dao.model.SearchEdition
import ru.fantlab.android.data.dao.response.SearchAuthorsResponse
import ru.fantlab.android.data.dao.response.SearchEditionsResponse
import ru.fantlab.android.provider.rest.DataManager
import ru.fantlab.android.ui.base.mvp.presenter.BasePresenter

class SearchEditionsPresenter : BasePresenter<SearchEditionsMvp.View>(), SearchEditionsMvp.Presenter {

	private var page: Int = 1
	private var previousTotal: Int = 0
	private var lastPage: Int = Integer.MAX_VALUE

	override fun onCallApi(page: Int, parameter: String?): Boolean {
		if (page == 1) {
			lastPage = Integer.MAX_VALUE
			sendToView { it.getLoadMore().reset() }
		}
		setCurrentPage(page)
		if (page > lastPage || lastPage == 0 || parameter == null) {
			sendToView { it.hideProgress() }
			return false
		}
		if (previousTotal == 1000) {
			sendToView {
				it.hideProgress()
				it.showMessage(R.string.error, R.string.results_warning)
			}
			return false
		}
		makeRestCall(
				getEditionsFromServer(parameter, page).toObservable(),
				Consumer { (editions, totalCount, lastPage) ->
					this.lastPage = lastPage
					sendToView {
						with (it) {
							onNotifyAdapter(editions, page)
							onSetTabCount(totalCount)
						}
					}
				}
		)
		return true
	}

	private fun getEditionsFromServer(query: String, page: Int):
			Single<Triple<ArrayList<SearchEdition>, Int, Int>> =
			DataManager.searchEditions(query, page)
					.map { getEditions(it) }

	private fun getEditions(response: SearchEditionsResponse): Triple<ArrayList<SearchEdition>, Int, Int> =
			Triple(response.editions.items, response.editions.totalCount, response.editions.last)

	override fun onItemClick(position: Int, v: View?, item: SearchEdition) {
		sendToView { it.onItemClicked(item) }
	}

	override fun onItemLongClick(position: Int, v: View?, item: SearchEdition) {
	}

	override fun getCurrentPage(): Int = page

	override fun getPreviousTotal(): Int = previousTotal

	override fun setCurrentPage(page: Int) {
		this.page = page
	}

	override fun setPreviousTotal(previousTotal: Int) {
		this.previousTotal = previousTotal
	}
}