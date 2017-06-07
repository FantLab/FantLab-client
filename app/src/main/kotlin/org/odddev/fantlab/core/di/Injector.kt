package org.odddev.fantlab.core.di

/**
 * @author kenrube
 * *
 * @since 23.08.16
 */

object Injector {

	private var appComponent: AppComponent? = null

	fun init(appComponent: AppComponent) {
		Injector.appComponent = appComponent
	}

	fun getAppComponent(): AppComponent =
		appComponent as? AppComponent ?: throw RuntimeException("AppComponent not initialized yet!")
}
