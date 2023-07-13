package com.example.myrijks.ui.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.example.myrijks.R
import com.example.myrijks.domain.model.error.ErrorEntity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(requireView()).also {
                // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }
    }

inline fun <T : ViewBinding> ViewGroup.viewBinding(factory: (layoutInflater: LayoutInflater, viewGroup: ViewGroup, isAttachToParent: Boolean) -> T) =
    factory(LayoutInflater.from(context), this, false)


fun ErrorEntity.showSnackBar(view: View, retryAction: () -> Unit) {
    val context = view.context
    when (this) {
        ErrorEntity.AccessDenied ->
            showActionableSnackbar(
                view,
                duration = SnackbarDuration.LONG,
                actionText = context.getString(R.string.retry),
                message = context.getString(R.string.access_denied),
                onClick = retryAction
            )

        ErrorEntity.Network, ErrorEntity.ServiceUnavailable ->
            showActionableSnackbar(
                view,
                duration = SnackbarDuration.LONG,
                actionText = context.getString(R.string.retry),
                message = context.getString(R.string.internet_connection_issues_please_check_connection),
                onClick = retryAction
            )

        ErrorEntity.NotFound ->
            showActionableSnackbar(
                view,
                duration = SnackbarDuration.LONG,
                actionText = context.getString(R.string.retry),
                message = context.getString(R.string.article_not_found),
                onClick = retryAction
            )

        ErrorEntity.Unknown ->
            showActionableSnackbar(
                view,
                duration = SnackbarDuration.LONG,
                actionText = context.getString(R.string.retry),
                message = context.getString(R.string.something_went_wrong),
                onClick = retryAction
            )

        null -> {}
    }
}