package org.odddev.fantlab.home

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.annotation.IntDef
import org.odddev.fantlab.R
import org.odddev.fantlab.autors.AutorsFragment
import org.odddev.fantlab.award.AwardsFragment
import org.odddev.fantlab.core.layers.router.Router
import org.odddev.fantlab.core.utils.FragmentUtils

/**
 * @author kenrube
 * *
 * @since 11.10.16
 */

internal class NavDrawerRouter(activity: HomeActivity, @IdRes containerId: Int) : Router<HomeActivity>(activity, containerId) {

	@IntDef(R.id.nav_autors.toLong(), R.id.nav_awards.toLong(), R.id.nav_search.toLong(),
			R.id.nav_profile.toLong(), R.id.nav_logout.toLong(), R.id.nav_login.toLong())
	@Retention(AnnotationRetention.SOURCE)
	internal annotation class NAV_DRAWER_ITEM

	@SuppressLint("SwitchIntDef")
	fun routeToNavDrawerItem(@NAV_DRAWER_ITEM item: Int) {
		when (item) {
			R.id.nav_autors -> FragmentUtils.replaceFragment(activity, containerId, AutorsFragment(), false)
			R.id.nav_awards -> FragmentUtils.replaceFragment(activity, containerId, AwardsFragment(), false)
		}
	}
}