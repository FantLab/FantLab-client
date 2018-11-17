package ru.fantlab.android.ui.modules.classificator.time

import android.os.Bundle
import ru.fantlab.android.R
import ru.fantlab.android.data.dao.model.ClassificatorModel
import ru.fantlab.android.helper.BundleConstant
import ru.fantlab.android.helper.FantlabHelper
import ru.fantlab.android.provider.storage.ClassificatoriesProvider
import ru.fantlab.android.ui.base.mvp.presenter.BasePresenter

class ClassificationTimePresenter : BasePresenter<ClassificationTimeMvp.View>(),
		ClassificationTimeMvp.Presenter {

	@com.evernote.android.state.State
	var workId: Int? = null
	var classificators: ArrayList<ClassificatorModel>? = null

	override fun onFragmentCreated(bundle: Bundle?) {
		if (bundle?.getInt(BundleConstant.EXTRA) == null) {
			throw NullPointerException("Either bundle or workId is null")
		}
		workId = bundle.getInt(BundleConstant.EXTRA)
		workId?.let { it ->
			classificators = ClassificatoriesProvider.loadClasses(FantlabHelper.ClassificatorTypes.TYPE_TIME.tag).items
			sendToView { it.onInitViews(classificators!!) }
		}
	}

	override fun onError(throwable: Throwable) {
		workId?.let { onWorkOffline(it) }
		super.onError(throwable)
	}

	override fun onWorkOffline(id: Int) {
		sendToView { it.showMessage(R.string.error, R.string.failed_data) }
	}
}